/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf4switches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    
    private static List<SwitchOrder> orders = new LinkedList<>();
    
    // CONFIG
    private static final int nTurns = 1;
    private static final boolean turnAll = false;
    
    public static void main(String... args) throws Exception
    {
        // SWITCHES
        
        List<Switch> switches = new ArrayList<>();
        switches.add(new Switch("Tempel",     Arrays.asList(4, 6, 12, 17)));
        switches.add(new Switch("Fidschi",    Arrays.asList(4, 7, 9, 12, 19)));
        switches.add(new Switch("Ofen",       Arrays.asList(3, 7, 9, 10, 19)));
        switches.add(new Switch("Stein",      Arrays.asList(3, 10, 14, 19)));
        switches.add(new Switch("Wasserfall", Arrays.asList(2, 5, 14, 16, 18)));
        switches.add(new Switch("Pier",       Arrays.asList(0, 2, 6, 9, 12, 17)));
        switches.add(new Switch("Baumstamm",  Arrays.asList(0, 1, 3, 6, 8, 10, 11, /*13, 15,*/ 17)));
        
        int nSwitches = switches.size();
        for (int n = 2; n <= nTurns;n++)
        {
            for (int i = 0;i < nSwitches;i++)
            {
                Switch t= switches.get(i);
                switches.add(new Switch(t.name, Arrays.asList(t.affectedLamps)));
            }
        }
        
        switches.forEach(sw -> {
            List<List<Switch>> permutatedOrders = permutate(sw, switches);
            permutatedOrders.forEach(o -> {
                orders.add(new SwitchOrder(o));
            });
        });
        
        System.out.println("Found all Permutations; " + orders.size() + " permutations");
        
        Thread t = new Thread(() -> {
            //final LampArray array = new LampArray(20, Arrays.asList(7, 9, 19));
            final LampArray array = new LampArray(20, Arrays.asList(7, 9, 13, 15, 19));
            int n = 0;
            for (SwitchOrder order : orders)
            {
                array.reset();
                array.apply(order);

                if (array.allOn())
                {
                    System.out.println("Yeaah!");
                    for (Switch sw : order.switchOrder)
                    {
                        System.out.println(sw.name);
                    }
                    System.out.println("----------");
                }
                
                n++;
                
                if (n % (orders.size() / 10) == 0)
                {
                    System.out.println("Progress: " + (n * 100 / orders.size()) + "%");
                }
            }
        });
        t.start(); 
        t.join();
    }
    
    public static List<List<Switch>> permutate(Switch firstSwitch, List<Switch> switches)
    {
        final LinkedList<Switch> remainingSwitches = new LinkedList<>();
        
        switches.forEach(sw -> {
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
                    if (turnAll)
                    {
                        permutation.add(0, sw);
                        permutations.add(permutation);
                    }
                    else
                    {
                        permutations.add(permutation);
                        List<Switch> copy = new LinkedList<Switch>(permutation);
                        copy.add(0, sw);
                        permutations.add(copy);
                    }
                });
            });
        }
        
        return permutations;
    }
}
