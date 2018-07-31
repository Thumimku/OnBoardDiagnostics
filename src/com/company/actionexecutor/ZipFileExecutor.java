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
 * This class used to save the log line into text file name by timestamp.
 * It also zip the text file.
 * @author thumilan@wso2.com
 */
public class ZipFileExecutor implements ActionExecutor {
    private static ZipFileExecutor zipFileExecutor;

    //Address the Configure xml file
    private String logDirpath = (System.getProperty("user.dir") + "/log/");

    //User to create singleton printline executor
    public static synchronized ZipFileExecutor getInstance() {
        if (zipFileExecutor == null) {
            zipFileExecutor = new ZipFileExecutor();
        }
        return zipFileExecutor;
    }

    //override method used to print the logLine
    @Override
    public void execute(StringBuilder logLine) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String logFilepath = timestamp.toString();
        String zipLogFilepath = logDirpath.concat(logFilepath).concat(logFilepath.concat(".zip"));
        String txtLogFilepath = logFilepath.concat(".txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipLogFilepath);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            zipOutputStream.putNextEntry(new ZipEntry(txtLogFilepath));

            byte[] bytes = logLine.toString().getBytes();
            zipOutputStream.write(bytes , 0 , bytes.length);
            zipOutputStream.closeEntry();
            zipOutputStream.close();

        } catch (IOException e) {

        }


    }




}
