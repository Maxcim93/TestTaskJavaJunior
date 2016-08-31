package com.maxim.testjunior.queueprocessing;

import com.maxim.testjunior.elements.Element;

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

    public ManagerProcessingElement(BlockingQueue<Element> sourceElements,int countThread, int sizeOutputElements){
        this.sourceElements=sourceElements;
        this.storageElements=new ElementGroupStorage(sizeOutputElements);

        this.countThread=countThread;
        this.executor= Executors.newFixedThreadPool(countThread);

        //старт обработки
        startProcessing();
    }

    private void startProcessing(){
        //создание и запуск выполнения сервиса формирующего хранилище элементов по группам
        executor.execute(new ElementsStorageBuilder(sourceElements,storageElements));
        //создание и запуск обработчиков элементов
        for(int i=0;i<countThread-1;i++)
            executor.execute(new ProcessorElements(storageElements,i));
    }

    public void stopProcessingElements(){
        executor.shutdownNow();
        System.out.println("Manager is stopped");
    }
}
