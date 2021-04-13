package proto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import logic.*;

public class CommandHandler {

    private static Method getMethod(String name) throws NoSuchMethodException, SecurityException
    {
        return CommandHandler.class.getMethod(name);
    }

    public static void processCommand(String line)
    {
        try {
            String[] splits = line.toLowerCase().split(" ");
            getMethod(splits[0]).invoke(null, (Object[]) splits);
        } catch (Exception e) {
            System.out.println("Hiba: " + e.getMessage());
        }
    }

    public static void loadmap(Object[] args) throws IOException 
    {
        try {
            FileInputStream fileIn = new FileInputStream("test.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Controller.LoadController((Controller) in.readObject());
            in.close();
            fileIn.close();
         } catch (IOException i) {
            i.printStackTrace();
            return;
         } catch (ClassNotFoundException e) {
            System.out.println("Employee class not found");
            e.printStackTrace();
            return;
         }//áûõ
    }

    public static void savemap(Object[] args) 
    {
        try {
            FileOutputStream fileOut =
            new FileOutputStream((String)args[1]);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Controller.getInstance());
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in test.ser");
         } catch (IOException i) {
            i.printStackTrace();
         }
    }

    public static void loadtest(Object[] args) throws IOException 
    {
        BufferedReader rd = new BufferedReader(new FileReader((String)args[1]));
        String line;
        while((line = rd.readLine()) != null)
        {
            processCommand(line);
        }
        rd.close();
    }

    public static void createmap(Object[] args) 
    {
        Controller.getNewControler();
    }

    public static void robotstep(Object[] args) throws Exception 
    {
        int robotId = Integer.parseInt((String)args[1]);
        Controller.getInstance().getRobot(robotId).step();
    }

    public static void ufostep(Object[] args) throws Exception 
    {
        int ufoId = Integer.parseInt((String)args[1]);
        Controller.getInstance().getUfo(ufoId).step();
    }

    public static void stargatestep(Object[] args) throws Exception
    {
        int stargateId = Integer.parseInt((String)args[1]);
        Controller.getInstance().getStargate(stargateId).step();
    }

    public static void sunstormcall(Object[] args) 
    {
        Controller.getInstance().explicitSunstorm(
            Integer.parseInt((String)args[1]),
            Integer.parseInt((String)args[2]),
            Integer.parseInt((String)args[3]),
            Integer.parseInt((String)args[4])
            );
    }

    public static void sunlightcall(Object[] args) 
    {
        Controller.getInstance().explicitSunlight(
            Integer.parseInt((String)args[1]),
            Integer.parseInt((String)args[2]),
            Integer.parseInt((String)args[3]),
            Integer.parseInt((String)args[4])
            );
    }

    public static void setneighbour(Object[] args) throws NumberFormatException, Exception 
    {
        Controller c = Controller.getInstance();
        Asteroid a1 = c.getAsteroid(Integer.parseInt((String)args[1]));
        Asteroid a2 = c.getAsteroid(Integer.parseInt((String)args[2]));
        a1.addNeighbour(a2);
        a2.addNeighbour(a1);
    }

    public static void addasteroid(Object[] args) throws Exception 
    {
        Controller c = Controller.getInstance();
        Resource r;
        
        switch ((String)args[1]) {
            case "carbon":
                r = new Carbon();
                break;
        
            case "iron":
                r = new Iron();
                break;

            case "ice":
                r = new Ice();
                break;
        
            case "uran":
                r = new Uran();
                break;

            case "null":
                r = null;
                break;

            default:
            throw new Exception("Ismeretlen nyersanyag: " + (String)args[1]);
        }
        c.addOrbit(new Asteroid(Integer.parseInt((String)args[2]), 
                                Integer.parseInt((String)args[3]), 
                                Integer.parseInt((String)args[4]), 
                                r, //arg1
                                Boolean.parseBoolean((String)args[5])));;
    }
    /**
     * Ket stargatet helyez el egy adott controllerben tarolt palyan
     * @param args [parancsnev, orbit szam1, orbit szama2, random/det mukodes]
     */
    public static void addpairedstargate(Object[] args)  throws Exception
    {
        Controller c = Controller.getInstance();
        boolean b;
        switch ((String)args[3]) {
            case "rand":
                b = true;
                break;        
            case "det":
                b=false;
                break;
            default:
                throw new Exception("Ismeretlen mukodes: " + (String)args[3]);
        }
        Stargate s1 = new Stargate();
        s1.setBehavior(b);
        Stargate s2 = new Stargate();
        s2.setBehavior(b);
        s1.entagle(s2);
        s2.entagle(s1);
        c.addStargate(s1);
        c.addStargate(s2);
        int orbitId1 = Integer.parseInt((String)args[1]);
        int orbitId2 = Integer.parseInt((String)args[2]);
        s1.place(c.getOrbit(orbitId1));
        s2.place(c.getOrbit(orbitId2));
    }

    public static void addhalfstargate(Object[] args) 
    {
        
    }

    public static void addrobot(Object[] args) {}

    public static void addufo(Object[] args) {}

    public static void addsettler(Object[] args) {}

    public static void movesettler(Object[] args) {}

    public static void diggingsettler(Object[] args) {}

    public static void miningsettler(Object[] args) {}

    public static void createrobot(Object[] args) throws Exception
    {
        int settlerId = Integer.parseInt((String)args[1]);
        Settler seged = Controller.getInstance().getSettler(settlerId);
        seged.createRobot();
        System.out.println();
    }

    public static void createstargate(Object[] args) throws Exception
    {
        int settlerId = Integer.parseInt((String)args[1]);
        Settler seged = Controller.getInstance().getSettler(settlerId);
        seged.createStargate();
        System.out.println("numOfGates: "+ seged.getStargates().size());
    }

    public static void placestargate (Object[] args) throws Exception
    {
        Controller c = Controller.getInstance();
        int settlerId = Integer.parseInt((String)args[1]);
        Settler seged = c.getSettler(settlerId);
        Stargate lehelyezett = seged.getStargates().get(0); //kiiratashoz eltaroljuk segedkent
        seged.placeStargate();
        //TODO itt orbot vagy aszteroida legyen?, mindeképp orbitban keres
        System.out.println(
            "StargateId: stargate_" + c.indexStargate(lehelyezett) +
            " Coords: " + lehelyezett.getCoords()[0] + " " + lehelyezett.getCoords()[1] +            
            " MyStop: orbit_" + (lehelyezett.getPlaced() ? c.indexOrbit(lehelyezett.getMyStop()) : "-") +
            " TwinId: stargate_" + c.indexStargate(lehelyezett.getMyTwin()) +
            " InLight: " + lehelyezett.getLight()+
            " Crazy: " + lehelyezett.getCrazy());
    }

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

    public static void listneighborasteroids (Object[] args) throws Exception
    {
        int asteroidId = Integer.parseInt((String)args[1]);
        Asteroid seged = Controller.getInstance().getAsteroid(asteroidId);
        for(int i = 0; i < seged.numOfNeighbor(); i++){
            System.out.println(
                "OrbitId: orbit_" + Controller.getInstance().indexOrbit(seged.getNeighbour(i)) +
                "  Coords: " + seged.getCoords()[0] + " " + seged.getCoords()[1]);
        }
    }

    public static void listasteroidcontent(Object[] args) {}
}
