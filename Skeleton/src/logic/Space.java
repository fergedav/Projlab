package logic;

import java.util.List;

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

    private int sunstorm_time;
    private List<Orbit> orbits;

    //csak a skeleton idejére, a tesztelést segíteni
    public void setSunstorm_time(int t)
    {
        sunstorm_time = t;
    }

    private void sunstormCall()
    {
        for(Orbit o: orbits){
            o.sunstormArrive();
        }
    }

    private void sunLightCall()
    {
        for(Orbit o: orbits){
            o.sunLightArrive(1000, 1000, 1000, 1000);
        }
    }

    private void sunstormDecrease()
    {
        sunstorm_time--;
    }

    public void step()
    {
        if(sunstorm_time == 0)
        {
            sunstormCall();
            sunstorm_time = 1;
        }
        else
            sunstormDecrease();

        sunLightCall();
    }

    public void addOrbit(Orbit o)
    {
        orbits.add(o);
    }

    public void removeOrbit(Orbit o)
    {
        orbits.remove(o);
    }

    
}
