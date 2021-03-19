package logic;

public class Asteroid extends Orbit {

    /**
     * Az aszteroida magja
     */
    private Resource core;
    //private boolean inSunlight = false;

    /**
     * Új Aszteroida
     * @param _x kezdő x hely
     * @param _y kezdő y hely
     * @param l rétegek száma
     * @param c Nyers anyag ami tartalmaz, lehet null ha üres
     */
    public Asteroid(int _x, int _y, int l, Resource c)
    {
        x = _x;
        y = _y;
        core = c;
        layers = l;
    }

    private void setLayers(int l)
    {
        layers = l;
    }

    /**
     * Megvizsgálja, hogy az aszteroida napközelben van-e illetve, hogy teljesen át van e fúrva a kérge. 
     * Ha mind a kettő igaz, meghívja a magja (a tárolt resource objektumára) reaction() függvényét.
     */
    private void reaction()
    {
        if(inLight && core != null && layers == 0)
            core.reaction(this);

    }

    /**
     * Robbanás esetén meghívja az összes jelenleg rajta tartózkodó utazóra a Explosion() metódust.
     */
    public void explosion()
    {
        for (Traveler t : travelers) {
            t.explosion();
        }
    }

    /**
     * Az aszteroidán végeznek ásást. Eggyel csökkenti a layers értékét. 
     * Ha a layers értéke eléri a 0-át, akkor meghívja az reaction() privát metódusát az aszteroidának.
     */
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
     * Ha a layers értéke 0-tól különböző akkor null-lal tér vissza.
     * @return Resource 
     */
    @Override
    public Resource retrieveResource()
    {
        if(layers != 0)
            return null;

        Resource c = core;
        core = null;

        return c;
    }

    /**
     * Visszateszik az üres aszteroidába a nyersanyagot. 
     * A paraméterként kapott Resource-t elhelyezi a core-ba, ha az null. 
     * Visszatérési értéke true, ha eredetileg null volt a core, amúgy false. 
     * Ha az aszteroida éppen napfényben van, ellenőrzi, hogy az újonnan betett anyagnak van e bármilyen reakciója.
     * @param r nyersanyag amit leraknak
     * @return sikeres volt a nyersanyag lerakása
     */
    @Override
    public boolean putResource(Resource r)
    {
        if(layers != 0)
            return false;

        if(core != null)
            return false;

        core = r;

        reaction();
        
        return true;
    }

    /**
     * Napvihar esetén megvizsgálja, hogy a layers értéke 0-e és hogy a core értéke null. 
     * Ha nem akkor az összes jelenleg rajta tartózkodó utazóra a Die() metódust hívja.
     */
    @Override
    public void sunstormArrive()
    {
        if(!(layers == 0 && core == null))
        {
            for (Traveler t : travelers) {
                t.die();
            }
        }
    }

    /**
     * Eldönti, hogy a koordinátái alapján beleesik-e a jelzett négyzetbe. 
     * Ha igen, akkor beállítja true-ra a inLight attribútumot és meghívja a magára a privát Reaction() metódust. 
     * Ha nem esik bele, akkor beállítja false-ra a inLight attribútumot.
     * @param x1 left
     * @param y1 top
     * @param x2 right
     * @param y2 bottom
     */
    @Override
    public void sunLightArrive(int x1, int y1, int x2, int y2)
    {
        super.sunLightArrive(x1, y1, x2, y2);
        if(inLight)
            reaction();

    }
}
