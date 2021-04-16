package logic;

import java.util.ArrayList;
import java.util.List;

import skeleton.Logger;

public abstract class Orbit implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8842665086008545927L;
    protected List<Orbit> neighbours;
    protected List<Traveler> travelers;
    protected boolean inLight = false;
    protected int layers = 0;
    protected int x; 
    protected int y;

    protected Orbit() {
        neighbours = new ArrayList<Orbit>();
        travelers = new ArrayList<Traveler>();
        
    }

    /**
     * Virtu�lis f�ggv�ny. 
     * Hozz�adja a param�terk�nt kapott Traveler-t az Orbit travelers list�j�hoz �s visszat�r �nmag�val.
     * @param t �rkezett traveler
     * @return saj�t maga
     */
    public Orbit addTraveler(Traveler t)
    {
        Logger.startFunctionLogComment(this, "addTraveler", "");
        travelers.add(t);
        Logger.endFunctionLog();
        return this;
    }


    /**
     * Visszat�r az Orbiton jelenleg tart�zkod� utaz�k list�j�val
     * @return Orbiton jelenleg tart�zkod� utaz�k
     */
    public List<Traveler> getTravelers()
    {
        Logger.startFunctionLogComment(this, "getTravelers", "");
        Logger.endFunctionLog();
        return travelers;
    }

    /**
     * Elt�vol�tja a param�terk�nt kapott Traveler-t az Orbit travelers list�j�b�l.
     * @param t Traveler
     */
    public void removeTraveler(Traveler t)
    {
        Logger.startFunctionLogComment(this, "removeTraveler", "");
        travelers.remove(t);
        Logger.endFunctionLog();
    }

    /**
     * A param�terk�nt kapott Orbit-ot hozz�adja a szomsz�ds�gi list�j�hoz.
     * @param o Orbit
     */
    public Orbit addNeighbour(Orbit o)
    {
        Logger.startFunctionLogComment(this, "addNeighbour", "");
        neighbours.add(o);
        Logger.endFunctionLog();
        return this;
    }

    /**
     * Kit�rli a param�terk�nt kapott Orbitot az objektum szomsz�ds�gi list�j�b�l.
     * @param o Orbit
     */
    public void removeNeighbour(Orbit o)
    {
        Logger.startFunctionLogComment(this, "removeNeighbour", "");
        neighbours.remove(o);
        Logger.endFunctionLog();
    }


    /**
     * Visszat�r az orbit i-edik szomsz�dj�val. Ha i-edik szomsz�d nem l�tezik null-t ad vissza.
     * @param i szomsz�d sorsz�ma
     * @return szomsz�dos Orbit
     */
    public Orbit getNeighbour(int i)
    {
        Logger.startFunctionLogComment(this, "getNeighbour", "");

        try {
            Orbit o = neighbours.get(i);
            Logger.endFunctionLog();
            return o;
        } catch (Exception e) {
            Logger.endFunctionLog();
            return this;
        }

        
    }

    public abstract void drilled();

    /**
     * visszadja a tartalmazott nyersanyagot
     * @return Resource 
     */
    public Resource retrieveResource()
    {
        Logger.startFunctionLogComment(this, "retrieveResource", "");
        Logger.endFunctionLog();
        return null;
    }

    /**
     * Virtu�lis f�ggv�ny. Alap�rtelmez�sben false-t ad vissza.
     * @param r
     * @return
     */
    public boolean putResource(Resource r)
    {
        Logger.startFunctionLogComment(this, "putResource", "");
        Logger.endFunctionLog();
        return false;
    }

    /**
     * Alap�rtelmez�sben megh�vja a travelers list�ja �sszes elem�re a Die() f�ggv�nyt.
     */
    public void sunstormArrive(int[] coords )
    {
        Logger.startFunctionLogComment(this, "sunstormArrive", "");
        
        if(!(
            coords[0] <= x &&
            coords[1] <= y &&
            coords[2] >= x &&
            coords[3] >= y
        )) return;

        ArrayList<Traveler> copy = new ArrayList<>();
        copy.addAll(travelers);
        for (Traveler t : copy) {
            t.die();
        }
        travelers = copy;

    }

    /**
     * Eld�nti, hogy a koordin�t�i alapj�n beleesik-e a jelzett n�gyzetbe. 
     * Ha igen, akkor be�ll�tja true-ra a inLight attrib�tumot, ha nem be�ll�tja false-ra.
     * @param int[] coords 
     */
    public void sunLightArrive(int[] coords )
    {

        if( coords[0] <= x &&
            coords[1] <= y &&
            coords[2] >= x &&
            coords[3] >= y)
        {
            inLight = true;
        }
        else
        {
            inLight = false;
        }

    }

    /**
     * M�g nem �tfurt r�tegek sz�ma
     * @return r�tegek
     */
    public int getLayers()
    {
        Logger.startFunctionLogComment(this, "getLayers", "");
        Logger.endFunctionLog();
        return layers;
    }

    public int numOfNeighbor()
    {   
        //ezt nem kell logolni.
        //Logger.startFunctionLogComment(this, "numOfNeighbor", "");
        //Logger.endFunctionLog();L
        return neighbours.size();
    }
    
    //PROTO F�GGV�NYEK INNENT�L//////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Visszadja az orbit koordinantatit 
     * x,y kooridnata
     * @return [x,y]
     */
    public int[] getCoords()
    {
        int i[]= {x,y};
        return i;
    }
    /**
     * Visszadja az orbit aktualis napfeny allapotata 
     * @return boolean true, ha napfeny van
     */
    public boolean getLight()
    {
        return inLight;
    }
    // perfix
    String prefix;
    /**
     * Visszater az adott Orbithoz tartozo prefixxel
     * @return String prefix
     */
    public String getPrefix()
    {
        return prefix;
    }
    /**
     * M�dos�tja a prefixet a parameterben kapott stringgel.
     * @param s prefix uj erteke
     */
    public void setPrefix(String s)
    {
        prefix = s;
        //TODO Controllerben be�rni megfelle� perixet, amikor hozzaadja a listahoz stargate/asteroid
    }

    public Resource getCore() {return null;}
}
