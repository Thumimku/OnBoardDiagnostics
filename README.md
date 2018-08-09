# OnBoard Diagnostics Tool for IS

## About

This tool is designed to diagnose errors and malicious flows in WSO2-Identity Server. It is a standalone application with small memory foot print (%CPU – 6 % and %MEM – 3.4 %). Currently in developing phase.

## Requirement

1. Java 8
2. IDE (Inteillj - not necessary )

## Current Progress

- Application can detect error logs in real time and write it in a seperate file for further research.
- Whenever error detected application do `Thread Dump` and save thread dumps in same folder of log line.
- Finally application zip the entire folder of error log and thread dumps.

## Configure wso2conf.xml

Inorder to run the application first program needs wsocarbon.log path and wso2carbon.pid path
- Set wso2carbon.log path
  - open wso2conf.xml (src/resources/wsp2conf.xml)
  - Replace the path with your wso2carbon.log file path (<IS_HOME>/repository/logs/wso2carbon.log)
- Set wso2carbon.pid path
  - open wso2conf.xml (src/resources/wsp2conf.xml)
  - Replace the path with your wso2carbon.pid file path (<IS_HOME>/wso2carbon.pid)

