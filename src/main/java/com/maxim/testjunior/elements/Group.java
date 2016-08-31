package com.maxim.testjunior.elements;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Класс предоставлет хранилище группы элементов.
 * Пердназначен для вывода элементов группы в строгой последовательсности.
 * В классе реализован функционал по по добавлниею элементов в группу, их сортировке
 * и вывода элементов в сторогой последовательности id (ITEM_ID) начиная с нулевого (ITEM_ID=0).
 */
public class Group {
    private int expectedId=0;//id (ITEM_ID) следующего по порядку элемента в группе
    private int idGroup;//id группы (GROUP_ID)
    private Queue<Element> elements= //очередь для хранения и сортировки поступающих элементов
            new PriorityQueue<Element>();
    private int sizeOutputBlock;//количество элементов для вывода в одном блоке

    /***
     * Конструктор инициализирует идентификатор группы (ID_GROUP) и
     * количество элементов в блоке вывода.
     * @param idGroup идентификатор группы
     */
    public Group(int idGroup,int sizeOutputBlock){
        this.idGroup=idGroup;
        this.sizeOutputBlock=sizeOutputBlock;
    }

    /**
     * Возвращает идентификатор.
     * @return идентификатор группы (ID_GROUP)
     */
    public int getId(){return idGroup;}

    /**
     * Потокобезопасный метод, добавляющий элемент в группу.
     * @param e новый элемент
     */
    public synchronized void insert(Element e){
        elements.offer(e);
    }

    /**
     * Метод потокобезопасный,
     * возвращает последовательность элементов группы заданной длиной
     * в порядке роста id (ITEM_ID) для обработки.
     * @return последовательность элементов группы для обработки.
     */
    public synchronized List<Element> getElements(){
        List<Element> outElements=new LinkedList<Element>();//элементы для вывода на обработку
        int countAddedEl=0;//количество выведенных на обработку элементов
        while(countAddedEl<sizeOutputBlock) {
            //проверка на присутствие следующего по порядку элемента в группе
            if (elements.peek() != null && elements.peek().idEl == expectedId) {
                //вывод элемента
                outElements.add(elements.poll());
                expectedId++;
                countAddedEl++;

            //выход из цикла вывода в случае если элемента следующего по порядку нет
            } else {
                break;
            }
        }
        return outElements;
    }
}
