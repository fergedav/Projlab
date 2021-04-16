package logic;

import java.util.Random;

import skeleton.Logger;

public class Stargate extends Orbit {
    /**
     *
     */
    private static final long serialVersionUID = -6397383425201864620L;
    private Stargate myTwin;
    private boolean isPlaced;
    private Orbit myStop;
    private boolean beCrazy = false;
    public static Controller stargeteController;

    public Stargate()
    {
        Logger.startFunctionLogComment(this, "Stargate", "<<create>>");
        stargeteController = Controller.getInstance();
        setPrefix("stargate_"+id_counter++);
        Logger.endFunctionLog();
    }

    /**
     * Törli magát a myStop-ja szomszédsági listájából.
     * //és megöli a rajtalévõket. (szöveg alapján nem definiált.)
     */
    private void dieAnother()
    {
        Logger.startFunctionLogComment(this, "dieAnother", "");

        if(myStop!=null){
            myStop.removeNeighbour(this); 
            for(Traveler t : travelers) {t.die();}
        }

        stargeteController.stargateDie(this);
        Logger.endFunctionLog();
    }

    @Override
    public void drilled()
    {
        Logger.startFunctionLogComment(this, "drilled", "");
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
        dieAnother();

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

        myStop = o.addNeighbour(this);

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
    
    /**
     * megnézi, hogy a teleportkapu meg van-e kergülve. 
     * Ha igen, akkor a StargateMoves() metódusával véletlen átlép a jelenlegi myStop-jának egy szomszédjára. 
     * A WhereTo() metódusával eldönti, hogy melyik szomszédos orbitra lépjen tovább, majd átlép oda.
     */
    public void step()
    {
        if(beCrazy)
            stargateMoves();
    }

    /**
     * Lépést indít a jelenlegi myStop-jának egy véletlen szomszédjára.
     */
    private void stargateMoves()
    {
        //uj szomszed keresese
        Orbit newLoc = myStop.getNeighbour(whereTo());

        myStop.removeNeighbour(this);

        place(newLoc);

    }

    /**
     * Eldönti, hogy myStop-jának melyik szomszédos aszteroidára lépjen tovább.
     * Véletlen generál egy értéket az aszteroidája szomszédsági listájának nagysága alapján.
     * @return
     */
    private int whereTo()
    {
        int n = 0;
        if(behavior)
        {
            int num = myStop.numOfNeighbor();
            Random r = new Random(num);
            n = r.nextInt(num);
        }
        
        Logger.endFunctionLog();
        return n;
    }

    @Override
    public void sunstormArrive(int[] coords )
    {
        if(
            coords[0] <= x &&
            coords[1] <= y &&
            coords[2] >= x &&
            coords[3] >= y
        ) {
            for (Traveler t : travelers) {
                t.die();
            }
            beCrazy = true;
        };

        
    }

    @Override
    public Orbit addNeighbour(Orbit o)
    {
        return myStop.addNeighbour(o);
    }
    
    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Determinisztikus - random viselkedes
     */
    private boolean behavior;
    /**
     * Determinisztikus - random viselkedeshez
     * setter
     */
    public void setBehavior(boolean det_rand)
    {
        behavior = det_rand;
    }
    /**
     * Visszater az aktualis helyevel (orbit)
     * @return Orbit myStop
     */
    public Orbit getMyStop(){
        return myStop;
    }
    /**
     * Visszater a kapu parjaval
     * @return Stargate myTwin
     */
    public Stargate getMyTwin(){
        return myTwin;
    }
    /**
     * Visszater hogy a kapu megorult-e?
     * @return boolean beCrazy
     */
    public boolean getCrazy(){
        return beCrazy;
    }

    public static int id_counter = 0;
}
