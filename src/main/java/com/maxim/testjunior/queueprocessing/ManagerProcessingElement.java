package com.maxim.testjunior.queueprocessing;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.BlockingQueue;



/**
 * Created by Максим on 30.08.2016.
 */
public class ManagerProcessingElement {
    private BlockingQueue<Element> sourceElements;
    private ElementGroupStorage storageElements;
    private ExecutorService executor;
    private int countThread;

    private ElementsStorageBuilder builderStorage;
    private List<ProcessorElements> processors;

    public ManagerProcessingElement(BlockingQueue<Element> sourceElements,int countThread){
        this.sourceElements=sourceElements;
        this.storageElements=new ElementGroupStorage();

        this.countThread=countThread;
        this.executor= Executors.newFixedThreadPool(countThread);

        //старт обработки
        startProcessing();
    }

    private void startProcessing(){
        //создание и запуск выполнения сервиса формирующего хранилище элементов по группам
        builderStorage=new ElementsStorageBuilder(sourceElements,storageElements);
        //создание обработчиков элементов
        processors=new ArrayList<ProcessorElements>();
        for(int i=0;i<countThread-1;i++)
            processors.add(new ProcessorElements(storageElements,i));
        //запуск обарботчиков элементов
        for(ProcessorElements processor:processors)
            processor.run();
    }

    public void stopProcessingElements(){
        executor.shutdownNow();
        System.out.println("Manager is stopped");
    }
}
