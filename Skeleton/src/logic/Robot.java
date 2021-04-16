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
        Logger.endFunctionLog();
    }
    /**
     * Visszat�r egy v�letlen v�lasztott sz�mmal. A robot
     * erre az idex� aszteroid�ra l�p majd �t.
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
            Random r = new Random(num);
            n = r.nextInt(num);
        }
        
        Logger.endFunctionLog();
        return n;
    }

    /**
     * L�p�st ind�t a pillanatnyi tart�zkod�si hely�nek v�letlen szomsz�dj�ra.
     */
    private void robotMoves()
    {  
        Logger.startFunctionLogComment(this, "robotMoves", "");
        int next = whereTo();
        move(next);
        Logger.endFunctionLog();
    }

    /**
     * ha megh�v�dik, akkor a robot egy m�sik, az aktu�lis aszteroid�j�nak egy szomsz�dj�ra ker�l �t.
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
     * Az aszteroid�ra, amin jelenleg tart�zkodik,
     * �nmag�val megh�vja a RemoveTraveler(Traveler t) met�dust,
     * majd a controler-nek is jelzi �nmag�val megh�vva Robot_die(Robot r) met�dust, majd t�rli �nmag�t.
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
     * A robot megn�zi, hogy az aktu�lis aszteroid�j�n lehet-e m�g f�rni. Ha igen f�r,
     * ha nem, akkor a RobotMoves() met�dus�val v�letlen l�p. A WhereTo() met�dus�val eld�nti,
     * hogy melyik szomsz�dos aszteroid�ra l�pjen tov�bb, majd �tl�p oda.
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

    //a skeleton erej�ig seg�dfgv
    public void setLocation(Orbit o){
        currentLocation = o;
    }
    
    //PROTO F�GGV�NYEK INNENT�L//////////////////////////////////////////////////////////////////////////////////////////////////////

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
        Controller c = Controller.getInstance();
        int robotId = c.indexRobot(this);
        System.out.println(
            "RobotId: robot_"+ robotId+" Location: "+ c.indexOrbit(currentLocation)
        );
    }

    static int id_counter = 0;
}
