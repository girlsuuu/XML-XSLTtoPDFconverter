package ru.thelensky;

import org.apache.fop.apps.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfFromFOP {
    public static void exec(String xmlFileName, String xslFileName) {
        try {
            Path path = Paths.get(System.getProperty("user.dir"));
            File cwd = new File(System.getProperty("user.dir"));

            File xmlFile = path.resolve(xmlFileName).toFile();
            File xslFile = path.resolve(xslFileName).toFile();
            File pdfFile = new File(cwd, "result.pdf");

            final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            OutputStream outputStream = new FileOutputStream(pdfFile);
            outputStream = new BufferedOutputStream(outputStream);
            try {
                // Construct fop with desired output format
                Fop fop;
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outputStream);

                // Setup XSLT
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslFile));

                // Setup input for XSLT transformation
                Source source = new StreamSource(xmlFile);

                // Resulting SAX events (the generated FO) must be piped through to FOP
                Result result = new SAXResult(fop.getDefaultHandler());

                // Start XSLT transformation and FOP processing
                transformer.transform(source, result);

            } catch (FOPException | TransformerException e) {
                e.printStackTrace();
            } finally {
                outputStream.close();
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }


    }
}
