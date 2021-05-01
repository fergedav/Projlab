package logic;

import java.util.ArrayList;
import java.util.List;

import grafikus.IDrawable;
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
     * megkora a pálya, generáláshoz és napfényhez kell
     */
    private int mapSize = 100;

    /**
     * Megszabja, hogy hány körönként legyen napvihar
     */
    private int sunstormTime;

    /**
     * hány kör van még hátra a napviharig
     */
    private int sunstormTimer;

    /**
     * inicializ˜lja a p˜ly˜t, a maximum inventory kapacit˜st, valamint a napviharok k˜z˜tt eltel˜ id˜t.
     */
    public void initGame()
    {
        // TODO idokoz random beallitasa majd mashol 
        sunstormTimer = 8;

        int asteroidCount = 20;
        int settlerCount = 2;
        int ufoCount = 1;

        //aszteroidák gener˜l˜sa
        for (int i = 0; i < asteroidCount; i++) 
        {
            
            addAsteroid(new Asteroid(
                getRandomNumber(0, mapSize), 
                getRandomNumber(0, mapSize), 
                getRandomNumber(1, 5), generateRandomResource(), true));
        }

        // k˜zeli aszteroid˜k ˜sszekapcsol˜sa
        for (int i = 0; i < asteroidCount; i++) 
        {
            for (int j = i + 1; j < asteroidCount; j++) 
            {
                Asteroid a1 = asteroids.get(i);
                Asteroid a2 = asteroids.get(j);
                if(getAsteroidDistance(a1, a2) <= mapSize / 5)
                {
                    a1.addNeighbour(a2);
                    a2.addNeighbour(a1);
                }
            }
        }

        //settlerek l˜trehoz˜sa
        for (int i = 0; i < settlerCount; i++)
        {
            addSettler(new Settler(asteroids.get(getRandomNumber(0, asteroidCount-1))));
        }

        //Ufok gener˜l˜sa
        for (int i = 0; i < ufoCount; i++)
        {
            addUfo(new Ufo(asteroids.get(getRandomNumber(0, asteroidCount-1))));
        }
    }

    /**
     * k˜t aszteroida t˜vols˜g˜nak kisz˜mol˜sa a gener˜l˜shoz, szomsz˜d ˜sszep˜ros˜t˜skor
     * @param a1 els˜ aszteroida
     * @param a2 m˜sodik aszteroida
     * @return t˜vols˜guk
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
     * random sz˜m gener˜l˜sa megadott intervallumban
     * @param min legkisebb sz˜m
     * @param max legnagyobb sz˜m
     * @return random sz˜m
     */
    private int getRandomNumber(int min, int max)
    {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Random nyersanyag l˜trehoz˜sa a p˜lya gener˜l˜shoz
     * @return random t˜pus˜ nyersanyag (Carbon, ICe Iron, Uran)
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
     * Elind˜tja a j˜t˜kot: be˜ll˜tja a gameIsOn attributum ˜rt˜k˜t true-ra.
     * A GameEnd() met˜dus megh˜v˜s˜ig folyamatosan a saj˜t Step() met˜dus˜t h˜vogatja.
     */
    public void startGame()
    {
        Logger.startFunctionLogComment(this, "startGame", "");

        gameIsOn = true;

        initGame();
        step();
        NextSetller();

        Logger.endFunctionLog();
    }

    /**
     * A megh˜v˜sakor futtat egy k˜rt a j˜t˜kb˜l, ebben megh˜vja a k˜rnyezeti v˜ltoz˜sokat ˜s l˜pteti 
     * a j˜t˜kban el?fordul˜ l˜ptethet? objektumokat a megfelel? sorrendben 
     * (sorrend: Napvihar ˜s napf˜ny esem˜nyek, megkerg˜lt ?rkapuk, robotok, uf˜k, v˜g˜l telepesek).
     */
    private void step()
    {

        if(sunstormTimer == 0)
        {
            sunstormCall();
            sunstormTimer = getRandomNumber(4,9);
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
     * Hozz˜adja a param˜terk˜nt kapott telepest a controller telepeseket tartalmaz˜ list˜j˜hoz
     * @param s a hozz˜adand˜ telepes
     */
    public void addSettler(Settler s)
    {
        settlers.add(s);
    }

        /**
     * Jegyzi egy telepes hal˜l˜t
     * 
     * Ha egy telepes meghal, akkor megh˜vja  ezt a Settler_die() f˜ggv˜nyt, 
     * ami elt˜vol˜tja a settlers list˜b˜l a param˜terk˜nt kapott Settler objektumot
     * ˜s ˜t˜rja a Settler_alive v˜ltoz˜ ˜rt˜k˜t.
     * Ha nem marad t˜bb settler, j˜t˜k v˜g˜t h˜v.
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
     * A j˜t˜k v˜g˜t kezel˜ f˜ggv˜ny, be˜ll˜tja a gameIsOn attributumot ˜false˜-ra. 
     * Amikor a settlers list˜ban nincsen m˜r telepes vagy a b˜zis meg˜p˜l
     * akkor ez a f˜ggv˜ny megh˜v˜dik, hogy v˜get vessen a j˜t˜knak, illetve k˜v˜lr˜l is h˜vhat˜.
     */
    public void endGame()
    {   
        Logger.startFunctionLogComment(this, "endGame", "");
        gameIsOn = false;
        Logger.endFunctionLog();

    }

    /**
     * a param˜terk˜nt megkapott robotot hozz˜adja a robots list˜j˜hoz
     * @param r
     */
    public void addRobot(Robot r)
    {
        Logger.startFunctionLogComment(this, "addRobot", "");
        robots.add(r);
        Logger.endFunctionLog();
    }

    /**
     * ha egy robot meghal, akkor megh˜vja ezt a Robot_die() f˜ggv˜nyt,
     *  ami elt˜vol˜tja a robots list˜b˜l az param˜terk˜nt kapott Robot objektumot.
     * @param r az elt˜volitand˜ robot.
     */
    public void robotDie(Robot r)
    {
        Logger.startFunctionLogComment(this, "robotDie", "");

        robots.remove(r);

        Logger.endFunctionLog();
    }

     /**
     * Hozz˜adja a param˜terk˜nt kapott uf˜t a controller ufo-kat tartalmaz˜ list˜j˜hoz
     * @param u
     */
    public void addUfo(Ufo ufo)
    {
        ufos.add(ufo);
    }

    /**
     * elt˜vol˜tja a kapott uf˜t a Controller ufos list˜j˜b˜l, ha  a kapott ufo benne volt.
     * @param u ufo
     */
    public void ufoDie(Ufo u)
    {
        ufos.remove(u);
    }

    /**
     * a param˜terk˜nt megkapott teleportkaput hozz˜adja a stargates list˜j˜hoz
     * @param s kapu
     */
    public void addStargate(Stargate s)
    {
        //TODO PREFIXET FELVENNI

        orbits.add(s);
        stargates.add(s);

    }

    /**
     *  ha egy teleportkaput meghal, akkor a destuktor˜nak a megh˜v˜sa el?tt, 
     *  megh˜vja  ezt a Stargate_die() f˜ggv˜nyt, ami elt˜vol˜tja a stargates list˜b˜l az adott Stargate objektumot.
     * @param s
     */
    public void stargateDie(Stargate s)
    {
        orbits.remove(s);
        stargates.remove(s);
    }

    /**
     * Hozz˜adadja a param˜terk˜nt kapott Orbitot az orbits list˜hoz.
     * @param o
     */
    public void addOrbit(Orbit o)
    {
        orbits.add(o);
        //TODO ezt tiszt˜zni
        //ideiglenes a prto idejere, konnyebb eleres erdekeben
        if(o.getClass() == Asteroid.class)
            asteroids.add((Asteroid)o);
        else if(o.getClass() == Stargate.class)
            stargates.add((Stargate)o);
    }
    /**
     * Kiveszi a param˜terk˜nt kapott Orbitot az orbits list˜b˜l.
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
     * Megh˜vja minden aszteroid˜n a sunstormArrive met˜dust.
     */
    private void sunstormCall()
    {
        Logger.startFunctionLogComment(this, "sunstormCall", "");

        int stormAreaSize = 20;
        int coords[] = new int[4];
        coords[0] = getRandomNumber(0, mapSize - stormAreaSize);
        coords[1] = getRandomNumber(0, mapSize - stormAreaSize);
        coords[2] = coords[0] + stormAreaSize;
        coords[3] = coords[1] + stormAreaSize;

        //a lista módosulna ezért másolni kell és azon végig menni
        ArrayList<Orbit> copy = new ArrayList<>();
        copy.addAll(orbits);
        for(Orbit o: copy){
            o.sunstormArrive(coords);
        }
        Logger.endFunctionLog();
    }

    /**
     * Megh˜vja az ˜sszes t˜rolt Orbit SunLightArrive()-met˜dus˜t, v˜letlen sorsolt koordin˜t˜kkal
     */
    private void sunLightCall()
    {
        Logger.startFunctionLogComment(this, "sunLightCall", "");

        int lightAreaSize = 30;
        int coords[] = new int[4];
        coords[0] = getRandomNumber(0, mapSize - lightAreaSize);
        coords[1] = getRandomNumber(0, mapSize - lightAreaSize);
        coords[2] = coords[0] + lightAreaSize;
        coords[3] = coords[1] + lightAreaSize;

        for(Orbit o: orbits){
            o.sunLightArrive(coords);
        }
        Logger.endFunctionLog();
    }

    /**
     * Eggyel cs˜kkenti a sunstorm_time ˜rt˜k˜t.
     */
    private void sunstormDecrease()
    {
        Logger.startFunctionLogComment(this, "sunstormDecrease", "");
        sunstormTimer--;
        Logger.endFunctionLog();
    }

    /**
     * megmondja hány kör van vissza a következõ napviharig
     * @return hátralévõ körök
     */
    public int getSunstormTime()
    {
        return sunstormTimer;
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

    private static IDrawable UI;
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

