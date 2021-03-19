package logic;

public class Robot extends Traveler {
    
    private int whereTo()
    {
        return 0;
    }

    private void robotMoves()
    {
        int next = whereTo();
        move(next);
    }

    @Override
    public void explosion()
    {
        robotMoves();
    }

    @Override
    public void die()
    {
        currentLocation.removeTraveler(this);
        controler.robotDie(this);
    }

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
