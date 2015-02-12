package aquarium;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import aquarium.creatures.*;
import aquarium.ui.AquariumFrame;

/**
 * The Aquarium holds the animation logic for creatures which may be 
 * added using {@link fillWithCreatures}. It is normally created and
 * started from AquariumMain.
 */
public class Aquarium
{
    /**
     * The width of the Aquarium in pixels.
     */
    public static final int WIDTH = 900;
    /**
     * The height of the Aquarium's water area in pixels.
     */
    public static final int HEIGHT = 300;
    /**
     * The height of the Aquarium's sky area in pixels.
     */
    public static final int SKY_HEIGHT = 300;
    
    private ArrayList<Creature> creatures;
    private Timer updateTimer;
    private AquariumFrame frame = null;
    
    /** 
     * Creates an empty Aquarium.
     * 
     * @see     fillWithCreatures
     */
    public Aquarium()
    {
        this.creatures = new ArrayList<Creature>();
        
        ActionListener timerAction = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateCreatureLocations();
            }
        };
        this.updateTimer = new Timer(100, timerAction);
        this.updateTimer.setRepeats(true);
    }
    
    /**
     * Gets the creatures in the Aquarium.
     * 
     * @return     The list of Creature objects inhabiting the Aquarium.
     */
    public ArrayList<Creature> getCreatures()
    {
        return creatures;
    }
    
    /**
     * Fills the Aquarium by creating and adding {@link Creature}s.
     */
    public void fillWithCreatures()
    {
        creatures.add(new BoringFish("Bob"));
    }

    /**
     * Empties the Aquarium by removing all creatures.
     */
    public void empty()
    {
        creatures.clear();
    }
    
    /**
     * Sets the parent {@link AquariumFrame} for this Aquarium.
     * 
     * @param   frame   The {@link AquariumFrame} for this Aquarium.
     */
    public void setFrame(AquariumFrame frame)
    {
        this.frame = frame;
    }
    
    /**
     * Calls the {@link Creature#updateLocation} method for each {@link Creature} in the
     * Aquarium, and then calls:
     * <ul>
     * <li>{@link Creature#hitLeftWall} if the left edge of the Creature has touched or gone
     * past the left wall;
     * <li>{@link Creature#hitRightWall} if the right edge of the Creature has touched or gone
     * past the right wall;
     * <li>{@link Creature#hitFloor} if the bottom edge of the Creature has touched or gone
     * past the Aquarium floor;
     * <li>{@link Creature#hitSurface} if the Creature is touching the surface of the water;
     * <li>{@link Creature#hitClouds} if the Creature has touched or gone past the top of the sky.
     * </ul>
     */
    public void updateCreatureLocations()
    {
        for(Creature c : creatures)
        {
            c.updateLocation();
            
            BufferedImage img = c.getAppearance();
            int w = img.getWidth();
            int h = img.getHeight();
            // (x,y) are upper left coordinate here
            int x = c.getLocation().x - (w / 2);
            int y = c.getLocation().y - (h / 2);
            if(x <= 0)
            {
                c.hitLeftWall();
            }
            if(y <= -SKY_HEIGHT)
            {
                c.hitClouds();
            }
            else if(y <= 0 && y >= -w)
            {
                c.hitSurface();
            }
            if(x >= WIDTH - w)
            {
                c.hitRightWall();
            }
            if(y >= HEIGHT - h)
            {
                c.hitFloor();
            }
        }
        
        if(frame != null)
        {
            frame.repaintCanvas();
        }
    }
    
    /**
     * Starts the Aquarium animation.
     */
    public void start()
    {
        updateTimer.start();
    }
    
    /**
     * Stops the Aquarium animation.
     */
    public void stop()
    {
        updateTimer.stop();
    }
}
