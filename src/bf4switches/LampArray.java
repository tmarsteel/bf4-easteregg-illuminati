package bf4switches;

import java.util.List;

public class LampArray
{
    private boolean[] lampStates;
    private boolean[] initialState;
    
    public LampArray(List<Integer> initialOn)
    {
        lampStates = new boolean[20];
        initialState = new boolean[20];
        
        initialOn.forEach(idx -> initialState[idx] = true);
    }

    public boolean allOn()
    {
        for (int n = 0;n < lampStates.length;n++)
            if (!lampStates[n])
                return false;
        
        return true;
    }
    
    public void reset()
    {
        for (int n = 0;n < lampStates.length;n++)
            lampStates[n] = initialState[n];
    }
    
    public void apply(SwitchOrder order)
    {
        for (int n = 0;n < order.switchOrder.length;n++)
            this.apply(order.switchOrder[n]);
    }
    
    public void apply(Switch sw)
    {
        for (int n = 0;n < sw.affectedLamps.length;n++)
        {
            lampStates[sw.affectedLamps[n]] = !lampStates[sw.affectedLamps[n]];
        }
    }
}
