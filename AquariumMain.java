import aquarium.Aquarium;
import aquarium.ui.AquariumFrame;

public class AquariumMain
{
    /**
     * The main method for the AP Aquarium. The method:
     * <ul>
     * <li>Creates an Aquarium, and calls {@link Aquarium#fillWithCreatures}
     * <li>Creates an AquariumFrame, displays it, and starts the Aquarium.
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
        aquarium.start();
    }

}
