package logic;

public class Stargate extends Orbit {
    private Stargate myPair;
    private boolean isPlaced;
    private Asteroid myStop;

    private void dieAnother()
    {
        if(myStop != null)
            myStop.removeNeighbour(this);
    }

    private Asteroid transport(Traveler t)
    {
        return myStop.addTraveler(t);
    }

    public void die()
    {
        myPair.dieAnother();
    }

    public void entagle(Stargate other)
    {
        myPair = other;
    }

    public boolean isPlaced()
    {
        return isPlaced;
    }

    public void place(Orbit o)
    {
        myStop = (Asteroid) o;
        isPlaced = true;
    }
    @Override
    public Orbit addTraveler(Traveler t){
        if(myPair.isPlaced())
            return myPair.transport(t);
        return this;
    }
}
