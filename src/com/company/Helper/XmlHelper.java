package com.company.Helper;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlHelper {
    //this class is used to interact with xml files

    //Initiate DocumentBuilderFactory
    private DocumentBuilderFactory documentBuilderFactory;
    //Initiate DocumentBuilder
    private DocumentBuilder documentBuilder;
    //Initiate Configure xml Document
    private Document Confdocument;
    //Initiate Regex xmll Document
    private Document Regexdocument;


    public XmlHelper() {

        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            normaliseRegexFile();
            normaliseConfFile();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }



    //Address the Configure xml file
    private final File ConfFile= new File(System.getProperty("user.dir")+"/conf/wso2conf.xml");
    //Address the pattern xml file
    private final File PatternFile= new File(System.getProperty("user.dir")+"/conf/RegExPattern.xml");

    //this method is used to normalize Configure file
    private void normaliseConfFile(){
        try {
            Confdocument = documentBuilder.parse(ConfFile);
            Confdocument.getDocumentElement().normalize();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //this method is used to normalize Regex File
    private void normaliseRegexFile(){
        try {
            Regexdocument= documentBuilder.parse(PatternFile);
            Regexdocument.getDocumentElement().normalize();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // this method is used to get file path from Configure xml file
    public  String getLogFilePath(){
        return Confdocument.getElementsByTagName("path").item(0).getTextContent();



    }
    // this method is used to get general error regex from RegExPatter xml file
    public String getGeneralErrorRegEx(){

        return Regexdocument.getElementsByTagName("GeneralErrorRegex").item(0).getTextContent();

    }
    // this method is used to get general info regex from RegExPatter xml file
    public  String getGeneralInfoRegEx(){
        return Regexdocument.getElementsByTagName("GeneralInfoRegex").item(0).getTextContent();

    }
}
