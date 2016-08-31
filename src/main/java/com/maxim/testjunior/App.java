package com.maxim.testjunior;

import com.maxim.testjunior.elements.Element;
import com.maxim.testjunior.generator.SourceElements;
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
        System.out.println("Обработаны элементы: ");
        BlockingQueue<Element> source=new LinkedBlockingQueue<Element>();
        SourceElements generator = new SourceElements(source,1000,1000);
        ManagerProcessingElement managerProcessing=new ManagerProcessingElement(source,6,30);

        //оставновка выполнения сервиса по обработки очереди
        TimeUnit.SECONDS.sleep(10);
        managerProcessing.stopProcessingElements();
    }
}
