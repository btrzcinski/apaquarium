package aquarium.creatures;

/**
 * A LineSwimmer is an ImageCreature which:
 * <ul>
 * <li>Moves at a given whole integer slope speed, in a straight line;
 * <li>Bounces off of the walls, the floor, and the surface of the water,
 * in order to stay underwater.
 * </ul>
 */
public class LineSwimmer extends ImageCreature
{

    private int deltaX;
    private int deltaY;

    /**
     * Creates a LineSwimmer.
     * 
     * @param   name    The friendly name for this LineSwimmer
     * @param   fileName    The name of the image file representing this LineSwimmer
     * @param   speed   The whole integer slope indicating how fast this LineSwimmer will move
     */
    public LineSwimmer(String name, String fileName, int speed)
    {
        super(name, fileName);
        this.deltaX = speed;
        this.deltaY = speed;
    }

    /**
     * Moves the LineSwimmer in the amount of its given speed in both the X and Y directions.
     */
    public void updateLocation()
    {
        int newX = getLocation().x + deltaX;
        int newY = getLocation().y + deltaY;
        getLocation().move(newX, newY);
    }

    /**
     * Reverses course along the X axis when the left wall is hit.
     */
    public void hitLeftWall()
    {
        deltaX = -deltaX;

    }

    /**
     * Reverses course along the X axis when the right wall is hit.
     */
    public void hitRightWall()
    {
        deltaX = -deltaX;

    }

    /**
     * Reverses course along the Y axis when the floor is hit.
     */
    public void hitFloor()
    {
        deltaY = -deltaY;

    }

    /**
     * Reverses course along the Y axis when the surface of the water is hit.
     */
    public void hitSurface()
    {
        deltaY = -deltaY;

    }
    
    /**
     * Reverses course along the Y axis when the top of the Aquarium is hit.
     * 
     * <em>Note</em>: This method should never be called based on this LineSwimmer's
     * movements.
     */
    public void hitClouds()
    {
        // We should never hit the clouds, but I guess we'd better change course!
        deltaY = -deltaY;
    }

}
