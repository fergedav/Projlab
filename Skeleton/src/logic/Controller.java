package logic;

import java.util.ArrayList;
import java.util.List;

import grafikus.IDrawable;
import grafikus.MainFrame;
import skeleton.Logger;


/**
 * Az Objektum felelõssége a játékmenetet vezérelni. Õ tartja számon a teljes pálya objektumait, és azokat amelyeket kell, lépteti õket körönként,. Inicializálja, elindítja futtatja és megfelelõ körülmények esetén leállítja a játékot. 
 * Õ hív napvihart és napfényt, az aszteroidákra, stargatekre, õ hívja meg a robotok, ufók, stargatek és telepesek lépéseit.
 * Ha minden settler meghal befejezi a játékot.
 */
public class Controller implements java.io.Serializable {

	private static final long serialVersionUID = -425692066249723082L;

	/**
     * Megadja hogy kell e futtatni a játékot vagy sem.
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
     * A játékban éppen aktuálisan lév? összes Ufo típusú objektumot tároló privát lista
     */
    private List<Ufo> ufos = new ArrayList<>();

    /**
     * A játékban található összes Orbit egy listában tárolva
     */
    private List<Orbit> orbits = new ArrayList<>();

    /**
     * A játékban található összes Stargate egy listában tárolva
     */
    private List<Stargate> stargates = new ArrayList<>();



    /**
     * Megszabja, hogy hány körönként legyen napvihar
     */
    private int sunstormTime;

    /**
     * hány kör van még hátra a napviharig
     */
    private int sunstormTimmer;

    /**
     * inicializálja a pályát, a maximum inventory kapacitást, valamint a napviharok között eltelõ idõt.
     */
    public void initGame()
    {
        // TODO idokoz random beallitasa majd mashol 
        sunstormTime = 8;

        int asteroidCount = 40;
        int settlerCount = 10;
        int ufoCount = 30;

        //aszteroidák generálása
        for (int i = 0; i < asteroidCount; i++) 
        {
            
            addAsteroid(new Asteroid(
                getRandomNumber(0, 100), 
                getRandomNumber(0, 100), 
                getRandomNumber(1, 5), generateRandomResource(), false));
        }

        // közeli aszteroidák összekapcsolása
        for (int i = 0; i < asteroidCount; i++) 
        {
            for (int j = i + 1; j < asteroidCount; j++) 
            {
                Asteroid a1 = asteroids.get(i);
                Asteroid a2 = asteroids.get(j);
                if(getAsteroidDistance(a1, a2) <= 15)
                {
                    a1.addNeighbour(a2);
                    a2.addNeighbour(a1);
                }
            }
        }

        //settlerek létrehozása
        for (int i = 0; i < settlerCount; i++)
        {
            addSettler(new Settler(asteroids.get(getRandomNumber(0, asteroidCount-1))));
        }

        //Ufok generálása
        for (int i = 0; i < ufoCount; i++)
        {
            addUfo(new Ufo(asteroids.get(getRandomNumber(0, asteroidCount-1))));
        }
    }

    /**
     * két aszteroida távolságának kiszámolása a generáláshoz, szomszéd összepárosításkor
     * @param a1 elsõ aszteroida
     * @param a2 második aszteroida
     * @return távolságuk
     */
    private int getAsteroidDistance(Asteroid a1, Asteroid a2)
    {
        int[] c1 = a1.getCoords();
        int[] c2 = a2.getCoords();

        int x = c1[0] - c2[0];
        int y = c1[1] - c2[1];

        return (int)Math.sqrt(x*x + y*y);
    }

    /**
     * random szám generálása megadott intervallumban
     * @param min legkisebb szám
     * @param max legnagyobb szám
     * @return random szám
     */
    private int getRandomNumber(int min, int max)
    {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Random nyersanyag létrehozása a pálya generáláshoz
     * @return random típusú nyersanyag (Carbon, ICe Iron, Uran)
     */
    private Resource generateRandomResource()
    {
        int n = getRandomNumber(0, 5);
        switch (n) {
            case 0:
                return new Carbon();
            case 1:
                return new Ice();
            case 2:
                return new Iron();
            case 3:
                return new Uran();
            default:
                return null;
        }
    }

    /**
     * Elindítja a játékot: beállítja a gameIsOn attributum értékét true-ra.
     * A GameEnd() metódus meghívásáig folyamatosan a saját Step() metódusát hívogatja.
     */
    public void startGame()
    {
        Logger.startFunctionLogComment(this, "startGame", "");

        gameIsOn = true;
        //while(gameIsOn){

          //  step();
        //}

            //TODO init, új pálya

        initGame();
        step();
        NextSetller();

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
            sunstormTimmer = getRandomNumber(4,9);
        }
        else
            sunstormDecrease();

        sunLightCall();

        for(Stargate s : stargates){ s.step();}
        for(Robot r : robots){ r.step(); }
        for(Ufo u : ufos){u.step();}
        //for(Settler s : settlers){s.step(); }
    }

    /**
     * Hozzáadja a paraméterként kapott telepest a controller telepeseket tartalmazó listájához
     * @param s a hozzáadandó telepes
     */
    public void addSettler(Settler s)
    {
        settlers.add(s);
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
     * A játék végét kezelõ függvény, beállítja a gameIsOn attributumot “false”-ra. 
     * Amikor a settlers listában nincsen már telepes vagy a bázis megépül
     * akkor ez a függvény meghívódik, hogy véget vessen a játéknak, illetve kívülrõl is hívható.
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
     * ha egy robot meghal, akkor meghívja ezt a Robot_die() függvényt,
     *  ami eltávolítja a robots listából az paraméterként kapott Robot objektumot.
     * @param r az eltávolitandó robot.
     */
    public void robotDie(Robot r)
    {
        Logger.startFunctionLogComment(this, "robotDie", "");

        robots.remove(r);

        Logger.endFunctionLog();
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
     * eltávolítja a kapott ufót a Controller ufos listájából, ha  a kapott ufo benne volt.
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
        //TODO ezt tisztázni
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

        // TODO rendes coords random kellene legyen majd mûködés közben
        int coords[] = new int[4];
        ArrayList<Orbit> copy = new ArrayList<>();
        copy.addAll(orbits);
        for(Orbit o: copy){
            o.sunstormArrive(coords);
        }
        Logger.endFunctionLog();
    }

    /**
     * Meghívja az összes tárolt Orbit SunLightArrive()-metódusát, véletlen sorsolt koordinátákkal
     */
    private void sunLightCall()
    {
        Logger.startFunctionLogComment(this, "sunLightCall", "");

        // TODO rendes random coords
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

        List<Orbit> copy = new ArrayList<>();
        copy.addAll(orbits);
        for(Orbit o : copy){
            o.sunstormArrive(coords);
        }
    }

    public void explicitSunlight(int x1, int y1, int x2, int y2)
    {
        int coords[] = {x1,y1,x2,y2};

        List<Orbit> copy = new ArrayList<>();
        copy.addAll(orbits);
        for(Orbit o : copy){
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
                " Resources: Uran: "+ futo.getInventory().getNumOfUran() + " Ice: " + futo.getInventory().getNumOfIce() + 
                " Iron: "+ futo.getInventory().getNumOfIron() + " Carbon: "  + futo.getInventory().getNumOfCarbon() + 
                " Gates: " + futo.getStargates().size()
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
            String core = "null";
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

    private IDrawable UI;
    private int settlerCounter = 0;

    public void setFrame(IDrawable ui)
    {
        UI = ui;
    }

    public void NextSetller()
    {
        UI.Draw(settlers.get(settlerCounter));
        settlerCounter++;
        if( settlerCounter >= settlers.size())
        {
            step();
            settlerCounter = 0;
        }
    }
}

