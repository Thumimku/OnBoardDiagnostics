package com.company;

import com.company.ActionExecutor.ActionExecutorConnector;
import com.company.Helper.XmlHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

 public class MatchRuleEngine {
     //this class is used match the test line with the regex
    private String generalErrorRegex;// general error regex
    private String generalInfoRegex;//general info regex
    private Pattern pattern;
    private Matcher matcher;
    private Boolean hasEngineApproved;// check whether the line is eligible or not

    private StringBuilder LogLine;// stringBuilder used to build LogLine

    private ActionExecutorConnector actionExecutorConnector;// connector used to connect the action executor

    public MatchRuleEngine() {
        hasEngineApproved=false;
        generalInfoRegex=new XmlHelper().getGeneralInfoRegEx();
        generalErrorRegex=new XmlHelper().getGeneralErrorRegEx();
        actionExecutorConnector= new ActionExecutorConnector();

    }


    private boolean checkInitialErrorMatch(String testline){
        // check the testline with error regex
        pattern=Pattern.compile(generalErrorRegex);
        matcher=pattern.matcher(testline);
        return matcher.find();
    }
    private boolean checkInitialInfoMatch(String testline){
        // check the testline with info regex
        pattern=Pattern.compile(generalInfoRegex);
        matcher=pattern.matcher(testline);
        return matcher.find();
    }
    public void validateTestline(String testline){
        //this method is used to validate the test line
        if (hasEngineApproved){
            if(checkInitialInfoMatch(testline)){
                hasEngineApproved=false;
                actionExecutorConnector.printexecution(LogLine);

            }else{
                //hasEngineApproved remain true
                
                LogLine=LogLine.append(testline);

                //what if command end in parse mode how to get Log line???
            }


        }else{
            if(checkInitialErrorMatch(testline)){
                hasEngineApproved=true;
                LogLine=new StringBuilder();
                LogLine=LogLine.append(testline);


            }
        }
    }
}
