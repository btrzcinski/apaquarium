package aquarium.creatures;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * A Creature which has an appearance it paints on its own.
 */
public abstract class PaintedCreature implements Creature
{
    private String name;
    protected final int width;
    protected final int height;
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
    
    public abstract void updateLocation();
    
    public abstract void hitLeftWall();
    
    public abstract void hitRightWall();
    
    public abstract void hitFloor();
    
    public abstract void hitSurface();
    
    public abstract void hitClouds();
    
    protected abstract void paint(Graphics g);
}
