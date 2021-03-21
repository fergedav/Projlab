package skeleton;

import java.lang.System;
import java.lang.reflect.Method;


public class Logger {
    /**
     * tabul�torok sz�ma a h�v�sok sor�n
     */
    private static int stackDebth = 1;

    
    /** 
     * f�ggv�ny object keres� f�ggv�ny a logol�shoz
     * @param o objektum aminek a f�ggv�ny�t h�vjuk
     * @param name f�ggv�ny neve amit h�vunk
     * @return Method f�ggv�ny ha van ilyen nev�
     * @throws Exception  kiv�tel ha nincs ilyen nev� f�ggv�ny
     */
    private static Method findMethod(Object o, String name) throws Exception {
        Method[] allMethods = o.getClass().getMethods();
        for (Method method : allMethods) {
            if (method.getName().equals(name))
                return method;
        }

        throw new Exception("Nincs ilyen f�ggv�ny: " + name);
    }

    
    public static void formatPrint(String str)
    {
        System.out.println("\n---" + str + "---\n");
    }

    /** 
     * Form�z� seg�df�ggv�ny a logol�shoz
     * @param obj objektum aminek a f�ggv�ny�t h�vjuk
     * @param funcName f�ggv�ny neve amit h�vunk
     * @param comment komment a h�v�ssal kapcsolatban
     */
    public static void startFunctionLogComment(Object obj, String funcName, String comment) {
        for (int i = 0; i < stackDebth; i++)
            System.out.print("|\t");

        //String str = obj.getClass().getSimpleName() + "." + funcName + "()";
        String str =  "(" + obj.getClass().getSimpleName() + ") "  + funcName + "()";

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
     * F�ggv�ny megh�v�sa �s logol�sa
     * @param obj objektum aminek a f�ggv�ny�t h�vjuk
     * @param funcName f�ggv�ny neve amit h�vunk
     * @param comment komment a h�v�ssal kapcsolatba. "" a komment elhagy�s�hoz
     * @param args argumentumok amivel h�vjuk a f�ggv�nyt
     * @return T h�vott f�ggv�ny visszat�r�si �rt�ke ha �rdekes
     */
    public static <T> T log(Object obj, String objName, String funcName, String comment, Object... args) {
        T res = null;
        try {
            Method m = findMethod(obj, funcName);
            startFunctionLogComment(obj, objName + "." + m.getName(), comment);
            res = (T) m.invoke(obj, args);
            endFunctionLog();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return res;
    }
}
