package logic;

import java.util.ArrayList;
import java.util.List;


import skeleton.Logger;

public class Controller {
    /**
     * folyamatban van e a jatek
     */
    private boolean gameIsOn = false;

    /**
     * a játékban  éppen aktuálisan létezõ összes Settler típusú objektumot tároló privát lista.
     */

    private List<Settler> settlers = new ArrayList<>();
    
    /**
     * a játékban éppen aktuálisan lévõ összes Robot típusú objektumot tároló privát lista.
     */
    private List<Robot> robots = new ArrayList<>();

    /**
     * A játékban található összes Orbit egy listában tárolva
     */
    private List<Orbit> orbits = new ArrayList<>();

    /**
     * A játékban található összes Stargate egy listában tárolva
     */
    private List<Stargate> stargates = new ArrayList<>();

    /**
     * A játékban éppen aktuálisan lév? összes Ufo típusú objektumot tároló privát lista
     */
    private List<Ufo> ufos = new ArrayList<>();

    /**
     * Megszabja, hogy hány körönként legyen napvihar
     */
    private int sunstormTime;

    /**
     * hány kör van még hátra a napviharig
     */
    private int sunstormTimmer;

    public void initGame()
    {
        // TODO idokoz random beallitasa majd mashol
        sunstormTime = 8;
    }

    /**
     * Elindítja és futtatja a játékot.
     * 
     */
    public void startGame()
    {
        Logger.startFunctionLogComment(this, "startGame", "");

        gameIsOn = true;
        while(gameIsOn){

            step();
        }

        Logger.endFunctionLog();
    }

    /**
     * A meghívásakor futtat egy kört a játékból, ebben meghívja a környezeti változásokat és lépteti 
     * a játékban el?forduló léptethet? objektumokat a megfelel? sorrendben 
     * (sorrend: Napvihar és napfény események, megkergült ?rkapuk, robotok, ufók, végül telepesek).
     */
    private void step()
    {

        if(sunstormTimmer == 0)
        {
            sunstormCall();
            sunstormTimmer = sunstormTime;
        }
        else
            sunstormDecrease();

        sunLightCall();

        for(Stargate s : stargates){ s.step();}
        for(Robot r : robots){ r.step(); }
        for(Ufo u : ufos){u.step();}
        for(Settler s : settlers){s.step(); }
    }

    /**
     * leállítja a játkot.
     */
    public void endGame()
    {   
        Logger.startFunctionLogComment(this, "endGame", "");
        gameIsOn = false;
        Logger.endFunctionLog();

    }

    /**
     * a paraméterként megkapott robotot hozzáadja a robots listájához
     * @param r
     */
    public void addRobot(Robot r)
    {
        Logger.startFunctionLogComment(this, "addRobot", "");

        robots.add(r);

        Logger.endFunctionLog();
    }

    /**
     * Jegyzi egy telepes halálát
     * 
     * Ha egy telepes meghal, akkor meghívja  ezt a Settler_die() függvényt, 
     * ami eltávolítja a settlers listából a paraméterként kapott Settler objektumot
     * és átírja a Settler_alive változó értékét.
     * Ha nem marad több settler, játék végét hív.
     * 
     * @param s
     */
    public void settlerDie(Settler s)
    {
        Logger.startFunctionLogComment(this, "settlerDie", "");

        settlers.remove(s);

        if(settlers.size()==0)
            endGame();

        Logger.endFunctionLog();
    }

    /**
     * ha egy robot meghal, akkor meghívja ezt a Robot_die() függvényt, ami eltávolítja a robots listából az paraméterként kapott
     * Robot objektumot.
     * @param r
     */
    public void robotDie(Robot r)
    {
        Logger.startFunctionLogComment(this, "robotDie", "");

        robots.remove(r);

        Logger.endFunctionLog();
    }
    /**
     * Hozzáadja a paraméterként kapott telepest a controller telepeseket tartalmazó listájához
     * @param s
     */
    public void addSettler(Settler s)
    {
        settlers.add(s);
    }

    /**
     * eltávolítja a kapott ufót a Controller ufos listájából, ha   a kapott ufo benne volt.
     * @param u ufo
     */
    public void ufoDie(Ufo u)
    {
        ufos.remove(u);
    }

    /**
     * a paraméterként megkapott teleportkaput hozzáadja a stargates listájához
     * @param s kapu
     */
    public void addStargate(Stargate s)
    {
        orbits.add(s);
        stargates.add(s);
    }

    /**
     *  ha egy teleportkaput meghal, akkor a destuktorának a meghívása el?tt, 
     *  meghívja  ezt a Stargate_die() függvényt, ami eltávolítja a stargates listából az adott Stargate objektumot.
     * @param s
     */
    public void stargateDie(Stargate s)
    {
        orbits.remove(s);
        stargates.remove(s);
    }

    /**
     * Hozzáadadja a paraméterként kapott Orbitot az orbits listához.
     * @param o
     */
    public void addOrbit(Orbit o)
    {
        orbits.add(o);

        //ideiglenes a prto idejere, konnyebb eleres erdekeben
        if(o.getClass() == Asteroid.class)
            asteroids.add((Asteroid)o);
    }
    /**
     * Kiveszi a paraméterként kapott Orbitot az orbits listából.
     * @param o
     */
    public void removeOrbit(Orbit o)
    {
        orbits.remove(o);
        
        //ideiglenes a prto idejere, konnyebb eleres erdekeben
        if(o.getClass() == Asteroid.class)
            asteroids.remove(o);
    }

    /**
     * Meghívja minden aszteroidán a sunstormArrive metódust.
     */
    private void sunstormCall()
    {
        Logger.startFunctionLogComment(this, "sunstormCall", "");

        // TODO rendes coords
        int coords[] = new int[4];

        for(Orbit o: orbits){
            o.sunstormArrive(coords);
        }
        Logger.endFunctionLog();
    }

    /**
     * Meghívja minden aszteroidán a sunLightArrive metódust.
     */
    private void sunLightCall()
    {
        Logger.startFunctionLogComment(this, "sunLightCall", "");

        // TODO rendes coords
        int coords[] = new int[4];

        for(Orbit o: orbits){
            o.sunLightArrive(coords);
        }
        Logger.endFunctionLog();
    }

    /**
     * Eggyel csökkenti a sunstorm_time értékét.
     */
    private void sunstormDecrease()
    {
        Logger.startFunctionLogComment(this, "sunstormDecrease", "");
        sunstormTimmer--;
        Logger.endFunctionLog();
    }

    //Proto fuggvenyek

    private List<Asteroid> asteroids = new ArrayList<>();

    public Robot getRobot(int index) throws Exception
    {
        if(index < 0 || index >= robots.size())
            throw new Exception("Nincs ilyen robot: " + index);

        return robots.get(index);
    }

    public Ufo getUfo(int index) throws Exception
    {
        if(index < 0 || index >= ufos.size())
            throw new Exception("Nincs ilyen ufo: " + index);

        return ufos.get(index);
    }

    public Stargate getStargate(int index) throws Exception
    {
        if(index < 0 || index >= stargates.size())
            throw new Exception("Nincs ilyen kapu: " + index);

        return stargates.get(index);
    }

    public Asteroid getAsteroid(int index) throws Exception
    {
        if(index < 0 || index >= stargates.size())
            throw new Exception("Nincs ilyen aszteroida: " + index);

        return asteroids.get(index);
    }

    public void explicitSunstorm(int x1, int y1, int x2, int y2)
    {
        int coords[] = {x1,y1,x2,y2};

        for(Orbit o: orbits){
            o.sunstormArrive(coords);
        }
    }

    public void explicitSunlight(int x1, int y1, int x2, int y2)
    {
        int coords[] = {x1,y1,x2,y2};

        for(Orbit o : orbits){
            o.sunLightArrive(coords);
        }
    }

    //end of Proto fuggvenyek

    ///Singleton stuff
    private static Controller instance;

    private Controller()
    {
        Logger.startFunctionLogComment(this, "Controler", "<<create>>");
        Logger.endFunctionLog();
    }

     /**
     * Létrhoz és visszaadja, vagy ha már volt létrehozva akkor visszaadja az elsõ létrehozott controllert.
     * 
     * @return a Controler objektum
     */
    public static Controller getInstance()
    {
        if(instance == null)
        instance = new Controller();
        return instance;
    }
    /**
     *  teszteléshez szükséges, új példányt ad vissza mindig.
     */ 
    public static Controller getNewControler()
    {
        instance = new Controller();
        return instance;
    }

    /// Singleton ends here
}
