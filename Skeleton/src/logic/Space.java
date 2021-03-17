package logic;

import java.util.List;

public class Space {
    ///Singleton stuff
    private static Space instance;

    private Space()
    {}

    public static Space getInstance()
    {
        if(instance == null)
            instance = new Space();
        return instance;
    }

    /// Singleton ends here

    private int sunstorm_time;
    private List<Orbit> orbits;

    private void sunstormCall()
    {}

    private void sunLightCall()
    {}

    private void sunstormDecrease()
    {
        sunstorm_time--;
    }

    public void step()
    {
        if(sunstorm_time == 0)
            sunstormCall();
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
