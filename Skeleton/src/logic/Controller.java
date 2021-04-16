package logic;

import java.util.ArrayList;
import java.util.List;

import skeleton.Logger;

public class Controller implements java.io.Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = -425692066249723082L;

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

        //TODO beírni robotnak a prefixet; törölni majd

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
     * Hozzáadja a paraméterként kapott ufót a controller ufo-kat tartalmazó listájához
     * @param u
     */
    public void addUfo(Ufo ufo)
    {
        ufos.add(ufo);
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
        //TODO PREFIXET FELVENNI

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

        //TODO beírni a prefixét.
        //TODO ez alul furcsa... nekem nem tetszik. mert add orbitot hív stargate, vagy asteroid beírás is följebb

        //ideiglenes a prto idejere, konnyebb eleres erdekeben
        if(o.getClass() == Asteroid.class)
            asteroids.add((Asteroid)o);
        else if(o.getClass() == Stargate.class)
            stargates.add((Stargate)o);
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
        Asteroid.id_counter = 0;
        Stargate.id_counter = 0;
        Settler.id_counter = 0;
        Robot.id_counter = 0;
        Ufo.id_counter = 0;
        return instance;
    }

    public static void LoadController(Controller c)
    {
        instance = c;
    }

    /// Singleton ends here
    
//PROTO FÜGGVÉNYEK INNENTÕL///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Proto fuggvenyek ------------------------------------------------------------------------------

    public List<Asteroid> asteroids = new ArrayList<>();

    public void addAsteroid(Asteroid a) 
    { 
        asteroids.add(a);
        orbits.add(a);
    }

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
        if(index < 0 || index >= asteroids.size())
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

    public List<Robot> getRobots(){
        return robots;
    }
    public List<Ufo> getUfos(){
        return ufos;
    }

    //end of Proto fuggvenyek ---------------------------------------------------------------------------

    /**
     * Index alapján visszatér a settlers lista adott elemével
     * @param index
     * @return az adott indexu Settler a settlers listaban
     * @throws Exception  nincs ilyen indexu telepes
     */
    public Settler getSettler(int index) throws Exception
    {
        if(index < 0 || index >= settlers.size())
            throw new Exception("Nincs ilyen telepes: " + index);

        return settlers.get(index);
    }
    /**
     * Index alapján visszatér az orbits lista adott elemével
     * @param index
     * @return az adott indexu Orbit az orbits listaban
     * @throws Exception  nincs ilyen indexu orbit
     */
    public Orbit getOrbit(int index) throws Exception
    {
        if(index < 0 || index >= orbits.size())
            throw new Exception("Nincs ilyen orbit: " + index);

        return orbits.get(index);
    }
    /**
     * Megadja egy Orbit sorszamat az orbits listaban
     * @param o keresett orbit
     * @return a listaban levo sorszama
     * @throws ClassCastException rossz parameteru 
     */
    public int indexOrbit(Orbit o) throws ClassCastException
    {
        return orbits.indexOf(o);
    }
    /**
     * Megadja egy Asteroid sorszamat az asteroids listaban
     * @param a keresett aszteroida
     * @return a listaban levo sorszama
     * @throws ClassCastException rossz parameteru 
     */
    public int indexAsteroid(Asteroid a) throws ClassCastException
    {
        return asteroids.indexOf(a);
    }
    /**
     * Megadja egy Stargate sorszamat a stargates listaban
     * @param s keresett stargate
     * @return a listaban levo sorszama
     * @throws ClassCastException rossz parameteru 
     */
    public int indexStargate(Stargate s) throws ClassCastException
    {
        return stargates.indexOf(s);
    }
    /**
     * Megadja egy Robot sorszamat a robots listaban
     * @param r keresett robot
     * @return a listaban levo sorszama
     * @throws ClassCastException rossz parameteru 
     */
    public int indexRobot(Robot r) throws ClassCastException
    {
        return robots.indexOf(r);
    }
    /**
     * Megadja egy Ufo sorszamat az ufos listaban
     * @param u keresett ufo
     * @return a listaban levo sorszama
     * @throws ClassCastException rossz parameteru 
     */
    public int indexUfo(Ufo u) throws ClassCastException
    {
        return ufos.indexOf(u);
    }
    /**
     * Megadja egy Settler sorszamat az settlers listaban
     * @param s keresett telepes
     * @return a listaban levo sorszama
     * @throws ClassCastException rossz parameteru 
     */
    public int indexSettler(Settler s) throws ClassCastException
    {
        return settlers.indexOf(s);
    }
    /**
     * Kiirja a kimenetre a stargatek tartalmat
     */
    public void liststargates()
    {
        for (Stargate futo : stargates) {
            //core lehet nulla. lehet belõle baj. elõbb kell kezelni.
            String MyStop = " - ";
            if(futo.getMyStop() != null)
                MyStop = futo.getMyStop().getPrefix();
            System.out.println(
                "StargateId: " + futo.getPrefix()+
                " MyTwin: " + futo.getMyTwin().getPrefix()+
                " MyStop: " + MyStop +
                " Crazy: " + futo.getCrazy()
            );
        }
    }

    public void listsettlers()
    {
        for (Settler futo : settlers) {
            System.out.println(
                "SettlerId: "+ futo.getPrefix() +
                " Location: "+ futo.getcurrentLocation().getPrefix() +
                " Coords: " + futo.getcurrentLocation().getCoords()[0] + " " +futo.getcurrentLocation().getCoords()[1] +
                " Resources: Uran: "+ futo.getInventory().getNumOfUran() + " Ice: " + futo.getInventory().getNumOfIce() + " Iron: "+ futo.getInventory().getNumOfIron() + "Carbon: "  + futo.getInventory().getNumOfCarbon() + " Gates: " + futo.getStargates().size()
                );
        }
    }

    /**
     * beállítja a game is on váltó értékét
     */
    public void setGameIsOn(boolean a) { gameIsOn = a;}

    /**
     * visszaadja a game is on váltó értékét
     */
    public boolean getGameIsOn() {return gameIsOn;}

    /**
     * kiírja az összes aszteroidát
     */
    public void listAsteroids()
    {
        for (Asteroid futo : asteroids) {
            //core lehet nulla. lehet belõle baj. elõbb kell kezelni.
            String core = " - ";
            if(futo.getCore() != null)
                core = futo.getCore().getClass().getSimpleName();
            System.out.println(
                "AsteroidId: "+ futo.getPrefix() +
                " Coords: " + futo.getCoords()[0] + " "+ futo.getCoords()[1] +
                " Core: " + core +
                " inLight: " + futo.inLight +
                " Layers: " + futo.getLayers()
            );
        }
    }
    /**
     * Visszadja az aktuálisan aktiv robotok darabszamat
     * @return int robotok szama
     */
    public int numOfRobots()
    {
        return robots.size();
    }
    /**
     * Visszadja az aktuálisan aktív robotok darabszámát
     * @return az utolso robot a robots listaban
     */
    public Robot lastRobot()
    {
        return robots.get(robots.size()-1);
    }
}

