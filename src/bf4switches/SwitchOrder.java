package bf4switches;

import java.util.List;

public class SwitchOrder {
    public final Switch[] switchOrder;
    
    public SwitchOrder(List<Switch> list)
    {
        switchOrder = new Switch[list.size()];
        
        list.toArray(switchOrder);
    }
}
