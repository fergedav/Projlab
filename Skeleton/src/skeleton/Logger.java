package skeleton;

import java.lang.System;

public class Logger {
    /**
     * tabul�torok sz�ma a h�v�sok sor�n
     */
    private static int stackDebth = 0;

    /** 
     * Form�z� f�ggv�ny a logol�shoz
     * @param obj objektum aminek a f�ggv�ny�t h�vjuk
     * @param funcName f�ggv�ny neve amit h�vunk
     * @param comment komment a h�v�ssal kapcsolatban
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
     * tabok cs�kkent�se a h�v�s v�ge ut�n
     */
    public static void endFunctionLog() {
        stackDebth--;
    }

    /**
     * M�sik form�z� f�ggv�ny
     * @param str sz�veg amit form�z
     */
    public static void formatPrint(String str)
    {
        System.out.println("\n---" + str + "---\n");
    }
}
