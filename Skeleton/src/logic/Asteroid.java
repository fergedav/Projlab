package logic;

import java.util.ArrayList;
import java.util.List;

public class Asteroid extends Orbit {

    /**
     *
     */
    private static final long serialVersionUID = -2021672783921175891L;
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
        x = _x;
        y = _y;
        core = c;
        layers = l;
        inLight = light;
        setPrefix("asteroid_"+id_counter++);
    }    

    /**
     * Megvizsgálja, hogy az aszteroida napközelben van-e illetve, hogy teljesen át van e fúrva a kérge. 
     * Ha mind a kettõ igaz, meghívja a magja (a tárolt resource objektumára) reaction() függvényét.
     */
    private void reaction()
    {
        if(inLight && core != null && this.layers == 0)
            core.reaction(this);
    }

    /**
     * Robbanás esetén meghívja az összes jelenleg rajta tartózkodó utazóra a Explosion() metódust.
     */
    public void explosion()
    {
        List<Traveler> copy = new ArrayList<>(travelers);
        for (Traveler t : copy) {
            t.explosion();
        }
        travelers = copy;
    }

    /**
     * Az aszteroidán végeznek ásást. Eggyel csökkenti a layers értékét. 
     * Ha a layers értéke eléri a 0-át, akkor meghívja az reaction() privát metódusát az aszteroidának.
     */
    @Override
    public void drilled()
    {
        if(layers != 0)
        {
            layers--;
            if(layers == 0)
            {
                reaction();
            }
        }
    }

    /**
     * A hívó visszakapja az aszteroida magját. 
     * Ha a layers értéke 0, akkor visszatér a magjában található nyersanyaggal, az új core érték null. 
     * Ha a layers értéke 0-tól különbözõ akkor null-lal tér vissza.
     * @return Resource , az aszteroida magját
     */
    @Override
    public Resource retrieveResource()
    {
        if(layers != 0){
             return null;
        }
           
        Resource c = core;
        core = null;
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
        if(layers != 0)
        {
            return false;
        }
            
        if(core != null)
        {
            return false;
        }
        
        core = r;
        reaction();
        return true;
    }

    /**
     * Napvihar esetén megvizsgálja, hogy a layers értéke 0-e és hogy a core értéke null. 
     * Ha nem akkor az összes jelenleg rajta tartózkodó utazóra a Die() metódust hívja.
     * @param int[] coords 
     */
    @Override
    public void sunstormArrive(int[] coords)
    {
        //napfényben vagyok e //napviharban inkább talán...
        if(!(
            coords[0] <= x &&
            coords[1] <= y &&
            coords[2] >= x &&
            coords[3] >= y
        )) return;

        if(!(layers == 0 && core == null))
        {
            ArrayList<Traveler> copy = new ArrayList<>();
            copy.addAll(travelers);
            for (Traveler t : copy) {
                t.die();
            }
        }
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
        super.sunLightArrive(coords);
        if(inLight && core != null)
            reaction();
    }

    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Visszaadja az aszteroida magjában lévõ Resourceot
     */
    @Override
    public Resource getCore() {return core;}

    /**
     * a tesztesetekben megjelenenõ prefixhez szükséges id(controller szerinti szám)
     */
    public static int id_counter = 0;

    @Override
    public String toString()
    {
        return prefix;
    }
}
