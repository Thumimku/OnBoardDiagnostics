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

    //Initiate DocumentBuilder
    private static DocumentBuilder documentBuilder;
    //Initiate Configure xml Document and regex document
    private static Document confDocument, regexDocument;
    //static string regex;
    public static String generalInfoRegex;
    //static string regex;
    public static String generalErrorRegex;

    public static String PIdFilePath;

    //static string regex;
    public static String CorrelationLogPath;

    public static String TimeRegex;

    public static void parsingData() {

        try {
            //Initiate DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            normaliseRegexFile();
            normaliseconfFile();
            generalInfoRegex = regexDocument.getElementsByTagName("GeneralInfoRegex").item(0).getTextContent();
            generalErrorRegex = regexDocument.getElementsByTagName("GeneralErrorRegex").item(0).getTextContent();


            PIdFilePath = confDocument.getElementsByTagName("pidpath").item(0).getTextContent();
            CorrelationLogPath = confDocument.getElementsByTagName("correlationtimingPath").item(0).getTextContent();
            TimeRegex = regexDocument.getElementsByTagName("timeRegex").item(0).getTextContent();

        } catch (ParserConfigurationException e) {
            System.out.print("Unable to parse xml files");
        }
    }

    public XmlHelper() {

        try {
            //Initiate DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            normaliseRegexFile();
            normaliseconfFile();
        } catch (ParserConfigurationException e) {
            System.out.print("Unable to parse xml files");
        }
    }

    //Address the Configure xml file

    static File confFile = new File(System.getProperty("user.dir") + "/src/main/resources/wso2conf.xml");
    //Address the pattern xml file
    static File patternFile = new File(System.getProperty("user.dir") + "/src/main/resources/RegExPattern.xml");

    /**
     * This method used to normalise the conf file.
     */
    private static void normaliseconfFile() {

        try {
            confDocument = documentBuilder.parse(confFile);
            confDocument.getDocumentElement().normalize();
        } catch (IOException e) {
            System.out.print("Unable to parse wso2 conf xml file. Please Check the path and permission to access the file.");
        } catch (SAXException e) {
            System.out.print("Unable to parse wso2 conf xml file. Please Check the path and permission to access the file.");

        }

    }

    /**
     * This method used to normalise the regex file.
     */
    private static void normaliseRegexFile() {

        try {
            regexDocument = documentBuilder.parse(patternFile);
            regexDocument.getDocumentElement().normalize();
        } catch (SAXException e) {
            System.out.print("Unable to parse regex xml file. Please Check the path and permission to access the file.");

        } catch (IOException e) {
            System.out.print("Unable to parse regex xml file. Please Check the path and permission to access the file.");

        }
    }

    /**
     * Used to get path of wso2 carbon log file.
     *
     * @return String - Path of WSO2carbon.log
     */
    public String getLogFilePath() {

        return confDocument.getElementsByTagName("logPath").item(0).getTextContent();
    }

    /**
     * Used to get path of wso2 java pid.
     *
     * @return String - Path of wso2carbon.pid
     */
    public String getPidFilePath() {

        return confDocument.getElementsByTagName("pidpath").item(0).getTextContent();
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
    public static String getGeneralInfoRegEx() {

        return regexDocument.getElementsByTagName("GeneralInfoRegex").item(0).getTextContent();

    }

    /**
     * Used to get oom error regex pattern.
     *
     * @return String - oom error Regex pattern
     */
    public String getOomErrorRegEx() {

        return regexDocument.getElementsByTagName("OomErrorRegex").item(0).getTextContent();
    }

    /**
     * Used to get file path of timing Log.
     *
     * @return String - timing Log path.
     */
    public String gettimingLogPath() {

        return confDocument.getElementsByTagName("timingPath").item(0).getTextContent();
    }





}
