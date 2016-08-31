package com.maxim.testjunior;

import com.maxim.testjunior.queueprocessing.ElementBlockingQueue;
import com.maxim.testjunior.queueprocessing.GeneratorElement;
import com.maxim.testjunior.queueprocessing.ManagerProcessingElement;

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

        ManagerProcessingElement managerProcessingElement=
                new ManagerProcessingElement(5);
        ElementBlockingQueue queue=null;
        GeneratorElement generator = new GeneratorElement(managerProcessingElement.getQueue(),100,100);
        /*ElementBlockingQueue queue=null;
        GeneratorElement generator = new GeneratorElement(queue=new ElementBlockingQueue(5),1000,1000);
        for(;;)
            System.out.println(queue.take());*/
       // TimeUnit.SECONDS.sleep(5);
    }
}
