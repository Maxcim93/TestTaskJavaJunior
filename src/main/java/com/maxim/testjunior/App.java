package com.maxim.testjunior;

import com.maxim.testjunior.queueprocessing.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        BlockingQueue<Element> source=new LinkedBlockingQueue<Element>();
        GeneratorElement generator = new GeneratorElement(source,100,100);
        ElementGroupStorage storage=new ElementGroupStorage();
        ElementStorageBuilder builderStorage=new ElementStorageBuilder(source,storage);
        ProcessorElement processorElement1=new ProcessorElement(storage);
        ProcessorElement processorElement2=new ProcessorElement(storage);
        ProcessorElement processorElement3=new ProcessorElement(storage);
        /*ElementBlockingQueue queue=null;
        GeneratorElement generator = new GeneratorElement(queue=new ElementBlockingQueue(5),1000,1000);
        for(;;)
            System.out.println(queue.take());*/
       // TimeUnit.SECONDS.sleep(5);
    }
}
