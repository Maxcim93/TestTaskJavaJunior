package com.maxim.testjunior.queueprocessing;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Максим on 30.08.2016.
 */
public class ElementBlockingQueue extends AbstractQueue<Element> implements BlockingQueue<Element>  {
    private Map<Integer,Group> elements;
    private int capacity;
    private int countAddedEl=0;

    public ElementBlockingQueue(int capacity){
        this.capacity=capacity;
        this.elements= new HashMap<Integer,Group>();
    }

    public synchronized Element take()  throws InterruptedException{
        for(;;) {
            List<Group> groups=new LinkedList<Group>(elements.values());
            Collections.shuffle(groups);

            for (Group group : groups) {
                if (group.containExpectedElement())
                    return group.getElement();
            }
            wait();
        }
    }

    public synchronized void put(Element e) throws InterruptedException{
       /* if(countAddedEl==capacity) {
            countAddedEl=0;
            notifyAll();
        }*/

        if(elements.keySet().contains(e.idGroup)) {
            elements.get(e.idGroup).insert(e);
        } else{
            //elements.put(e.idGroup,new Group());
            elements.get(e.idGroup).insert(e);
        }
        notifyAll();

        //countAddedEl++;
    }



    public int	remainingCapacity(){throw new UnsupportedOperationException();}
    public boolean	offer(Element e, long timeout, TimeUnit unit) {throw new UnsupportedOperationException();}
    public int	drainTo(Collection<? super Element> c, int maxElements){throw new UnsupportedOperationException();}
    public int	drainTo(Collection<? super Element> c){throw new UnsupportedOperationException();}
    public Element	poll(long timeout, TimeUnit unit) {throw new UnsupportedOperationException();}
    public Element	poll(){throw new UnsupportedOperationException();}
    public Element	peek(){throw new UnsupportedOperationException();}
    public boolean	offer(Element e){throw new UnsupportedOperationException();}
    public int size(){throw new UnsupportedOperationException();}
    public Iterator<Element> iterator(){throw new UnsupportedOperationException();}
}
