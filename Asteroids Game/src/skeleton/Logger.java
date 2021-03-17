package skeleton;

import java.lang.System;
import java.lang.reflect.Method;

public class Logger {
    /**
     * tabulátorok száma a hívások során
     */
    private static int stackDebth = 0;

    
    /** 
     * függvény object kereső függvény a logoláshoz
     * @param o objektum aminek a függvényét hívjuk
     * @param name függvény neve amit hívunk
     * @return Method függvény ha van ilyen nevű
     * @throws Exception  kivétel ha nincs ilyen nevű függvény
     */
    private static Method findMethod(Object o, String name) throws Exception {
        Method[] allMethods = o.getClass().getMethods();
        for (Method method : allMethods) {
            if (method.getName().equals(name))
                return method;
        }

        throw new Exception("Nincs ilyen függvény: " + name);
    }

    
    /** 
     * Formázó segédfüggvény a logoláshoz
     * @param obj objektum aminek a függvényét hívjuk
     * @param funcName függvény neve amit hívunk
     * @param comment komment a hívással kapcsolatban
     */
    private static void startFunctionLogComment(Object obj, String funcName, String comment) {
        for (int i = 0; i < stackDebth; i++)
            System.out.print("\t");

        //String str = obj.getClass().getSimpleName() + "." + funcName + "()";
        String str =  "(" + obj.getClass().getSimpleName() + ") "  + funcName + "()";

        if (!comment.equals(""))
            str += (" -- " + comment);

        System.out.println(str);
        stackDebth++;
    }

    /** 
     * tabok csökkentése a hívás vége után
     */
    private static void endFunctionLog() {
        stackDebth--;
    }

    
    /** 
     * Függvény meghívása és logolása
     * @param obj objektum aminek a függvényét hívjuk
     * @param funcName függvény neve amit hívunk
     * @param comment komment a hívással kapcsolatba. "" a komment elhagyásához
     * @param args argumentumok amivel hívjuk a függvényt
     * @return T hívott függvény visszatérési értéke ha érdekes
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
