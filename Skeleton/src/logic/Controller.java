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
     * Lek�rdezhet� j�t�k �llapot
     */
    public enum GameState
    {
        NotStarted, // Controller l�trej�ttekor, alapk�rt�k
        Running, //ha a j�t�k elindult, startGame()
        Lost, // settlerDie() ha az utols� settler is meghalt
        Won //j�t�k v�ge nyertek EndGame()
    }

    /**
     * J�t�k aktu�lis �llapota
     */
    private GameState gameState = GameState.NotStarted;

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
     * A j�t�kban tal�lhat� �sszes Asteroid egy list�ban t�rolva
     */
    public List<Asteroid> asteroids = new ArrayList<>();

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
     * Fel�let ami felel�s a grafikus megjelen�t�s�rt
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
     * inicializ�lja a ply�t, a maximum inventory kapacit�st, valamint a napviharok k�z�tt eltel� id�t.
     */
    public void initGame(int settlercount)
    {
        sunstormTimer = sunstormTime;

        int asteroidCount = 3;
        int settlerCount = settlercount;
        int ufoCount = 0;

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
     * kkt aszteroida t�vols�g�nak kisz�mol�sa a gener�l�shoz, szomsz�d �sszep�ros�t�skor
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
     * @return random t�pus� nyersanyag (Carbon, Ice Iron, Uran)
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
     * Elind�tja a j�t�kot
     */
    public void startGame(int settlercount)
    {
        //J�t�k �llapot v�ltoztat�sa
        gameState = GameState.Running;
        //p�lya fel�p�t�se
        initGame(settlercount);
        //G�p vez�relt elemek l�ptet�se
        step();
        //settler rajzol�sa
        NextSetller();
    }

    /**
     * A megh�v�sakor futtat egy k�rt a j�t�kb�l, ebben megh�vja a k�rnyezeti v�ltoz�sokat �s l�pteti 
     * a j�t�kban el�fordul� l�ptethet� objektumokat a megfelel� sorrendben 
     * (sorrend: Napvihar �s napf�ny esem�nyek, megkerg�lt �rkapuk, robotok, uf�k).
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
     * Hozz�adja a param�terk�nt kapott telepest a controller telepeseket tartalmaza list�j�hoz
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
     * Ha nem marad t�bb settler, j�t�k v�g�t h�v.
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
     * a param�terk�nt megkapott robotot hozz�adja a robots list�j�hoz
     * @param r �j robot
     */
    public void addRobot(Robot r)
    {
        robots.add(r);
    }

    /**
     * ha egy robot meghal, akkor megh�vja ezt a Robot_die() f�ggv�nyt,
     * ami elt�vol�tja a robots list�b�l az param�terk�nt kapott Robot objektumot.
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
     *  ha egy teleportkaput meghal, akkor a destuktor�nak a megh�v�sa el�tt, 
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
        else if(o.getClass() == Stargate.class)
            stargates.remove(o);
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

        for(Orbit o: orbits)
        {
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
     * Megmondja h�ny k�r van vissza a k�vetkez� napviharig.
     * @return h�tral�v� k�r�k
     */
    public int getSunstormTime()
    {
        return sunstormTimer;
    }

    /**
     * �j Aszteroida berigisztr�l�sa a Controllerbe
     * @param a �j Aszteroida
     */
    public void addAsteroid(Asteroid a) 
    { 
        asteroids.add(a);
        orbits.add(a);
    }

    /**
     * Grafikus fel�let referenci�j�nak be�ll�t�sa
     * @param ui fel�let ami a megjelen�t�st v�gzi
     */
    public void setFrame(IDrawable ui)
    {
        UI = ui;
    }

    /**
     * �rtes�ti a grafikus fel�letet, hogy lehet rajzolni a k�vetkez� Settlert.
     * n�veli a settlerCounter-t, ha a k�rbe az �sszes Settler megvolt l�ptepi a t�bbi j�t�k elemet
     * �s reseteli a settlerCounter-t
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
        // ha m�r mindenki l�pett, resetelj�k a settlereket, �s l�ptetj�k a p�ly�t.
        if (!WeGotSomebodyToStep){
            for (Settler s : settlers) {
                s.InvertHasStepped();
            }           
            step();
            
            //lista lehet �res, mert mindenki meghalt a step ut�n
            if(settlers.size() > 0){
            TheChosenOne = settlers.get(0);
            }
        }

        // ezen a ponton, ha a TheChosenOne == null, akkor a j�t�k v�get �rt, �s a gameStatus = GameStatus.Lost kell legyen!!
        // Ha a TheChosenOne == null, de a j�tk nem �rt v�get, akkor tuti kiakad�s j�n a grafikus r�szn�l.
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
     *  �J j�t�k eset�n teljes reset, �j controller, id sz�ml�l�k reset
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

