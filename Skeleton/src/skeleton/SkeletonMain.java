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
     * A telepes (j�t�kos) az aktu�lis aszteroid�r�l egy szomsz�dos aszteroid�ra l�p.
     * A program ki�rja a konzolra az aszteroid�ra l�p�shez kapcsol�d� f�ggv�ny h�v�sokat.
     */
    public static void _1_1_MoveOnAsteroid()
    {
        Logger.formatPrint("Init");

        /** A teszt lefut�s�hoz sz�ks�ges objektumok inicializ�l�sa. */
        Asteroid a1 = new Asteroid(0, 0, 8, null);
        Asteroid a2 = new Asteroid(1, 0, 8, null);
        Settler s = new Settler(a1);
        
        /** �gy az s telepesnek van hov� l�pnie, a k�t el�bb l�trehozott aszteroida ez�ltal egym�s szomsz�dai lesznek. */
        a1.addNeighbour(a2);
        a2.addNeighbour(a1);

        Logger.formatPrint("_1_1_MoveOnAsteroid");

        /**A h�v�si l�nc els� eleme, amib�l elindul az eg�sz eset. */
        s.move(0);

        Logger.formatPrint("Teszt v�ge");
    }

    /**
     * A telepes (j�t�kos) olyan teleportkapura l�p, aminek m�g nincs lerakva a p�rja.
     * A program ki�rja a konzolra a teleportkapura l�p�shez kapcsol�d� f�ggv�ny h�v�sokat.
     */
    public static void _1_2_MoveOnOfflineGate()
    {
        Logger.formatPrint("Init");

        /** A teszt lefut�s�hoz sz�ks�ges objektumok inicializ�l�sa. K�t csillagkaput kell l�trehozzunk, 
		 * mert a lerakott csillagkapunak mindenk�pp �ssze kell legyen k�tve
         * egy m�sikkal, m�g akkor is ha a p�rja m�g nincs lerakva egy aszteroida szomsz�ds�g�ba.
         */
        Stargate s1 = new Stargate();
        Stargate s2 = new Stargate();

        /** A l�t teleportkapu ez�ltal �ssze lesz k�tve. */
        s1.entagle(s2);
        s2.entagle(s1);
        Asteroid a1 = new Asteroid(0, 0, 8, null);
        Settler s = new Settler(a1);

        /** Az s1 kapu  le van helyezve az a1 aszteroida szomsz�ds�g�ba.*/
        a1.addNeighbour(s1);
        s1.place(a1);

        Logger.formatPrint("_1_2_MoveOnOfflineGate");

        /**A h�v�si l�nc els� eleme, amib�l elindul az eg�sz eset. */
        s.move(0);
        Logger.formatPrint("Teszt v�ge");
    }
    /**
     * A telepes (j�t�kos) olyan teleportkapura l�p, aminek m�r le van rakva a p�rja.
     * A program ki�rja a konzolra az teleportkapura l�p�shez �s a teleport�l�shoz kapcsol�d� f�ggv�ny h�v�sokat.
     */
    public static void _1_3_MoveOnOnlineGate()
    {
        Logger.formatPrint("Init");

         /** L�tre hozzuk a csillagkapukat amiken kereszt�l teleport�lni fogunk */
        Stargate s1 = new Stargate();
        Stargate s2 = new Stargate();

        /** �sszek�tj�k �ket, hogy el tudj�k egym�st �rni. */
        s1.entagle(s2);
        s2.entagle(s1);

        /** L�trehozzuk az aszteroid�kat amikhez a csillagkapuk tartozni fognak. */
        Asteroid a1 = new Asteroid(0, 0, 8, null);
        Asteroid a2 = new Asteroid(0, 0, 8, null);

        /** L�trehozzuk a telepest, aki majd teleport�lni fog. */
        Settler s = new Settler(a1);

        /** Elhelyezz�k a csillagkapukat az aszteroid�k szomsz�ds�g�ba. */
        a1.addNeighbour(s1);
        a2.addNeighbour(s2);
        s1.place(a1);
        s2.place(a2);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");
        /**A h�v�si l�nc els� eleme, amib�l elindul az eg�sz eset. */
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
     * A telepes (j�t�kos) egy egys�ggel m�lyebbre �s az aszteroida k�rg�ben, de m�g nem �sta �t teljesen.
     * A program ki�rja a konzolra az �s�shoz kapcsol�d� f�ggv�ny h�v�sokat.
     */
    public static void _2_1_SimpleDigging()
    {
        Logger.formatPrint("Init");
        
        /** L�trehozzuk a telepest aki �s �s az aszteroid�t amin �s. */
        Asteroid a = new Asteroid(0, 0, 8, null);
        Settler s = new Settler(a);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");
        /**A h�v�si l�nc els� eleme, amib�l elindul az eg�sz eset. */
        s.digging();

        Logger.formatPrint("Teszt v�ge"); 
    }


    /**
     * A telepes (j�t�kos) egy egys�ggel m�lyebbre �s az aszteroida k�rg�ben, teljesen �t�sta (az aszteroida nincs napk�zelben).
     * A program ki�rja a konzolra az �s�shoz �s az �tt�r�shez kapcsol�d� f�ggv�ny h�v�sokat.
     */
    public static void _2_2_DiggingAndBreakthrought()
    {
        Logger.formatPrint("Init");
        
        /** L�trehozzuk a telepest aki �s �s az aszteroid�t amin �s. Az aszteroida harmadik param�tere, ami a k�regvastags�got jel�li, kezdetnek 1, hogy egy �s�ssal
         * �t lehessen t�rni a k�rget �s lefuthasson a teszteset t�bbi f�ggv�nyh�v�sa is.
         */
        Asteroid a = new Asteroid(0, 0, 1, null);
        Settler s = new Settler(a);
        
        a.addTraveler(s);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");
        /** A h�v�si l�nc els� eleme, amib�l elindul az eg�sz eset. */
        s.digging();

        Logger.formatPrint("Teszt v�ge");
    }

    /**
     * A telepes (j�t�kos) egy egys�ggel m�lyebbre �s az aszteroida k�rg�ben, 
	 * teljesen �t�sta, a magja ur�n (az aszteroida napk�zelben van, �gy robban).
     * A program ki�rja a konzolra az �s�shoz, az �tt�r�shez �s az ur�n robban�s�hoz kapcsol�d� f�ggv�ny h�v�sokat.
     */
    public static void _2_3_DiggingBreakthroughtUran()
    {
        Logger.formatPrint("Init");

        /** L�trehozzuk a telepest aki �s �s az aszteroid�t amin �s. Az aszteroida harmadik param�tere, ami a k�regvastags�got jel�li, kezdetnek 1, hogy egy �s�ssal
         * �t lehessen t�rni a k�rget �s lefuthasson a teszteset t�bbi f�ggv�nyh�v�sa is. Az a2 aszteroida az�rt kell, hogy a k�s�bbiekben l�trehozott robotnak
         * a robban�s hat�s�ra, legyen olyan aszteroda, ahova mehet a robban� aszteroid�r�l. Ez�rt �ll�tjuk be a k�t aszteroid�nak, hogy szomsz�dok legyenek. Az a1 els� k�t param�tere
         * most 1, mert ez jelenti azt, hogy napk�zelben van.
         */
        Asteroid a1 = new Asteroid(1, 1, 1, new Uran());
        Asteroid a2 = new Asteroid(0, 0, 1, null);
        Settler s = new Settler(a1);

        a2.addNeighbour(a1);
        a1.addNeighbour(a2);

        //felt�ltj�k az Inventorit Uran-nal, Iron-nal �s Ice-al
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
        /** A h�v�si l�nc els� eleme, amib�l elindul az eg�sz eset. */
        s.digging();

        Logger.formatPrint("Teszt v�ge");
    }

    /**
     * A telepes (j�t�kos) egy egys�ggel m�lyebbre �s az aszteroida k�rg�ben, teljesen �t�sta,
     *  a magja j�g (az aszteroida nincs napk�zelben, �gy elp�rolog).
     * A program ki�rja a konzolra az �s�shoz, az �tt�r�shez �s a j�g elp�rolg�s�hoz kapcsol�d� f�ggv�ny h�v�sokat.
     */
    public static void _2_4_DiggingBreakthroughtIce()
    {
        Logger.formatPrint("Init");
        /**L�trehozzuk a telepest aki �s �s az aszteroid�t amin �s. Az aszteroida harmadik param�tere, ami a k�regvastags�got jel�li, kezdetnek 1, hogy egy �s�ssal
         * �t lehessen t�rni a k�rget �s lefuthasson a teszteset t�bbi f�ggv�nyh�v�sa is. Az aszteroida els� k�t param�tere most 1, mert ez jelenti azt, hogy napk�zelben van.
         */
        Asteroid a = new Asteroid(1, 1, 1, new Ice());
        Settler s = new Settler(a);

        Space.getInstance().addOrbit(a);
        Space.getInstance().step();

        Logger.formatPrint("_2_4_DiggingBreakthroughtIce");
        /** A h�v�si l�nc els� eleme, amib�l elindul az eg�sz eset. */
        s.digging();

        Logger.formatPrint("Teszt v�ge");
    }
    

    /**
     * A telepes (j�t�kos) megk�s�rel egy teleportkapun �sni.
     * A program ki�rja az �s�si k�s�rlethez kapcsol�d� f�ggv�ny h�v�sokat.
     */
    public static void _2_5_DiggingOnStargate()
    {
        Logger.formatPrint("Init");

        /** K�t csillagkaput kell l�trehozzunk, mert a lerakott csillagkapunak
         *  mindenk�pp �ssze kell legyen k�tve egy m�sikkal, m�g akkor is ha a p�rja m�g nincs lerakva 
         * egy aszteroida szomsz�ds�g�ba. */
        Stargate st1 = new Stargate();
        Stargate st2 = new Stargate();
        

        st1.entagle(st2);
        st2.entagle(st1);
        Settler s = new Settler(st1);

        Logger.formatPrint("_2_5_DiggingOnStargate");
        /** A h�v�si l�nc els� eleme, amib�l elindul az eg�sz eset. */
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

    /**
     * B�ny�sz�si pr�b�lkoz�s nem teljesen �tf�rt k�rg� aszteroid�n.
     * eredm�yntelen lesz. M�g nem lehet b�ny�szni.
     * 
     * Init: l�trehozunk egy aszteroid�t aminek VAN k�rge �s vas magja, �s r� egy telepest.
     * A futtat�shoz a telepesnek kiadjuk a parancsot hogy b�ny�sszon. 
     */
    public static void _3_1_MiningOnNotFullyDrilledAsteroid()
    {
        Logger.formatPrint("Init");

        Asteroid asteroid = new Asteroid(1,1,10,new Iron());
        Settler settler = new Settler(asteroid);

        Logger.formatPrint("_3_1_MiningOnNotFullyDrilledAsteroid");

        settler.mining();

        Logger.formatPrint("Teszt V�ge");
    }

    /**
     * Vas b�ny�sz�s teljesen �tf�rt k�rg� aszteroid�n.
     * A b�ny�sz�s sikeres lesz, a telepes elteszi a f�met az inventorij�ba.
     * 
     * Init: l�trehozunk egy aszteroid�t aminek NINCS k�rge �s vas magja, �s r� egy telepest.
     * A futtat�shoz a telepesnek kiadjuk a parancsot hogy b�ny�sszon. 
     */
    public static void _3_2_MiningIronOnFullyDrilledAsteroid()
    {
        Logger.formatPrint("Init");

        Asteroid asteroid = new Asteroid(1,1,0,new Iron());
        Settler settler = new Settler(asteroid);

        Logger.formatPrint("_3_2_MiningIronOnFullyDrilledAsteroid");

        settler.mining();

        Logger.formatPrint("Teszt V�ge");
    }

    /**
     * B�ny�sz�s teljesen �tf�rt k�rg� aszteroid�n.
     * A b�ny�sz�s sikertelen lesz, nincs mit kib�ny�szni.
     * 
     * Init: l�trehozunk egy aszteroid�t aminek NINCS k�rge, NINCS magja, �s r� egy telepest.
     * A futtat�shoz a telepesnek kiadjuk a parancsot hogy b�ny�sszon. 
     */
    public static void _3_3_MiningOnFullyDrilledButEmptyAsteroid()
    {
        Logger.formatPrint("Init");

        Asteroid asteroid = new Asteroid(1,1,0,null);
        Settler settler = new Settler(asteroid);

        Logger.formatPrint("_3_3_MiningOnFullyDrilledButEmptyAsteroid");

        settler.mining();

        Logger.formatPrint("Teszt V�ge");
    }

    /**
     * B�ny�sz�s �rkapun.
     * A b�ny�sz�s sikertelen lesz, a kapun nem lehet b�ny�szni.
     * 
     * Init: l�trehozunk egy �rkaput �s r� egy telepest.
     * A futtat�shoz a telepesnek kiadjuk a parancsot hogy b�ny�sszon. 
     */
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

    /**
     * A telepes megpr�b�l m�g �t nem f�rt aszteroid�ba nyersanyagot visszatenni
     * A pr�b�lkoz�s sikertelen lesz, az aszteroid�nak m�g van k�rge.
     * 
     * Init: L�trehozunk egy aszteroid�t aminek VAN k�rge, �s r� egy telepest.
     * A telepes inventorij�ba tesz�nk egy f�met.
     * Az ind�t�shoz kiadjuk a parancsot hogy pr�b�ljon letenni egy f�met.
     */
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

    /**
     * A telepes megpr�b�l egy �TF�RT, �RES aszteroid�ba  f�met visszatenni
     * A pr�b�lkoz�s sikeres lesz.
     * 
     * Init: L�trehozunk egy aszteroid�t aminek NINCS k�rge, NINCS a magj�ban semmi, �s r� egy telepest.
     * A telepes inventorij�ba tesz�nk egy f�met.
     * Az ind�t�shoz kiadjuk a parancsot hogy pr�b�ljon letenni egy f�met.
     */
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

     /**
     * A telepes megpr�b�l egy �TF�RT, �RES, NAPF�NYBEN L�V� aszteroid�ba JEGET visszatenni.
     * A pr�b�lkoz�s sikeres lesz, de a j�g a napf�nyt�l elolvad.
     * 
     * Init: L�trehozunk egy aszteroid�t aminek NINCS k�rge, NINCS a magj�ban semmi, �s r� egy telepest.
     * A telepes inventorij�ba tesz�nk egy Jeget.
     * 
     * L�trehozunk tov�bb� egy Space objektumot, mert az h�vja a napf�nyt az aszteroid�n, 
     * beregisztr�ljuk az aszteroid�t, �s l�ptetj�k hogy �rtes�tse az aszteroid�t, hogy f�nyben van.
     * 
     * Az ind�t�shoz kiadjuk a parancsot hogy pr�b�ljon letenni egy Jeget.
     */
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

    /**
     * A telepes megpr�b�l egy �TF�RT, �RES, NAPF�NYBEN L�V� aszteroid�ba UR�NT visszatenni.
     * A pr�b�lkoz�s sikeres lesz, de az Ur�n a napf�nyt�l felrobban.
     * 
     * Init: L�trehozunk egy aszteroid�t aminek NINCS k�rge, NINCS a magj�ban semmi, �s r� egy telepest.
     * A telepes inventorij�ba tesz�nk egy Ur�nt.
     * 
     * L�trehozunk tov�bb� egy Space objektumot, mert az h�vja a napf�nyt az aszteroid�n, 
     * beregisztr�ljuk az aszteroid�t, �s l�ptetj�k hogy �rtes�tse az aszteroid�t, hogy f�nyben van.
     * 
     * Az ind�t�shoz kiadjuk a parancsot hogy pr�b�ljon letenni egy Ur�nt.
     */
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

    /**
     * A telepes megpr�b�l egy �rkapuba (Stargate)  vasat  visszatenni.
     * A pr�b�lkoz�s sikertelen lesz, az �rkapuba nem lehet anyagokat tenni.
     * 
     * Init: L�trehozunk k�t �rkaput, �sszek�tj�k �ket, mert p�rban m�k�dnek, �s l�trehozunk az egyikre egy telepest.
     * A telepes inventorij�ba tesz�nk egy vasat.
     * 
     * 
     * Az ind�t�shoz kiadjuk a parancsot hogy pr�b�ljon letenni egy f�met.
     */
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
     * Init: Egy aszteroid�n l�trehoztunk egy settlert, aki k�pes l�trehozni egy robotot
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
     * Init: Egy aszteroid�n l�trehoztunk egy settlert, aki nem k�pes l�trehozni robotot, mert nincs elegend� nyersanyagja
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
     * Init: Egy aszteroid�n l�trehoztunk k�t settlert, akik k�pesek fel�p�teni a b�zist
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
     * Init: Egy aszteroid�n l�trehoztunk k�t settlert, akik nem k�pesek fel�p�teni a b�zist, mert nincs meg a sz�ks�ges nyersanyag mennyis�g�k
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
     * A telepes (j�t�kos) l�trehoz egy �j teleportkapi-p�rt �s elrakt�rozza a t�rol�j�ban �ket.
     * A program ki�rja a konzolra a teleportkapi-p�r k�sz�t�s m�velet�hez kapcsol�d� f�ggv�ny h�v�sokat.
     * Init: Egy aszteroid�n l�trehoztunk egy settlert, aki k�pes l�trehozni egy teleportkapi-p�rt.
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
     * A telepes (j�t�kos) megpr�b�l l�trehozni egy �j teleportkapi-p�rt, de nincs el�g nyersanyagja.
     * A program ki�rja a konzolra a sikertelen teleportkapi-p�r k�sz�t�s m�velet�hez kapcsol�d� f�ggv�ny h�v�sokat.
     * Init: Egy aszteroid�n l�trehoztunk egy settlert, aki nem k�pes l�trehozni egy teleportkapi-p�rt, mert nincs elegend� nyersanyagja.
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
     * Init: Egy aszteroid�n l�trehoztunk egy settlert, aki l�trehoz egy teleportkapi-p�rt mag�hoz (els� stargate() h�v�s).
     *      ur�na ebb�l a k�t akapub�l helyez el egyet a teszt sor�n.
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
     * A napf�ny el�r egy aszteroid�t, amelynek nincs teljesen �tf�rva a k�rge.
     * 
     * Az aszteroid�ban l�v� nyersanyag nem tud reakci�ba l�pni a napf�nnyel.
     * A program a konzolra �rja a napf�ny �rkez�s�vel kapcsolatos f�ggv�nyh�v�sokat.
     * 
     * Init: l�trehozunk egy Space objektumot, ebbe felvesz�nk egy aszteroid�t, amelynek
     * nincs �tf�rva a k�rge, �s Iron a magja.
     * (a sunstorm_time �rt�k�t �tre �ll�tjuk, hogy ne legyen most napvihar)
     * 
     * A Space step() met�dusa fogja ind�tani a f�ggv�nyh�v�sokat.
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
     * A napf�ny el�r egy aszteroid�t, amelynek �t van f�rva a k�rge, �s vas van benne.
     * 
     * A napf�ny reakci�ba l�p a benne l�v� vassal. A program a konzolra �rja a napf�ny
     * �rkez�s�vel �s a reakci�val kapcsolatos f�ggv�nyh�v�sokat.
     * 
     * Init: l�trehozunk egy Space objektumot, ebbe felvesz�nk egy aszteroid�t, amelynek �t van f�rva
     * a k�rge �s Iron a magja.
     * (a sunstorm_time �rt�k�t �tre �ll�tjuk, hogy ne legyen most napvihar)
     * 
     * A Space step() met�dusa fogja ind�tani a f�ggv�nyh�v�sokat.
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
     * A napf�ny el�r egy aszteroid�t, amelynek �t van f�rva a k�rge, �s ur�n van benne.
     * 
     * A napf�ny reakci�ba l�p a benne l�v� ur�nnal. Ekkor az aszteroida felrobban, 
     * megsemmis�tve a benne l�v� nyersanyagot. Az aszteroid�n l�v� Robotok �trep�lnek
     * m�s aszteroid�ra, m�g a Telepesek meghalnak. A program a konzolra �rja a napf�ny 
     * �rkez�s�vel, a reakci�val, robban�ssal �s meghal�ssal kapcsolatos f�ggv�nyh�v�sokat.
     * 
     * Init: l�trehozunk egy Controller objektumot, ami l�trehoz egy space objektumot is.
     * Felvesz�nk k�t aszteroid�t, az egyiket a spacebe (ez �t van f�rva �s ur�n a magja),
     * hogy csak azt �rje el a napf�ny. Erre az aszteroid�ra felvesz�nk egy telepest �s egy robotot.
     * (a sunstorm_time �rt�k�t �tre �ll�tjuk, hogy ne legyen most napvihar)
     * 
     * A space step() met�dusa ind�tja a f�ggv�nyh�v�sokat.
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
     * A napf�ny el�r egy aszteroid�t, amelynek �t van f�rva a k�rge, �s j�g van benne.
     * 
     * A napf�ny reakci�ba l�p a benne l�v� j�ggel, ami �gy elp�rolog. 
     * A program a konzolra �rja a napf�ny �rkez�s�vel �s a reakci�val kapcsolatos f�ggv�nyh�v�sokat.
     * 
     * Init: l�trehozunk egy Space objektumot, ebbe felvesz�nk egy aszteroid�t, amelynek �t van f�rva
     * a k�rge �s Ice a magja.
     * (a sunstorm_time �rt�k�t �tre �ll�tjuk, hogy ne legyen most napvihar)
     * 
     * A Space step() met�dusa fogja ind�tani a f�ggv�nyh�v�sokat.
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
     *  A robot egy nem teljesen kif�rt aszteroid�n �ll, ekkor f�rni kezdi azt. 
     * 
     * A program ki�rja a robot f�r�s�val j�r� f�ggv�nyh�v�sokat.
     * 
     * Init: l�trehozunk egy nem �tf�rt aszteroid�t. Erre tessz�k r� a robotot.
     * 
     * A robot step() met�dusa ind�tja a f�ggv�nyh�v�sokat.
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
     * A robot egy teljesen kif�rt aszteroid�n van, ez�rt v�laszt egy szomsz�dot, �s oda mozog.
     * 
     * A program ki�rja a konzolra a robot mozg�s�val j�r� f�ggv�nyh�v�sokat.
     * 
     * Init: l�trehozunk k�t szomsz�dos aszteroid�t, az egyik teljesen �t van f�rva. Erre
     * az �tf�rt aszteroid�ra tessz�k r� a robotot.
     * 
     * A robot step() met�dusa ind�tja a f�ggv�nyh�v�sokat.
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
