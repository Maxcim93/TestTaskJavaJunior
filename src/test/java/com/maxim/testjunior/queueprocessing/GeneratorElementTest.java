package com.maxim.testjunior.queueprocessing;

import org.junit.Assert;
import org.junit.Test;
/**
 * Created by Максим on 30.08.2016.
 */
public class GeneratorElementTest {
    @Test
    public void testGenerateElement() throws Exception {
        //инициализация ожидаемых элементов
        Element[][] expectedElements=new Element[][]
            {{new Element(0,0),new Element(0,1),new Element(0,2)},
            {new Element(1,0),new Element(1,1),new Element(1,2)},
            {new Element(2,0),new Element(2,1),new Element(2,2)}};

        Element[][] getElements=new Element[3][3];

        //генерация элементов
        GeneratorElement.Generator generator =new GeneratorElement.Generator(3,3);
        while(generator.curCountGroup()>0) {
            Element element = generator.generateElement();
            getElements[element.idGroup][element.idEl]=element;
        }

        //сравнение полученных результатов с ожидаемыми
        Assert.assertArrayEquals(expectedElements[0],getElements[0]);
        Assert.assertArrayEquals(expectedElements[1],getElements[1]);
        Assert.assertArrayEquals(expectedElements[2],getElements[2]);
    }

}