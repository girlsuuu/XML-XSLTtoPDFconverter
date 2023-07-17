# XML-XSLTtoPDFconverter
Generate a PDF file with input as XML and XSLT files  

## Function

- upload a xml file and a xsl file to generate a PDF file on a webpage

## Demo Online

​	 	 [http://82.156.161.150:3000/front](http://82.156.161.150:3000/front)

## Preparation

- Java 17 or higher

- Maven

- node.js

- npm  

   ```npm install express```

   `npm install multer`

   `npm install cors`

   `npm install iconv-lite`

## How To Run

```git clone https://github.com/girlsuuu/XML-XSLTtoPDFconverter.git```

``` cd XML-XSLTtoPDFconverter```

Run the application :

```node nodeapp.js```

Visit the web on your browser with url :

 [http://localhost:3000/front](http://localhost:3000/front)

## XML File Example

```xml
<?xml version="1.0" encoding="utf-8"?>
<note>
    <to>Nancy</to>
    <from>Jake</from>
    <heading>Reminder</heading>
    <body>Don't forget me this weekend!</body>
</note>
```

 This file is simply for a test  to convert.  If in lack of your own xml file,  save this xml file  as an example in an optional position on your computer for later upload.

## XSLT File Example

```xsl
<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-portrait"
                                       page-height="29.7cm" page-width="21.0cm" margin="2cm">
                    <fo:region-body/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4-portrait">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        Hello, <xsl:value-of select="note/to"/>!
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
```

 Just as the former xml file, this file is simply for a test  to convert.  If in lack of your own xsl file,  save this xsl file  as an example in an optional position on your computer for later upload.

## Expected Output

```pdf
Hello, Nancy!
```

## Thanks

[https://github.com/thelensky/xmlToPdfGenerator](https://github.com/thelensky/xmlToPdfGenerator)

Apply as a reference from this repository with some improvement.
