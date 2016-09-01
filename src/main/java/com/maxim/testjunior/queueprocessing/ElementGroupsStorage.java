package com.maxim.testjunior.queueprocessing;

import com.maxim.testjunior.elements.Element;
import com.maxim.testjunior.elements.Group;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Класс осуществялет функции хранилища для групп элементов.
 * Предоставляет возможность многопоточного добавления элементов в группы,
 * и предоставление групп потокам-обработчикам {@link ProcessorElements} для извлечения элементов.
 * Предоставление потокам-обрабочикам групп существляется по
 * принципу резервирования - зарезрвированная группа доступна только получившему потоку.
 * Поток получает группу, выполняет операции обработки и возвращает группу в хранилище,
 * после чего она доступна для резервирвания другими потоками.
 * @author Maksim Mekh
 * @version 1.0
 */
public class ElementGroupsStorage {
    private Map<Integer,Group> groups=new HashMap<Integer,Group>();     //группы элементов
    private LinkedList<Group> freeGroups = new LinkedList<Group>();     //ссдыки на свободные группы
    private LinkedList<Group> checkoutGroups=new LinkedList<Group>();   //ссылки на зарезервированные группы
    private int countFreeGroup=0;   //количество свободных групп
    private int sizeOutputElements; //количесвто элементов, выводимых из одной группы на обработку

    /**
     * Конструктор инициализирует количество элементов,
     * выводимых из одной группы на обработку.
     * @param sizeOutputElements количество элементов выводимых на обработку из группы.
     */
    public ElementGroupsStorage(int sizeOutputElements){
        this.sizeOutputElements=sizeOutputElements;
    }

    /**
     * Метод осуществляет добавление элемента
     * в хранилище в соответствующую группу. Если группа не существует,
     * то производится создание соответствующей группы.
     * @param e новый элемент.
     */
    public synchronized void insertElement(Element e){
        if(groups.keySet().contains(e.idGroup)) {
            //добавление поступившего элемента в сущ. группу
            groups.get(e.idGroup).insert(e);
        } else{
            //создание группы для элемента
            Group newGroup=new Group(e.idGroup,sizeOutputElements);
            groups.put(newGroup.getId(),newGroup);
            //группа помечается как свободная для резервирования
            freeGroups.add(newGroup);
            countFreeGroup++;
            //элемент помещается в созданную группу
            groups.get(e.idGroup).insert(e);
            //ожидающие свободной группы потоки пробуждаются
            notifyAll();
        }
    }

    /**
     * Метод производит резервирование группы для запросившего
     * потока обработки и возвращает соответствующую группу в поток.
     * Если нет свободных групп, поток ожидает освобождения одной из них.
     * @return Группа элементов зарезервированная для потока.
     * @throws InterruptedException
     */
    public synchronized Group checkoutGroup() throws InterruptedException{
        while(true) {
            //свободная группа-есть
            if (countFreeGroup > 0) {
                //вызывется первая свободная группа
                Group freeGroup=freeGroups.removeFirst();
                //группа резервируется
                checkoutGroups.add(freeGroup);
                //уменьшается счетки свободных групп
                countFreeGroup--;
                //возвращается запросившему потоку
                return freeGroup;
            //свободной группы нет
            } else {
                //если нет совободных групп-поток ожидает
                wait();
            }
        }
    }

    /**
     * Метод осуществляет возвращение потоком зарезервированной группы.
     * Группа становиться доступной для резервирования вновь.
     * @param group возвращаемая группа.
     */
    public synchronized void checkInGroup(Group group){
        //возвращаемая группа присутствует в зарезервированных
        if(checkoutGroups.remove(group)) {
            //возвращение группу в незанятые
            freeGroups.addLast(group);
            //увеличения счетка свободных групп
            countFreeGroup++;
            //пробуждение ожидающих группу потоков
            notifyAll();
        }
    }
}
