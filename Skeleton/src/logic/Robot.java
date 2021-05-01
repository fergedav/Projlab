package logic;
import java.util.Random;

public class Robot extends Traveler {
    
    /**
     *
     */
    private static final long serialVersionUID = -3138426053591207283L;
    /**
     * Konstruktor
     */
    public Robot(Orbit start)
    {
        super();
        prefix = "robot_"+id_counter++;
        Controller.getInstance().addRobot(this);

        currentLocation = start.addTraveler(this);
    }
    /**
     * Visszatér egy véletlen választott számmal. A robot
     * erre az idexû aszteroidára lép majd át.
     * @return választott sorszámú aszteroida a listából
     */
    private int whereTo()
    {
        int n = 0;
        int num = currentLocation.numOfNeighbor();
        if(num == 0) 
            return 0;
        Random r = new Random();
        n = r.nextInt(num);
        return n;
    }

    /**
     * Lépést indít a pillanatnyi tartózkodási helyének véletlen szomszédjára.
     */
    private void robotMoves()
    {  
        int next = whereTo();
        move(next);
    }

    /**
     * ha meghívódik, akkor a robot egy másik, az aktuális aszteroidájának egy szomszédjára kerül át.
     */
    @Override
    public void explosion()
    {
        robotMoves();
    }

    /**
     * A robot meghal.
     * 
     * Az aszteroidára, amin jelenleg tartózkodik,
     * önmagával meghívja a RemoveTraveler(Traveler t) metódust,
     * majd a controler-nek is jelzi önmagával meghívva Robot_die(Robot r) metódust, majd törli önmagát.
     */
    @Override
    public void die()
    {
        currentLocation.removeTraveler(this);        

        controler.robotDie(this);
    }

    /**
     * A robot megnézi, hogy az aktuális aszteroidáján lehet-e még fúrni. Ha igen fúr,
     * ha nem, akkor a RobotMoves() metódusával véletlen lép. A WhereTo() metódusával eldönti,
     * hogy melyik szomszédos aszteroidára lépjen tovább, majd átlép oda.
     */
    @Override
    public void step()
    {
        if(currentLocation.getLayers() != 0)
        {
            digging();
        }
        else
            robotMoves();
    }

    /**
     * Id -ja a robotnak
     */
    public static int id_counter = 0;
}
