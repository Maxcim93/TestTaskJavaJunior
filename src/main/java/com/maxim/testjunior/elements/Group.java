package com.maxim.testjunior.elements;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Класс предоставлет хранилище группы элементов.
 * Пердназначен для вывода элементов группы в строгой последовательсности.
 * Новые элементы добавляются в группу и сортируются в соответствии с id (ITEM_ID), при выводе проверяется
 * наличие слеудющего по порядку id элемента, если элемента с следующим по порядку id - нет,вывод блокируется.
 * Вывод элементов осуществляется начиная с нулевого id (ITEM_ID = 0).
 */
public class Group {
    private int expectedId=0;   //id (ITEM_ID) следующего по порядку элемента в группе
    private int idGroup;        //id группы (GROUP_ID)
    private Queue<Element> elements= //сортирующая очередь для хранения поступающих элементов
            new PriorityQueue<Element>();
    private int sizeOutputBlock;    //количесвто элементов, выводимых подряд из группы на обработку

    /**
     * Конструктор инициализирует идентификатор группы (ID_GROUP) и
     * количество элементов, выводимых на обработку подряд из группы .
     * @param idGroup идентификатор группы.
     */
    public Group(int idGroup,int sizeOutputBlock){
        this.idGroup=idGroup;
        this.sizeOutputBlock=sizeOutputBlock;
    }

    /**
     * Возвращает идентификатор.
     * @return Идентификатор группы (ID_GROUP).
     */
    public int getId(){return idGroup;}

    /**
     * Метод добавляет элемент в группу.
     * При добавление все элементе автоматически сортируются. В случае добавление
     * элемента с несоответствующим идентификатором вызывается исключение.
     * @param e новый элемент.
     * @throws IllegalArgumentException исклчючение возвращаемое в случае несоответствия идентификатров групп.
     */
    public synchronized void insert(Element e){
        if(e.idGroup!=this.idGroup)
            throw new IllegalArgumentException("This elements from another group (e.idGroup!=this.idGroup)");
        elements.offer(e);
    }

    /**
     * Метод возвращает на обработку заданной длины последовательность элементов из группы
     * в порядке роста id (ITEM_ID). При формировании последовательности осуществляется
     * проверка на наличие слеюудщего по порядку id элемента, если элемента нет,
     * то возвращается последовательность меньшей длины или пустая коллекция.
     * @return Последовательность элементов в порядке роста id (ITEM_ID) из группы для обработки.
     */
    public synchronized List<Element> getElements(){
        List<Element> outElements=new LinkedList<Element>();//элементы для вывода на обработку
        int countAddedEl=0;//количество отобранных на обработку элементов
        while(countAddedEl<sizeOutputBlock) {
            //проверка на присутствие следующего по порядку элемента в группе
            if (elements.peek() != null && elements.peek().idEl == expectedId) {
                //добавление к выводу
                outElements.add(elements.poll());
                //изменение следующего в очереди id элемента
                expectedId++;
                //увеличение количества отобранных элементов
                countAddedEl++;
            //выход из цикла вывода в случае отсутствия элемента с id следующего по порядку
            } else {
                //выход из цикла добавления на обработку
                break;
            }
        }
        return outElements;
    }
}
