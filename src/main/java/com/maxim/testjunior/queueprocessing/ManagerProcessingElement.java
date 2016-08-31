package com.maxim.testjunior.queueprocessing;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * Created by Максим on 30.08.2016.
 */
public class ManagerProcessingElement extends Thread implements Runnable {
    BlockingQueue<Element> elements;
    ScheduledExecutorService executor;
    private int countThread;

    public ManagerProcessingElement(int countThread){
        this.elements=new ElementBlockingQueue(7);
        this.countThread=countThread;
        this.executor= Executors.newScheduledThreadPool(5);
        //this.executor= Executors;
        //старт
        this.start();
    }

    public BlockingQueue<Element> getQueue(){return elements;}

    public void run(){
        try{
           /* for(int i=0;i<countThread;i++)
                new ProcessorElement(elements);*/

            while(!Thread.currentThread().isInterrupted()){
                final Element element=elements.take();

                executor.schedule(new Runnable(){
                                    public void run(){
                                        System.out.println(element);
                                    }
                                }, 10L, TimeUnit.MILLISECONDS);
                //TimeUnit.MILLISECONDS.sleep(0);
            }
        }catch(InterruptedException e){
            executor.shutdown();
            System.out.println("Generator is stopped");
        }
    }
}
