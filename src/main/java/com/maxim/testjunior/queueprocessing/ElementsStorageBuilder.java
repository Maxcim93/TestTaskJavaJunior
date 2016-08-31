package com.maxim.testjunior.queueprocessing;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Максим on 31.08.2016.
 */
public class ElementsStorageBuilder extends Thread {
    private BlockingQueue<Element> source;
    private ElementGroupStorage storage;

    public ElementsStorageBuilder(BlockingQueue<Element> source, ElementGroupStorage storage){
        this.source=source;
        this.storage=storage;
        start();
    }

    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                storage.insertElement(source.take());
            }
        }catch(InterruptedException e){
            System.out.println("Storage elements is stopped");
        }
    }
}
