package com.company;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Typical Java Main class.
 */
public class Main {

    public static void main(String[] args) throws IOException {

//        System.out.print("................Loading  OnBoard Diagnostics Tool For WSO2 IS.............\n\n");
//        System.out.print("......Loading Log File path Configuration data from wso2conf.xml .........\n\n");
//        XmlHelper.parsingData();
//        System.out.print(" wso2carbon.log File path :- " + new XmlHelper().getLogFilePath() + "\n");
//        System.out.print(" wso2carbon.pid File path :- " + XmlHelper.PIdFilePath + "\n");
//        System.out.print("......Loading Regex Patterns from RegExPattern.xml........................\n\n");
//
//        //json parser to parse the ErrorConf.json  file
//        JSONParser parser = new JSONParser();
//
//        //create new errorinfo instance to load error information.
//        try {
//            new ErrorInfo((JSONArray) parser.parse(new FileReader(
//                    System.getProperty("user.dir") + "/src/main/resources/ErrorConf.json")));
//
//        } catch (IOException e) {
//            System.out.print(e.getMessage());
//        } catch (ParseException e) {
//            System.out.print(e.getMessage());
//        }
//        //Initiate tailer class to tail the file
//        //Xml helper provide path location from xml file
//        //LogTailReader is the implementation of the tailer class.
//        new Tailer(new File(new XmlHelper().getLogFilePath()), new LogTailReader()).run();


//        TreeNode<String> treeRoot = SampleData.getSet1();
//        for (TreeNode<String> node : treeRoot) {
//            System.out.println( node.data);
//        }
        JSONParser parser = new JSONParser();
                try {
                   JSONObject jsonObject =(JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/resources/RegexTree.json"));
//                   ErrorRegexTree.createroot(jsonObject);
//                   System.out.print(ErrorRegexTree.root.id+"\n");
//                    Set<ArrayList<String>> keys = ErrorRegexTree.root.children.keySet();
//                    for(ArrayList<String> key: keys){
//                        System.out.println("Value of "+key.toString() +" is: "+ErrorRegexTree.root.children.get(key) +"  "+ErrorRegexTree.root.children.get(key).diagnosis.toString()+"\n" );
//                    }
                    ErrorRegexTree.root =ErrorRegexTree.expandTree(jsonObject);
//                    ErrorRegexTree.checkTree(ErrorRegexTree.root);

                    StringBuilder sb = new StringBuilder();
                    sb.append("TID: [-1234] [] [2018-09-26 10:18:22,517] ERROR {org.apache.catalina.core.StandardWrapperValve} -  Servlet.service() for servlet [jsp] in context with path [/STRATOS_ROOT] threw exception [An exception occurred processing JSP page /hello.jsp at line 7\n" +
                            "\n" +
                            "4:    <body>\n" +
                            "5:       Hello World!<br/>\n" +
                            "6:       <%\n" +
                            "7:          int[] arr = new int[Integer.MAX_VALUE];\n" +
                            "8:       %>\n" +
                            "9:    </body>\n" +
                            "10: </html>\n" +
                            "\n PoolExhaustedException" +
                            "\n" +
                            "Stacktrace:] with root cause \n" +
                            "java.lang.OutOfMemoryError: Requested array size exceeds VM limit\n" +
                            "\tat org.apache.jsp.hello_jsp._jspService(hello_jsp.java:89)\n" +
                            "\tat org.apache.jasper.runtime.HttpJspBase.service(HttpJspBase.java:70)\n" +
                            "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:731)\n" +
                            "\tat org.apache.jasper.servlet.JspServletWrapper.service(JspServletWrapper.java:439)\n" +
                            "\tat org.apache.jasper.servlet.JspServlet.serviceJspFile(JspServlet.java:395)\n" +
                            "\tat org.apache.jasper.servlet.JspServlet.service(JspServlet.java:339)\n" +
                            "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:731)\n" +
                            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\n" +
                            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n" +
                            "\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)\n" +
                            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n" +
                            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n" +
                            "\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:219)\n" +
                            "\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:110)\n" +
                            "\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:169)\n" +
                            "\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:103)\n" +
                            "\tat org.wso2.carbon.identity.context.rewrite.valve.TenantContextRewriteValve.invoke(TenantContextRewriteValve.java:80)\n" +
                            "\tat org.wso2.carbon.identity.authz.valve.AuthorizationValve.invoke(AuthorizationValve.java:91)\n" +
                            "\tat org.wso2.carbon.identity.auth.valve.AuthenticationValve.invoke(AuthenticationValve.java:65)\n" +
                            "\tat org.wso2.carbon.tomcat.ext.valves.CompositeValve.continueInvocation(CompositeValve.java:99)\n" +
                            "\tat org.wso2.carbon.tomcat.ext.valves.CarbonTomcatValve$1.invoke(CarbonTomcatValve.java:47)\n" +
                            "\tat org.wso2.carbon.webapp.mgt.TenantLazyLoaderValve.invoke(TenantLazyLoaderValve.java:57)\n" +
                            "\tat org.wso2.carbon.tomcat.ext.valves.TomcatValveContainer.invokeValves(TomcatValveContainer.java:47)\n" +
                            "\tat org.wso2.carbon.tomcat.ext.valves.CompositeValve.invoke(CompositeValve.java:62)\n" +
                            "\tat org.wso2.carbon.tomcat.ext.valves.CarbonStuckThreadDetectionValve.invoke(CarbonStuckThreadDetectionValve.java:159)\n" +
                            "\tat org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:962)\n" +
                            "\tat org.wso2.carbon.tomcat.ext.valves.CarbonContextCreatorValve.invoke(CarbonContextCreatorValve.java:57)\n" +
                            "\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\n" +
                            "\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:445)\n" +
                            "\tat org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1115)\n" +
                            "\tat org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:637)\n" +
                            "\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1775)");
                    ErrorRegexTree.findDiagnosis(sb);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        } catch (ParseException e) {
            System.out.print(e.getMessage());
        }

    }
}
