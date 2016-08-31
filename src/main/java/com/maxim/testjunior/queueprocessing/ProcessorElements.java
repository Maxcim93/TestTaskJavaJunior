package com.maxim.testjunior.queueprocessing;

import com.maxim.testjunior.elements.Element;
import com.maxim.testjunior.elements.Group;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Максим on 31.08.2016.
 */
public class ProcessorElements implements Runnable {
    private ElementGroupStorage storage;
    private int idProcessor;
    public ProcessorElements(ElementGroupStorage storage, int idProcessor){
        this.idProcessor=idProcessor;
        this.storage=storage;
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
            //ожидание получения исключения
            TimeUnit.MICROSECONDS.sleep(100);
        }catch(Exception e){
            System.out.println("Processor-"+idProcessor+" is stopped");
        }
    }
}
