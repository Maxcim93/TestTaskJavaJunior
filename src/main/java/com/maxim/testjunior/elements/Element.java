package com.maxim.testjunior.elements;

/**
 * Класс описывает элемент, характеризующийся
 * id элемента (ITEM_ID) и id группы (GROUP_ID), к которой он относится.
 */
public class Element implements Comparable<Element>{
    /**Id элемента (ITEM_ID).*/
    public final int idEl;
    /**Id группы (GROUP_ID).*/
    public final int idGroup;

    /**
     * Конструктор инициализирует
     * id элемента (ITEM_ID) и id группы (GROUP_ID).
     * @param idGroup id группы (GROUP_ID),
     * @param idEl id элемента (ITEM_ID).
     */
    public Element(int idGroup,int idEl){
        this.idEl=idEl;
        this.idGroup=idGroup;
    }

    /**
     * Метод осуществляет сравнение текущего элемента с переданным
     * по идентификаторам (ITEM_ID),если элементы принадлежат к группе
     * с одним идентификатором.
     * @param e элемент для сравнения.
     * @return Возвращает -1 если id переданного элемента меньше,
     * 1 если id переданного элемента больше,
     * 0 если id равны.
     * @throws IllegalArgumentException Исклчючение возвращаемое в случае несоответствия идентификатров групп.
     */
    public int compareTo(Element e){
        if(e.idGroup!=this.idGroup)
            throw new IllegalArgumentException("This elements from another group (e.idGroup!=this.idGroup)");
        return (this.idEl<e.idEl)? -1:(this.idEl>e.idEl? 1:0);
    }

    /**
     * Метод возвращает строку, содержащую
     * идентификатор элемента (ITEM_ID) и
     * идентификатор группы (GROUP_ID).
     * @return Cтрока, содеражащая id элемента и id группы.
     */
    @Override
    public String toString(){
        return "Group: "+idGroup+" Id: "+idEl;
    }

    /**
     * Элементы эквиваленты при наличии идентичных ITEM_ID и GROUP_ID.
     * @param e - объект для проверки идентичности.
     * @return True - объекты идентичны, false - нет.
     */
    @Override
    public boolean equals(Object e){
        Element el=(Element)e;
        return (this.idEl==el.idEl && this.idGroup==el.idGroup)? true:false;
    }
}
