package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchRuleEngine {
    private String generalErrorRegex;
    private String generalInfoRegex;
    private static   MatchRuleEngine matchRuleEngine;
    private Pattern pattern;
    private Matcher matcher;
    private Boolean parsemode;

    private MatchRuleEngine() {
        parsemode=false;
    }
    public static synchronized MatchRuleEngine getInstance(){
        if(matchRuleEngine==null){
            matchRuleEngine=new MatchRuleEngine();
        }
        return matchRuleEngine;
    }

    private boolean checkInitialErrorMatch(String testline){
        generalErrorRegex=new XmlHelper().getGeneralErrorRegEx();
        pattern=Pattern.compile(generalErrorRegex);
        matcher=pattern.matcher(testline);
        if(matcher.find()){
            return true;
        }else{
            return false;
        }
    }
    private boolean checkInitialInfoMatch(String testline){
        generalInfoRegex=new XmlHelper().getGeneralInfoRegEx();
        pattern=Pattern.compile(generalInfoRegex);
        matcher=pattern.matcher(testline);
        if(matcher.find()){
            return true;
        }else{
            return false;
        }
    }
    public void validateTestline(String testline){
        if (parsemode==true){
            if(checkInitialInfoMatch(testline)){
                parsemode=false;

            }else{
                //parsemode remain true
                System.out.println(testline);
            }


        }else{
            if(checkInitialErrorMatch(testline)){
                parsemode=true;
                System.out.println(testline);


            }else{
                //parsemode remain false
            }
        }
    }
}
