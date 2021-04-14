package logic;

public class Ufo extends Traveler {

    /**
     *
     */
    private static final long serialVersionUID = -6867257992338262330L;
    private Inventory inv;

    public Ufo()
    {
        //remelhetoleg kelloen nagy
        inv = new Inventory(10000);
    }

    private int whereTo()
    {
        // TODO det nem det
        return 0;
    }


    private void ufoMoves()
    {
        move(whereTo());
    }

    @Override
    public void die() {
        currentLocation.removeTraveler(this);
        controler.ufoDie(this);
    }

    @Override
    public void explosion() {
        die();   
    }

    @Override
    public void step() {
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
        if(r != null) inv.addResource(r);

        return r != null;
    }
    
    //PROTO F�GGV�NYEK INNENT�L//////////////////////////////////////////////////////////////////////////////////////////////////////




}