package logic;

import java.util.ArrayList;
import java.util.List;

public class Controler {
    ///Singleton stuff
    private static Controler instance;
    private boolean gameIsOn;

    private Controler()
    {}

    public static Controler getInstance()
    {
        if(instance == null)
            instance = new Controler();
        return instance;

    }

    /// Singleton ends here

    /**
     * ebben a privát változóban van számon tartva, hogy hány telepes van még életben, azaz, hány játékos van még játékban. 
     * Értéke a settlers lista elemszáma.
     */
    private int settlersAlive = 0; // nem lenne több értelme egy List.Count()-t hívó függvénynek?


    /**
     * 
     */
    private Space space= Space.getInstance();

    /**
     * a játékban  éppen aktuálisan létező összes Settler típusú objektumot tároló privát lista.
     */

    private List<Settler> settlers = new ArrayList<>();
    
    /**
     * a játékban éppen aktuálisan lévő összes Robot típusú objektumot tároló privát lista.
     */
    private List<Robot> robots = new ArrayList<>();

    public void initGame()
    {}

    /**
     * Elindítja és futtatja a játékot.
     * 
     */
    public void startGame()
    {
        gameIsOn = true;
        while(gameIsOn){

            space.step();
            for(Robot r : robots){ r.step();}
            for(Settler s : settlers){s.step();}
        }
    }
    /**
     * leállítja a játkot.
     */
    public void endGame()
    {   
        gameIsOn = false;
    }

    /**
     * a paraméterként megkapott robotot hozzáadja a robots listájához
     * @param r
     */
    public void addRobot(Robot r)
    {
        robots.add(r);
    }

    /**
     * Jegyzi egy telepes halálát
     * 
     * Ha egy telepes meghal, akkor meghívja  ezt a Settler_die() függvényt, 
     * ami eltávolítja a settlers listából a paraméterként kapott Settler objektumot
     * és átírja a Settler_alive változó értékét.
     * Ha nem marad több settler, játék végét hív.
     * 
     * @param s
     */
    public void settlerDie(Settler s)
    {
        settlers.remove(s);
        settlersAlive--;

        if(settlers.size()==0)
            endGame();
    }

    /**
     * ha egy robot meghal, akkor meghívja ezt a Robot_die() függvényt, ami eltávolítja a robots listából az paraméterként kapott
     * Robot objektumot.
     * @param r
     */
    public void robotDie(Robot r)
    {
        robots.remove(r);
    }
}
