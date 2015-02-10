package aquarium.creatures;

public class LineSwimmer extends ImageCreature
{

    private int deltaX;
    private int deltaY;

    public LineSwimmer(String name, String fileName, int speed)
    {
        super(name, fileName);
        this.deltaX = speed;
        this.deltaY = speed;
    }

    public void updateLocation()
    {
        int newX = getLocation().x + deltaX;
        int newY = getLocation().y + deltaY;
        getLocation().move(newX, newY);
    }

    public void hitLeftWall()
    {
        deltaX = -deltaX;

    }

    public void hitRightWall()
    {
        deltaX = -deltaX;

    }

    public void hitFloor()
    {
        deltaY = -deltaY;

    }

    public void hitSurface()
    {
        deltaY = -deltaY;

    }
    
    public void hitClouds()
    {
        // We should never hit the clouds, but I guess we'd better change course!
        deltaY = -deltaY;
    }

}
