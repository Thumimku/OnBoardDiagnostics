package com.company.actionexecutor;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ZipLineExecutor used to print the logLines into the console.
 * Currently this class designed as a Singleton class inorder to optimise CPU usage.
 * Further research is required to change the design.
 *
 * ActionExecutor interface is implemented for code reuse.
 * @see ActionExecutor
 * @author thumilan@wso2.com
 */
public class ZipFileExecutor implements ActionExecutor {

    private static ZipFileExecutor zipFileExecutor; // static instance for singleton method.

    /**
     *private Constructor.
     */
    private ZipFileExecutor() {
    }


    private String logDirpath = (System.getProperty("user.dir") + "/log/"); //Refers the log Folder.

    /**
     * Singleton method used to create object.
     * @return PrintLineExecutor
     */
     static synchronized ZipFileExecutor getInstance() {

        if (zipFileExecutor == null) { // Return new instance if current object is null.
            zipFileExecutor = new ZipFileExecutor();
        }
        return zipFileExecutor;
    }

    /**
     * override method used to print the logLines.
     * @param logLine the line
     */
    @Override
    public void execute(StringBuilder logLine) {


        String logFilepath =  new Timestamp(System.currentTimeMillis()).toString(); //Get current time stamp

        //Set the location of the zip file
        String zipLogFilepath = logDirpath.concat(logFilepath).concat(logFilepath.concat(".zip"));

        String txtLogFilepath = logFilepath.concat(".txt"); //Set the text file path into the zip file
        try {
            // FileOutputStream to create zipFile
            FileOutputStream fileOutputStream = new FileOutputStream(zipLogFilepath);
            // ZipOutputStream to create zipFile
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            // Create text file path into the zip file
            zipOutputStream.putNextEntry(new ZipEntry(txtLogFilepath));

            byte[] bytes = logLine.toString().getBytes(); // Change logLine into byte array.
            zipOutputStream.write(bytes , 0 , bytes.length);
            zipOutputStream.closeEntry();
            zipOutputStream.close();

        } catch (IOException e) {
            // Ignore
        }


    }




}
