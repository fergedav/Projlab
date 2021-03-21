package logic;

import skeleton.Logger;

public class Stargate extends Orbit {
    private Stargate myTwin;
    private boolean isPlaced;
    private Orbit myStop;


    public Stargate()
    {
        Logger.startFunctionLogComment(this, "Stargate", "<create>");
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

        
        //myStop = null;
        myTwin.dieAnother();

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

        myStop =  o;
        isPlaced = true;
        neighbours.add(o);

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
}
