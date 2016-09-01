package com.maxim.testjunior.queueprocessing;

import com.maxim.testjunior.elements.Element;
import com.maxim.testjunior.generator.GeneratorElements;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Maksim Mekh
 * @version 1.0
 */
public class SourceElementsTest {
    /**
     * Осущестялется тестирование корректности функционирования генератора элементов
     * {@link GeneratorElements}
     * @throws Exception
     */
    @Test
    public void testGenerateElement() throws Exception {
        //инициализация ожидаемых элементов
        Element[][] expectedElements=new Element[][]
            {{new Element(0,0),new Element(0,1),new Element(0,2)},
            {new Element(1,0),new Element(1,1),new Element(1,2)},
            {new Element(2,0),new Element(2,1),new Element(2,2)}};

        Element[][] getElements=new Element[3][3];

        //генерация элементов
        GeneratorElements generator =new GeneratorElements(3,3);
        while(generator.getCurCountGroup()>0) {
            Element element = generator.generateElement();
            getElements[element.idGroup][element.idEl]=element;
        }

        //сравнение полученных результатов с ожидаемыми
        Assert.assertArrayEquals(expectedElements, getElements);
    }

}