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
     * a j�t�kban  �ppen aktu�lisan l�tez� �sszes Settler t�pus� objektumot t�rol� priv�t lista.
     */

    private List<Settler> settlers = new ArrayList<>();
    
    /**
     * a j�t�kban �ppen aktu�lisan l�v� �sszes Robot t�pus� objektumot t�rol� priv�t lista.
     */
    private List<Robot> robots = new ArrayList<>();

    /**
     * A j�t�kban tal�lhat� �sszes Orbit egy list�ban t�rolva
     */
    private List<Orbit> orbits = new ArrayList<>();

    /**
     * A j�t�kban tal�lhat� �sszes Stargate egy list�ban t�rolva
     */
    private List<Stargate> stargates = new ArrayList<>();

    /**
     * A j�t�kban �ppen aktu�lisan l�v? �sszes Ufo t�pus� objektumot t�rol� priv�t lista
     */
    private List<Ufo> ufos = new ArrayList<>();

    /**
     * Megszabja, hogy h�ny k�r�nk�nt legyen napvihar
     */
    private int sunstormTime;

    /**
     * h�ny k�r van m�g h�tra a napviharig
     */
    private int sunstormTimmer;

    public void initGame()
    {
        // TODO idokoz random beallitasa majd mashol
        sunstormTime = 8;
    }

    /**
     * Elind�tja �s futtatja a j�t�kot.
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
     * A megh�v�sakor futtat egy k�rt a j�t�kb�l, ebben megh�vja a k�rnyezeti v�ltoz�sokat �s l�pteti 
     * a j�t�kban el?fordul� l�ptethet? objektumokat a megfelel? sorrendben 
     * (sorrend: Napvihar �s napf�ny esem�nyek, megkerg�lt ?rkapuk, robotok, uf�k, v�g�l telepesek).
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
     * le�ll�tja a j�tkot.
     */
    public void endGame()
    {   
        Logger.startFunctionLogComment(this, "endGame", "");
        gameIsOn = false;
        Logger.endFunctionLog();

    }

    /**
     * a param�terk�nt megkapott robotot hozz�adja a robots list�j�hoz
     * @param r
     */
    public void addRobot(Robot r)
    {
        Logger.startFunctionLogComment(this, "addRobot", "");
        robots.add(r);

        //TODO be�rni robotnak a prefixet; t�r�lni majd

        Logger.endFunctionLog();
    }

    /**
     * Jegyzi egy telepes hal�l�t
     * 
     * Ha egy telepes meghal, akkor megh�vja  ezt a Settler_die() f�ggv�nyt, 
     * ami elt�vol�tja a settlers list�b�l a param�terk�nt kapott Settler objektumot
     * �s �t�rja a Settler_alive v�ltoz� �rt�k�t.
     * Ha nem marad t�bb settler, j�t�k v�g�t h�v.
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
     * ha egy robot meghal, akkor megh�vja ezt a Robot_die() f�ggv�nyt, ami elt�vol�tja a robots list�b�l az param�terk�nt kapott
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
     * Hozz�adja a param�terk�nt kapott telepest a controller telepeseket tartalmaz� list�j�hoz
     * @param s
     */
    public void addSettler(Settler s)
    {
        settlers.add(s);
    }

     /**
     * Hozz�adja a param�terk�nt kapott uf�t a controller ufo-kat tartalmaz� list�j�hoz
     * @param u
     */
    public void addUfo(Ufo ufo)
    {
        ufos.add(ufo);
    }

    /**
     * elt�vol�tja a kapott uf�t a Controller ufos list�j�b�l, ha   a kapott ufo benne volt.
     * @param u ufo
     */
    public void ufoDie(Ufo u)
    {
        ufos.remove(u);
    }

    /**
     * a param�terk�nt megkapott teleportkaput hozz�adja a stargates list�j�hoz
     * @param s kapu
     */
    public void addStargate(Stargate s)
    {
        //TODO PREFIXET FELVENNI

        orbits.add(s);
        stargates.add(s);

    }

    /**
     *  ha egy teleportkaput meghal, akkor a destuktor�nak a megh�v�sa el?tt, 
     *  megh�vja  ezt a Stargate_die() f�ggv�nyt, ami elt�vol�tja a stargates list�b�l az adott Stargate objektumot.
     * @param s
     */
    public void stargateDie(Stargate s)
    {
        orbits.remove(s);
        stargates.remove(s);
    }

    /**
     * Hozz�adadja a param�terk�nt kapott Orbitot az orbits list�hoz.
     * @param o
     */
    public void addOrbit(Orbit o)
    {


        orbits.add(o);

        //TODO be�rni a prefix�t.
        //TODO ez alul furcsa... nekem nem tetszik. mert add orbitot h�v stargate, vagy asteroid be�r�s is f�ljebb

        //ideiglenes a prto idejere, konnyebb eleres erdekeben
        if(o.getClass() == Asteroid.class)
            asteroids.add((Asteroid)o);
        else if(o.getClass() == Stargate.class)
            stargates.add((Stargate)o);
    }
    /**
     * Kiveszi a param�terk�nt kapott Orbitot az orbits list�b�l.
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
     * Megh�vja minden aszteroid�n a sunstormArrive met�dust.
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
     * Megh�vja minden aszteroid�n a sunLightArrive met�dust.
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
     * Eggyel cs�kkenti a sunstorm_time �rt�k�t.
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
     * L�trhoz �s visszaadja, vagy ha m�r volt l�trehozva akkor visszaadja az els� l�trehozott controllert.
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
     *  tesztel�shez sz�ks�ges, �j p�ld�nyt ad vissza mindig.
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
    
//PROTO F�GGV�NYEK INNENT�L///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
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
     * Index alapj�n visszat�r a settlers lista adott elem�vel
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
     * Index alapj�n visszat�r az orbits lista adott elem�vel
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
            //core lehet nulla. lehet bel�le baj. el�bb kell kezelni.
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
     * be�ll�tja a game is on v�lt� �rt�k�t
     */
    public void setGameIsOn(boolean a) { gameIsOn = a;}

    /**
     * visszaadja a game is on v�lt� �rt�k�t
     */
    public boolean getGameIsOn() {return gameIsOn;}

    /**
     * ki�rja az �sszes aszteroid�t
     */
    public void listAsteroids()
    {
        for (Asteroid futo : asteroids) {
            //core lehet nulla. lehet bel�le baj. el�bb kell kezelni.
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
     * Visszadja az aktu�lisan aktiv robotok darabszamat
     * @return int robotok szama
     */
    public int numOfRobots()
    {
        return robots.size();
    }
    /**
     * Visszadja az aktu�lisan akt�v robotok darabsz�m�t
     * @return az utolso robot a robots listaban
     */
    public Robot lastRobot()
    {
        return robots.get(robots.size()-1);
    }
}

