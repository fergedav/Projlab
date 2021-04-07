package proto;

import java.lang.reflect.Method;

public class CommandHandler {

    private static Method getMethod(String name) throws NoSuchMethodException, SecurityException
    {
        return CommandHandler.class.getMethod(name);
    }

    public static void processCommand(String line)
    {
        try {
            String[] splits = line.split(" ");
            getMethod(splits[0]).invoke(null, (Object[])splits);
        } catch (Exception e) {
            System.out.println("Hiba: " + e.getMessage());
        }
    }

    public static void loadmap(Object[] args) {}

    public static void savemap(Object[] args) {}

    public static void loadtest(Object[] args) {}

    public static void createmap(Object[] args) {}

    public static void robotstep(Object[] args) {}

    public static void ufostep(Object[] args) {}

    public static void stargatestep(Object[] args) {}

    public static void sunstormcall(Object[] args) {}

    public static void sunlightcall(Object[] args) {}

    public static void setneighbour(Object[] args) {}

    public static void addasteroid(Object[] args) {}

    public static void addpairedstargate(Object[] args) {}

    public static void addhalfstargate(Object[] args) {}

    public static void addrobot(Object[] args) {}

    public static void addufo(Object[] args) {}

    public static void addsettler(Object[] args) {}

    public static void movesettler(Object[] args) {}

    public static void diggingsettler(Object[] args) {}

    public static void miningsettler(Object[] args) {}

    public static void createrobot(Object[] args) {}

    public static void createstargate(Object[] args) {}

    public static void placestargate (Object[] args) {}

    public static void createbase(Object[] args) {}

    public static void replaceresourcesettler(Object[] args) {}

    public static void settlerinfo(Object[] args) {}

    public static void robotinfo (Object[] args) {}

    public static void ufoinfo(Object[] args) {}

    public static void asteroidinfo(Object[] args) {}

    public static void stargateinfo(Object[] args) {}

    public static void listsettlers(Object[] args) {}

    public static void listrobots (Object[] args) {}

    public static void listufos(Object[] args) {}

    public static void listasteroids(Object[] args) {}

    public static void liststargates(Object[] args) {}

    public static void listneighborasteroids (Object[] args) {}

    public static void listasteroidcontent(Object[] args) {}
}
