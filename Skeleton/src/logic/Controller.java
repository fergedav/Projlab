package logic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import grafikus.IDrawable;


/**
 * Az Objektum felelõssége a játékmenetet vezérelni. Õ tartja számon a teljes pálya objektumait, és azokat amelyeket kell, lépteti õket körönként,. Inicializálja, elindítja futtatja és megfelelõ körülmények esetén leállítja a játékot. 
 * Õ hív napvihart és napfényt, az aszteroidákra, stargatekre, õ hívja meg a robotok, ufók, stargatek és telepesek lépéseit.
 * Ha minden settler meghal befejezi a játékot.
 */
public class Controller implements java.io.Serializable {

	private static final long serialVersionUID = -425692066249723082L;

    /**
     * Lekérdezhetõ játék állapot
     */
    public enum GameState
    {
        NotStarted, // Controller létrejöttekor, alapkérték
        Running, //ha a játék elindult, startGame()
        Lost, // settlerDie() ha az utolsó settler is meghalt
        Won //játék vége nyertek EndGame()
    }

    /**
     * Játék aktuális állapota
     */
    private GameState gameState = GameState.NotStarted;

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
     * A játékban található összes Asteroid egy listában tárolva
     */
    public List<Asteroid> asteroids = new ArrayList<>();

    /**
     * megkora a pálya, generáláshoz és napfényhez kell
     */
    private int mapSize = 100;

    /**
     * Megszabja, hogy hány körönként legyen napvihar
     */
    private int sunstormTime = 8;

    /**
     * hány kör van még hátra a napviharig
     */
    private int sunstormTimer;

    /** 
     * Felület ami felelõs a grafikus megjelenítésért
     */
    private static IDrawable UI;

    
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
            System.out.printf("A palya mentése sikeres volt ide: " + path);
        }
		catch (Exception i) 
		{
            System.out.printf("A palya mentése sikertelen volt: "+ path);
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
        	System.out.println("Betoltés sikertelen: "+path);
         	i.printStackTrace();
       	}
    }

    /**
     * inicializálja a plyát, a maximum inventory kapacitást, valamint a napviharok között eltelõ idõt.
     */
    public void initGame(int settlercount)
    {
        sunstormTimer = sunstormTime;

        int asteroidCount = 3;
        int settlerCount = settlercount;
        int ufoCount = 0;

        //aszteroidák generálása
        for (int i = 0; i < asteroidCount; i++) 
        {
            
            addAsteroid(new Asteroid(
                getRandomNumber(0, mapSize), 
                getRandomNumber(0, mapSize), 
                getRandomNumber(1, 5), generateRandomResource(), true));
        }

        // közeli aszteroidák összekapcsolása
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

             //kontruktorban hozzáadja magát a listához
            new Settler(asteroids.get(getRandomNumber(0, asteroidCount-1)));
        }

        //Ufok generálása
        for (int i = 0; i < ufoCount; i++)
        {
            //addUfo(new Ufo(asteroids.get(getRandomNumber(0, asteroidCount-1))));

            //kontruktorban hozzáadja magát a listához
            new Ufo(asteroids.get(getRandomNumber(0, asteroidCount-1)));
        }
    }

    /**
     * kkt aszteroida távolságának kiszámolása a generáláshoz, szomszéd összepárosításkor
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
     * @return random típusú nyersanyag (Carbon, Ice Iron, Uran)
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
     * Elindítja a játékot
     */
    public void startGame(int settlercount)
    {
        //Játék állapot változtatása
        gameState = GameState.Running;
        //pálya felépítése
        initGame(settlercount);
        //Gép vezérelt elemek léptetése
        step();
        //settler rajzolása
        NextSetller();
    }

    /**
     * A meghívásakor futtat egy kört a játékból, ebben meghívja a környezeti változásokat és lépteti 
     * a játékban elõforduló léptethetõ objektumokat a megfelelõ sorrendben 
     * (sorrend: Napvihar és napfény események, megkergült ûrkapuk, robotok, ufók).
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
    }

    /**
     * Hozzáadja a paraméterként kapott telepest a controller telepeseket tartalmaza listájához
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
     * Ha nem marad több settler, játék végét hív.
     * @param s meghallt settler
     */
    public void settlerDie(Settler s)
    {
        settlers.remove(s);

        if(settlers.size() == 0)
        {
            gameState = GameState.Lost;
        }
    }

    /**
     * a paraméterként megkapott robotot hozzáadja a robots listájához
     * @param r új robot
     */
    public void addRobot(Robot r)
    {
        robots.add(r);
    }

    /**
     * ha egy robot meghal, akkor meghívja ezt a Robot_die() függvényt,
     * ami eltávolítja a robots listából az paraméterként kapott Robot objektumot.
     * @param r az eltávolitandó robot.
     */
    public void robotDie(Robot r)
    {
        robots.remove(r);
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
        orbits.add(s);
        stargates.add(s);
    }

    /**
     *  ha egy teleportkaput meghal, akkor a destuktorának a meghívása elõtt, 
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
        else if(o.getClass() == Stargate.class)
            stargates.remove(o);
    }

    /**
     * Meghívja minden aszteroidán a sunstormArrive metódust.
     */
    private void sunstormCall()
    {
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
    }

    /**
     * Meghívja az összes tárolt Orbit SunLightArrive()-metódusát, véletlen sorsolt koordinátákkal
     */
    private void sunLightCall()
    {
        int lightAreaSize = 30;
        int coords[] = new int[4];
        coords[0] = getRandomNumber(0, mapSize - lightAreaSize);
        coords[1] = getRandomNumber(0, mapSize - lightAreaSize);
        coords[2] = coords[0] + lightAreaSize;
        coords[3] = coords[1] + lightAreaSize;

        for(Orbit o: orbits)
        {
            o.sunLightArrive(coords);
        }
    }

    /**
     * Eggyel csökkenti a sunstorm_time értékét.
     */
    private void sunstormDecrease()
    {
        sunstormTimer--;
    }

    /**
     * Megmondja hány kör van vissza a következõ napviharig.
     * @return hátralévõ körök
     */
    public int getSunstormTime()
    {
        return sunstormTimer;
    }

    /**
     * Új Aszteroida berigisztrálása a Controllerbe
     * @param a új Aszteroida
     */
    public void addAsteroid(Asteroid a) 
    { 
        asteroids.add(a);
        orbits.add(a);
    }

    /**
     * Grafikus felület referenciájának beállítása
     * @param ui felület ami a megjelenítást végzi
     */
    public void setFrame(IDrawable ui)
    {
        UI = ui;
    }

    /**
     * Értesíti a grafikus felületet, hogy lehet rajzolni a következõ Settlert.
     * növeli a settlerCounter-t, ha a körbe az összes Settler megvolt léptepi a többi játék elemet
     * és reseteli a settlerCounter-t
     */
    public void NextSetller()
    {
        Settler TheChosenOne = null;
        boolean WeGotSomebodyToStep = false;
        for (Settler s : settlers) {
            if(!s.getHasStepped()){
                TheChosenOne = s;
                WeGotSomebodyToStep = true;
                break;
            }
        }
        // ha már mindenki lépett, reseteljük a settlereket, és léptetjük a pályát.
        if (!WeGotSomebodyToStep){
            for (Settler s : settlers) {
                s.InvertHasStepped();
            }           
            step();
            
            //lista lehet üres, mert mindenki meghalt a step után
            if(settlers.size() > 0){
            TheChosenOne = settlers.get(0);
            }
        }

        // ezen a ponton, ha a TheChosenOne == null, akkor a játék véget ért, és a gameStatus = GameStatus.Lost kell legyen!!
        // Ha a TheChosenOne == null, de a játk nem ért véget, akkor tuti kiakadás jön a grafikus résznél.
        if(TheChosenOne != null)
        TheChosenOne.InvertHasStepped();
        UI.Draw(TheChosenOne);
    }

    public void endGame() 
    {
        gameState = GameState.Won;
	}

    public GameState getGameState()
    {
        return gameState;
    }

    ///Singleton stuff
    private static Controller instance;

    private Controller(){}

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
     *  ÚJ játék esetén teljes reset, új controller, id számlálók reset
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
}

