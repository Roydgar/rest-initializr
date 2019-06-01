package tk.roydgar.restinitializr.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class CompositeActionListenerWithPriorities implements ActionListener {

    private Map<Integer, ArrayList<ActionListener>> listeners = new TreeMap<>();

    @Override
    public void actionPerformed(ActionEvent e) {
        TreeSet<Integer> t = new TreeSet<>(listeners.keySet());
        Iterator<Integer> it = t.descendingIterator();
        while (it.hasNext()){
            int x = it.next();
            ArrayList<ActionListener> l = listeners.get(x);
            for (ActionListener a : l){
                a.actionPerformed(e);
            }
        }
    }
    public boolean deleteActionListener(ActionListener a){
        for (Integer x : listeners.keySet()){
            for (int i=0; i<listeners.get(x).size(); i++){
                if (listeners.get(x).get(i) == a){
                    listeners.get(x).remove(i);
                    return true;
                }
            }
        }
        return false;
    }
    public void addActionListener(ActionListener a, int priority){
        deleteActionListener(a);
        if(!listeners.containsKey(priority)){
            listeners.put(priority, new ArrayList<>());
        }
        listeners.get(priority).add(a);
    }

}