package com.maxim.testjunior.queueprocessing;

import com.maxim.testjunior.elements.Element;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Класс реализуют задачу для выполнения в отдельном потоке по беспрерывному
 * добавлению элементов из исходной очереди в хранилище групп элементов {@link ElementGroupsStorage}, из которого
 * в дальнейшем элементы получают потоки-обработчики {@link ProcessorElements}.
 * @see ElementGroupsStorage
 * @author Maksim Mekh
 * @version 1.0
 */
public class ElementsStorageBuilder implements Runnable {
    private BlockingQueue<Element> sourceQueue; //исходная очередь поступающих на обработку элементов
    private ElementGroupsStorage storage;       // хранилище групп элементов

    /**
     * Конструктор инциализирует входную очередь и хранилище групп элементов.
     * @param sourceQueue исходная очередь,
     * @param storage хранилище групп элементов.
     */
    public ElementsStorageBuilder(BlockingQueue<Element> sourceQueue, ElementGroupsStorage storage){
        this.sourceQueue=sourceQueue;
        this.storage=storage;
    }


    /**
     * Метод представляет реализацию задачи по бесперывному добавлению элементов
     * из входной очереди в хранилище элементов путем вызова из
     * объекта хранилища метода {@link ElementGroupsStorage#insertElement(Element)}.
     */
    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                //получение элемента из очереди и добавление в хранилище
                storage.insertElement(sourceQueue.take());
            }
            //ожидание получения исключения
            TimeUnit.MICROSECONDS.sleep(100);
        }catch(InterruptedException e){
            System.out.println("Storage elements is stopped");
        }
    }
}
