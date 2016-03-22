package aquarium.creatures;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * A Creature which has an appearance it paints on its own.
 */
public abstract class PaintedCreature implements Creature
{
    private String name;
    private int width;
    private int height;
    private Point location;
    
    /**
     * Creates an PaintedCreature.
     * 
     * @param   name    The friendly name of the PaintedCreature
     * @param   width   The desired width of this PaintedCreature
     * @param   height  The desired height of this PaintedCreature
     */
    public PaintedCreature(String name, int width, int height)
    {
        this.name = name;
        this.width = width;
        this.height = height;

        // Start the PaintedCreature on the very edge
        // The location is the center of the creature, so we want the creature to be inside the Aquarium entirely
        // when it is placed
        this.location = new Point(width / 2, height / 2);
    }

    public String getName()
    {
        return name;
    }
    
    public Point getLocation()
    {
        return location;
    }
    
    public BufferedImage getAppearance()
    {
        BufferedImage appearance = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = appearance.getGraphics();
        paint(g);
        return appearance;
    }
    
    /**
     * Gets the width of the PaintedCreature.
     * 
     * @return Width, in pixels
     */
    public int getWidth()
    {
    	return this.width;
    }
    
    /**
     * Sets the width of the PaintedCreature.
     * 
     * @param w Width, in pixels
     */
    public void setWidth(int w)
    {
    	this.width = w;
    }
    
    /**
     * Gets the height of the PaintedCreature.
     * 
     * @return Height, in pixels
     */
    public int getHeight()
    {
    	return this.height;
    }
    
    /**
     * Sets the height of the PaintedCreature.
     * 
     * @param h Height, in pixels
     */
    public void setHeight(int h)
    {
    	this.height = h;
    }
    
    public abstract void updateLocation();
    
    public abstract void hitLeftWall();
    
    public abstract void hitRightWall();
    
    public abstract void hitFloor();
    
    public abstract void hitSurface();
    
    public abstract void hitClouds();
    
    public abstract void paint(Graphics g);
}
