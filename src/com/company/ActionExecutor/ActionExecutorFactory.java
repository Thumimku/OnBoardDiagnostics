package com.company.ActionExecutor;

public class ActionExecutorFactory {
    //this factory is used to create various action executor class objects.
    protected ActionExecutor getActionExecutor(String executorType){
        if(executorType==null){
            return null;
        }if(executorType.equalsIgnoreCase("PRINTLINEEXECUTOR")){
            return PrintLineExecutor.getInstance();
        }
        return null;
    }
}
