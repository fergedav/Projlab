package logic;

public class Asteroid extends Orbit {

    private Resource core;
    //private boolean inSunlight = false;

    public Asteroid(int _x, int _y, int l, Resource c)
    {
        x = _x;
        y = _y;
        core = c;
        layers = l;
    }

    private void setLayers(int l)
    {
        layers = l;
    }

    private void reaction()
    {
        if(inLight && core != null && layers == 0)
            core.reaction(this);

    }

    public void explosion()
    {
        for (Traveler t : travelers) {
            t.explosion();
        }
    }

    public void drilled()
    {
        if(layers != 0)
        {
            layers--;
            if(layers == 0)
            {
                reaction();
            }
        }
    }

    @Override
    public Resource retrieveResource()
    {
        if(layers != 0)
            return null;

        Resource c = core;
        core = null;

        return c;
    }

    @Override
    public boolean putResource(Resource r)
    {
        if(layers != 0)
            return false;

        if(core != null)
            return false;

        core = r;

        reaction();
        
        return true;
    }

    @Override
    public void sunstormArrive()
    {
        if(!(layers == 0 && core == null))
        {
            for (Traveler t : travelers) {
                t.die();
            }
        }
    }

    @Override
    public void sunLightArrive(int x1, int y1, int x2, int y2)
    {
        if(x >= x1 && y >= y1 &&
            x <= x2 && y <= y2)
        {
            inLight = true;
            reaction();
        }
        else
        {
            inLight = false;
        }

    }
}
