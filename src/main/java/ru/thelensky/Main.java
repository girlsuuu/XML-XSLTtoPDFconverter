package ru.thelensky;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<String, String> mapArgs;

        if (args.length > 0) {
            mapArgs = new HashMap();
            Arrays.stream(args).forEach(arg -> {
                String[] entity = arg.split("=");
                if(entity[0].equals("xml") || entity[0].equals("xsl")){
                    mapArgs.put(entity[0], entity[1]);
                }
            });

            PdfFromFOP.exec(mapArgs.get("xml"), mapArgs.get("xsl"));
        }
    }
}
