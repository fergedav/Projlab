package logic;

import java.util.List;

public abstract class Orbit {
    protected List<Orbit> neighbours;
    protected List<Traveler> travelers;
    protected boolean inLight = false;
    protected int layers;
    protected int x; //senki nem tárolja a koordinátákat tömbben xd
    protected int y;

    public Orbit addTraveler(Traveler t)
    {
        travelers.add(t);
        return this;
    }

    public List<Traveler> getTravelers()
    {
        return travelers;
    }

    public void removeTraveler(Traveler t)
    {
        travelers.remove(t);
    }

    public void addNeighbour(Orbit o)
    {
        neighbours.add(o);
    }

    public void removeNeighbour(Orbit o)
    {
        neighbours.remove(o);
    }

    public Orbit getNeighbour(int i)
    {
        return neighbours.get(i);
    }

    public void drilled()
    { }

    public Resource retrieveResource()
    {
        return null;
    }

    public boolean putResource(Resource r)
    {
        return false;
    }

    public void sunstormArrive()
    {
        for (Traveler t : travelers) {
            t.die();
        }
    }

    public void sunLightArrive(int x1, int y1, int x2, int y2)
    {
        for (Traveler t : travelers) {
            t.die();
        }
    }

    public int getLayers()
    {
        return layers;
    }

}
