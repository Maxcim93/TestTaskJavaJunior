package com.maxim.testjunior;

import com.maxim.testjunior.elements.Element;
import com.maxim.testjunior.generator.GeneratorElements;
import com.maxim.testjunior.generator.SourceElements;
import com.maxim.testjunior.queueprocessing.ManagerProcessingElement;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Имитация обработки очереди.
 */
public class App
{
    //ПАРАМЕТРЫ СИМУЛЯЦИИ:

    public static int COUNT_GROUP=1000;     //количество групп элементов
    public static int COUNT_ELEMENTS=1000;  //количество элементов в группе
    public static int COUNT_THREAD=6;       //количество потоков
    public static int SIZE_OUTPUT_BLOCK=30; //количесвто элементов выводимых подряд из одной группы
    public static long RUN_TIME_SECONDS=10; //время выполнения симуляции (секунды)

    public static void main( String[] args ) throws Exception
    {
        System.out.println("Обработаны элементы: ");

        //инициализация исходной очереди
        BlockingQueue<Element> queueElements=new LinkedBlockingQueue<Element>();
        //создание генератора элементов
        GeneratorElements generatorElements=new GeneratorElements(COUNT_GROUP,COUNT_ELEMENTS);
        //создание потока имитирующего источник элементов, заполняющих очередь
        SourceElements sourceElements = new SourceElements(queueElements,generatorElements);
        //запуск на выполение обработки очереди
        ManagerProcessingElement managerProcessing=new ManagerProcessingElement(queueElements,COUNT_THREAD,SIZE_OUTPUT_BLOCK);

        //оставновка выполнения сервиса по обработке очереди
        TimeUnit.SECONDS.sleep(RUN_TIME_SECONDS);
        managerProcessing.stopProcessingElements();
    }
}
