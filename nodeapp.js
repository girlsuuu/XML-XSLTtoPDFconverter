const express = require("express");
const multer = require("multer");
const { exec } = require("child_process");
const fs = require("fs");
const path = require("path");
const cors = require("cors");
const app = express();
const upload = multer({ dest: "uploads/" });
const iconv = require("iconv-lite");

app.use(cors());

let pdfFile = null;

let isPdfReady = false; // 这是你的标志变量

app.post(
  "/convert",
  upload.fields([
    { name: "xmlFile", maxCount: 1 },
    { name: "xsltFile", maxCount: 1 },
  ]),
  (req, res) => {
    const xmlFile = req.files["xmlFile"][0].path;
    const xsltFile = req.files["xsltFile"][0].path;

    exec(
      `java -jar target/pdf-from-xml-1.0-SNAPSHOT-spring-boot.jar xml=${xmlFile} xsl=${xsltFile}`,
      (error, stdout, stderr) => {
        if (error) {
          console.error(`exec error: ${error}`);
          return;
        }
        console.log(`stdout: ${stdout}`);
        console.error(`stderr: ${stderr.toString("GBK")}`);

        // 假设生成的PDF文件名是output.pdf，位于当前目录下
        pdfFile = path.join(__dirname, "result.pdf");

        // 打印一些调试信息
        console.log(`PDF file: ${pdfFile}`);
        fs.access(pdfFile, fs.constants.F_OK, (err) => {
          console.log(`PDF file ${err ? "does not exist" : "exists"}`);
          isPdfReady = !err; // 如果文件存在，设置标志为 true
        });
      }
    );

    res.send("Files uploaded!");
  }
);
app.get("/front", function (req, res) {
  res.sendFile(path.join(__dirname, "front.html"));
});

app.get("/download", function (req, res) {
  if (!isPdfReady) {
    res.status(503).send("PDF file is not ready yet, please try again later");
    return;
  }

  if (pdfFile) {
    res.setHeader("Content-Type", "application/pdf");
    res.setHeader("Content-Disposition", "attachment; filename=result.pdf");

    let fileStream = fs.createReadStream(pdfFile);
    fileStream.pipe(res);
  } else {
    res.send("No PDF file available");
  }
});

app.listen(3000, () => {
  console.log("App is listening on port 3000");
});
