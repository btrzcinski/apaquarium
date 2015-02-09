package aquarium.creatures;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import aquarium.Aquarium;

public class BoringFish extends LineSwimmer
{
    public BoringFish(String name)
    {
        super(name, "BoringFish.jpg", 10);

        // Start the fish in the center
        int startX = Aquarium.WIDTH / 2;
        int startY = Aquarium.HEIGHT / 2;
        getLocation().move(startX, startY);
    }
}
