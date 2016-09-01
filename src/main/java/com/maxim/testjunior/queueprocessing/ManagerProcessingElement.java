package com.maxim.testjunior.queueprocessing;

import com.maxim.testjunior.elements.Element;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.BlockingQueue;


/**
 * Класс  осуществляет управление процессом обработки элементов.
 * В зависиомсти от заданного количества потоков вызвает на выполнение
 * поток, формирующий хранилище элементов {@link ElementsStorageBuilder}, и потоки-обработчики
 * {@link ProcessorElements}, получающие на обработку элементы из хранилища.
 * @author Maksim Mekh
 * @version 1.0
 */
public class ManagerProcessingElement {
    private BlockingQueue<Element> sourceQueueElements;  //исходная очередь элементов на обработку
    private ElementGroupsStorage storageElements;       // хранилище групп элементов
    private ExecutorService executor;   // пул потоков
    private int countThread;            //количество потоков


    /**
     * Конструктор из входных праметров инициализирует исходную очередь элементов, количество потоков
     * и количесвто элементов, выводимых подряд из одной группы на обработку.
     * Так же создается хранилище элементов и пул потоков.
     * В завершении производится созадние и вызов конструктора хранилища
     * {@link ElementsStorageBuilder} и потоков-обработчиков {@link ProcessorElements}.
     * @param sourceQueueElements исходная очередь элементов,
     * @param countThread количество потоков,
     * @param sizeOutputElements количесвто элементов, выводимых подряд из одной группы на обработку.
     */
    public ManagerProcessingElement(BlockingQueue<Element> sourceQueueElements,int countThread, int sizeOutputElements){
        this.sourceQueueElements=sourceQueueElements;
        this.storageElements=new ElementGroupsStorage(sizeOutputElements);

        this.countThread=countThread;
        this.executor= Executors.newFixedThreadPool(countThread);

        //старт обработки
        startProcessing();
    }

    private void startProcessing(){
        //создание и запуск выполнения сервиса формирующего хранилище элементов по группам
        executor.execute(new ElementsStorageBuilder(sourceQueueElements,storageElements));
        //создание и запуск обработчиков элементов
        for(int i=0;i<countThread-1;i++)
            executor.execute(new ProcessorElements(storageElements,i));
    }

    /**
     * Метод осуществояет оставноку выполнения потока, формирующего хранилище, и
     * потоков-обработчиков через пул потоков в котором они запущены.
     */
    public void stopProcessingElements(){
        executor.shutdownNow();
        System.out.println("Manager is stopped");
    }
}
