package logic;

import java.util.ArrayList;
import java.util.Random;

public class Stargate extends Orbit {
    /**
     *
     */
    private static final long serialVersionUID = -6397383425201864620L;
    /**
     * A stargate párja, amivel össze van kötve.
     */
    private Stargate myTwin;
    /**
     * Megmondja, hogy le van-e helyezve a stargate
     */
    private boolean isPlaced;
    /**
     * A stargatehez tartozó aszteroida
     */
    private Orbit myStop;
    /**
     * Ha a stargatet napvihar éri, ez a változó igaz értéket kap.
     * Ha az értéke igaz, elkezd bolyongani az aszteroidák között.
     */
    private boolean beCrazy = false;
    /**
     * A játékot irányító controller
     */
    public static Controller stargeteController;

    public Stargate()
    {
        stargeteController = Controller.getInstance();
        prefix = "stargate_"+id_counter++;
    }

    /**
     * Törli magát a myStop-ja szomszédsági listájából.
     * //és megöli a rajtalévõket. (szöveg alapján nem definiált.)
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
     * Átküldi az utazót a sajat myStopjára.
     * @param t az érkezett utazó
     * @return A teleportkapuhoz tartozó aszteroida.
     */
    private Orbit transport(Traveler t)
    {
        Orbit ret_o = myStop.addTraveler(t);
        return ret_o;
    }

    /**
     * Megöli a kapu párját.
     */
    public void die()
    {
        myTwin.dieAnother();
        dieAnother();
    }

    /**
     * Összekapcsolja a kaput a paraméterként kapuval.
     * @param other a kapcsolódó kapu
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
     * Elhelyezi a kaput a paraméterként kapott Orbit körül.
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
     * Utazó érekzik a kapura.
     * @param t az érkezett utazó
     * @return Orbit az utazó érkezik. 
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
        int n = 0;
        int num = myStop.numOfNeighbor();

        if(num == 0) 
            return 0;
        
        Random r = new Random();
        n = r.nextInt(num);
        return n;
    }

    /**
     * A napvihar erkezéséért felelõs, paraméterként kapja 2 koordinátát, ezen belûl hív napvihart
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
     * Ha a stargatehez hozzáadnak egy szomszédot, az
     * automatikusan az õ aszteroidájához adódik hozzá
     * @return az aszteroidához újonnan hozzáadott szomszéd
     */
    @Override
    public Orbit addNeighbour(Orbit o)
    {
        return myStop.addNeighbour(o);
    }

    /**
     * Ha a paraméterben kapott érték 0, visszaadja az aszteroidáját, ha más, akkor önmagát.
     * @return a stargate aszteroidája
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
     * a tesztesetekben megjelenenõ prefixhez szükséges id(controller szerinti szám)
     */
    public static int id_counter = 0;
}
