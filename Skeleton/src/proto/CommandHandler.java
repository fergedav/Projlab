package proto;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import logic.*;

public class CommandHandler {

    // Erre a printStream-re írja ki a toCurrentStream(String s) a kapott stringet. ez kell a filebairányíthatósághoz.
    static PrintStream currentOut = System.out;

    public static void toCurrentStream(String s){ currentOut.println(s); }

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
        Controller load;
        boolean loadFlag = false;
        String filename = (String)args[1];
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            load = (Controller) in.readObject();
            in.close();
            fileIn.close();
            loadFlag = true;
        } catch (IOException i) {
            System.out.println("Betoltés sikertelen: IOException");
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("Betoltés sikertelen: ClassNotFoundException");
            e.printStackTrace();
            return;
        }
        if(loadFlag)
        {
             Controller.LoadController(load);
             System.out.println("A palya betoltese sikeres volt: " + filename);
        }

    }

    public static void savemap(Object[] args) 
    {
        String filename = (String)args[1];
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Controller.getInstance());
            out.close();
            fileOut.close();
            System.out.printf("A palya mentése sikeres volt ide: " + filename);
         } catch (IOException i) {
            System.out.printf("A palya mentése sikertelen volt");
            i.printStackTrace();
         }
    }

    //nincs kész, nem biztos hogy mûködik.
    public static void loadtest(Object[] args) throws IOException 
    {
        try
        {
            BufferedReader rd = new BufferedReader(new FileReader((String)args[1]));

            FileOutputStream fileOut = new FileOutputStream((String)args[2]); //célfilera irányítja a current out-ot
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            currentOut = new PrintStream(out);

            String line;
            int line_counter = 0;
            while((line = rd.readLine()) != null)
            {
                line_counter++;
                processCommand(line);
            }
            rd.close();
            currentOut.close();

            currentOut = System.out; // visszaállítja
        }
        catch(Exception e)
        {
            System.out.println("loadTest: exception (rossz argumentumok?)");
        }
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

    public static void addhalfstargate(Object[] args)  throws Exception
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
        int orbitId = Integer.parseInt((String)args[1]);
        int settlerId = Integer.parseInt((String)args[2]);
        s1.place(c.getOrbit(orbitId));
        c.getSettler(settlerId).addOneStargate(s2);
    }

    public static void addrobot(Object[] args) throws Exception
    {
        Controller c = Controller.getInstance();
        Robot r = new Robot();
        int orbitId = Integer.parseInt((String)args[0]);
        r.setLocation(c.getOrbit(orbitId));
        boolean b;
        switch ((String)args[1]) {
            case "rand":
                b = true;
                break;        
            case "det":
                b=false;
                break;
            default:
                throw new Exception("Ismeretlen mukodes: " + (String)args[1]);
        }
        r.setBehavior(b);
    }

    public static void addufo(Object[] args) throws Exception
    {
        Controller c = Controller.getInstance();
        Ufo u = new Ufo();
        u.setLocation(c.getOrbit(Integer.parseInt((String)args[0])));
        boolean b;
        switch ((String)args[1]) {
            case "rand":
                b = true;
                break;        
            case "det":
                b=false;
                break;
            default:
                throw new Exception("Ismeretlen mukodes: " + (String)args[1]);
        }
        u.setBehavior(b);
    }

    public static void addsettler(Object[] args) throws Exception
    {
        Controller c = Controller.getInstance();
        Settler s = new Settler(c.getOrbit(Integer.parseInt((String)args[0])));
        Inventory si = s.getInventory();
        for(int i=0; i<Integer.parseInt((String)args[1]); i++){
            si.addResource(new Uran());
        }
        for(int i=0; i<Integer.parseInt((String)args[2]); i++){
            si.addResource(new Ice());
        }
        for(int i=0; i<Integer.parseInt((String)args[3]); i++){
            si.addResource(new Iron());
        }
        for(int i=0; i<Integer.parseInt((String)args[4]); i++){
            si.addResource(new Carbon());
        }
        switch (Integer.parseInt((String)args[5])) {
            case 0:
                break;        
            case 2:
                s.createStargate();
                break;
            default:
                throw new Exception("Ismeretlen mukodes: " + (String)args[5]);
        }
    }

    public static void movesettler(Object[] args) {}

    public static void diggingsettler(Object[] args) {}

    public static void miningsettler(Object[] args) {}

    public static void createrobot(Object[] args) {}

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
        Stargate lehelyezett = seged.getStargates().get(0); 
        seged.placeStargate();
        System.out.println(
            "StargateId: " + lehelyezett.getPrefix() +"_"+
                c.indexStargate(lehelyezett) +
            " Coords: " + lehelyezett.getCoords()[0] + " " + lehelyezett.getCoords()[1] +            
            " MyStop: " + 
                (lehelyezett.getPlaced() ? 
                (lehelyezett.getMyStop().getPrefix() +"_"+c.indexAsteroid((Asteroid)lehelyezett.getMyStop())) 
                : "-") +
            " TwinId: " + lehelyezett.getMyTwin().getPrefix() +"_"+
                c.indexStargate(lehelyezett.getMyTwin()) +
            " InLight: " + lehelyezett.getLight()+
            " Crazy: " + lehelyezett.getCrazy()
        );
    }

    public static void createbase(Object[] args) {}

    public static void replaceresourcesettler(Object[] args) {}

    public static void settlerinfo(Object[] args) {}

    public static void robotinfo (Object[] args) throws Exception
    {
        Controller c = Controller.getInstance();
        int robotId = Integer.parseInt((String)args[0]);
        Robot r = c.getRobot(robotId);
        r.robotInfo();
    }

    public static void ufoinfo(Object[] args) throws Exception 
    {
        Controller c = Controller.getInstance();
        int ufoId = Integer.parseInt((String)args[0]);
        Ufo u = c.getUfo(ufoId);
        u.ufoInfo();
    }

    public static void asteroidinfo(Object[] args) throws Exception
    {
        Controller c = Controller.getInstance();
        int asteroidId = Integer.parseInt((String)args[0]);
        Asteroid a = c.getAsteroid(asteroidId);
        String inLight;
        if(a.getLight()){
            inLight = "true";
        }
        else inLight = "false";

        System.out.println(
            "AsteroidId: asteroid_"+c.indexAsteroid(a)+ " Coords: "+a.getCoords()[0]+" "+ a.getCoords()[1]+
            " Core: " + a.getCore().toString()+ " inLight: "+ inLight+" Layers: "+ a.getLayers()
        );
    }

    public static void stargateinfo(Object[] args) throws Exception
    {
        int stargateId = Integer.parseInt((String)args[1]);
        Stargate vizsgalt = Controller.getInstance().getStargate(stargateId);
        System.out.println(
            "StargateId: " + vizsgalt.getPrefix() +"_"+
                Controller.getInstance().indexStargate(vizsgalt) +
            " Coords: " + vizsgalt.getCoords()[0] + " " + vizsgalt.getCoords()[1] +            
            " MyStop: " + 
                (vizsgalt.getPlaced() ? 
                (vizsgalt.getMyStop().getPrefix() +"_"+Controller.getInstance().indexAsteroid((Asteroid)vizsgalt.getMyStop())) 
                : "-") +
            " TwinId: " + vizsgalt.getMyTwin().getPrefix() +"_"+
                Controller.getInstance().indexStargate(vizsgalt.getMyTwin()) +
            " InLight: " + vizsgalt.getLight()+
            " Crazy: " + vizsgalt.getCrazy()
        );
    }

    public static void listsettlers(Object[] args) {}

    public static void listRobots (Object[] args) 
    {
        Controller c = Controller.getInstance();
        List<Robot> robots= c.getRobots();
        for(Robot r: robots){
            r.robotInfo();
        }
    }

    public static void listUfos(Object[] args) 
    {
        Controller c = Controller.getInstance();
        List<Ufo> ufos= c.getUfos();
        for(Ufo u: ufos){
            u.ufoInfo();
        }
    }

    public static void listasteroids(Object[] args) {}

    public static void liststargates(Object[] args) 
    {
        Controller.getInstance().liststargates();
    }

    public static void listneighborasteroids (Object[] args) throws Exception
    {
        int asteroidId = Integer.parseInt((String)args[1]);
        Asteroid seged = Controller.getInstance().getAsteroid(asteroidId);
        for(int i = 0; i < seged.numOfNeighbor(); i++){
            System.out.println(
                "OrbitId: " + seged.getNeighbour(i).getPrefix() +"_"+ Controller.getInstance().indexOrbit(seged.getNeighbour(i)) +
                "  Coords: " + seged.getNeighbour(i).getCoords()[0] + " " + seged.getNeighbour(i).getCoords()[1]);
        }
    }

    public static void listasteroidcontent(Object[] args) throws Exception
    {
        int asteroidId = Integer.parseInt((String)args[1]);
        Asteroid aktualis = Controller.getInstance().getAsteroid(asteroidId);
        System.out.print("Travelers: ");
        String seged;
        for (Traveler t : aktualis.getTravelers()) {
            switch (t.getPrefix()) {
                case "robot":
                    seged = String.valueOf(Controller.getInstance().indexRobot((Robot)t));
                    break;        
                case "ufo":
                    seged = String.valueOf(Controller.getInstance().indexUfo((Ufo)t));
                    break;
                case "settler":
                    seged = String.valueOf(Controller.getInstance().indexSettler((Settler)t));
                    break;
                default:
                    throw new Exception("Ismeretlen traveler: " + t.getPrefix());
            }
            System.out.print(t.getPrefix()+"_"+seged+", ");
        }
        System.out.println(); //ugorjon sort es ujba folytassa
    }
}
