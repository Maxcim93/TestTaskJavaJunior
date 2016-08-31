package com.maxim.testjunior.queueprocessing;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Максим on 31.08.2016.
 */
public class Group {
    private int expectedId=0;
    private int idGroup;
    private Queue<Element> elements=
            new PriorityQueue<Element>();

    public Group(int idGroup){
        this.idGroup=idGroup;
    }

    public int getId(){return idGroup;}

    public synchronized void insert(Element e){
        elements.offer(e);
    }

    public synchronized List<Element> getElements(){
        List<Element> outElements=new LinkedList<Element>();
        int countAddedEl=0;
        while(countAddedEl<10) {
            if (elements.peek() != null && elements.peek().idEl == expectedId) {
                outElements.add(elements.poll());
                expectedId++;
                countAddedEl++;
            } else {
                break;
            }
        }
        return outElements;
    }
}
