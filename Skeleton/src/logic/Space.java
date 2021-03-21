package logic;

import java.util.ArrayList;
import java.util.List;
import skeleton.Logger;

public class Space {
    ///Singleton stuff
    private static Space instance;

    private Space()
    {
        Logger.startFunctionLogComment(this, "Space", "<<create>>");

        orbits = new ArrayList<Orbit>();
        setSunstorm_time(0);

        Logger.endFunctionLog();
    }

    /**
     * L�trhoz �s visszaadja, vagy ha m�r volt l�trehozva akkor visszaadja az els� l�trehozott Space.
     * 
     * @return a Space objektum
     */
    public static Space getInstance()
    {
        if(instance == null)
        instance = new Space();
        return instance;
    }
    /**
     * ez csak a tesztel�shez kell
     * @return az �j space obj.
     */
    public static Space getNewSpace()
    {
        instance = new Space();
        return instance;
    }


    /// Singleton ends here

    /**
     * Megszabja, hogy h�ny k�r�nk�nt legyen napvihar.
     */
    private int sunstorm_time;

    /**
     * A j�t�kban tal�lhat� �sszes Orbit egy list�ban t�rolva.
     */
    private List<Orbit> orbits;

    //csak a skeleton idej�re, a tesztel�st seg�teni
    public void setSunstorm_time(int t)
    {
        sunstorm_time = t;
    }

    /**
     * Megh�vja minden aszteroid�n a sunstormArrive met�dust.
     */
    private void sunstormCall()
    {
        Logger.startFunctionLogComment(this, "sunstormCall", "");
        for(Orbit o: orbits){
            o.sunstormArrive();
        }
        Logger.endFunctionLog();
    }

    //csak a skeleton idej�re, a tesztel�st seg�teni
    public void tempSunstormCall(){
        sunstormCall();
    }

    /**
     * Megh�vja minden aszteroid�n a sunLightArrive met�dust.
     */
    private void sunLightCall()
    {
        Logger.startFunctionLogComment(this, "sunLightCall", "");

        for(Orbit o: orbits){
            o.sunLightArrive(0, 0, 1000, 1000);
        }
        Logger.endFunctionLog();
    }

    /**
     * Eggyel cs�kkenti a sunstorm_time �rt�k�t.
     */
    private void sunstormDecrease()
    {
        Logger.startFunctionLogComment(this, "sunstormDecrease", "");
        sunstorm_time--;
        Logger.endFunctionLog();
    }

    /**
     * L�pteti a Space-t. Ha �ppen napvihar k�r van, megh�vja a SunstormCall() met�dust,
     * ami sz�l minden aszteroid�nak, hogy napvihar van. Emellett minden k�rben megh�vja
     * a SunLightCall() met�dust, amely kijel�l egy ter�lelet, ahol napf�ny lesz.
     */
    public void step()
    {
        Logger.startFunctionLogComment(this, "step", "");

        if(sunstorm_time == 0)
        {
            sunstormCall();
            sunstorm_time = 1;
        }
        else
            sunstormDecrease();

        sunLightCall();

        Logger.endFunctionLog();
    }

    /**
     * Hozz�adadja a param�terk�nt kapott Orbitot az orbits list�hoz.
     * @param o
     */
    public void addOrbit(Orbit o)
    {
        orbits.add(o);
    }
    /**
     * Kiveszi a param�terk�nt kapott Orbitot az orbits list�b�l.
     * @param o
     */
    public void removeOrbit(Orbit o)
    {
        orbits.remove(o);
    }

    
}
