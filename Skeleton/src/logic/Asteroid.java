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
     * Új Aszteroida
     * @param _x kezdõ x hely
     * @param _y kezdõ y hely
     * @param l rétegek száma
     * @param c Nyers anyag ami tartalmaz, lehet null ha üres
     */
    public Asteroid(int _x, int _y, int l, Resource c, boolean light)
    {
        super();
        Logger.startFunctionLogComment(this, "Asteroid", "<<create>>");

        x = _x;
        y = _y;
        core = c;
        layers = l;
        inLight = light;

        Logger.endFunctionLog();
    }    

    /**
     * Megvizsgálja, hogy az aszteroida napközelben van-e illetve, hogy teljesen át van e fúrva a kérge. 
     * Ha mind a kettõ igaz, meghívja a magja (a tárolt resource objektumára) reaction() függvényét.
     */
    private void reaction()
    {
        Logger.startFunctionLogComment(this, "reaction", "");

        if(inLight && core != null && this.layers == 0)
            core.reaction(this);

        Logger.endFunctionLog();
    }

    /**
     * Robbanás esetén meghívja az összes jelenleg rajta tartózkodó utazóra a Explosion() metódust.
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
     * Az aszteroidán végeznek ásást. Eggyel csökkenti a layers értékét. 
     * Ha a layers értéke eléri a 0-át, akkor meghívja az reaction() privát metódusát az aszteroidának.
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
     * A hívó visszakapja az aszteroida magját. 
     * Ha a layers értéke 0, akkor visszatér a magjában található nyersanyaggal, az új core érték null. 
     * Ha a layers értéke 0-tól különbözõ akkor null-lal tér vissza.
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
     * Visszateszik az üres aszteroidába a nyersanyagot. 
     * A paraméterként kapott Resource-t elhelyezi a core-ba, ha az null. 
     * Visszatérési értéke true, ha eredetileg null volt a core, amúgy false. 
     * Ha az aszteroida éppen napfényben van, ellenõrzi, hogy az újonnan betett anyagnak van e bármilyen reakciója.
     * @param r nyersanyag amit leraknak
     * @return sikeres volt a nyersanyag lerakása
     */
    @Override
    public boolean putResource(Resource r)
    {
        Logger.startFunctionLogComment(this, "putResource", "");

        if(layers != 0)
        {
            Logger.endFunctionLog();
            return false;
        }
            

        if(core != null)
        {
            Logger.endFunctionLog();
            return false;
        }
        
        core = r;

        reaction();
        
        Logger.endFunctionLog();

        return true;
    }

    /**
     * Napvihar esetén megvizsgálja, hogy a layers értéke 0-e és hogy a core értéke null. 
     * Ha nem akkor az összes jelenleg rajta tartózkodó utazóra a Die() metódust hívja.
     */
    @Override
    public void sunstormArrive(int[] coords)
    {
        Logger.startFunctionLogComment(this, "sunstormArrive", "");

        //napfényben vagyok e
        if(!(
            coords[0] <= x &&
            coords[1] <= y &&
            coords[2] >= x &&
            coords[3] >= y
        )) return;

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
     * Eldönti, hogy a koordinátái alapján beleesik-e a jelzett négyzetbe. 
     * Ha igen, akkor beállítja true-ra a inLight attribútumot és meghívja a magára a privát Reaction() metódust. 
     * Ha nem esik bele, akkor beállítja false-ra a inLight attribútumot.
     * @param int[] coords 
     */
    @Override
    public void sunLightArrive(int[] coords )
    {
        Logger.startFunctionLogComment(this, "sunLightArrive", "");

        super.sunLightArrive(coords);
        if(inLight)
            reaction();

        Logger.endFunctionLog();
    }

    @Override
    public Resource peekCore()
    {
        return core;
    }
}
