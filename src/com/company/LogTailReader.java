package com.company;


import com.company.helper.XmlHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**




 */
public class LogTailReader {
    //this class used to read carbon log file



    //Initiate Log file path
    private String logFilePath;
    //Initiate logFile length
    private Long logFileLength;
    //Initiate int charRead
    private int charRead;
    //Initiate Line Builder;
    private String lineBuilder;
    //Initiate MatchRule Engine
    private final MatchRuleEngine matchRuleEngine;



    //constructor - set initial Lof file length 0 and set Log file path;
    public LogTailReader() {
        logFilePath = new XmlHelper().getLogFilePath();
        logFileLength = 0L;
        charRead = 0;
        lineBuilder = "";
        matchRuleEngine = new MatchRuleEngine();

    }



    public void tailfile() {
        //Method used to tail the log file
        try {
            if (logFilePath != null) {
                RandomAccessFile logFile = new RandomAccessFile(logFilePath, "r");
                logFileLength = logFile.length();
                System.out.print(logFile.length() + "\n");
                FileChannel fileChannel = logFile.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(512);


                while (true) {
                    if (logFile.length() > logFileLength) {
                        //Lines added.

                        logFile.seek(logFileLength);
                        //skip for last read position
                        charRead = fileChannel.read(byteBuffer);
                        while (charRead != -1) {
                            logFileLength = logFile.length();
                            byteBuffer.flip();

                            while (byteBuffer.hasRemaining()) {
                                String charString = String.valueOf((char) byteBuffer.get());
                                lineBuilder = lineBuilder + charString;
                                if (charString.compareTo("\n") == 0) {
                                    matchRuleEngine.validateTestline(lineBuilder);
                                    lineBuilder = "";

                                }
                            }

                            byteBuffer.clear();
                            charRead = fileChannel.read(byteBuffer);
                        }


                    } else if (logFile.length() < logFileLength) {
                        //file lines deleted need to reset.


                        logFileLength = 0L;
                        logFile.seek(logFileLength);
                        //skip to initial positoin
                        charRead = fileChannel.read(byteBuffer);
                        while (charRead != -1) {
                            logFileLength = logFile.length();
                            byteBuffer.flip();

                            while (byteBuffer.hasRemaining()) {
                                String charString = String.valueOf((char) byteBuffer.get());
                                lineBuilder = lineBuilder + charString;
                                if (charString.compareTo("\n") == 0) {
                                    matchRuleEngine.validateTestline(lineBuilder);
                                    lineBuilder = "";

                                }
                            }

                            byteBuffer.clear();
                            charRead = fileChannel.read(byteBuffer);
                        }

                    }


                }

            }
        } catch (FileNotFoundException e) {
            System.out.print("File Not Found. Please Check the path");

        } catch (IOException e) {
            System.out.print("IO error occurred.");

        }
    }





}
