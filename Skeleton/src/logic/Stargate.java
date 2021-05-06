package logic;

import java.util.ArrayList;
import java.util.Random;

public class Stargate extends Orbit {
    /**
     *
     */
    private static final long serialVersionUID = -6397383425201864620L;
    /**
     * A stargate p�rja, amivel �ssze van k�tve.
     */
    private Stargate myTwin;
    /**
     * Megmondja, hogy le van-e helyezve a stargate
     */
    private boolean isPlaced;
    /**
     * A stargatehez tartoz� aszteroida
     */
    private Orbit myStop;
    /**
     * Ha a stargatet napvihar �ri, ez a v�ltoz� igaz �rt�ket kap.
     * Ha az �rt�ke igaz, elkezd bolyongani az aszteroid�k k�z�tt.
     */
    private boolean beCrazy = false;
    /**
     * A j�t�kot ir�ny�t� controller
     */
    public static Controller stargeteController;

    public Stargate()
    {
        stargeteController = Controller.getInstance();
        prefix = "stargate_"+id_counter++;
    }

    /**
     * T�rli mag�t a myStop-ja szomsz�ds�gi list�j�b�l.
     * //�s meg�li a rajtal�v�ket. (sz�veg alapj�n nem defini�lt.)
     */
    private void dieAnother()
    {
        if(myStop!=null){
            myStop.removeNeighbour(this); 
            for(Traveler t : travelers) {t.die();}
        }

        stargeteController.stargateDie(this);
    }

    @Override
    public void drilled()
    {
    }
    /**
     * �tk�ldi az utaz�t a sajat myStopj�ra.
     * @param t az �rkezett utaz�
     * @return A teleportkapuhoz tartoz� aszteroida.
     */
    private Orbit transport(Traveler t)
    {
        Orbit ret_o = myStop.addTraveler(t);
        return ret_o;
    }

    /**
     * Meg�li a kapu p�rj�t.
     */
    public void die()
    {
        myTwin.dieAnother();
        dieAnother();
    }

    /**
     * �sszekapcsolja a kaput a param�terk�nt kapuval.
     * @param other a kapcsol�d� kapu
     */
    public void entagle(Stargate other)
    {
        myTwin = other;
    }

    /**
     * Visszaadja, hogy le van-e helyezve a kapu.
     * @return true, ha le van helyezve.
     */
    public boolean getPlaced()
    {
        return isPlaced;
    }

    /**
     * Elhelyezi a kaput a param�terk�nt kapott Orbit k�r�l.
     * @param o az orbit, ahova leteszik
     */
    public void place(Orbit o)
    {
        myStop = o.addNeighbour(this);
        this.x = o.getCoords()[0];
        this.y = o.getCoords()[1];
        isPlaced = true;
    }
    
    /**
     * Utaz� �rekzik a kapura.
     * @param t az �rkezett utaz�
     * @return Orbit az utaz� �rkezik. 
     */
    @Override
    public Orbit addTraveler(Traveler t)
    {
        if(myTwin.getPlaced()){
            return myTwin.transport(t);
        }
        travelers.add(t);
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
        int num = myStop.numOfNeighbor();

        if(num == 0) 
            return 0;
        
        Random r = new Random();
        n = r.nextInt(num);
        return n;
    }

    /**
     * A napvihar erkez�s��rt felel�s, param�terk�nt kapja 2 koordin�t�t, ezen bel�l h�v napvihart
     */
    @Override
    public void sunstormArrive(int[] coords )
    {
        if(
            coords[0] <= x &&
            coords[1] <= y &&
            coords[2] >= x &&
            coords[3] >= y
        ) {
            beCrazy = true;
            ArrayList<Traveler> copy = new ArrayList<>();
            copy.addAll(travelers);
            for (Traveler t : copy)
            {
                t.die();
            }
        };

        
    }

    /**
     * Ha a stargatehez hozz�adnak egy szomsz�dot, az
     * automatikusan az � aszteroid�j�hoz ad�dik hozz�
     * @return az aszteroid�hoz �jonnan hozz�adott szomsz�d
     */
    @Override
    public Orbit addNeighbour(Orbit o)
    {
        return myStop.addNeighbour(o);
    }

    /**
     * Ha a param�terben kapott �rt�k 0, visszaadja az aszteroid�j�t, ha m�s, akkor �nmag�t.
     * @return a stargate aszteroid�ja
     */
    @Override
    public Orbit getNeighbour(int i)
    {
        if (i == 0)
            return myStop;
        else return this;
    }
    
    /**
     * Visszater az aktualis helyevel (orbit)
     * @return Orbit myStop
     */
    public Orbit getMyStop()
    {
        return myStop;
    }
    /**
     * Visszater a kapu parjaval
     * @return Stargate myTwin
     */
    public Stargate getMyTwin()
    {
        return myTwin;
    }
    /**
     * Visszater hogy a kapu megorult-e?
     * @return boolean beCrazy
     */
    public boolean getCrazy()
    {
        return beCrazy;
    }
    /**
     * a tesztesetekben megjelenen� prefixhez sz�ks�ges id(controller szerinti sz�m)
     */
    public static int id_counter = 0;
}
