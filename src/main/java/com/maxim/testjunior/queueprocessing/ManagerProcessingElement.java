package com.maxim.testjunior.queueprocessing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Максим on 30.08.2016.
 */
public class ManagerProcessingElement extends Thread implements Runnable {
    ElementBlockingQueue elements;
    ExecutorService executor;

    public ManagerProcessingElement(int countThread,int countGroup){
        this.elements=new ElementBlockingQueue(countGroup);
        this.executor= Executors.newFixedThreadPool(countThread);

        //старт
        this.start();
    }

    public ElementBlockingQueue getQueue(){return elements;}

    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                final Element element=elements.take();
                executor.execute(new Runnable(){
                    public void run(){
                        System.out.println("IdGroup: "+element.idGroup+
                                " IdElement: "+element.idEl);
                    }
                });
            }
        }catch(InterruptedException e){
            executor.shutdown();
            System.out.println("Generator is stopped");
        }
    }
}
