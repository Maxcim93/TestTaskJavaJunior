package com.maxim.testjunior.queueprocessing;

import com.maxim.testjunior.elements.Element;
import com.maxim.testjunior.generator.SourceElements;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by Максим on 30.08.2016.
 */
public class SourceElementsTest {
    @Test
    public void testGenerateElement() throws Exception {
        //инициализация ожидаемых элементов
       /* Element[][] expectedElements=new Element[][]
            {{new Element(0,0),new Element(0,1),new Element(0,2)},
            {new Element(1,0),new Element(1,1),new Element(1,2)},
            {new Element(2,0),new Element(2,1),new Element(2,2)}};

        Element[][] getElements=new Element[3][3];

        //генерация элементов
        SourceElements.Generator generator =new SourceElements.Generator(3,3);
        while(generator.curCountGroup()>0) {
            Element element = generator.generateElement();
            getElements[element.idGroup][element.idEl]=element;
        }

        //сравнение полученных результатов с ожидаемыми
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                Assert.assertEquals(expectedElements[i][j],getElements[i][j]);
        /*Assert.assertArrayEquals(expectedElements[1],getElements[1]);
        Assert.assertArrayEquals(expectedElements[2],getElements[2]);*/
    }

}