package com.maxim.testjunior.queueprocessing;

/**
 * Created by Максим on 30.08.2016.
 */
public class Element implements Comparable<Element>{
    public final int idEl;
    public final int idGroup;

    public Element(int idGroup,int idEl){
        this.idEl=idEl;
        this.idGroup=idGroup;
    }

    public int compareTo(Element e){
        return (this.idEl<e.idEl)? -1:(this.idEl>e.idEl? 1:0);
    }

    @Override
    public String toString(){
        return "Group: "+idGroup+" Id: "+idEl;
    }
}
