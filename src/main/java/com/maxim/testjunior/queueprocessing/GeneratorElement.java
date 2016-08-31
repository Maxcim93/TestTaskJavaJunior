package com.maxim.testjunior.queueprocessing;

import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Максим on 30.08.2016.
 */
public class GeneratorElement extends Thread implements Runnable{
    public static class Generator{
        private int countGroup;
        private int countEl;
        private Map<Integer,Queue<Integer>> shuffleIdGroup;
        private List<Integer> groupId;

        public Generator(int countGroup, int countEl) {
            this.countEl = countEl;
            this.countGroup = countGroup;

            //заполнение групп индентификаторами элементов в случайном порядке
            shuffleIdGroup = new HashMap<Integer, Queue<Integer>>();
            Integer[] id = new Integer[this.countEl];
            for (int j = 0; j < this.countEl; j++)
                id[j] = j;
            for (int i = 0; i < this.countGroup; i++) {
                List<Integer> shuffleId = new LinkedList<Integer>(Arrays.asList(id));
                Collections.shuffle(shuffleId,RND);
                shuffleIdGroup.put(i, (Queue<Integer>) shuffleId);
            }
            //заполнение массива иентификаторами групп
            Integer[] gId = new Integer[this.countGroup];
            for (int i = 0; i < this.countGroup; i++) {
                gId[i] = i;
            }
            groupId=new ArrayList<Integer>(Arrays.asList(gId));
        }
        Element generateElement(){
            int randomGroupId=groupId.get(RND.nextInt(countGroup));
            Queue<Integer> idElements=shuffleIdGroup.get(randomGroupId);
            int randomIdElement=idElements.remove();

            if(idElements.isEmpty()){
                shuffleIdGroup.remove(randomGroupId);
                groupId.remove((Object)randomGroupId);
                countGroup--;
            }

            return new Element(randomGroupId,randomIdElement);
        }

        public int curCountGroup(){return countGroup;}
    }

    //private BlockingQueue<Element> queue;
    BlockingQueue<Element> elements;
    private static Random RND=new Random();
    private Generator generator;

    public GeneratorElement(BlockingQueue<Element> elements,int countGroup, int countEl){
        this.elements=elements;
        this.generator=new Generator(countGroup,countEl);

        //старт
        this.start();
    }

    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                while(generator.countGroup>0)
                    elements.put(generator.generateElement());
                Thread.currentThread().interrupt();
            }
        }catch(InterruptedException e){
            System.out.println("Generator is stopped");
        }
    }


}
