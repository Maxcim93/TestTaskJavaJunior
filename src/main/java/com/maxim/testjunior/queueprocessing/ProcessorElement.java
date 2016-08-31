package com.maxim.testjunior.queueprocessing;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Максим on 31.08.2016.
 */
public class ProcessorElement extends Thread {
    ElementGroupStorage storage;
    public ProcessorElement(ElementGroupStorage storage){
        this.storage=storage;
        start();
    }

    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                Group group=storage.checkoutGroup();
                while(true){
                    if(group.containExpectedElement())
                        System.out.println(group.getElement());
                    else
                        break;
                }
                storage.checkIn(group);
            }
        }catch(InterruptedException e){
            System.out.println("Processor is stopped");
        }
    }
}
