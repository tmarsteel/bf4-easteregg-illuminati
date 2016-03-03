package bf4switches;

import java.util.List;

/**
 *
 * @author tobia
 */
public class Switch {
    public final String name;
    public final Integer[] affectedLamps;
    
    public Switch(String name, List<Integer> affectedLamps)
    {
        this.name = name;
        this.affectedLamps = new Integer[affectedLamps.size()];
        affectedLamps.toArray(this.affectedLamps);
    }
}
