package logic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import grafikus.IDrawable;


/**
 * Az Objektum felel�ss�ge a j�t�kmenetet vez�relni. � tartja sz�mon a teljes p�lya objektumait, �s azokat amelyeket kell, l�pteti �ket k�r�nk�nt,. Inicializ�lja, elind�tja futtatja �s megfelel� k�r�lm�nyek eset�n le�ll�tja a j�t�kot. 
 * � h�v napvihart �s napf�nyt, az aszteroid�kra, stargatekre, � h�vja meg a robotok, uf�k, stargatek �s telepesek l�p�seit.
 * Ha minden settler meghal befejezi a j�t�kot.
 */
public class Controller implements java.io.Serializable {

	private static final long serialVersionUID = -425692066249723082L;

	/**
     * Megadja hogy kell e futtatni a j�t�kot vagy sem.
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
     * A j�t�kban �ppen aktu�lisan l�v? �sszes Ufo t�pus� objektumot t�rol� priv�t lista
     */
    private List<Ufo> ufos = new ArrayList<>();

    /**
     * A j�t�kban tal�lhat� �sszes Orbit egy list�ban t�rolva
     */
    private List<Orbit> orbits = new ArrayList<>();

    /**
     * A j�t�kban tal�lhat� �sszes Stargate egy list�ban t�rolva
     */
    private List<Stargate> stargates = new ArrayList<>();

    /**
     * megkora a p�lya, gener�l�shoz �s napf�nyhez kell
     */
    private int mapSize = 100;

    /**
     * Megszabja, hogy h�ny k�r�nk�nt legyen napvihar
     */
    private int sunstormTime = 8;

    /**
     * h�ny k�r van m�g h�tra a napviharig
     */
    private int sunstormTimer;


    /**
     * Jatek elmentese szerializalassal
     * @param path fajl neve
     */
    public void saveGame(String path)
    {
       	try 
		{
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Controller.getInstance());
            out.close();
            fileOut.close();
            System.out.printf("A palya ment�se sikeres volt ide: " + path);
        }
		catch (Exception i) 
		{
            System.out.printf("A palya ment�se sikertelen volt: "+ path);
            i.printStackTrace();
        }
    }

    /**
     * Jatek betoltese szerializalassal
     * @param path fajl neve
     */
    public void loadGame(String path)
    {
        Controller load;
        try 
		{
            FileInputStream fileIn = new FileInputStream(path);
         	ObjectInputStream in = new ObjectInputStream(fileIn);
        	load = (Controller) in.readObject();
        	in.close();
         	fileIn.close();
            instance = load;
            instance.NextSetller();
       	} 
		catch (Exception i) 
		{
        	System.out.println("Betolt�s sikertelen: "+path);
         	i.printStackTrace();
       	}
    }

    /**
     * inicializ�lja a p�ly�t, a maximum inventory kapacit�st, valamint a napviharok k�z�tt eltel� id�t.
     */
    public void initGame()
    {
        sunstormTimer = sunstormTime;

        int asteroidCount = 100;
        int settlerCount = 1;
        int ufoCount = 1;

        //aszteroid�k gener�l�sa
        for (int i = 0; i < asteroidCount; i++) 
        {
            
            addAsteroid(new Asteroid(
                getRandomNumber(0, mapSize), 
                getRandomNumber(0, mapSize), 
                getRandomNumber(1, 5), generateRandomResource(), true));
        }

        // k�zeli aszteroid�k �sszekapcsol�sa
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

        //settlerek letrehozasa
        for (int i = 0; i < settlerCount; i++)
        {
            //addSettler(new Settler(asteroids.get(getRandomNumber(0, asteroidCount-1))));

             //kontruktorban hozz�adja mag�t a list�hoz
            new Settler(asteroids.get(getRandomNumber(0, asteroidCount-1)));
        }

        //Ufok gener�l�sa
        for (int i = 0; i < ufoCount; i++)
        {
            //addUfo(new Ufo(asteroids.get(getRandomNumber(0, asteroidCount-1))));

            //kontruktorban hozz�adja mag�t a list�hoz
            new Ufo(asteroids.get(getRandomNumber(0, asteroidCount-1)));
        }
    }

    /**
     * k�t aszteroida t�vols�g�nak kisz�mol�sa a gener�l�shoz, szomsz�d �sszep�ros�t�skor
     * @param a1 els� aszteroida
     * @param a2 m�sodik aszteroida
     * @return t�vols�guk
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
     * random sz�m gener�l�sa megadott intervallumban
     * @param min legkisebb sz�m
     * @param max legnagyobb sz�m
     * @return random sz�m
     */
    private int getRandomNumber(int min, int max)
    {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Random nyersanyag l�trehoz�sa a p�lya gener�l�shoz
     * @return random t�pus� nyersanyag (Carbon, ICe Iron, Uran)
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
     * Elind�tja a j�t�kot: be�ll�tja a gameIsOn attributum �rt�k�t true-ra.
     * A GameEnd() met�dus megh�v�s�ig folyamatosan a saj�t Step() met�dus�t h�vogatja.
     */
    public void startGame()
    {
        gameIsOn = true;

        initGame();
        step();
        NextSetller();
    }

    /**
     * A megh�v�sakor futtat egy k�rt a j�t�kb�l, ebben megh�vja a k�rnyezeti v�ltoz�sokat �s l�pteti 
     * a j�t�kban el?fordul� l�ptethet? objektumokat a megfelel? sorrendben 
     * (sorrend: Napvihar �s napf�ny esem�nyek, megkerg�lt ?rkapuk, robotok, uf�k, v�g�l telepesek).
     */
    private void step()
    {

        if(sunstormTimer == 0)
        {
            sunstormCall();
            sunstormTimer = sunstormTime;
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
     * Hozz�adja a param�terk�nt kapott telepest a controller telepeseket tartalmaz� list�j�hoz
     * @param s a hozz�adand� telepes
     */
    public void addSettler(Settler s)
    {
        settlers.add(s);
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
        settlers.remove(s);

        if(settlers.size()==0)
            endGame();
    }

    /**
     * A j�t�k v�g�t kezel� f�ggv�ny, be�ll�tja a gameIsOn attributumot �false�-ra. 
     * Amikor a settlers list�ban nincsen m�r telepes vagy a b�zis meg�p�l
     * akkor ez a f�ggv�ny megh�v�dik, hogy v�get vessen a j�t�knak, illetve k�v�lr�l is h�vhat�.
     */
    public void endGame()
    {   
        gameIsOn = false;
    }

    /**
     * a param�terk�nt megkapott robotot hozz�adja a robots list�j�hoz
     * @param r
     */
    public void addRobot(Robot r)
    {
        robots.add(r);
    }

    /**
     * ha egy robot meghal, akkor megh�vja ezt a Robot_die() f�ggv�nyt,
     *  ami elt�vol�tja a robots list�b�l az param�terk�nt kapott Robot objektumot.
     * @param r az elt�volitand� robot.
     */
    public void robotDie(Robot r)
    {
        robots.remove(r);
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
     * elt�vol�tja a kapott uf�t a Controller ufos list�j�b�l, ha  a kapott ufo benne volt.
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
        int stormAreaSize = 20;
        int coords[] = new int[4];
        coords[0] = getRandomNumber(0, mapSize - stormAreaSize);
        coords[1] = getRandomNumber(0, mapSize - stormAreaSize);
        coords[2] = coords[0] + stormAreaSize;
        coords[3] = coords[1] + stormAreaSize;

        //a lista m�dosulna ez�rt m�solni kell �s azon v�gig menni
        ArrayList<Orbit> copy = new ArrayList<>();
        copy.addAll(orbits);
        for(Orbit o: copy){
            o.sunstormArrive(coords);
        }
    }

    /**
     * Megh�vja az �sszes t�rolt Orbit SunLightArrive()-met�dus�t, v�letlen sorsolt koordin�t�kkal
     */
    private void sunLightCall()
    {
        int lightAreaSize = 30;
        int coords[] = new int[4];
        coords[0] = getRandomNumber(0, mapSize - lightAreaSize);
        coords[1] = getRandomNumber(0, mapSize - lightAreaSize);
        coords[2] = coords[0] + lightAreaSize;
        coords[3] = coords[1] + lightAreaSize;

        for(Orbit o: orbits){
            o.sunLightArrive(coords);
        }
    }

    /**
     * Eggyel cs�kkenti a sunstorm_time �rt�k�t.
     */
    private void sunstormDecrease()
    {
        sunstormTimer--;
    }

    /**
     * megmondja h�ny k�r van vissza a k�vetkez� napviharig
     * @return h�tral�v� k�r�k
     */
    public int getSunstormTime()
    {
        return sunstormTimer;
    }


    ///Singleton stuff
    private static Controller instance;

    private Controller()
    {
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
                " Resources: Uran: "+ futo.getInventory().getNumOfUran() + " Ice: " + futo.getInventory().getNumOfIce() + 
                " Iron: "+ futo.getInventory().getNumOfIron() + " Carbon: "  + futo.getInventory().getNumOfCarbon() + 
                " Gates: " + futo.getStargates().size()
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

