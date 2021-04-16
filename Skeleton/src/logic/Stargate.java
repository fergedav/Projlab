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
     * T�rli mag�t a myStop-ja szomsz�ds�gi list�j�b�l.
     * //�s meg�li a rajtal�v�ket. (sz�veg alapj�n nem defini�lt.)
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
     * �tk�ldi az utaz�t a sajat myStopj�ra.
     * @param t az �rkezett utaz�
     * @return A teleportkapuhoz tartoz� aszteroida.
     */
    private Orbit transport(Traveler t)
    {
        Logger.startFunctionLogComment(this, "transport", "");

        Orbit ret_o = myStop.addTraveler(t);

        Logger.endFunctionLog();

        return ret_o;
    }

    /**
     * Meg�li a kapu p�rj�t.
     */
    public void die()
    {  
        Logger.startFunctionLogComment(this, "die", "");

        myTwin.dieAnother();
        dieAnother();

        Logger.endFunctionLog();
    }

    /**
     * �sszekapcsolja a kaput a param�terk�nt kapuval.
     * @param other a kapcsol�d� kapu
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
     * Elhelyezi a kaput a param�terk�nt kapott Orbit k�r�l.
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
     * Utaz� �rekzik a kapura.
     * @param t az �rkezett utaz�
     * @return Orbit az utaz� �rkezik. 
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
     * megn�zi, hogy a teleportkapu meg van-e kerg�lve. 
     * Ha igen, akkor a StargateMoves() met�dus�val v�letlen �tl�p a jelenlegi myStop-j�nak egy szomsz�dj�ra. 
     * A WhereTo() met�dus�val eld�nti, hogy melyik szomsz�dos orbitra l�pjen tov�bb, majd �tl�p oda.
     */
    public void step()
    {
        if(beCrazy)
            stargateMoves();
    }

    /**
     * L�p�st ind�t a jelenlegi myStop-j�nak egy v�letlen szomsz�dj�ra.
     */
    private void stargateMoves()
    {
        //uj szomszed keresese
        Orbit newLoc = myStop.getNeighbour(whereTo());

        myStop.removeNeighbour(this);

        place(newLoc);

    }

    /**
     * Eld�nti, hogy myStop-j�nak melyik szomsz�dos aszteroid�ra l�pjen tov�bb.
     * V�letlen gener�l egy �rt�ket az aszteroid�ja szomsz�ds�gi list�j�nak nagys�ga alapj�n.
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
    
    //PROTO F�GGV�NYEK INNENT�L//////////////////////////////////////////////////////////////////////////////////////////////////////
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
