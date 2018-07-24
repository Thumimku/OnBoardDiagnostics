package com.company;

public class MatchRuleEngine {
    private String generalRegex;
    private static   MatchRuleEngine matchRuleEngine;

    private MatchRuleEngine() {
        generalRegex=new XmlHelper().getGeneralErrorRegEx();
    }
    public static synchronized MatchRuleEngine getInstance(){
        if(matchRuleEngine==null){
            matchRuleEngine=new MatchRuleEngine();
        }
        return matchRuleEngine;
    }

    public boolean checkinitialmatch(String testline){
        if(testline.matches(generalRegex)){
            return true;
        }else{
            return false;
        }
    }
}
