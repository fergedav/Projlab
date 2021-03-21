package skeleton;

import java.lang.System;

public class Logger {
    /**
     * tabulátorok száma a hívások során
     */
    private static int stackDebth = 0;

    /** 
     * Formázó függvény a logoláshoz
     * @param obj objektum aminek a függvényét hívjuk
     * @param funcName függvény neve amit hívunk
     * @param comment komment a hívással kapcsolatban
     */
    public static void startFunctionLogComment(Object obj, String funcName, String comment) {
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
        stackDebth--;
    }

    /**
     * Másik formázó függvény
     * @param str szöveg amit formáz
     */
    public static void formatPrint(String str)
    {
        System.out.println("\n---" + str + "---\n");
    }
}
