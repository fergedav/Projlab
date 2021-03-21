package logic;

import java.util.ArrayList;
import java.util.List;

import skeleton.Logger;

public class Asteroid extends Orbit {

    /**
     * Az aszteroida magja
     */
    private Resource core;
    //private boolean inSunlight = false;

    /**
     * �j Aszteroida
     * @param _x kezd� x hely
     * @param _y kezd� y hely
     * @param l r�tegek sz�ma
     * @param c Nyers anyag ami tartalmaz, lehet null ha �res
     */
    public Asteroid(int _x, int _y, int l, Resource c)
    {
        super();
        Logger.startFunctionLogComment(this, "Asteroid", "<<create>>");

        x = _x;
        y = _y;
        core = c;
        layers = l;
        
        Logger.endFunctionLog();
    }
    /*
    private void setLayers(int l)
    {
        layers = l;
    }
    */

    /**
     * Megvizsg�lja, hogy az aszteroida napk�zelben van-e illetve, hogy teljesen �t van e f�rva a k�rge. 
     * Ha mind a kett� igaz, megh�vja a magja (a t�rolt resource objektum�ra) reaction() f�ggv�ny�t.
     */
    private void reaction()
    {
        Logger.startFunctionLogComment(this, "reaction", "");

        if(inLight && core != null && this.layers == 0)
            core.reaction(this);

        Logger.endFunctionLog();
    }

    /**
     * Robban�s eset�n megh�vja az �sszes jelenleg rajta tart�zkod� utaz�ra a Explosion() met�dust.
     */
    public void explosion()
    {
        Logger.startFunctionLogComment(this, "explosion", "");

        List<Traveler> copy = new ArrayList<>(travelers);
        for (Traveler t : copy) {
            t.explosion();
        }
        travelers = copy;

        Logger.endFunctionLog();
    }

    /**
     * Az aszteroid�n v�geznek �s�st. Eggyel cs�kkenti a layers �rt�k�t. 
     * Ha a layers �rt�ke el�ri a 0-�t, akkor megh�vja az reaction() priv�t met�dus�t az aszteroid�nak.
     */
    @Override
    public void drilled()
    {
        Logger.startFunctionLogComment(this, "drilled", "");

        if(layers != 0)
        {
            layers--;
            if(layers == 0)
            {
                reaction();
            }
        }

        Logger.endFunctionLog();
    }

    /**
     * A h�v� visszakapja az aszteroida magj�t. 
     * Ha a layers �rt�ke 0, akkor visszat�r a magj�ban tal�lhat� nyersanyaggal, az �j core �rt�k null. 
     * Ha a layers �rt�ke 0-t�l k�l�nb�z� akkor null-lal t�r vissza.
     * @return Resource 
     */
    @Override
    public Resource retrieveResource()
    {
        Logger.startFunctionLogComment(this, "retrieveResource", "");

        if(layers != 0){
            Logger.endFunctionLog();
             return null;
        }
           
        Resource c = core;
        core = null;

        Logger.endFunctionLog();
        return c;
    }

    /**
     * Visszateszik az �res aszteroid�ba a nyersanyagot. 
     * A param�terk�nt kapott Resource-t elhelyezi a core-ba, ha az null. 
     * Visszat�r�si �rt�ke true, ha eredetileg null volt a core, am�gy false. 
     * Ha az aszteroida �ppen napf�nyben van, ellen�rzi, hogy az �jonnan betett anyagnak van e b�rmilyen reakci�ja.
     * @param r nyersanyag amit leraknak
     * @return sikeres volt a nyersanyag lerak�sa
     */
    @Override
    public boolean putResource(Resource r)
    {
        Logger.startFunctionLogComment(this, "putResource", "");

        if(layers != 0)
            return false;

        if(core != null)
            return false;

        core = r;

        reaction();
        
        Logger.endFunctionLog();

        return true;
    }

    /**
     * Napvihar eset�n megvizsg�lja, hogy a layers �rt�ke 0-e �s hogy a core �rt�ke null. 
     * Ha nem akkor az �sszes jelenleg rajta tart�zkod� utaz�ra a Die() met�dust h�vja.
     */
    @Override
    public void sunstormArrive()
    {
        Logger.startFunctionLogComment(this, "sunstormArrive", "");

        if(!(layers == 0 && core == null))
        {
            List<Traveler> copy = new ArrayList<>(travelers);
            for (Traveler t : copy) {
                t.die();
            }
            travelers = copy;
        }

        Logger.endFunctionLog();
    }

    /**
     * Eld�nti, hogy a koordin�t�i alapj�n beleesik-e a jelzett n�gyzetbe. 
     * Ha igen, akkor be�ll�tja true-ra a inLight attrib�tumot �s megh�vja a mag�ra a priv�t Reaction() met�dust. 
     * Ha nem esik bele, akkor be�ll�tja false-ra a inLight attrib�tumot.
     * @param x1 left
     * @param y1 top
     * @param x2 right
     * @param y2 bottom
     */
    @Override
    public void sunLightArrive(int x1, int y1, int x2, int y2)
    {
        Logger.startFunctionLogComment(this, "sunLightArrive", "");

        super.sunLightArrive(x1, y1, x2, y2);
        if(inLight)
            reaction();

        Logger.endFunctionLog();
    }
}
