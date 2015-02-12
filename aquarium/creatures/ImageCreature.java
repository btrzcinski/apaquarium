package aquarium.creatures;

import javax.imageio.ImageIO;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * A Creature which can be given the name of a file residing in &quot;aquarium\images&quot;
 * and will use that image file as the appearance.
 */
public abstract class ImageCreature implements Creature
{
    private String name;
    private Point location;
    private BufferedImage appearance;
    
    /**
     * Creates an ImageCreature.
     * 
     * @param   name    The friendly name of the ImageCreature
     * @param   fileName    The name of the image for this ImageCreature (e.g., &quot;image.jpg&quot;)
     */
    public ImageCreature(String name, String fileName)
    {
        this.name = name;
        this.location = new Point(0, 0);
        
        try
        {
            loadAppearance(fileName);
        } catch (IOException ioe)
        {
            System.err.println("Defaulting to a 5x5 gray box");
            appearance = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
            Graphics g = appearance.getGraphics();
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, 5, 5);
        }
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
        return appearance;
    }
    
    public abstract void updateLocation();
    
    public abstract void hitLeftWall();
    
    public abstract void hitRightWall();
    
    public abstract void hitFloor();
    
    public abstract void hitSurface();
    
    public abstract void hitClouds();
    
    // fileName should be a file inside the aquarium.images package
    // example: "aquarium\images\fish.jpg" would be just "fish.jpg"
    private void loadAppearance(String fileName) throws IOException
    {
        try
        {
            String packagePath = "aquarium/images/" + fileName;
            URL imgResource = ClassLoader.getSystemResource(packagePath);
            if(imgResource == null)
            {
                throw new FileNotFoundException(packagePath + " (" + imgResource + ")");
            }
            appearance = ImageIO.read(imgResource);
            
        } catch (FileNotFoundException fe)
        {
            System.err.println("Image not found: " + fe.getMessage());
            System.err.println("Does this image exist? Is it in the aquarium/images folder?");
            throw fe;
        } catch (IOException e)
        {
            System.err.println("Problems reading image file: " + fileName);
            System.err.println("Is this a valid image file?");
            throw e;
        }
    }

}
