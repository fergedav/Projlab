package logic;

import java.util.ArrayList;
import java.util.List;

public class Controler {
    ///Singleton stuff
    private static Controler instance;

    private Controler()
    {}

    public static Controler getInstance()
    {
        if(instance == null)
            instance = new Controler();
        return instance;

    }

    /// Singleton ends here

    private int settlersAlive = 0; // nem lenne több értelme egy List.Count()-t hívó függvénynek?
    private Space spacee = Space.getInstance();
    private List<Settler> settlers = new ArrayList<>();
    private List<Robot> robots = new ArrayList<>();

    public void initGame()
    {}

    public void startGame()
    {}

    public void endGame()
    {}

    public void addRobot(Robot r)
    {

    }

    public void robotDie(Robot r)
    {

    }

    public void settlerDie(Settler s)
    {

    }
}
