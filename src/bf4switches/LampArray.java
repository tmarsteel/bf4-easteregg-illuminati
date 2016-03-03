package bf4switches;

import java.util.List;

public class LampArray
{
    private boolean[] lampStates;
    private boolean[] initialState;
    
    public LampArray(int nLamps, List<Integer> initialOn)
    {
        lampStates = new boolean[nLamps];
        initialState = new boolean[nLamps];
        
        initialOn.forEach(idx -> initialState[idx] = true);
    }
    
    public void setLampState(int lampIndex, boolean state)
    {
        lampStates[lampIndex] = state;
    }
    
    public boolean getLampState(int lampIndex)
    {
        return lampStates[lampIndex];
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
