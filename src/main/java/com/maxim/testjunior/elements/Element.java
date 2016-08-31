package com.maxim.testjunior.elements;

/**
 * Класс описывающий элемент, характеризующийся
 * id элемента (ITEM_ID) и id группы (GROUP_ID) к которой она относится.
 */
public class Element implements Comparable<Element>{
    public final int idEl;//id элемента (ITEM_ID)
    public final int idGroup;// id группы (GROUP_ID)

    /**
     * Конструктор инициализирует
     * id элемента (ITEM_ID) и id группы (GROUP_ID)
     * @param idGroup id группы (GROUP_ID)
     * @param idEl id элемента (ITEM_ID)
     */
    public Element(int idGroup,int idEl){
        this.idEl=idEl;
        this.idGroup=idGroup;
    }

    /**
     * Метод явялющийся частью интерфейса
     * @see Comparable ,
     * осуществляет сравнение текущего элемента с переданным
     * по идентификаторам элементов (ITEM_ID)
     * @param e элемент для сравнения
     * @return -1 если id переданного элемента меньше,
     * 1 если id переданного элемента больше,
     * 0 если id равны.
     */
    public int compareTo(Element e){
        return (this.idEl<e.idEl)? -1:(this.idEl>e.idEl? 1:0);
    }

    /**
     * Метод возвращает идентификатор элемента(ITEM_ID)
     * и идентификатор группы (GROUP_ID) для упрощения вывода информации об
     * элементе
     * @return строка, содеражащая id элемента и id группы
     */
    @Override
    public String toString(){
        return "Group: "+idGroup+" Id: "+idEl;
    }
}
