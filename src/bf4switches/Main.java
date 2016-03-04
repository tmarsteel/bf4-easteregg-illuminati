/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf4switches;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main
{
    public static void main(String... args) throws Exception
    {
        String[] switchNames = new String[] { "Temple", "Tree", "Pagota", "Furnace", "Rock", "Pier", "Waterfall", "Rock" };
        
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        
        final List<Integer> defaultOn = Arrays.asList(readNumberLine("On by default", r));
        
        LampArray lamps = new LampArray(defaultOn);
        List<Switch> switches = new ArrayList<>(switchNames.length);
        SwitchOrder solution = null;
        
        for (String switchName : switchNames)
        {
            List<Integer> switchOn = Arrays.asList(readNumberLine("On with " + switchName + " switch ONLY", r));
            
            List<Integer> switchAffects = new ArrayList<>(20);
            switchOn.forEach(lampOn -> {
                if (!defaultOn.contains(lampOn))
                    switchAffects.add(lampOn);
            });
            defaultOn.forEach(defOn -> {
                if (!switchOn.contains(defOn))
                    switchAffects.add(defOn);
            });
            
            switches.add(new Switch(switchName, switchAffects));
            
            // try to find a solution
            solution = findSolution(switches, lamps);
            
            if (solution != null)
                break;
        }
        
        if (solution == null)
        {
            System.out.println("No solution found :(");
        }
        else
        {
            for (Switch sw : solution.switchOrder)
                System.out.print(sw.name + " ");
            System.out.println();
        }
    }
    
    private static SwitchOrder findSolution(List<Switch> switches, LampArray lamps)
    {
        List<SwitchOrder> permutations = permutate(switches);
        for (SwitchOrder order : permutations)
        {
            lamps.reset();
            lamps.apply(order);

            if (lamps.allOn())
                return order;
        }
        return null;
    }
    
    private static Integer[] readNumberLine(String prompt, BufferedReader r)
        throws Exception
    {
        while (true)
        {
            System.out.println(prompt + " (space separated numbers):");
            
            String[] numberStrs = r.readLine().split(" ");
            Integer[] numbers = new Integer[numberStrs.length];
            
            try
            {
                for (int n = 0;n < numberStrs.length;n++)
                {
                    numbers[n] = Integer.parseInt(numberStrs[n]) - 1;
                    if (numbers[n] > 19 || numbers[n] < 0)
                        throw new IllegalArgumentException();
                }
                
                return numbers;
            }
            catch (IllegalArgumentException ex)
            {
                System.err.println("Invalid input, try again.");
            }
        }
    }
    
    private static List<SwitchOrder> permutate(List<Switch> switches)
    {
        final List<SwitchOrder> orders = new LinkedList<>();
        switches.forEach(sw -> {
            List<List<Switch>> permutatedOrders = permutate(sw, switches);
            permutatedOrders.forEach(o -> {
                orders.add(new SwitchOrder(o));
            });
            orders.add(new SwitchOrder(Arrays.asList(sw)));
        });
        
        return orders;
    }
    
    private static List<List<Switch>> permutate(Switch firstSwitch, List<Switch> allSwitches)
    {
        final LinkedList<Switch> remainingSwitches = new LinkedList<>();
        
        allSwitches.forEach(sw -> {
            if (firstSwitch != sw)
                remainingSwitches.add(sw);
        });
        
        final List<List<Switch>> permutations = new LinkedList<>();
        
        if (remainingSwitches.size() == 1)
        {
            List<Switch> permutation = new LinkedList<>();
            permutation.add(firstSwitch);
            permutation.add(remainingSwitches.get(0));
            permutations.add(permutation);
        }
        else
        {
            remainingSwitches.forEach(sw -> {
                List<List<Switch>> subPermutations = permutate(sw, remainingSwitches);
                subPermutations.forEach(permutation -> {
                    permutations.add(permutation);
                    List<Switch> copy = new LinkedList<Switch>(permutation);
                    copy.add(0, firstSwitch);
                    permutations.add(copy);
                });
            });
        }
        
        return permutations;
    }
}
