package logic;

import skeleton.Logger;

public class Stargate extends Orbit {
    private Stargate myTwin;
    private boolean isPlaced;
    private Asteroid myStop;

    /**
     * Törli magát a myStop-ja szomszédsági listájából.
     */
    private void dieAnother()
    {
        Logger.startFunctionLogComment(this, "dieAnother", "");

        if(myStop!=null)
            myStop.removeNeighbour(this);

        Logger.endFunctionLog();
    }

    /**
     * Átküldi az utazót a sajat myStopjára.
     * @param t az érkezett utazó
     * @return A teleportkapuhoz tartozó aszteroida.
     */
    private Orbit transport(Traveler t)
    {
        Logger.startFunctionLogComment(this, "transport", "");

        Orbit ret_o = myStop.addTraveler(t);

        Logger.endFunctionLog();

        return ret_o;
    }

    /**
     * Megöli a kapu párját.
     */
    public void die()
    {  
        Logger.startFunctionLogComment(this, "die", "");

        myTwin.dieAnother();

        Logger.endFunctionLog();
    }

    /**
     * Összekapcsolja a kaput a paraméterként kapuval.
     * @param other a kapcsolódó kapu
     */
    public void entagle(Stargate other)
    {
        Logger.startFunctionLogComment(this, "entangle", "");

        myTwin = other;

        Logger.endFunctionLog();
    }

    /**
     * Visszaadja, hogy le van-e helyezve a kapu.
     * @return true, ha le van helyezve.
     */
    public boolean getPlaced()
    {
        Logger.startFunctionLogComment(this, "getPlaced", "");

        Logger.endFunctionLog();

        return isPlaced;
    }

    /**
     * Elhelyezi a kaput a paraméterként kapott Orbit körül.
     * @param o az orbit, ahova leteszik
     */
    public void place(Orbit o)
    {
        Logger.startFunctionLogComment(this, "place", "");

        myStop = (Asteroid) o;
        isPlaced = true;
        
        Logger.endFunctionLog();
    }
    
    /**
     * Utazó érekzik a kapura.
     * @param t az érkezett utazó
     * @return Orbit az utazó érkezik. 
     */
    @Override
    public Orbit addTraveler(Traveler t){
        Logger.startFunctionLogComment(this, "addTraveler", "");
        Orbit ret_o;
        if(myTwin.getPlaced()){
            ret_o=myTwin.transport(t);

            Logger.endFunctionLog();

            return ret_o;
        }

        Logger.endFunctionLog();

        return this;
    }
}
