package com.company;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlHelper {
    //this class is used to interact with xml files

    public XmlHelper() {
    }
    //Address the Configure xml file
    private final File ConfFile= new File(System.getProperty("user.dir")+"/conf/wso2conf.xml");


    protected  String getLogFilePath(){
        // this method is used to get file path from Configure xml file
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(ConfFile);
            doc.getDocumentElement().normalize();


            return doc.getElementsByTagName("path").item(0).getTextContent();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        }

    }
}
