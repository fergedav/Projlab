package skeleton;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
    {}
    public static void _1_2_MoveOnOfflineGate()
    {}
    public static void _1_3_MoveOnOnlineGate()
    {}

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
    {}
    public static void _2_2_DiggingAndBreakthrought()
    {}
    public static void _2_3_DiggingBreakthroughtUran()
    {}
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
    {}
    public static void _4_2_SettlerReplaceIron()
    {}
    public static void _4_3_SettlerPutsBackIceAndItMeltsInTheSunlight()
    {}
    public static void _4_4_SettlerPutsBackUranAndItExplodes()
    {}
    public static void _4_5_SettlerTriesToPutBackIronIntoAStargate()
    {}

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
    {}
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
    {}
    public static void _8_2_SunlightArrivesToFullyDrilledAsteroidWithIron()
    {}
    public static void _8_3_SunlightArrivesToFullyDrilledAsteroidWithUran()
    {}
    public static void _8_4_SunlightArrivesToFullyDrilledAsteroidWithIce()
    {}

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
    {}
    public static void _9_2_RobotIsOnFullyDrilledAsteroid()
    {}
}
