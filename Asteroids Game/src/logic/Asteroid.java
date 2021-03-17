package logic;

public class Asteroid extends Orbit {

    private Resource core;
    private boolean inSunlight = false;

    private void setLayers(int l)
    {
        layers = l;
    }

    private void reaction()
    {
        if(inSunlight && core != null && layers == 0)
            core.reaction(this);

    }

    public void explosion()
    {}

    public void drilled()
    {}

    @Override
    public Resource retrieveResource()
    {
        return core;
    }

    @Override
    public boolean putResource(Resource r)
    {
        if(layers != 0)
            return false;

        if(core != null)
            return false;

        core = r;

        
        
        return true;
    }

    @Override
    public void sunstormArrive()
    {}

    @Override
    public void sunLightArrive(int x1, int y1, int x2, int y2)
    {
        if(x >= x1 && y >= y1 &&
            x <= x2 && y <= y2)
        {
            inLight = true;
        }
        else
        {
            inLight = false;
        }

    }
}
