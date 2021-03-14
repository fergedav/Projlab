package logic;

public class Stargate extends Orbit {
    private Stargate myPair;
    private boolean isPlaced;
    private Asteroid myStop;

    private void dieAnother()
    {}

    private Asteroid transport(Traveler t)
    {
        return null;
    }

    public void die()
    {}

    public void entagle(Stargate other)
    {
        myPair = other;
    }

    public boolean isPlaced()
    {
        return isPlaced;
    }

    public void place(Orbit o)
    {}

}
