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

import com.company.helper.XmlHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * This class is used to grep database query from timing log.
 * When an error occur interpreter grep the correlation id of the request from wso2carbon.log.
 * From that correlation ID this class greps db queries which related to particular request.
 * @see com.company.Interpreter
 *
 * Finally it creates DB_Query.txt file which contains all db queries related to particular request.
 * Timing log file path configured in wso2conf.xml file.
 *
 */
public class DatabaseQueryExtracter {

    private RandomAccessFile timingLog; // timing log file.

    private FileChannel timingLogChannel; // file channel for timing log file.

    private long position; // current cursor position in timing log file.

    private final ByteBuffer buffer; // buffer used to read from timing log file.

    private String logLine; // log line in timing log.

    private long filesize; // file size of timing log.

    private StringBuilder queryBuilder; // String builder for concatinate all log lines.

    private String folderPath; // dump folder path.

    private static final String filename = "DB_Query.txt"; // name of db query.

    /**
     * Public constructor with allocated buffer size of 512.
     *
     * @param folderPath - dump folder path.
     */
    public DatabaseQueryExtracter(String folderPath) {

        this.buffer = ByteBuffer.allocate(512);

        if (new File(new XmlHelper().gettimingLogPath()).exists()) {
            try {
                this.timingLog = new RandomAccessFile(new XmlHelper().gettimingLogPath(), "r");
                this.timingLogChannel = timingLog.getChannel();
                this.position = 0;
                this.filesize = 0;
                this.queryBuilder = new StringBuilder();
                this.folderPath = folderPath;

            } catch (FileNotFoundException e) {
                System.out.print("unable to find timing log");
            }
        } else {
            timingLog = null;
            System.out.print("Unable find timing log file. Please add this feature for efficient diagnosis");
        }

    }

    /**
     * This method is used to call by Interpreter after extracts the request id in order to find the db logs.
     * @param requestID - request correlation id
     */
    public void ScanForQuary(String requestID) {

        if (timingLog != null) { // check whether timing log is enabled or not.
            try {
                filesize = timingLog.length(); // assign current timing log file length.
                while (position < filesize) { // make sure to read all log lines that logged until error occur.
                    position = readLines(timingLogChannel, requestID); // read the line and adjust cursor position.
                }
                if (!(queryBuilder.toString().isEmpty())) {
                    // check whether error log line exits in order to dump in dump folder
                    logExtractedQuery(queryBuilder);
                }
            } catch (IOException e) {
                System.out.print("Unable to read timing log file due to "+ e.getMessage());
            }

        }
    }

    /**
     * This method is used to
     * @param fileChannel
     * @param requestID
     * @return
     */
    private long readLines(FileChannel fileChannel, String requestID) {

        long read = 0;
        try {
            read = fileChannel.read(buffer);
            buffer.flip();
            while (buffer.hasRemaining()) {
                String charString = String.valueOf((char) buffer.get());
                logLine = logLine + charString;
                if (charString.compareTo("\n") == 0) {
                    inspectquery(logLine, requestID);
                    logLine = "";
                }

            }

            buffer.clear();
            return fileChannel.position();
        } catch (IOException e) {
            System.out.print("Unable to read from the log file due to : " + e.getMessage());
        }
        return read;
    }

    private void inspectquery(String logLine, String requestID) {

        if (logLine.contains(requestID)) {
            queryBuilder.append(logLine);
        }
    }

    private void logExtractedQuery(StringBuilder queryBuilder) {

        try {
            FileWriter writer = new FileWriter(folderPath + "/" + filename,true);
            writer.write(queryBuilder.toString());
            writer.close();
        } catch (IOException e) {
            System.out.print("Unable to write db query file.");
        }
    }

}
