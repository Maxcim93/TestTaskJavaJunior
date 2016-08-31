package com.maxim.testjunior.queueprocessing;

import java.util.List;

/**
 * Created by Максим on 31.08.2016.
 */
public class ProcessorElements extends Thread {
    private ElementGroupStorage storage;
    private int idProcessor;
    public ProcessorElements(ElementGroupStorage storage, int idProcessor){
        this.idProcessor=idProcessor;
        this.storage=storage;

        start();
    }

    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                Group group=storage.checkoutGroup();
                List<Element> elementsOnProc=group.getElements();
                for(Element element: elementsOnProc){
                        System.out.println(element);
                }
                storage.checkIn(group);
            }
        }catch(InterruptedException e){
            System.out.println("Processor-"+idProcessor+" is stopped");
        }
    }
}
