package aquarium;

import aquarium.creatures.ChangingSwimmer;
import aquarium.creatures.LineSwimmer;

/**
 * Class holding the main method for the AP Aquarium.
 */
public class AquariumMain
{
    /**
     * The main method for the AP Aquarium. The method creates an {@link Aquarium} and 
     * adds a ChangingSwimmer and a LineSwimmer.
     * 
     * @param   args    Command line arguments (unused)
     */
    public static void main(String[] args)
    {
        Aquarium aquarium = new Aquarium();
        
        aquarium.addCreature(new ChangingSwimmer("Bob", 5));
        aquarium.addCreature(new LineSwimmer("Jane", 10));
    }
}
