package logic;

import java.util.List;
import skeleton.Logger;

public class Space {
    ///Singleton stuff
    private static Space instance;

    private Space()
    {
    setSunstorm_time(0);
    }

    public static Space getInstance()
    {
        if(instance == null)
            instance = new Space();
        return instance;
    }

    /// Singleton ends here

    /**
     * Megszabja, hogy hány körönként legyen napvihar.
     */
    private int sunstorm_time;

    /**
     * A játékban található összes Orbit egy listában tárolva.
     */
    private List<Orbit> orbits;

    //csak a skeleton idejére, a tesztelést segíteni
    public void setSunstorm_time(int t)
    {
        sunstorm_time = t;
    }

    /**
     * Meghívja minden aszteroidán a sunstormArrive metódust.
     */
    private void sunstormCall()
    {
        Logger.startFunctionLogComment(this, "sunstormCall", "");
        for(Orbit o: orbits){
            o.sunstormArrive();
        }
        Logger.endFunctionLog();
    }

    /**
     * Meghívja minden aszteroidán a sunLightArrive metódust.
     */
    private void sunLightCall()
    {
        Logger.startFunctionLogComment(this, "sunLightCall", "");

        for(Orbit o: orbits){
            o.sunLightArrive(0, 0, 1000, 1000);
        }
        Logger.endFunctionLog();
    }

    /**
     * Eggyel csökkenti a sunstorm_time értékét.
     */
    private void sunstormDecrease()
    {
        Logger.startFunctionLogComment(this, "sunstormDecrease", "");
        sunstorm_time--;
        Logger.endFunctionLog();
    }

    /**
     * Lépteti a Space-t. Ha éppen napvihar kör van, meghívja a SunstormCall() metódust,
     * ami szól minden aszteroidának, hogy napvihar van. Emellett minden körben meghívja
     * a SunLightCall() metódust, amely kijelöl egy terülelet, ahol napfény lesz.
     */
    public void step()
    {
        Logger.startFunctionLogComment(this, "step", "");

        if(sunstorm_time == 0)
        {
            sunstormCall();
            sunstorm_time = 1;
        }
        else
            sunstormDecrease();

        sunLightCall();

        Logger.endFunctionLog();
    }

    /**
     * Hozzáadadja a paraméterként kapott Orbitot az orbits listához.
     * @param o
     */
    public void addOrbit(Orbit o)
    {
        orbits.add(o);
    }
    /**
     * Kiveszi a paraméterként kapott Orbitot az orbits listából.
     * @param o
     */
    public void removeOrbit(Orbit o)
    {
        orbits.remove(o);
    }

    
}
