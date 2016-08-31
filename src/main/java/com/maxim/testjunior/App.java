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
        GeneratorElement generator = new GeneratorElement(source,1000,1000);
        ManagerProcessingElement managerProcessing=new ManagerProcessingElement(source,6);
        managerProcessing.stopProcessingElements();
    }
}
