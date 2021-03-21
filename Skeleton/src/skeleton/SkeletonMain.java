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
                    System.out.println("Hibï¿½s sorszï¿½m!");   
                
                
            } catch (Exception e) {
                System.out.println("Hiba: " + e.getMessage());
            }
            
        }
    }

    public static boolean SelectCategory()
    {
        System.out.println("1. Mozgï¿½s");
        System.out.println("2. Fï¿½rï¿½s");
        System.out.println("3. Bï¿½nyï¿½szat");
        System.out.println("4. Anyag visszarakï¿½sa");
        System.out.println("5. ï¿½pï¿½tï¿½s");
        System.out.println("6. Csillagkapu dolgok");
        System.out.println("7. Napvihar");
        System.out.println("8. Napfï¿½ny ï¿½rkezï¿½se");
        System.out.println("9. Robot Step\n");
        System.out.println("10. Kilï¿½pï¿½s");
        
        int category = SelectOption("Vï¿½laszd ki a kategï¿½ria sorszï¿½mï¿½t: (1-10)", 1,10);

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
                System.out.println("A program leï¿½ll.");
                return false;
            default:
                break;
        }

        return true;
    }

    public static void _1_Movement()
    {
        System.out.println("Mozgï¿½s esetek:");
        System.out.println("\t1. Move On Asteroid");
        System.out.println("\t2. Move On Offline Gate");
        System.out.println("\t3. Move On Online Gate");

        int opt = SelectOption("Vï¿½laszd ki a use-case sorszï¿½mï¿½t: (1-3)", 1,3);
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

    public static void _1_1_MoveOnAsteroid()
    {
        Logger.formatPrint("Init");

        /** A teszt lefutÃ¡sÃ¡hoz szÃ¼ksÃ©ges objektumok inicializÃ¡lÃ¡sa. */
        Asteroid a1 = new Asteroid(0, 0, 8, null);
        Asteroid a2 = new Asteroid(1, 0, 8, null);
        Settler s = new Settler(a1);
        
        /** Ãgy az s telepesnek van hovÃ¡ lÃ©pnie, a kÃ©t elÅ‘bb lÃ©trehozott aszteroida ezÃ¡ltal egymÃ¡s szomszÃ©dai lesznek. */
        a1.addNeighbour(a2);
        a2.addNeighbour(a1);

        Logger.formatPrint("_1_1_MoveOnAsteroid");

        /**A hÃ­vÃ¡si lÃ¡nc elsÅ‘ eleme, amibÅ‘l elindul az egÃ©sz eset. */
        s.move(0);

        Logger.formatPrint("Teszt vï¿½ge");
    }
    public static void _1_2_MoveOnOfflineGate()
    {
        Logger.formatPrint("Init");

        /** A teszt lefutÃ¡sÃ¡hoz szÃ¼ksÃ©ges objektumok inicializÃ¡lÃ¡sa. KÃ©t csillagkaput kell lÃ©trehozzunk, mert a lerakott csillagkapunak mindenkÃ©pp Ã¶ssze kell legyen kÃ¶tve
         * egy mÃ¡sikkal, mÃ©g akkor is ha a pÃ¡rja mÃ©g nincs lerakva egy aszteroida szomszÃ©dsÃ¡gÃ¡ba.
        */
        Stargate s1 = new Stargate();
        Stargate s2 = new Stargate();
        Asteroid a1 = new Asteroid(0, 0, 8, null);
        Settler s = new Settler(a1);

        /** A lÃ©t teleportkapu ezÃ¡ltal Ã¶ssze lesz kÃ¶tve. */
        s1.entagle(s2);
        s2.entagle(s1);

        /** Az s1 kapu  le van helyezve az a1 aszteroida szomszÃ©dsÃ¡gÃ¡ba.*/
        a1.addNeighbour(s1);
        s1.place(a1);

        Logger.formatPrint("_1_2_MoveOnOfflineGate");

        /**A hÃ­vÃ¡si lÃ¡nc elsÅ‘ eleme, amibÅ‘l elindul az egÃ©sz eset. */
        s.move(0);
        Logger.formatPrint("Teszt vï¿½ge");
    }
    public static void _1_3_MoveOnOnlineGate()
    {
        Logger.formatPrint("Init");

        /** LÃ©tre hozzuk a csillagkapukat amiken keresztÃ¼l teleportÃ¡lni fogunk */
        Stargate s1 = new Stargate();
        Stargate s2 = new Stargate();

        /** Ã–sszekÃ¶tjÃ¼k Å‘ket, hogy el tudjÃ¡k egymÃ¡st Ã©rni. */
        s1.entagle(s2);
        s2.entagle(s1);

        /** LÃ©trehozzuk az aszteroidÃ¡kat amikhez a csillagkapuk tartozni fognak. */
        Asteroid a1 = new Asteroid(0, 0, 8, null);
        Asteroid a2 = new Asteroid(0, 0, 8, null);

        /** LÃ©trehozzuk a telepest, aki majd teleportÃ¡lni fog. */
        Settler s = new Settler(a1);

        /** ElhelyezzÃ¼k a csillagkapukat az aszteroidÃ¡k szomszÃ©dsÃ¡gÃ¡ba. */
        a1.addNeighbour(s1);
        a2.addNeighbour(s2);
        s1.place(a1);
        s2.place(a2);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");
        /**A hÃ­vÃ¡si lÃ¡nc elsÅ‘ eleme, amibÅ‘l elindul az egÃ©sz eset. */
        s.move(0);
        Logger.formatPrint("Teszt vï¿½ge");

    }

    public static void _2_Digging()
    {
        System.out.println("Fï¿½rï¿½s esetek:");
        System.out.println("\t1. Simple Digging");
        System.out.println("\t2. Digging And Breakthrought");
        System.out.println("\t3. Digging Breakthrought Uran");
        System.out.println("\t4. Digging Breakthrought Ice");
        System.out.println("\t5. Digging On Stargate");

        int opt = SelectOption("Vï¿½laszd ki a use-case sorszï¿½mï¿½t: (1-5)", 1,5);
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

    public static void _2_1_SimpleDigging()
    {
        Logger.formatPrint("Init");
        
        /** LÃ©trehozzuk a telepest aki Ã¡s Ã©s az aszteroidÃ¡t amin Ã¡s. */
        Asteroid a = new Asteroid(0, 0, 8, null);
        Settler s = new Settler(a);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");
        /**A hÃ­vÃ¡si lÃ¡nc elsÅ‘ eleme, amibÅ‘l elindul az egÃ©sz eset. */
        s.digging();

        Logger.formatPrint("Teszt vï¿½ge"); 
    }
    public static void _2_2_DiggingAndBreakthrought()
    {
        Logger.formatPrint("Init");
        
        /** LÃ©trehozzuk a telepest aki Ã¡s Ã©s az aszteroidÃ¡t amin Ã¡s. Az aszteroida harmadik paramÃ©tere, ami a kÃ©regvastagsÃ¡got jelÃ¶li, kezdetnek 1, hogy egy Ã¡sÃ¡ssal
         * Ã¡t lehessen tÃ¶rni a kÃ©rget Ã©s lefuthasson a teszteset tÃ¶bbi fÃ¼ggvÃ©nyhÃ­vÃ¡sa is.
         */
        Asteroid a = new Asteroid(0, 0, 1, null);
        Settler s = new Settler(a);
        
        a.addTraveler(s);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");
        /** A hÃ­vÃ¡si lÃ¡nc elsÅ‘ eleme, amibÅ‘l elindul az egÃ©sz eset. */
        s.digging();

        Logger.formatPrint("Teszt vï¿½ge");
    }
    public static void _2_3_DiggingBreakthroughtUran()
    {
        Logger.formatPrint("Init");

        /** LÃ©trehozzuk a telepest aki Ã¡s Ã©s az aszteroidÃ¡t amin Ã¡s. Az aszteroida harmadik paramÃ©tere, ami a kÃ©regvastagsÃ¡got jelÃ¶li, kezdetnek 1, hogy egy Ã¡sÃ¡ssal
         * Ã¡t lehessen tÃ¶rni a kÃ©rget Ã©s lefuthasson a teszteset tÃ¶bbi fÃ¼ggvÃ©nyhÃ­vÃ¡sa is. Az a2 aszteroida azÃ©rt kell, hogy a kÃ©sÅ‘bbiekben lÃ©trehozott robotnak
         * a robbanÃ¡s hatÃ¡sÃ¡ra, legyen olyan aszteroda, ahova mehet a robbanÃ³ aszteroidÃ¡rÃ³l. EzÃ©rt Ã¡llÃ­tjuk be a kÃ©t aszteroidÃ¡nak, hogy szomszÃ©dok legyenek. Az a1 elsÅ‘ kÃ©t paramÃ©tere
         * most 1, mert ez jelenti azt, hogy napkÃ¶zelben van.
         */
        Asteroid a1 = new Asteroid(1, 1, 1, new Uran());
        Asteroid a2 = new Asteroid(0, 0, 1, null);
        Settler s = new Settler(a1);

        a2.addNeighbour(a1);
        a1.addNeighbour(a2);

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
        /** A hÃ­vÃ¡si lÃ¡nc elsÅ‘ eleme, amibÅ‘l elindul az egÃ©sz eset. */
        s.digging();

        Logger.formatPrint("Teszt vï¿½ge");
    }
    public static void _2_4_DiggingBreakthroughtIce()
    {
        Logger.formatPrint("Init");
        /**LÃ©trehozzuk a telepest aki Ã¡s Ã©s az aszteroidÃ¡t amin Ã¡s. Az aszteroida harmadik paramÃ©tere, ami a kÃ©regvastagsÃ¡got jelÃ¶li, kezdetnek 1, hogy egy Ã¡sÃ¡ssal
         * Ã¡t lehessen tÃ¶rni a kÃ©rget Ã©s lefuthasson a teszteset tÃ¶bbi fÃ¼ggvÃ©nyhÃ­vÃ¡sa is. Az aszteroida elsÅ‘ kÃ©t paramÃ©tere most 1, mert ez jelenti azt, hogy napkÃ¶zelben van.
         */
        Asteroid a = new Asteroid(1, 1, 1, new Ice());
        Settler s = new Settler(a);

        Space.getInstance().addOrbit(a);
        Space.getInstance().step();

        Logger.formatPrint("_2_4_DiggingBreakthroughtIce");
        /** A hÃ­vÃ¡si lÃ¡nc elsÅ‘ eleme, amibÅ‘l elindul az egÃ©sz eset. */
        s.digging();

        Logger.formatPrint("Teszt vï¿½ge");
    }
    public static void _2_5_DiggingOnStargate()
    {
        Logger.formatPrint("Init");

        /** KÃ©t csillagkaput kell lÃ©trehozzunk, mert a lerakott csillagkapunak mindenkÃ©pp Ã¶ssze kell legyen kÃ¶tve egy mÃ¡sikkal, mÃ©g akkor is ha a pÃ¡rja mÃ©g nincs lerakva 
         * egy aszteroida szomszÃ©dsÃ¡gÃ¡ba. */
        Stargate st1 = new Stargate();
        Stargate st2 = new Stargate();
        

        st1.entagle(st2);
        st2.entagle(st1);
        Settler s = new Settler(st1);

        Logger.formatPrint("_2_5_DiggingOnStargate");
        /** A hÃ­vÃ¡si lÃ¡nc elsÅ‘ eleme, amibÅ‘l elindul az egÃ©sz eset. */
        s.digging();

        Logger.formatPrint("Teszt vï¿½ge");
    }

    public static void _3_Mining()
    {
        System.out.println("Bï¿½nyï¿½szat esetek:");
        System.out.println("\t1. Mining On Not Fully Drilled Asteroid");
        System.out.println("\t2. Mining Iron On Fully Drilled Asteroid");
        System.out.println("\t3. Mining On Fully Drilled But Empty Asteroid");
        System.out.println("\t4. Mining On Stargate");

        int opt = SelectOption("Vï¿½laszd ki a use-case sorszï¿½mï¿½t: (1-4)", 1,4);
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

        Logger.formatPrint("Teszt Vï¿½ge");
    }
    public static void _3_2_MiningIronOnFullyDrilledAsteroid()
    {
        Logger.formatPrint("Init");

        Asteroid asteroid = new Asteroid(1,1,0,new Iron());
        Settler settler = new Settler(asteroid);

        Logger.formatPrint("_3_2_MiningIronOnFullyDrilledAsteroid");

        settler.mining();

        Logger.formatPrint("Teszt Vï¿½ge");
    }
    public static void _3_3_MiningOnFullyDrilledButEmptyAsteroid()
    {
        Logger.formatPrint("Init");

        Asteroid asteroid = new Asteroid(1,1,0,null);
        Settler settler = new Settler(asteroid);

        Logger.formatPrint("_3_3_MiningOnFullyDrilledButEmptyAsteroid");

        settler.mining();

        Logger.formatPrint("Teszt Vï¿½ge");
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

        Logger.formatPrint("Teszt Vï¿½ge");
    }

    public static void _4_ReplaceResource()
    {
        System.out.println("Bï¿½nyï¿½szat esetek:");
        System.out.println("\t1. Settler Tries To Put Back Iron But He Canï¿½t");
        System.out.println("\t2. Settler Replace Iron");
        System.out.println("\t3. Settler Puts Back Ice And It Melts In The Sunlight");
        System.out.println("\t4. Settler Puts Back Uran And It Explodes");
        System.out.println("\t5. Settler Tries To Put Back Iron Into A Stargate");

        int opt = SelectOption("Vï¿½laszd ki a use-case sorszï¿½mï¿½t: (1-5)", 1,5);
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

        Logger.formatPrint("Teszt vï¿½ge");

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

        Logger.formatPrint("Teszt vï¿½ge");
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

        Logger.formatPrint("Teszt vï¿½ge");
    }

    public static void _4_4_SettlerPutsBackUranAndItExplodes()
    {
        Logger.formatPrint("Init");
        Controler cont = Controler.getNewControler();
        Asteroid a = new Asteroid(1, 1, 0, null);
        Settler sett = new Settler(a);
        cont.addSettler (new Settler(new Asteroid(0, 0, 1, null))) ;// azï¿½rt hogy az elsï¿½dleges settler halï¿½lï¿½val ne ï¿½rjen vï¿½get a jï¿½tï¿½k.
        Uran u = new Uran();
        Controler.getInstance().addSettler(sett);
        Space.getInstance().addOrbit(a);
        Space.getInstance().step();

        sett.getInventory().addUran(u);

        Logger.formatPrint("_4_4_SettlerPutsBackUranAndItExplodes");

        sett.replaceResource("Uran");

        Logger.formatPrint("Teszt vï¿½ge");
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

        Logger.formatPrint("Teszt vï¿½ge");
    }

    public static void _5_Build()
    {
        System.out.println("ï¿½pï¿½tï¿½s esetek:");
        System.out.println("\t1. Successful Robot Create");
        System.out.println("\t2. Failed Robot Create Missing Material");
        System.out.println("\t3. Successful Base Create");
        System.out.println("\t4. Failed Base Create Missing Material");

        int opt = SelectOption("Vï¿½laszd ki a use-case sorszï¿½mï¿½t: (1-4)", 1,4);
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
     * A telepes (játékos) létrehoz egy új robotot.
     * A program kiírja a konzolra a sikeres robot készítés mûveletéhez kapcsolódó függvény hívásokat.
     * Init: Egy aszteroidán létrehozunk egy settlert, aki képes létrehozni egy robotot
     * Feltöltjük az Inventoryt egy Uran, Iron, Carbon-nal.
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

        Logger.formatPrint("Teszt vï¿½ge");
    }
    /**
     * A telepes (játékos) létre próbál hozni egy új robotot, de nincs elég nyersanyagja.
     * A program kiírja a konzolra a sikertelen robot készítés mûveletéhez kapcsolódó függvény hívásokat.
     * Init: Egy aszteroidán létrehozunk egy settlert, aki nem képes létrehozni robotot, mert nincs elegendõ nyersanyagja
     */
    public static void _5_2_FailedRobotCreateMissingMaterial()
    {
        Logger.formatPrint("Init");

        Asteroid a = new Asteroid(5, 2, 5, null);
        Settler s1 = new Settler(a);

        Logger.formatPrint("_5_2_FailedRobotCreateMissingMaterial");

        s1.createRobot();

        Logger.formatPrint("Teszt vï¿½ge");
    }
    /**
     * A telepes (játékos) felépít az aszteroidán egy bázist a rajta lévõ többi telepessel és ezzel megnyerik a játékot. 
     * A telepeseknek van elég nyersanyaga a bázis megépítéséhez, 
     *      ekkor a program a képernyõre írja a bázis építésével és a játék befejezésével járó függvényhívásokat.
     * Init: Egy aszteroidán létrehozunk két settlert, akik képesek felépíteni a bázist
     * Feltöltjük s1 Settler Inventory-jába 3 Carbon, 3 Iron, 3 Uran, s2 Settler Inventory-jába 3 Ice nyersanyagot.
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

        Logger.formatPrint("Teszt vï¿½ge");
    }
     /**
     * A telepes (játékos) megpróbál felépíteni az aszteroidán egy bázist a rajta lévõ többi telepessel, de nincs elég nyersanyagjuk.  
     * A telepeseknek nincs elég nyersanyaga a bázis megépítéséhez,
     *      ekkor a program a képernyõre írja a bázis építésével való próbálkozáshoz kapcsolódó függvényhívásokat.
     * Init: Egy aszteroidán létrehozunk két settlert, akik nem képesek felépíteni a bázist, mert nincs meg a szükséges nyersanyag mennyiségük
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

        Logger.formatPrint("Teszt vï¿½ge");
    }

    public static void _6_Stargate()
    {
        System.out.println("Teleportkapu esetek:");
        System.out.println("\t1. Successful Stargate Create");
        System.out.println("\t2. Failed Stargate Create Missing Material");
        System.out.println("\t3. Successful Stargate Place");

        int opt = SelectOption("Vï¿½laszd ki a use-case sorszï¿½mï¿½t: (1-3)", 1,3);
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
     * A telepes (játékos) létrehoz egy új teleportkapu-párt és elraktározza a tárolójában õket.
     * A program kiírja a konzolra az teleportkapu-pár készítés mûveletéhez kapcsolódó függvény hívásokat.
     * Init: Egy aszteroidán létrehozunk egy settlert, aki képes létrehozni egy teleportkapu-párt.
     * Feltöltjük az Inventoryt 1 Uran, 2 Iron, és 1 Ice-cal.
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

        Logger.formatPrint("Teszt vï¿½ge");
    }
    /**
     * A telepes (játékos) megpróbál létrehozni egy új teleportkapu-párt, de nincs elég nyersanyagja.
     * A program kiírja a konzolra a sikertelen teleportkapu-pár készítés mûveletéhez kapcsolódó függvény hívásokat.
     * Init: Egy aszteroidán létrehozunk egy settlert, aki nem képes létrehozni egy teleportkapu-párt, mert nincs elegendõ nyersanyagja.
     */
    public static void _6_2_FailedStargateCreateMissingMaterial()
    {
        Logger.formatPrint("Init");

        Asteroid a1 = new Asteroid(6, 2, 4, null);
        Settler s1 = new Settler(a1);
        
        Logger.formatPrint("6_2_FailedStargateCreateMissingMaterial");

        s1.stargate();

        Logger.formatPrint("Teszt vï¿½ge");
    }
    /**
     * A telepes egy aszteroida szomszédságában lehelyez egy teleport kaput.
     * A program kiírja a konzolra az teleportkapu elhelyezésének mûveletéhez kapcsolódó függvény hívásokat.
     * Init: Egy aszteroidán létrehozunk egy settlert, aki létrehoz egy teleportkapu-párt magához (elsõ stargate() hívás).
     *      Utána ebbõl a két akapuból helyez el egyet a teszt során.
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
        Logger.formatPrint("Teszt vï¿½ge");
    }
    
    public static void _7_SunStorm()
    {
        System.out.println("Napvihar esetek:");
        System.out.println("\t1. Sunstorm On Not Fully Drilled Asteroid");
        System.out.println("\t2. Sunstorm On Not Empty Asteroid");
        System.out.println("\t3. Sunstorm On Safe Asteroid");

        int opt = SelectOption("Vï¿½laszd ki a use-case sorszï¿½mï¿½t: (1-3)", 1,3);
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
        cont.addSettler(new Settler(new Asteroid(20,20,1,null))); //ï¿½ ï¿½letben marad majd, nem lesz jï¿½tï¿½k vï¿½ge

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
        cont.addSettler(new Settler(new Asteroid(20,20,1,null))); //ï¿½ ï¿½letben marad majd, nem lesz jï¿½tï¿½k vï¿½ge

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
        cont.addSettler(new Settler(new Asteroid(20,20,1,null))); //ï¿½ ï¿½letben marad majd, nem lesz jï¿½tï¿½k vï¿½ge

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
        System.out.println("Napfï¿½ny ï¿½rkezï¿½se esetek:");
        System.out.println("\t1. Sunlight Arrives To Not Fully Drilled Asteroid");
        System.out.println("\t2. Sunlight Arrives To Fully Drilled Asteroid With Iron");
        System.out.println("\t3. Sunlight Arrives To Fully Drilled Asteroid With Uran");
        System.out.println("\t4. Sunlight Arrives To Fully Drilled Asteroid With Ice");

        int opt = SelectOption("Vï¿½laszd ki a use-case sorszï¿½mï¿½t: (1-4)", 1,4);
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

    public static void _8_1_SunlightArrivesToNotFullyDrilledAsteroid()
    {
        Logger.formatPrint("Init");

        Space s = Space.getNewSpace();
        s.setSunstorm_time(5);
        Asteroid a = new Asteroid(1,8,2,new Iron());
        s.addOrbit(a);

        Logger.formatPrint("_8_1_SunlightArrivesToNotFullyDrilledAsteroid");
        
        s.step();

        Logger.formatPrint("Teszt vï¿½ge");
    }
    public static void _8_2_SunlightArrivesToFullyDrilledAsteroidWithIron()
    {
        Logger.formatPrint("Init");

        Space s = Space.getNewSpace();
        s.setSunstorm_time(5);
        Asteroid a = new Asteroid(1,8,0,new Iron());
        s.addOrbit(a);

        Logger.formatPrint("_8_2_SunlightArrivesToFullyDrilledAsteroidWithIron");

        s.step();

        Logger.formatPrint("Teszt vï¿½ge");
    }
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
        // felvesz egy settlert az aszteroidï¿½ra ï¿½s 
        Settler set = new Settler(a1);
        Settler set2 = new Settler(a2);

        Robot rob = new Robot();
        rob.setLocation(a1);
        a1.addTraveler(rob);

        c.addRobot(rob);
        c.addSettler(set);

        Logger.formatPrint("_8_3_SunlightArrivesToFullyDrilledAsteroidWithUran");

        s.step();
        
        Logger.formatPrint("Teszt vï¿½ge");
    }
    public static void _8_4_SunlightArrivesToFullyDrilledAsteroidWithIce()
    {
        Logger.formatPrint("Init");

        Space s = Space.getNewSpace();
        s.setSunstorm_time(5);
        Asteroid a = new Asteroid(1,8,0,new Ice());
        s.addOrbit(a);

        Logger.formatPrint("_8_4_SunlightArrivesToFullyDrilledAsteroidWithIce");

        s.step();

        Logger.formatPrint("Teszt vï¿½ge");
    }

    public static void _9_Robot()
    {
        System.out.println("Robot esetek:");
        System.out.println("\t1. Robot Is On Not Fully Drilled Asteroid");
        System.out.println("\t2. Robot Is On Fully Drilled Asteroid");

        int opt = SelectOption("Vï¿½laszd ki a use-case sorszï¿½mï¿½t: (1-2)", 1,2);
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

    public static void _9_1_RobotIsOnNotFullyDrilledAsteroid()
    {
        Logger.formatPrint("Init");

        Asteroid a = new Asteroid(1,8,2,null);
        Robot r = new Robot();
        r.setLocation(a);
        a.addTraveler(r);

        Logger.formatPrint("_9_1_RobotIsOnNotFullyDrilledAsteroid");

        r.step();
        
        Logger.formatPrint("Teszt vï¿½ge");
    }
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
        
        Logger.formatPrint("Teszt vï¿½ge");
    }
}
