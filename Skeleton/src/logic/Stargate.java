package logic;

import skeleton.Logger;

public class Stargate extends Orbit {
    private Stargate myTwin;
    private boolean isPlaced;
    private Orbit myStop;
    private boolean beCrazy = false;
    public static Controller stargeteController;

    public Stargate()
    {
        Logger.startFunctionLogComment(this, "Stargate", "<<create>>");
        stargeteController = Controller.getInstance();
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
        }
        /*
        if(myStop!=null){
            for(Orbit orb : neighbours){
                orb.removeNeighbour(this);
            }           
            myStop = null;
        }        
        
        for(Traveler t : travelers){t.die();}
        */

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

        
        //myStop = null;
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
    
    /*
    @Override
    public void removeNeighbour(Orbit o)
    {
        Logger.startFunctionLogComment(this, "removeNeighbour", "");
                 
        if(o == myStop){
            this.die();
        }
        else
            neighbours.remove(o);
        
        Logger.endFunctionLog();
    }*/

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

        // nincs szomszed nem tud hova menni, marad itt
        if(newLoc == null)
            return;

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
        //TODO det nem det megcsinalni
        return 0;
    }

    @Override
    public void sunstormArrive(int[] coords )
    {
        // TODO sorrend nem biztos, figyelni kene hogy egy kor soran hogy jonnek a dolgok
        super.sunstormArrive(coords);//ez hív traveler die-t

        if(inLight)
            beCrazy = true;
    }

    @Override
    public Orbit addNeighbour(Orbit o)
    {
        return myStop.addNeighbour(o);
    }
}
