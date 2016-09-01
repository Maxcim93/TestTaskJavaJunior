package com.maxim.testjunior.generator;

import com.maxim.testjunior.elements.Element;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Класс предназначен для имитации заполнения очереди элементов на обработку из некоторого источника.
 * Функционал направлен на заполнение исходной очереди случайными элементами
 * {@link com.maxim.testjunior.elements.Element} с помощью генератора
 * {@link com.maxim.testjunior.generator.GeneratorElements}.
 */
public class SourceElements extends Thread{


    private BlockingQueue<Element> elementsQueue;
    private GeneratorElements generator;

    /**
     * Конструктор инициализирует очередь для вывода элементов и генератор элементов.
     * @param elementsQueue очередь для вывода элементов,
     * @param generator генератор элементов.
     */
    public SourceElements(BlockingQueue<Element> elementsQueue, GeneratorElements generator){
        this.elementsQueue=elementsQueue;
        this.generator=generator;

        //запуск генерации элементов в очередь в отдельном потоке
        this.start();
    }

    /**
     * Выполняет в потоке генерацию элементов в случайном порядке
     * с помощью вызова метода {@link GeneratorElements#generateElement()}
     * и помещает элемнты в исходную очередь.
     * По выводу всех элементов из всех групп поток завершается.
     */
    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                //пока сушествуют доступные группы элементов
                while(generator.getCurCountGroup() > 0)
                    //внеение в очередь нового элемента
                    // со случайными индентификаторами
                    elementsQueue.put(generator.generateElement());
                //завершение выполнения генератора
                Thread.currentThread().interrupt();
                TimeUnit.MICROSECONDS.sleep(100);
            }
        }catch(InterruptedException e){
            System.out.println("Generator is stopped");
        }
    }
}
