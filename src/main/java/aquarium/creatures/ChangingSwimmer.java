package aquarium.creatures;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A ChangingSwimmer is a PaintedCreature which:
 * <ul>
 * <li>Moves at a given whole integer slope speed, in a straight line;
 * <li>Can change X and Y speeds after being created;
 * <li>Bounces off of the walls, the floor, and the surface of the water,
 * in order to stay underwater.
 * </ul>
 */
public class ChangingSwimmer extends PaintedCreature
{
    private int deltaX;
    private int deltaY;    
    /**
     * Creates a ChangingSwimmer.
     * 
     * @param   name    The friendly name for this ChangingSwimmer
     * @param   speed   The whole integer slope indicating how fast this ChangingSwimmer will move
     */
    public ChangingSwimmer(String name, int speed)
    {
        super(name, 120, 100);
        this.deltaX = speed;
        this.deltaY = speed;
    }
    
    /**
     * Gets the ChangingSwimmer's speed in the X direction
     */
    public int getSpeedX() 
    {
        return this.deltaX;
        
    }
    
    /**
     * Gets the ChangingSwimmer's speed in the Y direction
     */
    public int getSpeedY()
    {
        return this.deltaY;
        
    }
    
    /**
     * Sets the ChangingSwimmer's speed in the X direction
     * <em>Note</em>: Fish need to keep moving horizontally to survive, so an X speed of 0 is not allowed
     */
    public void setSpeedX(int x) 
    {
        if (x != 0) 
        {
            this.deltaX = x;            
        }
        // Instead of setting the X speed to 0, set it to the slowest it can go: 1 (or -1)
        else
        {
            this.deltaX = this.deltaX / Math.abs(this.deltaX);
        }
    }
    
    /**
     * Sets the ChangingSwimmer's speed in the Y direction
     */
    public void setSpeedY(int y)
    {
        this.deltaY = y;
    }
    
    
    /**
     * Moves the ChangingSwimmer in the amount of its given speed in both the X and Y directions.
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
     * <em>Note</em>: This method should never be called based on this ChangingSwimmer's
     * movements.
     */
    public void hitClouds()
    {
        // We should never hit the clouds, but I guess we'd better change course!
        deltaY = -deltaY;
    }

    protected void paint(Graphics g)
    {
        g.setColor(Color.YELLOW);
        
        g.drawLine(0, (this.height / 2) - 15, 0, (this.height / 2) + 15);
        g.drawLine(0, (this.height / 2) - 15, (this.width / 2) + 10, this.height);
        g.drawLine(0, (this.height / 2) + 15, (this.width / 2) + 10, 0);
        g.drawLine((this.width / 2) + 10, this.height, this.width, this.height / 2);
        g.drawLine((this.width / 2) + 10, 0, this.width, this.height / 2);
    }
    
}
