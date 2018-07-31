package com.company.helper;
/**
 * Copyright (c) 2005-2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This helps to read xml file.
 * Config file and Regex files used to read by this class to extract the certain data
 * @author thumilan@wso2.com
 */
public class XmlHelper {
    //this class is used to interact with xml files

    //Initiate DocumentBuilderFactory
    private DocumentBuilderFactory documentBuilderFactory;
    //Initiate DocumentBuilder
    private DocumentBuilder documentBuilder;
    //Initiate Configure xml Document
    private Document confDocument;
    //Initiate Regex xml Document
    private Document regexDocument;


    public XmlHelper() {

        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            normaliseRegexFile();
            normaliseconfFile();
        } catch (ParserConfigurationException e) {
            
        }
    }



    //Address the Configure xml file
    private final File confFile = new File(System.getProperty("user.dir") + "/conf/wso2conf.xml");
    //Address the pattern xml file
    private final File patternFile = new File(System.getProperty("user.dir") + "/conf/RegExPattern.xml");

    //this method is used to normalize Configure file
    private void normaliseconfFile() {
        try {
            confDocument = documentBuilder.parse(confFile);
            confDocument.getDocumentElement().normalize();
        } catch (SAXException e) {

        } catch (IOException e) {

        }


    }
    //this method is used to normalize Regex File
    private void normaliseRegexFile() {
        try {
            regexDocument = documentBuilder.parse(patternFile);
            regexDocument.getDocumentElement().normalize();
        } catch (SAXException e) {

        } catch (IOException e) {

        }


    }

    // this method is used to get file path from Configure xml file
    public  String getLogFilePath() {
        return confDocument.getElementsByTagName("path").item(0).getTextContent();



    }
    // this method is used to get general error regex from RegExPatter xml file
    public String getGeneralErrorRegEx() {

        return regexDocument.getElementsByTagName("GeneralErrorRegex").item(0).getTextContent();

    }
    // this method is used to get general info regex from RegExPatter xml file
    public  String getGeneralInfoRegEx() {
        return regexDocument.getElementsByTagName("GeneralInfoRegex").item(0).getTextContent();

    }
}
