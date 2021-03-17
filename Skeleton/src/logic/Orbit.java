package logic;

import java.util.List;

public abstract class Orbit {
    protected List<Orbit> neighbours;
    protected List<Traveler> travelers;
    protected boolean inLight = false;
    protected int layers;
    protected int x; //senki nem tárolja a koordinátákat tömbben xd
    protected int y;

    public Asteroid addTraveler(Traveler t)
    {
        return null;
    }

    public Traveler[] getTravelers()
    {
        return null;
    }

    public void removeTraveler(Traveler t)
    {}

    public void addNeighbour(Orbit o)
    {}

    public void removeNeighbour()
    {}

    public Orbit getNeighbour()
    {
        return null;
    }

    public void drilled()
    {}

    public Resource retrieveResource()
    {
        return null;
    }

    public boolean putResource(Resource r)
    {
        return false;
    }

    public void sunstormArrive()
    {}

    public void sunLightArrive(int x1, int y1, int x2, int y2)
    {}

    public int getLayers()
    {
        return layers;
    }

}
