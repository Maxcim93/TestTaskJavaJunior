package com.maxim.testjunior.queueprocessing;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Created by Максим on 31.08.2016.
 */
public class ElementGroupStorage {
    private Map<Integer,Group> groups=new HashMap<Integer,Group>();
    private LinkedList<Group> freeGroups = new LinkedList<Group>();
    private LinkedList<Group> checkoutGroups=new LinkedList<Group>();
    private int countFreeGroup=0;

    public synchronized void insertElement(Element e){
        if(groups.keySet().contains(e.idGroup)) {
            groups.get(e.idGroup).insert(e);
        } else{
            Group newGroup=new Group(e.idGroup);
            groups.put(newGroup.getId(),newGroup);
            freeGroups.add(newGroup);
            countFreeGroup++;
            groups.get(e.idGroup).insert(e);
            notifyAll();
        }
    }

    public synchronized Group checkoutGroup() throws InterruptedException{
        while(true) {
            if (countFreeGroup > 0) {
                countFreeGroup--;
                Group freeGroup=freeGroups.removeFirst();
                checkoutGroups.add(freeGroup);
                return freeGroup;
            } else {
                wait();
            }
        }
    }

    public synchronized void checkIn(Group group){
        if(checkoutGroups.remove(group)) {
            freeGroups.addLast(group);
            countFreeGroup++;
            notifyAll();
        }
    }



}
