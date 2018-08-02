package com.company.helper;
/*
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
 * XmlHelper class used to read Strings in xml files.
 * wso2conf.xml file is used to declare the initial configuration for the application like configure path to log files.
 * RegExPattern.xml file is used to keep regex pattern for the MatchRule Engine.
 *
 * @author thumilan@wso2.com
 */
public class XmlHelper {

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
            // Ignore
        }
    }


    //Address the Configure xml file
    private final File confFile = new File(System.getProperty("user.dir") + "/conf/wso2conf.xml");
    //Address the pattern xml file
    private final File patternFile = new File(System.getProperty("user.dir") + "/conf/RegExPattern.xml");

    /**
     * This method used to normalise the conf file.
     */
    private void normaliseconfFile() {

        try {
            confDocument = documentBuilder.parse(confFile);
            confDocument.getDocumentElement().normalize();
        } catch (SAXException e) {
            // Ignore
        } catch (IOException e) {
            // Ignore
        }


    }

    /**
     * This method used to normalise the regex file.
     */
    private void normaliseRegexFile() {

        try {
            regexDocument = documentBuilder.parse(patternFile);
            regexDocument.getDocumentElement().normalize();
        } catch (SAXException e) {
            //Ignore
        } catch (IOException e) {
            //Ignore
        }
    }

    /**
     * Used to get path of wso2 carbon log file
     *
     * @return String - Path of WSO2carbon.log
     */
    public String getLogFilePath() {

        return confDocument.getElementsByTagName("path").item(0).getTextContent();
    }

    /**
     * Used to get general error regex pattern.
     *
     * @return String - general error Regex pattern
     */
    public String getGeneralErrorRegEx() {

        return regexDocument.getElementsByTagName("GeneralErrorRegex").item(0).getTextContent();
    }

    /**
     * Used to get general info regex pattern.
     *
     * @return String - general error Regex pattern
     */
    public String getGeneralInfoRegEx() {

        return regexDocument.getElementsByTagName("GeneralInfoRegex").item(0).getTextContent();

    }
}
