package com.maxim.testjunior.generator;

import com.maxim.testjunior.elements.Element;

import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Arrays;

/**
 * Класс является генератором элементов. Изначально определяется количество
 * групп элементов и количество элементов в группах. В дальнейшем класс
 * предоставляет возможность генерации элементов из случайно выбранных групп в случайном порядке для
 * последующего заполнения очереди.
 */
public class GeneratorElements{

    private int countGroups;
    private int countEl;
    private Map<Integer,Queue<Integer>> shuffleIdGroups; //группы с id элементов (ITEM_ID) в случайном порядке
    private List<Integer> groupIds;                      // id групп (GROUP_ID)
    private static Random RND=new Random();

    /**
     * Конструктор осуществляет генерацию id элементов для заданного количества групп в
     * виде очередей, содержащих id в случайном порядке.
     * @param countGroups количество групп,
     * @param countEl количество элементов в группах.
     */
    public GeneratorElements(int countGroups, int countEl) {
        this.countEl = countEl;
        this.countGroups = countGroups;

        //генерация идентификаторов элементов в группах
        shuffleIdGroups = new HashMap<Integer, Queue<Integer>>();
        Integer[] ids = new Integer[this.countEl];
        for (int j = 0; j < this.countEl; j++)
            ids[j] = j;
        //случайное изменение порядка элементов и внесение в харнилище групп
        for (int i = 0; i < this.countGroups; i++) {
            List<Integer> shuffleId = new LinkedList<Integer>(Arrays.asList(ids));
            Collections.shuffle(shuffleId,RND);
            shuffleIdGroups.put(i, (Queue<Integer>) shuffleId);
        }
        //заполнение иентификаторов групп
        Integer[] gIds = new Integer[this.countGroups];
        for (int i = 0; i < this.countGroups; i++) {
            gIds[i] = i;
        }
        groupIds=new ArrayList<Integer>(Arrays.asList(gIds));
    }

    /**
     * Метод создает элемент с случайным id из случайно выбранной группы
     * с помощью очередей, созданных для групп во время инициализации объекта в конструкторе.
     * @return Cлучайно выбранный элемент из случайной группы.
     */
    public Element generateElement(){
        //случайный выбор идентификатора группы
        int randomGroupId=groupIds.get(RND.nextInt(countGroups));
        //получение идентификаторов элементов в выбранной группе
        Queue<Integer> idElements= shuffleIdGroups.get(randomGroupId);
        //случайный выбор иднетификатора элемента из группы
        int randomIdElement=idElements.remove();

        //если группа полсе получения очередного элемента пуста
        if(idElements.isEmpty()){
            //удаление группы из хранилища
            shuffleIdGroups.remove(randomGroupId);
            //удаление идентификатора группы
            groupIds.remove((Object)randomGroupId);
            //измнение счетчика доступных групп
            countGroups--;
        }

        //создание и возвращение элемента с случайными идентификаторами
        return new Element(randomGroupId,randomIdElement);
    }

    /**
     * Возвращает текущее количество групп элементов. Явялется акутальным так как
     * при выводе всех элементов группа удаляется.
     * @return Текущее количество групп.
     */
    public int getCurCountGroup(){return countGroups;}
}