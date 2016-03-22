package aquarium;

import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Timer;

import aquarium.creatures.*;
import aquarium.ui.AquariumFrame;
import aquarium.ui.ControllerFrame;

/**
 * The Aquarium holds the animation logic for creatures which may be 
 * added using {@link addCreature}. It is normally created and
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
    
    private List<Creature> creatures;
    private Timer updateTimer;
    private AquariumFrame frame = null;
    
    /** 
     * Creates an empty Aquarium.
     */
    public Aquarium()
    {
        this.creatures = Collections.synchronizedList(new ArrayList<Creature>());
        
        ActionListener timerAction = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateCreatureLocations();
            }
        };
        this.updateTimer = new Timer(100, timerAction);
        this.updateTimer.setRepeats(true);
        
        // add 22 to height to account for title bar
        this.frame = new AquariumFrame(this, Aquarium.WIDTH, Aquarium.HEIGHT + Aquarium.SKY_HEIGHT + 22);
        
        ControllerFrame controllerFrame = new ControllerFrame(this);
        
        // Move controller to the right of the aquarium
        Point aquariumFrameLoc = this.frame.getLocation();
        controllerFrame.setLocation(aquariumFrameLoc.x + this.frame.getWidth(), aquariumFrameLoc.y);

        controllerFrame.setVisible(true);
        this.frame.setVisible(true);  
    }
    
    /**
     * Gets the creatures in the Aquarium.
     * 
     * Make sure you synchronize access to the collection with a synchronized block.
     * 
     * @return     The list of Creature objects inhabiting the Aquarium.
     */
    public List<Creature> getCreatures()
    {
        return creatures;
    }
    
    /**
     * Adds a Creature to the Aquarium.
     * 
     * @param c The Creature to add.
     */
    public void addCreature(Creature c)
    {
        synchronized (creatures)
        {
            creatures.add(c);
        }
        
        if (frame != null)
        {
            frame.repaintCanvas();
        }
    }
    
    /**
     * Removes a Creature from the Aquarium.
     * 
     * @param c The Creature to remove.
     */
    public void removeCreature(Creature c)
    {
        synchronized (creatures)
        {
            creatures.remove(c);
        }
        
        if (frame != null)
        {
            frame.repaintCanvas();
        }
    }

    /**
     * Empties the Aquarium by removing all creatures.
     */
    public void empty()
    {
        synchronized (creatures)
        {            
            creatures.clear();
        }
        
        if (frame != null)
        {
            frame.repaintCanvas();
        }
    }
    
    /**
     * <p>Calls the {@link Creature#updateLocation} method for each {@link Creature} in the
     * Aquarium, and then calls event methods in order.</p>
     *
     * <ol>
     * <li>{@link Creature#hitClouds} if the Creature has touched or gone past the top of the sky.
     * <li>{@link Creature#hitSurface} if the Creature is touching the surface of the water;
     * <li>{@link Creature#hitFloor} if the bottom edge of the Creature has touched or gone
     * past the Aquarium floor;
     * <li>{@link Creature#hitLeftWall} if the left edge of the Creature has touched or gone
     * past the left wall;
     * <li>{@link Creature#hitRightWall} if the right edge of the Creature has touched or gone
     * past the right wall;
     * </ol>
     * 
     * <p>These methods are called only once for a given movement. For example, if a creature approaches
     * and touches the left wall, then stays on or past the wall, {@link Creature#hitLeftWall} will be
     * called only once. If the creature reapproaches the wall and moves past it in the other direction,
     * {@link Creature#hitLeftWall} will be called again.</p>
     * <p>
     * A {@link Creature} can cause more than one event to be triggered simultaneously; for example, 
     * if a {@link Creature} moves from below the floor to above the clouds, {@link Creature#hitClouds},
     * {@link Creature#hitSurface}, and {@link Creature#hitFloor} will all be called.</p>
     *<p>
     * If multiple events occur at once, they will be called in the order they are listed above.</p>
     */
    public void updateCreatureLocations()
    {
        for (Creature c : creatures)
        {
            BufferedImage img = c.getAppearance();
            int w = img.getWidth();
            int h = img.getHeight();

            // (x,y) are upper left coordinate here
            int formerX = c.getLocation().x - (w / 2);
            int formerY = c.getLocation().y - (h / 2);
            c.updateLocation();
            int newX = c.getLocation().x - (w / 2);
            int newY = c.getLocation().y - (h / 2);

            if ((formerY > -SKY_HEIGHT && newY <= -SKY_HEIGHT) ||
                    (formerY < -SKY_HEIGHT && newY >= -SKY_HEIGHT))
            {
                c.hitClouds();
            }
            if ((formerY < -h && newY >= -h) ||
                    (formerY > 0 && newY <= 0))
            {
                c.hitSurface();
            }
            if ((formerY < (HEIGHT - h) && newY >= (HEIGHT - h)) ||
                    (formerY > HEIGHT && newY <= HEIGHT))
            {
                c.hitFloor();
            }

            if ((formerX > 0 && newX <= 0) ||
                    (formerX < 0 && newX >= 0))
            {
                c.hitLeftWall();
            }
            if ((formerX < (WIDTH - w) && newX >= (WIDTH - w)) ||
                    (formerX > WIDTH && newX <= WIDTH))
            {
                c.hitRightWall();
            }
        }
        
        if (frame != null)
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
    
    /**
     * Returns true if the Aquarium simulation is running.
     * 
     * @return  true if the Aquarium simulation is running
     */
    public boolean isRunning()
    {
        return updateTimer.isRunning();
    }
}
