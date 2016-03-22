package aquarium.creatures;

import java.awt.image.BufferedImage;
import java.awt.Point;

/**
 * A Creature inhabits an {@link aquarium.Aquarium} and primarily has a name, location, and appearance.
 * Implement this interface to create a Creature that can swim in the Aquarium and animate according to
 * the Aquarium's timeline.
 */
public interface Creature
{
    /**
     * Gets the friendly name of this Creature.
     * 
     * @return  The name of this Creature.
     */
    public String getName();

    /**
     * Gets the location of the center point of this Creature.
     * 
     * @return  The {@link Point} with the coordinates of the center point.
     */
    public Point getLocation();

    /**
     * Gets the image that can be used to render this Creature's appearance.
     * 
     * @return  The {@link BufferedImage} used to paint this Creature.
     */
    public BufferedImage getAppearance();
    
    /**
     * Causes the Creature to move in some way. {@link getLocation} can be used to retrieve
     * the updated location afterwards.
     * 
     * @see     getLocation
     * @see     aquarium.Aquarium#updateCreatureLocations
     */
    public void updateLocation();

    /**
     * Event method called by the Aquarium to indicate that the Creature has touched or passed
     * the left wall.
     * 
     * @see     aquarium.Aquarium#updateCreatureLocations
     */
    public void hitLeftWall();

    /**
     * Event method called by the Aquarium to indicate that the Creature has touched or passed
     * the right wall.
     * 
     * @see     aquarium.Aquarium#updateCreatureLocations
     */
    public void hitRightWall();

    /**
     * Event method called by the Aquarium to indicate that the Creature has touched or passed
     * the floor (bottom).
     * 
     * @see     aquarium.Aquarium#updateCreatureLocations
     */
    public void hitFloor();

    /**
     * Event method called by the Aquarium to indicate that the Creature is touching the water's
     * surface.
     * 
     * @see     aquarium.Aquarium#updateCreatureLocations
     */
    public void hitSurface();
    
    /**
     * Event method called by the Aquarium to indicate that the Creature has touched or passed
     * the top of the sky area.
     * 
     * @see     aquarium.Aquarium#updateCreatureLocations
     */
    public void hitClouds();
}
