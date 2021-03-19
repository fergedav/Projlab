package logic;

public class Stargate extends Orbit {
    private Stargate myTwin;
    private boolean isPlaced;
    private Asteroid myStop;

    /**
     * Törli magát a myStop-ja szomszédsági listájából.
     */
    private void dieAnother()
    {
        if(myStop!=null)
            myStop.removeNeighbour(this);
    }

    /**
     * Átküldi az utazót a sajat myStopjára.
     * @param t az érkezett utazó
     * @return A teleportkapuhoz tartozó aszteroida.
     */
    private Asteroid transport(Traveler t)
    {
        return myStop.addTraveler(t);
    }

    /**
     * Megöli a kapu párját.
     */
    public void die()
    {
        myTwin.dieAnother();
    }

    /**
     * Összekapcsolja a kaput a paraméterként kapuval.
     * @param other a kapcsolódó kapu
     */
    public void entagle(Stargate other)
    {
        myTwin = other;
    }

    /**
     * Visszaadja, hogy le van-e helyezve a kapu.
     * @return true, ha le van helyezve.
     */
    public boolean isPlaced()
    {
        return isPlaced;
    }

    /**
     * Elhelyezi a kaput a paraméterként kapott Orbit körül.
     * @param o az orbit, ahova leteszik
     */
    public void place(Orbit o)
    {
        myStop = (Asteroid) o;
        isPlaced = true;
    }
    
    /**
     * Utazó érekzik a kapura.
     * @param t az érkezett utazó
     * @return Orbit az utazó érkezik. 
     */
    @Override
    public Orbit addTraveler(Traveler t){
        if(myTwin.isPlaced())
            return myTwin.transport(t);
        return this;
    }
}
