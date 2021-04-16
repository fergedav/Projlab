package logic;
import skeleton.Logger;
import java.util.Random;

public class Robot extends Traveler {
    
    /**
     *
     */
    private static final long serialVersionUID = -3138426053591207283L;

    public Robot()
    {
        super();
        Logger.startFunctionLogComment(this, "Robot", "<<create>>");
        setPrefix("robot_"+id_counter++);
        Controller.getInstance().addRobot(this);
        Logger.endFunctionLog();
    }
    /**
     * Visszatér egy véletlen választott számmal. A robot
     * erre az idexû aszteroidára lép majd át.
     * @return
     */
    private int whereTo()
    {
        Logger.startFunctionLogComment(this, "whereTo", "");

        //rand = true, det = false
        int n = 0;
        if(behavior)
        {
            int num = currentLocation.numOfNeighbor();
            Random r = new Random();
            n = r.nextInt(num);
        }
        
        Logger.endFunctionLog();
        return n;
    }

    /**
     * Lépést indít a pillanatnyi tartózkodási helyének véletlen szomszédjára.
     */
    private void robotMoves()
    {  
        Logger.startFunctionLogComment(this, "robotMoves", "");
        int next = whereTo();
        move(next);
        Logger.endFunctionLog();
    }

    /**
     * ha meghívódik, akkor a robot egy másik, az aktuális aszteroidájának egy szomszédjára kerül át.
     */
    @Override
    public void explosion()
    {
        Logger.startFunctionLogComment(this, "explosion", "");
        robotMoves();
        Logger.endFunctionLog();

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
        Logger.startFunctionLogComment(this, "die", "");

        currentLocation.removeTraveler(this);        

        controler.robotDie(this);

        Logger.endFunctionLog();

    }

    /**
     * A robot megnézi, hogy az aktuális aszteroidáján lehet-e még fúrni. Ha igen fúr,
     * ha nem, akkor a RobotMoves() metódusával véletlen lép. A WhereTo() metódusával eldönti,
     * hogy melyik szomszédos aszteroidára lépjen tovább, majd átlép oda.
     */
    @Override
    public void step()
    {
        Logger.startFunctionLogComment(this, "step", "");

        if(currentLocation.getLayers()!=0){
            digging();
        }
        else
            robotMoves();

        Logger.endFunctionLog();
    }

    //a skeleton erejéig segédfgv
    public void setLocation(Orbit o){
        currentLocation = o;
    }
    
    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////

 /**
     * Determinisztikus - random viselkedes
     * rand = true, det = false
     */
    private boolean behavior;
    /**
     * Determinisztikus - random viselkedeshez
     *  rand = true, det = false
     */
    public void setBehavior(boolean det_rand)
    {
        behavior = det_rand;
    }

    public void robotInfo(){
        System.out.println(
            "RobotId: "+ this.prefix+" Location: "+ currentLocation.getPrefix()
        );
    }

    public static int id_counter = 0;
}
