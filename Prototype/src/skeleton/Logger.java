package skeleton;

import java.lang.System;

public class Logger {

    /**
     * Logolas be es kikapcsolasa
     */
    private static boolean isLogging = false;

    /**
     * tabulátorok száma a hívások során
     */
    private static int stackDebth = 0;


    public static void setLogState(boolean logging)
    {
        isLogging = logging;
    }

    /** 
     * Formázó függvény a logoláshoz
     * @param obj objektum aminek a függvényét hívjuk
     * @param funcName függvény neve amit hívunk
     * @param comment komment a hívással kapcsolatban
     */
    public static void startFunctionLogComment(Object obj, String funcName, String comment) {

        if(!isLogging) return;

        for (int i = 0; i < stackDebth; i++)
            System.out.print("\t");

        String str =  "" + obj.getClass().getSimpleName() + "."  + funcName + "()";

        if (!comment.equals(""))
            str += (" -- " + comment);

        System.out.println(str);
        stackDebth++;
    }

    /** 
     * tabok csökkentése a hívás vége után
     */
    public static void endFunctionLog() {
        if(!isLogging) return;
        stackDebth--;
    }

    /**
     * Másik formázó függvény
     * @param str szöveg amit formáz
     */
    public static void formatPrint(String str)
    {
        if(!isLogging) return;
        System.out.println("\n---" + str + "---\n");
    }
}
