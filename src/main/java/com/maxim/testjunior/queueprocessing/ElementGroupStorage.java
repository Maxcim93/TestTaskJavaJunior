package com.maxim.testjunior.queueprocessing;

import com.maxim.testjunior.elements.Element;
import com.maxim.testjunior.elements.Group;

import java.util.*;

/**
 * Created by Максим on 31.08.2016.
 */
public class ElementGroupStorage {
    private Map<Integer,Group> groups=new HashMap<Integer,Group>();
    private LinkedList<Group> freeGroups = new LinkedList<Group>();
    private LinkedList<Group> checkoutGroups=new LinkedList<Group>();
    private int countFreeGroup=0;
    private int sizeOutputElements;

    public ElementGroupStorage(int sizeOutputElements){
        this.sizeOutputElements=sizeOutputElements;
    }

    public synchronized void insertElement(Element e){
        if(groups.keySet().contains(e.idGroup)) {
            groups.get(e.idGroup).insert(e);
        } else{
            Group newGroup=new Group(e.idGroup,sizeOutputElements);
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
