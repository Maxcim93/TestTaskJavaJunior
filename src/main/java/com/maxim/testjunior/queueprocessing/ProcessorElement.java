package com.maxim.testjunior.queueprocessing;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Максим on 31.08.2016.
 */
public class ProcessorElement extends Thread {
    BlockingQueue<Element> queue;
    public ProcessorElement(BlockingQueue<Element> queue){
        this.queue=queue;
        start();
    }

    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                System.out.println(queue.take());
            }
        }catch(InterruptedException e){
            System.out.println("Processor is stopped");
        }
    }
}
