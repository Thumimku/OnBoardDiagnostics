package com.company.ActionExecutor;

public class ActionExecutorConnector {
    //connector class used to connect ActionExecutors
    private ActionExecutorFactory actionExecutorFactory;
    private ActionExecutor actionExecutor;
    public ActionExecutorConnector() {
        actionExecutorFactory = new ActionExecutorFactory();
    }
    //connect with printExecutor
    public void printexecution(StringBuilder logLine){
        actionExecutor=actionExecutorFactory.getActionExecutor("printlineexecutor");
        actionExecutor.execute(logLine);
    }
}
