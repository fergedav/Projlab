package skeleton;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import logic.*;

public class SkeletonMain {
    public static void main(String[] args) 
    {
        while(SelectCategory());
    }

    public static int SelectOption(String msg, int min, int max)
    {
        int n = -1;
        BufferedReader r = new BufferedReader (new InputStreamReader (System.in));
        while(true)
        {
            try {
                System.out.println("***"+msg+"***");                
                n = Integer.parseInt(r.readLine());

                if(n >= min && n <=max)
                    return n;
                    System.out.println("Hib�s sorsz�m!");   
                
                
            } catch (Exception e) {
                System.out.println("Hiba: " + e.getMessage());
            }
            
        }
    }

    public static boolean SelectCategory()
    {
        System.out.println("1. Mozg�s");
        System.out.println("2. F�r�s");
        System.out.println("3. B�ny�szat");
        System.out.println("4. Anyag visszarak�sa");
        System.out.println("5. �p�t�s");
        System.out.println("6. Csillagkapu dolgok");
        System.out.println("7. Napvihar");
        System.out.println("8. Napf�ny �rkez�se");
        System.out.println("9. Robot Step\n");
        System.out.println("10. Kil�p�s");
        
        int category = SelectOption("V�laszd ki a kateg�ria sorsz�m�t: (1-10)", 1,10);

        switch (category) {
            case 1:
                _1_Movement();
                break;
            case 2:
                _2_Digging();
                break;
            case 3:
                _3_Mining();
                break;
            case 4:
                _4_ReplaceResource();
                break;
            case 5:
                _5_Build();
                break;
            case 6:
                _6_Stargate();
                break;
            case 7:
                _7_SunStorm();
                break;
            case 8:
                _8_SunLight();
                break;
            case 9:
                _9_Robot();
                break;
            case 10:
                System.out.println("A program le�ll.");
                return false;
            default:
                break;
        }

        return true;
    }

    public static void _1_Movement()
    {
        System.out.println("Mozg�s esetek:");
        System.out.println("\t1. Move On Asteroid");
        System.out.println("\t2. Move On Offline Gate");
        System.out.println("\t3. Move On Online Gate");

        int opt = SelectOption("V�laszd ki a use-case sorsz�m�t: (1-3)", 1,3);
        switch(opt)
        {
            case 1:
            _1_1_MoveOnAsteroid();
                break;
            case 2:
            _1_2_MoveOnOfflineGate();
                break;
            case 3:
            _1_3_MoveOnOnlineGate();
                break;
            default:
                break;
        }
    }

    /**
     * A telepes (játékos) az aktuális aszteroidáról egy szomszédos aszteroidára lép.
     * A program kiírja a konzolra az aszteroidára lépéshez kapcsolódó függvény hívásokat.
     */
    public static void _1_1_MoveOnAsteroid()
    {
        Logger.formatPrint("Init");

        /** A teszt lefutásához szükséges objektumok inicializálása. */
        Asteroid a1 = new Asteroid(0, 0, 8, null);
        Asteroid a2 = new Asteroid(1, 0, 8, null);
        Settler s = new Settler(a1);
        
        /** Így az s telepesnek van hová lépnie, a két előbb létrehozott aszteroida ezáltal egymás szomszédai lesznek. */
        a1.addNeighbour(a2);
        a2.addNeighbour(a1);

        Logger.formatPrint("_1_1_MoveOnAsteroid");

        /**A hívási lánc első eleme, amiből elindul az egész eset. */
        s.move(0);

        Logger.formatPrint("Teszt v�ge");
    }

    /**
     * A telepes (játékos) olyan teleportkapura lép, aminek még nincs lerakva a párja.
     * A program kiírja a konzolra a teleportkapura lépéshez kapcsolódó függvény hívásokat.
     */
    public static void _1_2_MoveOnOfflineGate()
    {
        Logger.formatPrint("Init");

        /** A teszt lefutásához szükséges objektumok inicializálása. Két csillagkaput kell létrehozzunk, mert a lerakott csillagkapunak mindenképp össze kell legyen kötve
         * egy másikkal, még akkor is ha a párja még nincs lerakva egy aszteroida szomszédságába.
        */
        Stargate s1 = new Stargate();
        Stargate s2 = new Stargate();
        Asteroid a1 = new Asteroid(0, 0, 8, null);
        Settler s = new Settler(a1);

        /** A lét teleportkapu ezáltal össze lesz kötve. */
        s1.entagle(s2);
        s2.entagle(s1);

        /** Az s1 kapu  le van helyezve az a1 aszteroida szomszédságába.*/
        a1.addNeighbour(s1);
        s1.place(a1);

        Logger.formatPrint("_1_2_MoveOnOfflineGate");

        /**A hívási lánc első eleme, amiből elindul az egész eset. */
        s.move(0);
        Logger.formatPrint("Teszt v�ge");
    }

    /**
     * A telepes (játékos) olyan teleportkapura lép, aminek már le van rakva a párja.
     * A program kiírja a konzolra az teleportkapura lépéshez és a teleportáláshoz kapcsolódó függvény hívásokat.
     */
    public static void _1_3_MoveOnOnlineGate()
    {
        Logger.formatPrint("Init");

        /** Létre hozzuk a csillagkapukat amiken keresztül teleportálni fogunk */
        Stargate s1 = new Stargate();
        Stargate s2 = new Stargate();

        /** Összekötjük őket, hogy el tudják egymást érni. */
        s1.entagle(s2);
        s2.entagle(s1);

        /** Létrehozzuk az aszteroidákat amikhez a csillagkapuk tartozni fognak. */
        Asteroid a1 = new Asteroid(0, 0, 8, null);
        Asteroid a2 = new Asteroid(0, 0, 8, null);

        /** Létrehozzuk a telepest, aki majd teleportálni fog. */
        Settler s = new Settler(a1);

        /** Elhelyezzük a csillagkapukat az aszteroidák szomszédságába. */
        a1.addNeighbour(s1);
        a2.addNeighbour(s2);
        s1.place(a1);
        s2.place(a2);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");
        /**A hívási lánc első eleme, amiből elindul az egész eset. */
        s.move(0);
        Logger.formatPrint("Teszt v�ge");

    }

    public static void _2_Digging()
    {
        System.out.println("F�r�s esetek:");
        System.out.println("\t1. Simple Digging");
        System.out.println("\t2. Digging And Breakthrought");
        System.out.println("\t3. Digging Breakthrought Uran");
        System.out.println("\t4. Digging Breakthrought Ice");
        System.out.println("\t5. Digging On Stargate");

        int opt = SelectOption("V�laszd ki a use-case sorsz�m�t: (1-5)", 1,5);
        switch(opt)
        {
            case 1:
            _2_1_SimpleDigging();
                break;
            case 2:
            _2_2_DiggingAndBreakthrought();
                break;
            case 3:
            _2_3_DiggingBreakthroughtUran();
                break;
            case 4:
            _2_4_DiggingBreakthroughtIce();
                break;
            case 5:
            _2_5_DiggingOnStargate();
                 break;
        }
    }

    /**
     * A telepes (játékos) egy egységgel mélyebbre ás az aszteroida kérgében, de még nem ásta át teljesen.
     * A program kiírja a konzolra az ásáshoz kapcsolódó függvény hívásokat.
     */
    public static void _2_1_SimpleDigging()
    {
        Logger.formatPrint("Init");
        
        /** Létrehozzuk a telepest aki ás és az aszteroidát amin ás. */
        Asteroid a = new Asteroid(0, 0, 8, null);
        Settler s = new Settler(a);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");
        /**A hívási lánc első eleme, amiből elindul az egész eset. */
        s.digging();

        Logger.formatPrint("Teszt v�ge"); 
    }

    /**
     * A telepes (játékos) egy egységgel mélyebbre ás az aszteroida kérgében, teljesen átásta (az aszteroida nincs napközelben).
     * A program kiírja a konzolra az ásáshoz és az áttöréshez kapcsolódó függvény hívásokat.
     */
    public static void _2_2_DiggingAndBreakthrought()
    {
        Logger.formatPrint("Init");
        
        /** Létrehozzuk a telepest aki ás és az aszteroidát amin ás. Az aszteroida harmadik paramétere, ami a kéregvastagságot jelöli, kezdetnek 1, hogy egy ásással
         * át lehessen törni a kérget és lefuthasson a teszteset többi függvényhívása is.
         */
        Asteroid a = new Asteroid(0, 0, 1, null);
        Settler s = new Settler(a);
        
        a.addTraveler(s);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");
        /** A hívási lánc első eleme, amiből elindul az egész eset. */
        s.digging();

        Logger.formatPrint("Teszt v�ge");
    }

    /**
     * A telepes (játékos) egy egységgel mélyebbre ás az aszteroida kérgében, teljesen átásta, a magja urán (az aszteroida napközelben van, így robban).
     * A program kiírja a konzolra az ásáshoz, az áttöréshez és az urán robbanásához kapcsolódó függvény hívásokat.
     */
    public static void _2_3_DiggingBreakthroughtUran()
    {
        Logger.formatPrint("Init");

        /** Létrehozzuk a telepest aki ás és az aszteroidát amin ás. Az aszteroida harmadik paramétere, ami a kéregvastagságot jelöli, kezdetnek 1, hogy egy ásással
         * át lehessen törni a kérget és lefuthasson a teszteset többi függvényhívása is. Az a2 aszteroida azért kell, hogy a későbbiekben létrehozott robotnak
         * a robbanás hatására, legyen olyan aszteroda, ahova mehet a robbanó aszteroidáról. Ezért állítjuk be a két aszteroidának, hogy szomszédok legyenek. Az a1 első két paramétere
         * most 1, mert ez jelenti azt, hogy napközelben van.
         */
        Asteroid a1 = new Asteroid(1, 1, 1, new Uran());
        Asteroid a2 = new Asteroid(0, 0, 1, null);
        Settler s = new Settler(a1);

        a2.addNeighbour(a1);
        a1.addNeighbour(a2);

        //feltöltjük az Inventorit Uran-nal, Iron-nal és Ice-al
        Inventory seged = s.getInventory();
        seged.addUran(new Uran());
        seged.addIron(new Iron());
        seged.addIron(new Iron());
        seged.addIce(new Ice());
        s.stargate();

        Space space = Space.getNewSpace();
        space.addOrbit(a1);
        space.step();

        Robot r = new Robot();
        r.setLocation(a1);
        a1.addTraveler(r);

        Logger.formatPrint("_2_3_DiggingBreakthroughtUran");
        /** A hívási lánc első eleme, amiből elindul az egész eset. */
        s.digging();

        Logger.formatPrint("Teszt v�ge");
    }

    /**
     * A telepes (játékos) egy egységgel mélyebbre ás az aszteroida kérgében, teljesen átásta, a magja jég (az aszteroida nincs napközelben, így elpárolog).
     * A program kiírja a konzolra az ásáshoz, az áttöréshez és a jég elpárolgásához kapcsolódó függvény hívásokat.
     */
    public static void _2_4_DiggingBreakthroughtIce()
    {
        Logger.formatPrint("Init");
        /**Létrehozzuk a telepest aki ás és az aszteroidát amin ás. Az aszteroida harmadik paramétere, ami a kéregvastagságot jelöli, kezdetnek 1, hogy egy ásással
         * át lehessen törni a kérget és lefuthasson a teszteset többi függvényhívása is. Az aszteroida első két paramétere most 1, mert ez jelenti azt, hogy napközelben van.
         */
        Asteroid a = new Asteroid(1, 1, 1, new Ice());
        Settler s = new Settler(a);

        Space.getInstance().addOrbit(a);
        Space.getInstance().step();

        Logger.formatPrint("_2_4_DiggingBreakthroughtIce");
        /** A hívási lánc első eleme, amiből elindul az egész eset. */
        s.digging();

        Logger.formatPrint("Teszt v�ge");
    }

    /**
     * A telepes (játékos) megkísérel egy teleportkapun ásni.
     * A program kiírja az ásási kísérlethez kapcsolódó függvény hívásokat.
     */
    public static void _2_5_DiggingOnStargate()
    {
        Logger.formatPrint("Init");

        /** Két csillagkaput kell létrehozzunk, mert a lerakott csillagkapunak mindenképp össze kell legyen kötve egy másikkal, még akkor is ha a párja még nincs lerakva 
         * egy aszteroida szomszédságába. */
        Stargate st1 = new Stargate();
        Stargate st2 = new Stargate();
        

        st1.entagle(st2);
        st2.entagle(st1);
        Settler s = new Settler(st1);

        Logger.formatPrint("_2_5_DiggingOnStargate");
        /** A hívási lánc első eleme, amiből elindul az egész eset. */
        s.digging();

        Logger.formatPrint("Teszt v�ge");
    }

    public static void _3_Mining()
    {
        System.out.println("B�ny�szat esetek:");
        System.out.println("\t1. Mining On Not Fully Drilled Asteroid");
        System.out.println("\t2. Mining Iron On Fully Drilled Asteroid");
        System.out.println("\t3. Mining On Fully Drilled But Empty Asteroid");
        System.out.println("\t4. Mining On Stargate");

        int opt = SelectOption("V�laszd ki a use-case sorsz�m�t: (1-4)", 1,4);
        switch(opt)
        {
            case 1:
            _3_1_MiningOnNotFullyDrilledAsteroid();
                break;
            case 2:
            _3_2_MiningIronOnFullyDrilledAsteroid();
                break;
            case 3:
            _3_3_MiningOnFullyDrilledButEmptyAsteroid();
                break;
            case 4:
            _3_4_MiningOnStargate();
                break;
        }
    }

    public static void _3_1_MiningOnNotFullyDrilledAsteroid()
    {
        Logger.formatPrint("Init");

        Asteroid asteroid = new Asteroid(1,1,10,new Iron());
        Settler settler = new Settler(asteroid);

        Logger.formatPrint("_3_1_MiningOnNotFullyDrilledAsteroid");

        settler.mining();

        Logger.formatPrint("Teszt V�ge");
    }
    public static void _3_2_MiningIronOnFullyDrilledAsteroid()
    {
        Logger.formatPrint("Init");

        Asteroid asteroid = new Asteroid(1,1,0,new Iron());
        Settler settler = new Settler(asteroid);

        Logger.formatPrint("_3_2_MiningIronOnFullyDrilledAsteroid");

        settler.mining();

        Logger.formatPrint("Teszt V�ge");
    }
    public static void _3_3_MiningOnFullyDrilledButEmptyAsteroid()
    {
        Logger.formatPrint("Init");

        Asteroid asteroid = new Asteroid(1,1,0,null);
        Settler settler = new Settler(asteroid);

        Logger.formatPrint("_3_3_MiningOnFullyDrilledButEmptyAsteroid");

        settler.mining();

        Logger.formatPrint("Teszt V�ge");
    }
    public static void _3_4_MiningOnStargate()
    {        
        Logger.formatPrint("Init");

        Asteroid asteroid = new Asteroid(1, 1, 20, null);

        Stargate stargate1 = new Stargate();
        Stargate stargate2 = new Stargate();

        stargate1.entagle(stargate2);
        stargate2.entagle(stargate1);

        stargate1.place(asteroid);
        asteroid.addNeighbour(stargate1);

        Settler settler = new Settler(stargate1);

        Logger.formatPrint("_3_4_MiningOnStargate");

        settler.mining();

        Logger.formatPrint("Teszt V�ge");
    }

    public static void _4_ReplaceResource()
    {
        System.out.println("B�ny�szat esetek:");
        System.out.println("\t1. Settler Tries To Put Back Iron But He Can�t");
        System.out.println("\t2. Settler Replace Iron");
        System.out.println("\t3. Settler Puts Back Ice And It Melts In The Sunlight");
        System.out.println("\t4. Settler Puts Back Uran And It Explodes");
        System.out.println("\t5. Settler Tries To Put Back Iron Into A Stargate");

        int opt = SelectOption("V�laszd ki a use-case sorsz�m�t: (1-5)", 1,5);
        switch(opt)
        {
            case 1:
            _4_1_SettlerTriesToPutBackIronButHeCant();
                break;
            case 2:
            _4_2_SettlerReplaceIron();
                break;
            case 3:
            _4_3_SettlerPutsBackIceAndItMeltsInTheSunlight();
                break;
            case 4:
            _4_4_SettlerPutsBackUranAndItExplodes();
                break;
            case 5:
            _4_5_SettlerTriesToPutBackIronIntoAStargate();
                break;
        }
    }

    public static void _4_1_SettlerTriesToPutBackIronButHeCant()
    {
        Logger.formatPrint("Init");

        Asteroid a = new Asteroid(0, 0, 8, null);
        Settler sett = new Settler(a);
        Iron i = new Iron();

        sett.getInventory().addIron(i);

        Logger.formatPrint("_4_1_SettlerTriesToPutBackIronButHeCant");

        sett.replaceResource("Iron");

        Logger.formatPrint("Teszt v�ge");

    }
    public static void _4_2_SettlerReplaceIron()
    {
        Logger.formatPrint("Init");

        Asteroid a = new Asteroid(0, 0, 0, null);
        Settler sett = new Settler(a);
        Iron i = new Iron();

        sett.getInventory().addIron(i);

        Logger.formatPrint("_4_2_SettlerReplaceIron");

        sett.replaceResource("Iron");

        Logger.formatPrint("Teszt v�ge");
    }

    public static void _4_3_SettlerPutsBackIceAndItMeltsInTheSunlight()
    {
        Logger.formatPrint("Init");

        Asteroid a = new Asteroid(1, 1, 0, null);
        Settler sett = new Settler(a);
        Ice i = new Ice();
        Space.getInstance().addOrbit(a);
        Space.getInstance().step();

        sett.getInventory().addIce(i);

        Logger.formatPrint("_4_3_SettlerPutsBackIceAndItMeltsInTheSunlight");

        sett.replaceResource("Ice");

        Logger.formatPrint("Teszt v�ge");
    }

    public static void _4_4_SettlerPutsBackUranAndItExplodes()
    {
        Logger.formatPrint("Init");
        Controler cont = Controler.getNewControler();
        Asteroid a = new Asteroid(1, 1, 0, null);
        Settler sett = new Settler(a);
        cont.addSettler (new Settler(new Asteroid(0, 0, 1, null))) ;// az�rt hogy az els�dleges settler hal�l�val ne �rjen v�get a j�t�k.
        Uran u = new Uran();
        Controler.getInstance().addSettler(sett);
        Space.getInstance().addOrbit(a);
        Space.getInstance().step();

        sett.getInventory().addUran(u);

        Logger.formatPrint("_4_4_SettlerPutsBackUranAndItExplodes");

        sett.replaceResource("Uran");

        Logger.formatPrint("Teszt v�ge");
    }
    public static void _4_5_SettlerTriesToPutBackIronIntoAStargate()
    {
        Logger.formatPrint("Init");

        Stargate s1 = new Stargate();
        Stargate s2 = new Stargate();
        s1.entagle(s2);
        s2.entagle(s1);
        Settler sett = new Settler(s1);
        Iron i = new Iron();

        sett.getInventory().addIron(i);

        Logger.formatPrint("_4_5_SettlerTriesToPutBackIronIntoAStargate");

        sett.replaceResource("Iron");

        Logger.formatPrint("Teszt v�ge");
    }

    public static void _5_Build()
    {
        System.out.println("�p�t�s esetek:");
        System.out.println("\t1. Successful Robot Create");
        System.out.println("\t2. Failed Robot Create Missing Material");
        System.out.println("\t3. Successful Base Create");
        System.out.println("\t4. Failed Base Create Missing Material");

        int opt = SelectOption("V�laszd ki a use-case sorsz�m�t: (1-4)", 1,4);
        switch(opt)
        {
            case 1:
            _5_1_SuccessfulRobotCreate();
                break;
            case 2:
            _5_2_FailedRobotCreateMissingMaterial();
                break;
            case 3:
            _5_3_SuccessfulBaseCreate();
                break;
            case 4:
            _5_4_FailedBaseCreateMissingMaterial();
                break;
        }
    }

    /**
     * A telepes (j�t�kos) l�trehoz egy �j robotot.
     * A program ki�rja a konzolra a sikeres robot k�sz�t�s m�velet�hez kapcsol�d� f�ggv�ny h�v�sokat.
     * Init: Egy aszteroid�n l�trehozunk egy settlert, aki k�pes l�trehozni egy robotot
     * Felt�ltj�k az Inventoryt egy Uran, Iron, Carbon-nal.
     */
    public static void _5_1_SuccessfulRobotCreate()
    {
        Logger.formatPrint("Init");

        Asteroid a = new Asteroid(5, 1, 5, null);
        Settler s1 = new Settler(a);
        Inventory seged = s1.getInventory();
        seged.addUran(new Uran());
        seged.addIron(new Iron());
        seged.addCarbon(new Carbon());
        Controler c = Controler.getInstance();

        Logger.formatPrint("_5_1_SuccessfulRobotCreate");

        s1.createRobot();

        Logger.formatPrint("Teszt v�ge");
    }
    /**
     * A telepes (j�t�kos) l�tre pr�b�l hozni egy �j robotot, de nincs el�g nyersanyagja.
     * A program ki�rja a konzolra a sikertelen robot k�sz�t�s m�velet�hez kapcsol�d� f�ggv�ny h�v�sokat.
     * Init: Egy aszteroid�n l�trehozunk egy settlert, aki nem k�pes l�trehozni robotot, mert nincs elegend� nyersanyagja
     */
    public static void _5_2_FailedRobotCreateMissingMaterial()
    {
        Logger.formatPrint("Init");

        Asteroid a = new Asteroid(5, 2, 5, null);
        Settler s1 = new Settler(a);

        Logger.formatPrint("_5_2_FailedRobotCreateMissingMaterial");

        s1.createRobot();

        Logger.formatPrint("Teszt v�ge");
    }
    /**
     * A telepes (j�t�kos) fel�p�t az aszteroid�n egy b�zist a rajta l�v� t�bbi telepessel �s ezzel megnyerik a j�t�kot. 
     * A telepeseknek van el�g nyersanyaga a b�zis meg�p�t�s�hez, 
     *      ekkor a program a k�perny�re �rja a b�zis �p�t�s�vel �s a j�t�k befejez�s�vel j�r� f�ggv�nyh�v�sokat.
     * Init: Egy aszteroid�n l�trehozunk k�t settlert, akik k�pesek fel�p�teni a b�zist
     * Felt�ltj�k s1 Settler Inventory-j�ba 3 Carbon, 3 Iron, 3 Uran, s2 Settler Inventory-j�ba 3 Ice nyersanyagot.
     */
    public static void _5_3_SuccessfulBaseCreate()
    {
        Logger.formatPrint("Init");

        Controler c = Controler.getInstance();
        Asteroid a1 = new Asteroid(5, 3, 4, null);
        Settler s1 = new Settler(a1);
        Settler s2 = new Settler(a1);

        Inventory seged = s1.getInventory();
        for(int i =0; i<3;i++){
            seged.addCarbon(new Carbon());
            seged.addIron(new Iron());
            seged.addUran(new Uran());
        }

        seged = s2.getInventory();
        for(int i =0; i<3;i++){
            seged.addIce(new Ice());
        }

        Logger.formatPrint("_5_3_SuccessfulBaseCreate");

        s1.createBase();

        Logger.formatPrint("Teszt v�ge");
    }
     /**
     * A telepes (j�t�kos) megpr�b�l fel�p�teni az aszteroid�n egy b�zist a rajta l�v� t�bbi telepessel, de nincs el�g nyersanyagjuk.  
     * A telepeseknek nincs el�g nyersanyaga a b�zis meg�p�t�s�hez,
     *      ekkor a program a k�perny�re �rja a b�zis �p�t�s�vel val� pr�b�lkoz�shoz kapcsol�d� f�ggv�nyh�v�sokat.
     * Init: Egy aszteroid�n l�trehozunk k�t settlert, akik nem k�pesek fel�p�teni a b�zist, mert nincs meg a sz�ks�ges nyersanyag mennyis�g�k
     */
    public static void _5_4_FailedBaseCreateMissingMaterial()
    {
        Logger.formatPrint("Init");

        Controler c = Controler.getInstance();
        Asteroid a1 = new Asteroid(5, 3, 4, null);
        Settler s1 = new Settler(a1);
        Settler s2 = new Settler(a1);

        Logger.formatPrint("_5_4_FailedBaseCreateMissingMaterial");

        s1.createBase();

        Logger.formatPrint("Teszt v�ge");
    }

    public static void _6_Stargate()
    {
        System.out.println("Teleportkapu esetek:");
        System.out.println("\t1. Successful Stargate Create");
        System.out.println("\t2. Failed Stargate Create Missing Material");
        System.out.println("\t3. Successful Stargate Place");

        int opt = SelectOption("V�laszd ki a use-case sorsz�m�t: (1-3)", 1,3);
        switch(opt)
        {
            case 1:
            _6_1_SuccessfulStargateCreate();
                break;
            case 2:
            _6_2_FailedStargateCreateMissingMaterial();
                break;
            case 3:
            _6_3_SuccessfulStargatePlace();
                break;
        }
    }
    /**
     * A telepes (j�t�kos) l�trehoz egy �j teleportkapu-p�rt �s elrakt�rozza a t�rol�j�ban �ket.
     * A program ki�rja a konzolra az teleportkapu-p�r k�sz�t�s m�velet�hez kapcsol�d� f�ggv�ny h�v�sokat.
     * Init: Egy aszteroid�n l�trehozunk egy settlert, aki k�pes l�trehozni egy teleportkapu-p�rt.
     * Felt�ltj�k az Inventoryt 1 Uran, 2 Iron, �s 1 Ice-cal.
     */
    public static void _6_1_SuccessfulStargateCreate()
    {
        Logger.formatPrint("Init");

        Asteroid a1 = new Asteroid(6, 1, 4, null);
        Settler s1 = new Settler(a1);

        Inventory seged = s1.getInventory();
        seged.addUran(new Uran());
        seged.addIron(new Iron());
        seged.addIron(new Iron());
        seged.addIce(new Ice());
        
        Logger.formatPrint("_6_1_SuccessfulStargateCreate");

        s1.stargate();

        Logger.formatPrint("Teszt v�ge");
    }
    /**
     * A telepes (j�t�kos) megpr�b�l l�trehozni egy �j teleportkapu-p�rt, de nincs el�g nyersanyagja.
     * A program ki�rja a konzolra a sikertelen teleportkapu-p�r k�sz�t�s m�velet�hez kapcsol�d� f�ggv�ny h�v�sokat.
     * Init: Egy aszteroid�n l�trehozunk egy settlert, aki nem k�pes l�trehozni egy teleportkapu-p�rt, mert nincs elegend� nyersanyagja.
     */
    public static void _6_2_FailedStargateCreateMissingMaterial()
    {
        Logger.formatPrint("Init");

        Asteroid a1 = new Asteroid(6, 2, 4, null);
        Settler s1 = new Settler(a1);
        
        Logger.formatPrint("6_2_FailedStargateCreateMissingMaterial");

        s1.stargate();

        Logger.formatPrint("Teszt v�ge");
    }
    /**
     * A telepes egy aszteroida szomsz�ds�g�ban lehelyez egy teleport kaput.
     * A program ki�rja a konzolra az teleportkapu elhelyez�s�nek m�velet�hez kapcsol�d� f�ggv�ny h�v�sokat.
     * Init: Egy aszteroid�n l�trehozunk egy settlert, aki l�trehoz egy teleportkapu-p�rt mag�hoz (els� stargate() h�v�s).
     *      Ut�na ebb�l a k�t akapub�l helyez el egyet a teszt sor�n.
     */
    public static void _6_3_SuccessfulStargatePlace()
    {
        Logger.formatPrint("Init");

        Asteroid a1 = new Asteroid(6, 3, 8, null);

        Settler s = new Settler(a1);
        Inventory seged = s.getInventory();
        seged.addUran(new Uran());
        seged.addIron(new Iron());
        seged.addIron(new Iron());
        seged.addIce(new Ice());
        s.stargate();
        
        Logger.formatPrint("_6_3_SuccessfulStargatePlace");

        s.stargate();
        Logger.formatPrint("Teszt v�ge");
    }
    
    public static void _7_SunStorm()
    {
        System.out.println("Napvihar esetek:");
        System.out.println("\t1. Sunstorm On Not Fully Drilled Asteroid");
        System.out.println("\t2. Sunstorm On Not Empty Asteroid");
        System.out.println("\t3. Sunstorm On Safe Asteroid");

        int opt = SelectOption("V�laszd ki a use-case sorsz�m�t: (1-3)", 1,3);
        switch(opt)
        {
            case 1:
            _7_1_SunstormOnNotFullyDrilledAsteroid();
                break;
            case 2:
            _7_2_SunstormOnNotEmptyAsteroid();
                break;
            case 3:
            _7_3_SunstormOnSafeAsteroid();
                break;
        }
    }

    public static void _7_1_SunstormOnNotFullyDrilledAsteroid()
    {
        Logger.formatPrint("Init");

        Controler cont = Controler.getNewControler();
        Space space = cont.getSpace();
        cont.addSettler(new Settler(new Asteroid(20,20,1,null))); //� �letben marad majd, nem lesz j�t�k v�ge

        Asteroid asteroid = new Asteroid(1,1,10,new Iron());
        space.addOrbit(asteroid);

        Robot robot = new Robot();
        robot.setLocation(asteroid);
        asteroid.addTraveler(robot);
        cont.addRobot(robot);

        Settler settler = new Settler(asteroid);
        cont.addSettler(settler);

        Logger.formatPrint("_7_1_SunstormOnNotFullyDrilledAsteroid");

        space.tempSunstormCall();

        Logger.formatPrint("Teszt Vege");

    }
    public static void _7_2_SunstormOnNotEmptyAsteroid()
    {
        Logger.formatPrint("Init");

        Controler cont = Controler.getNewControler();
        Space space = cont.getSpace();
        cont.addSettler(new Settler(new Asteroid(20,20,1,null))); //� �letben marad majd, nem lesz j�t�k v�ge

        Asteroid asteroid = new Asteroid(1,1,0,new Iron());
        space.addOrbit(asteroid);

        Robot robot = new Robot();
        robot.setLocation(asteroid);
        asteroid.addTraveler(robot);
        cont.addRobot(robot);

        Settler settler = new Settler(asteroid);
        cont.addSettler(settler);

        Logger.formatPrint("_7_2_SunstormOnNotEmptyAsteroid");

        space.tempSunstormCall();

        Logger.formatPrint("Teszt Vege");
    }
    public static void _7_3_SunstormOnSafeAsteroid()
    {
        Logger.formatPrint("Init");

        Controler cont = Controler.getNewControler();
        Space space = cont.getSpace();
        cont.addSettler(new Settler(new Asteroid(20,20,1,null))); //� �letben marad majd, nem lesz j�t�k v�ge

        Asteroid asteroid = new Asteroid(1,1,0, null);
        space.addOrbit(asteroid);

        Robot robot = new Robot();
        robot.setLocation(asteroid);
        asteroid.addTraveler(robot);
        cont.addRobot(robot);

        Settler settler = new Settler(asteroid);
        cont.addSettler(settler);

        Logger.formatPrint("_7_3_SunstormOnSafeAsteroid");

        space.tempSunstormCall();

        Logger.formatPrint("Teszt Vege");

    }

    public static void _8_SunLight()
    {
        System.out.println("Napf�ny �rkez�se esetek:");
        System.out.println("\t1. Sunlight Arrives To Not Fully Drilled Asteroid");
        System.out.println("\t2. Sunlight Arrives To Fully Drilled Asteroid With Iron");
        System.out.println("\t3. Sunlight Arrives To Fully Drilled Asteroid With Uran");
        System.out.println("\t4. Sunlight Arrives To Fully Drilled Asteroid With Ice");

        int opt = SelectOption("V�laszd ki a use-case sorsz�m�t: (1-4)", 1,4);
        switch(opt)
        {
            case 1:
            _8_1_SunlightArrivesToNotFullyDrilledAsteroid();
                break;
            case 2:
            _8_2_SunlightArrivesToFullyDrilledAsteroidWithIron();
                break;
            case 3:
            _8_3_SunlightArrivesToFullyDrilledAsteroidWithUran();
                break;
            case 4:
            _8_4_SunlightArrivesToFullyDrilledAsteroidWithIce();
                break;
        }
    }

    /**
     * A napfény elér egy aszteroidát, amelynek nincs teljesen átfúrva a kérge.
     * 
     * Az aszteroidában lévő nyersanyag nem tud reakcióba lépni a napfénnyel.
     * A program a konzolra írja a napfény érkezésével kapcsolatos függvényhívásokat.
     * 
     * Init: létrehozunk egy Space objektumot, ebbe felveszünk egy aszteroidát, amelynek
     * nincs átfúrva a kérge, és Iron a magja.
     * (a sunstorm_time értékét ötre állítjuk, hogy ne legyen most napvihar)
     * 
     * A Space step() metódusa fogja indítani a függvényhívásokat.
     */
    public static void _8_1_SunlightArrivesToNotFullyDrilledAsteroid()
    {
        Logger.formatPrint("Init");

        Space s = Space.getNewSpace();
        s.setSunstorm_time(5);
        Asteroid a = new Asteroid(1,8,2,new Iron());
        s.addOrbit(a);

        Logger.formatPrint("_8_1_SunlightArrivesToNotFullyDrilledAsteroid");
        
        s.step();

        Logger.formatPrint("Teszt v�ge");
    }

    /**
     * A napfény elér egy aszteroidát, amelynek át van fúrva a kérge, és vas van benne.
     * 
     * A napfény reakcióba lép a benne lévő vassal. A program a konzolra írja a napfény
     * érkezésével és a reakcióval kapcsolatos függvényhívásokat.
     * 
     * Init: létrehozunk egy Space objektumot, ebbe felveszünk egy aszteroidát, amelynek át van fúrva
     * a kérge és Iron a magja.
     * (a sunstorm_time értékét ötre állítjuk, hogy ne legyen most napvihar)
     * 
     * A Space step() metódusa fogja indítani a függvényhívásokat.
     */
    public static void _8_2_SunlightArrivesToFullyDrilledAsteroidWithIron()
    {
        Logger.formatPrint("Init");

        Space s = Space.getNewSpace();
        s.setSunstorm_time(5);
        Asteroid a = new Asteroid(1,8,0,new Iron());
        s.addOrbit(a);

        Logger.formatPrint("_8_2_SunlightArrivesToFullyDrilledAsteroidWithIron");

        s.step();

        Logger.formatPrint("Teszt v�ge");
    }
    /**
     * A napfény elér egy aszteroidát, amelynek át van fúrva a kérge, és urán van benne.
     * 
     * A napfény reakcióba lép a benne lévő uránnal. Ekkor az aszteroida felrobban, 
     * megsemmisítve a benne lévő nyersanyagot. Az aszteroidán lévő Robotok átrepülnek
     * más aszteroidára, míg a Telepesek meghalnak. A program a konzolra írja a napfény 
     * érkezésével, a reakcióval, robbanással és meghalással kapcsolatos függvényhívásokat.
     * 
     * Init: létrehozunk egy Controller objektumot, ami létrehoz egy space objektumot is.
     * Felveszünk két aszteroidát, az egyiket a spacebe (ez át van fúrva és urán a magja),
     * hogy csak azt érje el a napfény. Erre az aszteroidára felveszünk egy telepest és egy robotot.
     * (a sunstorm_time értékét ötre állítjuk, hogy ne legyen most napvihar)
     * 
     * A space step() metódusa indítja a függvényhívásokat.
     */
    public static void _8_3_SunlightArrivesToFullyDrilledAsteroidWithUran()
    {
        Logger.formatPrint("Init");

        Controler c = Controler.getNewControler();
        Space s = c.getSpace();
        s.setSunstorm_time(5);

        Asteroid a1 = new Asteroid(1,8,0,new Uran());
        Asteroid a2 = new Asteroid(2,8,0, null);
        a1.addNeighbour(a2);
        a2.addNeighbour(a1);

        s.addOrbit(a1);
        // felvesz egy settlert az aszteroid�ra �s 
        Settler set = new Settler(a1);
        Settler set2 = new Settler(a2);

        Robot rob = new Robot();
        rob.setLocation(a1);
        a1.addTraveler(rob);

        c.addRobot(rob);
        c.addSettler(set);

        Logger.formatPrint("_8_3_SunlightArrivesToFullyDrilledAsteroidWithUran");

        s.step();
        
        Logger.formatPrint("Teszt v�ge");
    }
    /**
     * A napfény elér egy aszteroidát, amelynek át van fúrva a kérge, és jég van benne.
     * 
     * A napfény reakcióba lép a benne lévő jéggel, ami így elpárolog. 
     * A program a konzolra írja a napfény érkezésével és a reakcióval kapcsolatos függvényhívásokat.
     * 
     * Init: létrehozunk egy Space objektumot, ebbe felveszünk egy aszteroidát, amelynek át van fúrva
     * a kérge és Ice a magja.
     * (a sunstorm_time értékét ötre állítjuk, hogy ne legyen most napvihar)
     * 
     * A Space step() metódusa fogja indítani a függvényhívásokat.
     */
    public static void _8_4_SunlightArrivesToFullyDrilledAsteroidWithIce()
    {
        Logger.formatPrint("Init");

        Space s = Space.getNewSpace();
        s.setSunstorm_time(5);
        Asteroid a = new Asteroid(1,8,0,new Ice());
        s.addOrbit(a);

        Logger.formatPrint("_8_4_SunlightArrivesToFullyDrilledAsteroidWithIce");

        s.step();

        Logger.formatPrint("Teszt v�ge");
    }

    public static void _9_Robot()
    {
        System.out.println("Robot esetek:");
        System.out.println("\t1. Robot Is On Not Fully Drilled Asteroid");
        System.out.println("\t2. Robot Is On Fully Drilled Asteroid");

        int opt = SelectOption("V�laszd ki a use-case sorsz�m�t: (1-2)", 1,2);
        switch(opt)
        {
            case 1:
            _9_1_RobotIsOnNotFullyDrilledAsteroid();
                break;
            case 2:
            _9_2_RobotIsOnFullyDrilledAsteroid();
                break;
            case 3:
        }
    }

    /**
     *  A robot egy nem teljesen kifúrt aszteroidán áll, ekkor fúrni kezdi azt. 
     * 
     * A program kiírja a robot fúrásával járó függvényhívásokat.
     * 
     * Init: létrehozunk egy nem átfúrt aszteroidát. Erre tesszük rá a robotot.
     * 
     * A robot step() metódusa indítja a függvényhívásokat.
     */
    public static void _9_1_RobotIsOnNotFullyDrilledAsteroid()
    {
        Logger.formatPrint("Init");

        Asteroid a = new Asteroid(1,8,2,null);
        Robot r = new Robot();
        r.setLocation(a);
        a.addTraveler(r);

        Logger.formatPrint("_9_1_RobotIsOnNotFullyDrilledAsteroid");

        r.step();
        
        Logger.formatPrint("Teszt v�ge");
    }

    /**
     * A robot egy teljesen kifúrt aszteroidán van, ezért választ egy szomszédot, és oda mozog.
     * 
     * A program kiírja a konzolra a robot mozgásával járó függvényhívásokat.
     * 
     * Init: létrehozunk két szomszédos aszteroidát, az egyik teljesen át van fúrva. Erre
     * az átfúrt aszteroidára tesszük rá a robotot.
     * 
     * A robot step() metódusa indítja a függvényhívásokat.
     */
    public static void _9_2_RobotIsOnFullyDrilledAsteroid()
    {
        Logger.formatPrint("Init");
        
        Asteroid a1 = new Asteroid(1,8,0,null);
        Asteroid a2 = new Asteroid(2,8,2,null);
        a1.addNeighbour(a2);
        a2.addNeighbour(a1);
        Robot r = new Robot();
        r.setLocation(a1);
        a1.addTraveler(r);

        Logger.formatPrint("_9_2_RobotIsOnFullyDrilledAsteroid");

        r.step();
        
        Logger.formatPrint("Teszt v�ge");
    }
}
