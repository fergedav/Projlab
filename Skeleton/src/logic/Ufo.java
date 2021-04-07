package logic;

public class Ufo extends Traveler {

    private Inventory inv;

    public Ufo()
    {
        //remelhetoleg kelloen nagy
        inv = new Inventory(100);
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
        int l = currentLocation.getLayers();

        if(l == 0 && currentLocation.peekCore() != null)
            mining();
        else
            ufoMoves();
        
    }
    
    @Override
    public void digging()
    {
        //Ufo nem fur :(
    }

    private void mining()
    {
        //nem kene null check mert az van a step-ben, mining csak nem null core eseten hivodik, 
        //de explicit hivasok miatt inkabb rakok
        Resource r = currentLocation.retrieveResource();
        if(r != null) inv.addResource(r);
    }



}
