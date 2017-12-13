/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GertLehmann
 */
public class DrawTest {
    
    public DrawTest() {
    }
    
    @Before
    public void setUp() {
    }

    
    
    @Test
    public void testTegnTag() {
        System.out.println("tegnTag");
        int width = 0;
        int height = 0;
        Draw instance = new DrawImpl();
        String expResult = "";
        String result = instance.tegnTag(width, height);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRemme() {
        System.out.println("remme");
        Draw instance = new DrawImpl();
        String expResult = "";
        String result = instance.remme();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testStolper() {
        System.out.println("stolper");
        Draw instance = new DrawImpl();
        String expResult = "";
        String result = instance.stolper();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSpaer() {
        System.out.println("spaer");
        Draw instance = new DrawImpl();
        String expResult = "";
        String result = instance.spaer();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testKryds() {
        System.out.println("kryds");
        Draw instance = new DrawImpl();
        String expResult = "";
        String result = instance.kryds();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    public class DrawImpl implements Draw {

        public String tegnTag(int width, int height) {
            return "";
        }

        public String remme() {
            return "";
        }

        public String stolper() {
            return "";
        }

        public String spaer() {
            return "";
        }

        public String kryds() {
            return "";
        }
    }
    
}
