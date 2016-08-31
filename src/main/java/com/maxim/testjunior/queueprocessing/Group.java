package com.maxim.testjunior.queueprocessing;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Максим on 31.08.2016.
 */
public class Group {
    private int expectedId=0;
    private int idGroup;

    public Group(int idGroup){
        this.idGroup=idGroup;
    }

    public int getId(){return idGroup;}

    private Queue<Element> elements=
            new PriorityQueue<Element>();

    public void insert(Element e){
        elements.offer(e);
    }

    public Element getElement(){
        expectedId++;
        return elements.poll();
    }

    public boolean containExpectedElement(){
        boolean result;
        if(elements.peek()!=null && elements.peek().idEl==expectedId)
            result=true;
        else
            result=false;

        return result;
    }
}
