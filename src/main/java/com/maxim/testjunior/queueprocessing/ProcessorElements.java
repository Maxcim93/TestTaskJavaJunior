package com.maxim.testjunior.queueprocessing;

import com.maxim.testjunior.elements.Element;
import com.maxim.testjunior.elements.Group;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Класс описывает обрабочик элементов очереди.
 * Обрабока заклчается в выведении идентификатора элемента (ITEM_ID)
 * и идентификатора группы (GROUP_ID). Элементы на обработку задача получает из
 * хранилища групп элементов {@link ElementGroupsStorage}.
 * @author Maksim Mekh
 * @version 1.0
 */
public class ProcessorElements implements Runnable {
    private ElementGroupsStorage storage;   //хранилище групп элементов
    private int idProcessor;    //идентификатор обрабочика

    /**
     * Конструктор инициализирует из входных параметров
     * хранилище групп элементов на обработку и индентификатор.
     * @param storage хранилище групп элементов,
     * @param idProcessor идентификатор обработчика.
     */
    public ProcessorElements(ElementGroupsStorage storage, int idProcessor){
        this.idProcessor=idProcessor;
        this.storage=storage;
    }

    /**
     * Метод описывает задачау обработки элементов.
     * Изначально производится получение группы элементов из хранилища
     * путем резревирования через метод {@link ElementGroupsStorage#checkoutGroup()}.
     * Производится получение из группы блока элементов на обработку, элементы обрабатываются
     * (вывод ITEM_ID и GROUP_ID) и зарезервированная группа возвращается в хранилище через метод
     * {@link ElementGroupsStorage#checkInGroup(Group)}.
     */
    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                //резервирование и получение группы элементов
                Group group=storage.checkoutGroup();
                //получение элементов на обработку
                List<Element> elementsOnProc=group.getElements();
                //обработка элементов
                for(Element element: elementsOnProc){
                        System.out.println(element);
                }
                //возвращение элементов в хранилище
                storage.checkInGroup(group);
            }
            //ожидание получения исключения
            TimeUnit.MICROSECONDS.sleep(100);
        }catch(InterruptedException e){
            System.out.println("Processor-"+idProcessor+" is stopped");
        }
    }
}
