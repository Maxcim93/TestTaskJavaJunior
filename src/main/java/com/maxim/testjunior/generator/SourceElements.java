package com.maxim.testjunior.generator;

import com.maxim.testjunior.elements.Element;

import java.util.Random;
import java.util.Map;
import java.util.Queue;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Класс предназначен для заполнения исходной очереди случайными элементами
 * @see com.maxim.testjunior.elements.Element .
 */
public class SourceElements extends Thread{
    /**
     * Класс является генератором элементов. Изначально определяется количество
     * групп элементов и количество элементов в группах. В дальнейшем класс
     * предоставляет возможность генерации  элементов заданных групп в случайном порядке для
     * последующего заполнения очереди.
     */
    public static class GeneratorElements{

        private int countGroups;
        private int countEl;
        private Map<Integer,Queue<Integer>> shuffleIdGroups;//группы с id элементов (ITEM_ID) в случайном порядке
        private List<Integer> groupIds;// id групп (GROUP_ID)
        private static Random RND=new Random();

        /**
         * Конструктор осуществляет генерацию id элементов для заданного количества групп в
         * виде очередей, содержащих id в случайном порядке.
         * @param countGroups количество групп
         * @param countEl количество элементов в группах
         */
        public GeneratorElements(int countGroups, int countEl) {
            this.countEl = countEl;
            this.countGroups = countGroups;

            //заполнение групп индентификаторами элементов в случайном порядке
            shuffleIdGroups = new HashMap<Integer, Queue<Integer>>();
            Integer[] ids = new Integer[this.countEl];
            for (int j = 0; j < this.countEl; j++)
                ids[j] = j;
            for (int i = 0; i < this.countGroups; i++) {
                List<Integer> shuffleId = new LinkedList<Integer>(Arrays.asList(ids));
                Collections.shuffle(shuffleId,RND);
                shuffleIdGroups.put(i, (Queue<Integer>) shuffleId);
            }
            //заполнение массива иентификаторов групп
            Integer[] gIds = new Integer[this.countGroups];
            for (int i = 0; i < this.countGroups; i++) {
                gIds[i] = i;
            }
            groupIds=new ArrayList<Integer>(Arrays.asList(gIds));
        }

        /**
         * Метод создает элемент с случайным id из случайно выбранной группы
         * с помощью очередей, созданных для групп во время инициализации объекта в конструкторе.
         * {@link GeneratorElements(int, int)}
         * @return случайно выбранный элемент из случайной группы.
         */
        public Element generateElement(){
            int randomGroupId=groupIds.get(RND.nextInt(countGroups));
            Queue<Integer> idElements= shuffleIdGroups.get(randomGroupId);
            int randomIdElement=idElements.remove();

            if(idElements.isEmpty()){
                shuffleIdGroups.remove(randomGroupId);
                groupIds.remove((Object)randomGroupId);
                countGroups--;
            }

            return new Element(randomGroupId,randomIdElement);
        }

        /**
         * Возвращает текущее количество групп элементов. Явялется акутальным так как
         * при выводе всех элементов группа удаляется.
         * @return текущее количество групп
         */
        public int getCurCountGroup(){return countGroups;}
    }

    private BlockingQueue<Element> elementsQueue;
    private GeneratorElements generator;

    /**
     * Конструктор инициализирует очередь для вывода элементов,
     * количество групп, количество элементов в группах.
     * @param elementsQueue очередь для вывода элементов
     * @param countGroup количество групп
     * @param countEl количество элементов
     */
    public SourceElements(BlockingQueue<Element> elementsQueue, int countGroup, int countEl){
        this.elementsQueue=elementsQueue;
        this.generator=new GeneratorElements(countGroup,countEl);

        //запуск генерации элементов в очередь в отдельном потоке
        this.start();
    }

    /**
     * Выполняет в потоке генерацию элементов в случайном порядке с помощью класса
     * @see GeneratorElements и помещает элемнты в исходную очередь.
     * По выводу всех элементов из всех групп поток завершается.
     */
    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                while(generator.getCurCountGroup() > 0)
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
