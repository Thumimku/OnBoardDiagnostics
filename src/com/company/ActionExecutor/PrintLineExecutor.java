package com.company.ActionExecutor;

public class PrintLineExecutor implements ActionExecutor {
    private static PrintLineExecutor printLineExecutor;
    private PrintLineExecutor() {
    }
    //User to create singleton printline executor
    public static synchronized PrintLineExecutor getInstance(){
        if (printLineExecutor==null){
            printLineExecutor=new PrintLineExecutor();
        }
        return printLineExecutor;
    }
    //override method used to print the logLine
    @Override
    public void execute(StringBuilder logLine) {
        System.out.println(logLine);
    }
}
