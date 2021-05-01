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
    public Robot()
    {
        super();
        setPrefix("robot_"+id_counter++);
        Controller.getInstance().addRobot(this);
    }
    /**
     * Visszat�r egy v�letlen v�lasztott sz�mmal. A robot
     * erre az idex� aszteroid�ra l�p majd �t.
     * @return v�lasztott sorsz�m� aszteroida a list�b�l
     */
    private int whereTo()
    {
        //rand = true, det = false
        int n = 0;
        if(behavior)
        {
            int num = currentLocation.numOfNeighbor();
            if(num == 0) 
                return 0;
            Random r = new Random();
            n = r.nextInt(num);
        }
        return n;
    }

    /**
     * L�p�st ind�t a pillanatnyi tart�zkod�si hely�nek v�letlen szomsz�dj�ra.
     */
    private void robotMoves()
    {  
        int next = whereTo();
        move(next);
    }

    /**
     * ha megh�v�dik, akkor a robot egy m�sik, az aktu�lis aszteroid�j�nak egy szomsz�dj�ra ker�l �t.
     */
    @Override
    public void explosion()
    {
        robotMoves();
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
        currentLocation.removeTraveler(this);        

        controler.robotDie(this);
    }

    /**
     * A robot megn�zi, hogy az aktu�lis aszteroid�j�n lehet-e m�g f�rni. Ha igen f�r,
     * ha nem, akkor a RobotMoves() met�dus�val v�letlen l�p. A WhereTo() met�dus�val eld�nti,
     * hogy melyik szomsz�dos aszteroid�ra l�pjen tov�bb, majd �tl�p oda.
     */
    @Override
    public void step()
    {
        if(currentLocation.getLayers()!=0){
            digging();
        }
        else
            robotMoves();
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
     * @param det_rand boolean viselked�s
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
    /**
     * Id -ja a robotnak
     */
    public static int id_counter = 0;
}
