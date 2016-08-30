package com.maxim.testjunior.queueprocessing;

import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Максим on 30.08.2016.
 */
public class ElementBlockingQueue {
    private Map<Integer,Group> elements;
    private int countGroup;

    public ElementBlockingQueue(int countGroup){
        this.countGroup=countGroup;
        //инициализация хранилища элементов
        this.elements= new HashMap<Integer,Group>();
        for(int i=0;i<countGroup;i++) {
            elements.put(i,new Group());
        }
    }

    public synchronized Element take() throws InterruptedException{
        List<Group> groups=new LinkedList<Group>(elements.values());
        Collections.shuffle(groups);
        for(;;) {
            for (Group group : groups) {
                if (group.containExpectedElement())
                    return group.getElement();
            }
            wait();
        }
    }

    public synchronized void put(Element e) throws InterruptedException{
        //System.out.println("Insert: "+e);
        elements.get(e.idGroup).insert(e);
        notifyAll();
    }

    /**
    public int size(){return -1;}
    public boolean isEmpty(){return false;}
    public boolean contains(Object o){return false;}
    public Iterator<Element> iterator(){return null;}
    public Object[] toArray(){return null;}
    public <T> T[] toArray(T[] a){return null;}
    public boolean add(Element e){return false;}
    public boolean remove(Object o){return false;}
    public boolean containsAll(Collection<?> c){return false;}
    public boolean addAll(Collection<? extends Element> c){return false;}
    public boolean removeAll(Collection<?> c){return false;}
    public boolean retainAll(Collection<?> c){return false;}
     **/
}
