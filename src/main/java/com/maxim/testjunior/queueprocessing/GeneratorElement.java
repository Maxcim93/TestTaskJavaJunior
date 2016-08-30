package com.maxim.testjunior.queueprocessing;

import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Максим on 30.08.2016.
 */
public class GeneratorElement implements Runnable{
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
            for (int i = 0; i < countGroup; i++) {
                Integer[] id = new Integer[countEl];
                for (int j = 0; j < countEl; j++)
                    id[j] = j;
                List<Integer> shuffleId = new LinkedList<Integer>(Arrays.asList(id));
                Collections.shuffle(shuffleId,RND);
                shuffleIdGroup.put(i, (Queue<Integer>) shuffleId);
            }
            //заполнение массива иентификаторами групп
            Integer[] gId = new Integer[countGroup];
            for (int i = 0; i < countGroup; i++) {
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

    private BlockingQueue<Element> queue;
    private int countGroup;
    private int countEl;
    private static Random RND=new Random();
    private Generator generator;

    public GeneratorElement(BlockingQueue<Element> queue,int countGroup, int countEl){
        this.queue=queue;
        this.countGroup=countGroup;
        this.countEl=countEl;
        this.generator=new Generator(countGroup,countEl);
    }

    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                while(generator.countGroup>0)
                    queue.put(generator.generateElement());
            }
        }catch(InterruptedException e){
            System.out.println("Generator is stopped");
        }
    }


}
