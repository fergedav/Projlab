package logic;

public class Robot extends Traveler {
    
    /**
     * Visszatér egy véletlen választott számmal. A robot
     * erre az idexű aszteroidára lép majd át.
     * @return
     */
    private int whereTo()
    {
        return 0;
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
     * A robot meghal. Az aszteroidára, amin jelenleg tartózkodik,
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
        if(currentLocation.getLayers()!=0){
            digging();
        }
        else
            robotMoves();
    }
}
