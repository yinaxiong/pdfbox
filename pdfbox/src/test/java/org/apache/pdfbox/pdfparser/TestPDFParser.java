/*****************************************************************************
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 ****************************************************************************/

package org.apache.pdfbox.pdfparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TestPDFParser
{

    private static final String PATH_OF_PDF = "src/test/resources/input/yaddatest.pdf";
    private static File tmpDirectory = new File(System.getProperty("java.io.tmpdir"));

    private int numberOfTmpFiles = 0;

    /**
     * Initialize the number of tmp file before the test
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception
    {
        numberOfTmpFiles = getNumberOfTempFile();
    }

    /**
     * Count the number of temporary files
     * 
     * @return
     */
    private int getNumberOfTempFile()
    {
        int result = 0;
        File[] tmpPdfs = tmpDirectory.listFiles(new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return name.startsWith(COSParser.TMP_FILE_PREFIX)
                        && name.endsWith("pdf");
            }
        });

        if (tmpPdfs != null)
        {
            result = tmpPdfs.length;
        }

        return result;
    }

    @Test
    public void testPDFParserString() throws Exception
    {
        PDFParser pdfParser = new PDFParser(PATH_OF_PDF);
        executeParserTest(pdfParser);
    }

    @Test
    public void testPDFParserFile() throws IOException
    {
        PDFParser pdfParser = new PDFParser(new File(PATH_OF_PDF));
        executeParserTest(pdfParser);
    }

    @Test
    public void testPDFParserInputStream() throws IOException
    {
        PDFParser pdfParser = new PDFParser(new FileInputStream(PATH_OF_PDF));
        executeParserTest(pdfParser);
    }

    @Test
    public void testPDFParserStringScratchFile() throws Exception
    {
        PDFParser pdfParser = new PDFParser(PATH_OF_PDF, true);
        executeParserTest(pdfParser);
    }

    @Test
    public void testPDFParserFileScratchFile() throws IOException
    {
        PDFParser pdfParser = new PDFParser(new File(PATH_OF_PDF), true);
        executeParserTest(pdfParser);
    }

    @Test
    public void testPDFParserInputStreamScratchFile() throws IOException
    {
        PDFParser pdfParser = new PDFParser(new FileInputStream(PATH_OF_PDF), true);
        executeParserTest(pdfParser);
    }

    private void executeParserTest(PDFParser pdfParser) throws IOException
    {
        pdfParser.parse();
        assertNotNull(pdfParser.getDocument());
        // number tmp file must be the same
        assertEquals(numberOfTmpFiles, getNumberOfTempFile());
    }

}
