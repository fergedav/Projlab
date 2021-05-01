package logic;

import java.util.Random;

public class Ufo extends Traveler {

    /**
     *
     */
    private static final long serialVersionUID = -6867257992338262330L;

    public Ufo(Orbit start)
    {
        //remelhetoleg kelloen nagy
        inventory = new Inventory(10000);
        prefix = "ufo_"+id_counter++;
        currentLocation = start;
        Controller.getInstance().addUfo(this);
    }

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


    private void ufoMoves()
    {
        move(whereTo());
    }

    @Override
    public void die() 
    {
        currentLocation.removeTraveler(this);
        controler.ufoDie(this);
    }

    @Override
    public void explosion() 
    {
        die();   
    }

    @Override
    public void step() 
    {
        if(!mining())
            ufoMoves();   
    }
    
    @Override
    public void digging()
    {
        //Ufo nem fur :(
    }

    private boolean mining()
    {
        //nem kene null check mert az van a step-ben, mining csak nem null core eseten hivodik, 
        //de explicit hivasok miatt inkabb rakok
        Resource r = currentLocation.retrieveResource();

        //kiszedte, elrakja
        if(r != null)
            inventory.addResource(r);

        //sikeres volt a bányászat, ha r nem null akkor megkaptam a nyeranyagot
        return r != null;
    }

    public static int id_counter = 0;
}
