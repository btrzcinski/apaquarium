import java.awt.Point;

import aquarium.Aquarium;
import aquarium.ui.AquariumFrame;
import aquarium.ui.ControllerFrame;

/**
 * Class holding the main method for the AP Aquarium.
 */
public class AquariumMain
{
    /**
     * The main method for the AP Aquarium. The method:
     * <ul>
     * <li>Creates an {@link Aquarium}, and calls {@link Aquarium#fillWithCreatures}
     * <li>Creates an {@link AquariumFrame}, displays it, and starts the Aquarium.
     * </ul>
     * 
     * @param   args    Command line arguments (unused)
     * @see             Aquarium#fillWithCreatures
     */
    public static void main(String[] args)
    {
        Aquarium aquarium = new Aquarium();
        aquarium.fillWithCreatures();
        
        // add 22 to height to account for title bar
        AquariumFrame aquariumFrame = new AquariumFrame(
                aquarium, Aquarium.WIDTH, Aquarium.HEIGHT + Aquarium.SKY_HEIGHT + 22);
        aquarium.setFrame(aquariumFrame);
        aquariumFrame.setVisible(true);
        
        ControllerFrame controllerFrame = new ControllerFrame(aquarium);
        controllerFrame.setVisible(true);
        
        // Move controller to the right of the aquarium
        Point aquariumFrameLoc = aquariumFrame.getLocation();
        controllerFrame.setLocation(aquariumFrameLoc.x + aquariumFrame.getWidth(), aquariumFrameLoc.y);
    }
}
