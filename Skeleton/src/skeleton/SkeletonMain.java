package skeleton;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jdk.javadoc.internal.doclets.formats.html.markup.ContentBuilder;
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
                System.out.println(msg);                
                n = Integer.parseInt(r.readLine());

                if(n >= min && n <=max)
                    return n;
                    System.out.println("Hibás sorszám!");   
                
                
            } catch (Exception e) {
                System.out.println("Hiba: " + e.getMessage());
            }
            
        }
    }

    public static boolean SelectCategory()
    {
        System.out.println("1. Mozgás");
        System.out.println("2. Fúrás");
        System.out.println("3. Bányászat");
        System.out.println("4. Anyag visszarakása");
        System.out.println("5. Építés");
        System.out.println("6. Csillagkapu dolgok");
        System.out.println("7. Napvihar");
        System.out.println("8. Napfény érkezése");
        System.out.println("9. Robot Step\n");
        System.out.println("10. Kilépés");
        
        int category = SelectOption("Válaszd ki a kategória sorszámát: (1-10)", 1,10);

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
                return false;
        }
        return true;
    }

    public static void _1_Movement()
    {
        System.out.println("Mozgás esetek:");
        System.out.println("\t1. Move On Asteroid");
        System.out.println("\t2. Move On Offline Gate");
        System.out.println("\t3. Move On Online Gate");

        int opt = SelectOption("Válaszd ki a use-case sorszámát: (1-3)", 1,3);
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
        }
    }

    public static void _1_1_MoveOnAsteroid()
    {
        Logger.formatPrint("Init");

        Asteroid a1 = new Asteroid(0, 0, 8, null);
        Asteroid a2 = new Asteroid(1, 0, 8, null);
        Settler s = new Settler(a1);
        

        a1.addNeighbour(a2);
        a2.addNeighbour(a1);

        Logger.formatPrint("_1_1_MoveOnAsteroid");

        s.move(0);

        Logger.formatPrint("Teszt vége");
    }
    public static void _1_2_MoveOnOfflineGate()
    {
        Logger.formatPrint("Init");

        Stargate s1 = new Stargate();
        Stargate s2 = new Stargate();

        s1.entagle(s2);
        s2.entagle(s1);

        Asteroid a1 = new Asteroid(0, 0, 8, null);

        Settler s = new Settler(a1);

        a1.addNeighbour(s1);

        s1.place(a1);

        Logger.formatPrint("_1_2_MoveOnOfflineGate");

        s.move(0);
        Logger.formatPrint("Teszt vége");
    }
    public static void _1_3_MoveOnOnlineGate()
    {
        Logger.formatPrint("Init");

        Stargate s1 = new Stargate();
        Stargate s2 = new Stargate();

        s1.entagle(s2);
        s2.entagle(s1);

        Asteroid a1 = new Asteroid(0, 0, 8, null);
        Asteroid a2 = new Asteroid(0, 0, 8, null);

        Settler s = new Settler(a1);

        a1.addNeighbour(s1);

        a2.addNeighbour(s2);

        s1.place(a1);
        s2.place(a2);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");

        s.move(0);
        Logger.formatPrint("Teszt vége");

    }

    public static void _2_Digging()
    {
        System.out.println("Fúrás esetek:");
        System.out.println("\t1. Simple Digging");
        System.out.println("\t2. Digging And Breakthrought");
        System.out.println("\t3. Digging Breakthrought Uran");
        System.out.println("\t4. Digging Breakthrought Ice");
        System.out.println("\t5. Digging On Stargate");

        int opt = SelectOption("Válaszd ki a use-case sorszámát: (1-5)", 1,5);
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
        
        Asteroid a = new Asteroid(0, 0, 8, null);
        Settler s = new Settler(a);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");

        s.digging();

        Logger.formatPrint("Teszt vége"); 
    }
    public static void _2_2_DiggingAndBreakthrought()
    {
        Logger.formatPrint("Init");
        
        Asteroid a = new Asteroid(0, 0, 1, null);
        Settler s = new Settler(a);
        
        a.addTraveler(s);

        Logger.formatPrint("_1_3_MoveOnOnlineGate");

        s.digging();

        Logger.formatPrint("Teszt vége");
    }
    public static void _2_3_DiggingBreakthroughtUran()
    {
        Logger.formatPrint("Init");

        Asteroid a1 = new Asteroid(1, 1, 1, new Uran());
        Asteroid a2 = new Asteroid(0, 0, 1, null);
        Settler s = new Settler(a1);

        a2.addNeighbour(a1);
        a1.addNeighbour(a2);

        Stargate s1 = new Stargate();
        Stargate s2 = new Stargate();

        s1.entagle(s2);
        s2.entagle(s1);
        s2.place(a2);

        Space.getInstance().addOrbit(a1);
        Space.getInstance().step();

        Robot r = new Robot();
        r.setLocation(a1);
        a1.addTraveler(r);

        Logger.formatPrint("_2_3_DiggingBreakthroughtUran");

        s.digging();

        Logger.formatPrint("Teszt vége");
    }
    public static void _2_4_DiggingBreakthroughtIce()
    {}
    public static void _2_5_DiggingOnStargate()
    {}

    public static void _3_Mining()
    {
        System.out.println("Bányászat esetek:");
        System.out.println("\t1. Mining On Not Fully Drilled Asteroid");
        System.out.println("\t2. Mining Iron On Fully Drilled Asteroid");
        System.out.println("\t3. Mining On Fully Drilled But Empty Asteroid");
        System.out.println("\t4. Mining On Stargate");

        int opt = SelectOption("Válaszd ki a use-case sorszámát: (1-4)", 1,4);
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
    {}
    public static void _3_2_MiningIronOnFullyDrilledAsteroid()
    {}
    public static void _3_3_MiningOnFullyDrilledButEmptyAsteroid()
    {}
    public static void _3_4_MiningOnStargate()
    {}

    public static void _4_ReplaceResource()
    {
        System.out.println("Bányászat esetek:");
        System.out.println("\t1. Settler Tries To Put Back Iron But He Can’t");
        System.out.println("\t2. Settler Replace Iron");
        System.out.println("\t3. Settler Puts Back Ice And It Melts In The Sunlight");
        System.out.println("\t4. Settler Puts Back Uran And It Explodes");
        System.out.println("\t5. Settler Tries To Put Back Iron Into A Stargate");

        int opt = SelectOption("Válaszd ki a use-case sorszámát: (1-5)", 1,5);
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

        Logger.formatPrint("Teszt vége");

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

        Logger.formatPrint("Teszt vége");
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

        Logger.formatPrint("Teszt vége");
    }
    
    public static void _4_4_SettlerPutsBackUranAndItExplodes()
    {
        Logger.formatPrint("Init");

        Asteroid a = new Asteroid(1, 1, 0, null);
        Settler sett = new Settler(a);
        Uran u = new Uran();
        Controler.getInstance().addSettler(sett);
        Space.getInstance().addOrbit(a);
        Space.getInstance().step();

        sett.getInventory().addUran(u);

        Logger.formatPrint("_4_4_SettlerPutsBackUranAndItExplodes");

        sett.replaceResource("Uran");

        Logger.formatPrint("Teszt vége");
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

        Logger.formatPrint("Teszt vége");
    }

    public static void _5_Build()
    {
        System.out.println("Építés esetek:");
        System.out.println("\t1. Successful Robot Create");
        System.out.println("\t2. Failed Robot Create Missing Material");
        System.out.println("\t3. Successful Base Create");
        System.out.println("\t4. Failed Base Create Missing Material");

        int opt = SelectOption("Válaszd ki a use-case sorszámát: (1-4)", 1,4);
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

    public static void _5_1_SuccessfulRobotCreate()
    {
        Logger.formatPrint("Init");

        Asteroid a = new Asteroid(5, 1, 5, null);
        Settler s1 = new Settler(a);
        a.addTraveler(s1);
        s1.getInventory().addUran(new Uran());

    }
    public static void _5_2_FailedRobotCreateMissingMaterial()
    {}
    public static void _5_3_SuccessfulBaseCreate()
    {}
    public static void _5_4_FailedBaseCreateMissingMaterial()
    {}

    public static void _6_Stargate()
    {
        System.out.println("Teleportkapu esetek:");
        System.out.println("\t1. Successful Stargate Create");
        System.out.println("\t2. Failed Stargate Create Missing Material");
        System.out.println("\t3. Failed Stargate Create Already Have One");
        System.out.println("\t4. Successful Stargate Place");
        System.out.println("\t5. Failed Stargate Place");

        int opt = SelectOption("Válaszd ki a use-case sorszámát: (1-5)", 1,5);
        switch(opt)
        {
            case 1:
            _6_1_SuccessfulStargateCreate();
                break;
            case 2:
            _6_2_FailedStargateCreateMissingMaterial();
                break;
            case 3:
            _6_3_FailedStargateCreateAlreadyHaveOne();
                break;
            case 4:
            _6_4_SuccessfulStargatePlace();
                break;
            case 5:
            _6_5_FailedStargatePlace();
                break;
        }
    }

    public static void _6_1_SuccessfulStargateCreate()
    {}
    public static void _6_2_FailedStargateCreateMissingMaterial()
    {}
    public static void _6_3_FailedStargateCreateAlreadyHaveOne()
    {}
    public static void _6_4_SuccessfulStargatePlace()
    {}
    public static void _6_5_FailedStargatePlace()
    {}

    public static void _7_SunStorm()
    {
        System.out.println("Napvihar esetek:");
        System.out.println("\t1. Sunstorm On Not Fully Drilled Asteroid");
        System.out.println("\t2. Sunstorm On Not Empty Asteroid");
        System.out.println("\t3. Sunstorm On Safe Asteroid");

        int opt = SelectOption("Válaszd ki a use-case sorszámát: (1-3)", 1,3);
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
    {}
    public static void _7_2_SunstormOnNotEmptyAsteroid()
    {}
    public static void _7_3_SunstormOnSafeAsteroid()
    {}

    public static void _8_SunLight()
    {
        System.out.println("Napfény érkezése esetek:");
        System.out.println("\t1. Sunlight Arrives To Not Fully Drilled Asteroid");
        System.out.println("\t2. Sunlight Arrives To Fully Drilled Asteroid With Iron");
        System.out.println("\t3. Sunlight Arrives To Fully Drilled Asteroid With Uran");
        System.out.println("\t4. Sunlight Arrives To Fully Drilled Asteroid With Ice");

        int opt = SelectOption("Válaszd ki a use-case sorszámát: (1-4)", 1,4);
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

        Space s = Space.getInstance();
        Asteroid a = new Asteroid(1,8,2,new Iron());
        s.addOrbit(a);

        Logger.formatPrint("_8_1_SunlightArrivesToNotFullyDrilledAsteroid");
        
        s.step();

        Logger.formatPrint("Teszt vége");
    }
    public static void _8_2_SunlightArrivesToFullyDrilledAsteroidWithIron()
    {
        Logger.formatPrint("Init");

        Space s = Space.getInstance();
        Asteroid a = new Asteroid(1,8,0,new Iron());
        s.addOrbit(a);

        Logger.formatPrint("_8_2_SunlightArrivesToFullyDrilledAsteroidWithIron");

        s.step();

        Logger.formatPrint("Teszt vége");
    }
    public static void _8_3_SunlightArrivesToFullyDrilledAsteroidWithUran()
    {
        Logger.formatPrint("Init");

        Controler c = Controler.getInstance();
        Space s = Space.getInstance();
        Asteroid a1 = new Asteroid(1,8,2,new Iron());
        Asteroid a2 = new Asteroid(2,8,3, null);
        a1.addNeighbour(a2);
        a2.addNeighbour(a1);
        s.addOrbit(a1);
        Settler set = new Settler(a1);
        Robot r = new Robot();
        r.setLocation(a1);
        c.addRobot(r);
        c.addSettler(set);

        Logger.formatPrint("_8_3_SunlightArrivesToFullyDrilledAsteroidWithUran");

        s.step();
        
        Logger.formatPrint("Teszt vége");
    }
    public static void _8_4_SunlightArrivesToFullyDrilledAsteroidWithIce()
    {
        Logger.formatPrint("Init");

        Space s = Space.getInstance();
        Asteroid a = new Asteroid(1,8,2,new Ice());
        s.addOrbit(a);

        Logger.formatPrint("_8_4_SunlightArrivesToFullyDrilledAsteroidWithIce");

        s.step();

        Logger.formatPrint("Teszt vége");
    }

    public static void _9_Robot()
    {
        System.out.println("Robot esetek:");
        System.out.println("\t1. Robot Is On Not Fully Drilled Asteroid");
        System.out.println("\t2. Robot Is On Fully Drilled Asteroid");

        int opt = SelectOption("Válaszd ki a use-case sorszámát: (1-2)", 1,2);
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

        Logger.formatPrint("Init");
        Asteroid a = new Asteroid(1,8,2,null);
        Robot r = new Robot();
        r.setLocation(a);

        Logger.formatPrint("_9_1_RobotIsOnNotFullyDrilledAsteroid");

        r.step();
        
        Logger.formatPrint("Teszt vége");
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

        Logger.formatPrint("_9_2_RobotIsOnFullyDrilledAsteroid");

        r.step();
        
        Logger.formatPrint("Teszt vége");
    }
}
