package aquarium.creatures;

import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public interface Creature
{
    // The name of the Creature
    public String getName();

    // The center point of this Creature in the aquarium
    public Point getLocation();

    // Defines how this Creature will be rendered in the Aquarium
    public BufferedImage getAppearance();
    
    // Called on every 'tick' to let the creature move
    public void updateLocation();

    // These methods are called by the Aquarium to communicate
    // events that have happened to the Creature
    public void hitLeftWall();

    public void hitRightWall();

    public void hitFloor();

    public void hitSurface();
    
    public void hitClouds();
}
