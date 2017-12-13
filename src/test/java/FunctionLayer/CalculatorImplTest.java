/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionLayer;

import Exceptions.DataMapperException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GertLehmann
 */
public class CalculatorImplTest {
    
    public CalculatorImplTest() {
    }
    
    @Before
    public void setUp() {
    }


//
//   
//    @Test
//    public void testCalculateStolperUdenSkurKortCarport() throws DataMapperException{
//        double length = 240.0;
//        double width = 240.0;
//        double height = 300.0;
//        double skurLength = 0.0;
//        double skurWidth = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        int expResult = 4;
//        BillOfMaterials bom = instance.calculateStolper(length, width, height, skurLength, skurWidth);
//        int result = 0;
//        for (LineItem li : bom.getBomList()) {
//            result += li.getQuantity();
//        }
//        assertEquals(expResult, result);
//    }
//
//    @Test
//    public void testCalculateStolperUdenSkurLangCarport() throws DataMapperException{
//        double length = 720.0;
//        double width = 240.0;
//        double height = 300.0;
//        double skurLength = 0.0;
//        double skurWidth = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        int expResult = 6;
//        BillOfMaterials bom = instance.calculateStolper(length, width, height, skurLength, skurWidth);
//        int result = 0;
//        for (LineItem li : bom.getBomList()) {
//            result += li.getQuantity();
//        }
//        assertEquals(expResult, result);
//    }
//    
//    @Test
//    public void testCalculateStolperMedSkurSammeBreddeSomRemme() throws DataMapperException{
//        double length = 600.0;
//        double width = 360.0;
//        double height = 300.0;
//        double skurLength = 240.0;
//        double skurWidth = 330.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        int expResult = 9;
//        BillOfMaterials bom = instance.calculateStolper(length, width, height, skurLength, skurWidth);
//        int result = 0;
//        for (LineItem li : bom.getBomList()) {
//            result += li.getQuantity();
//        }
//        assertEquals(expResult, result);
//    }
//    
//    @Test
//    public void testCalculateStolperMedSkurSmallereEndRemme() throws DataMapperException{
//        double length = 600.0;
//        double width = 360.0;
//        double height = 300.0;
//        double skurLength = 240.0;
//        double skurWidth = 270.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        int expResult = 10;
//        BillOfMaterials bom = instance.calculateStolper(length, width, height, skurLength, skurWidth);
//        int result = 0;
//        for (LineItem li : bom.getBomList()) {
//            result += li.getQuantity();
//        }
//        assertEquals(expResult, result);
//    }
//    
    
//    @Test
//    public void testCalculateTagplader() {
//        System.out.println("calculateTagplader");
//        double length = 0.0;
//        double width = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateTagplader(length, width);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }

//    @Test
//    public void testCalculateTagMedSten() {
//        System.out.println("calculateTagMedSten");
//        double length = 0.0;
//        double width = 0.0;
//        double hypotenuse = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateTagMedSten(length, width, hypotenuse);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }


//    @Test
//    public void testCalculateRemme() {
//        System.out.println("calculateRemme");
//        double length = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateRemme(length);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateFladtStern() {
//        System.out.println("calculateFladtStern");
//        double length = 0.0;
//        double width = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateFladtStern(length, width);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }

//    @Test
//    public void testCalculateSkråtStern() {
//        System.out.println("calculateSkr\u00e5tStern");
//        double length = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateSkråtStern(length);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateVindskeder() {
//        System.out.println("calculateVindskeder");
//        double hypotenuse = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateVindskeder(hypotenuse);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateFladtSpær() {
//        System.out.println("calculateFladtSp\u00e6r");
//        double length = 0.0;
//        double width = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateFladtSpær(length, width);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateSkråtSpær() {
//        System.out.println("calculateSkr\u00e5tSp\u00e6r");
//        double length = 0.0;
//        double width = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateSkråtSpær(length, width);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateHulbånd() {
//        System.out.println("calculateHulb\u00e5nd");
//        double width = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateHulbånd(width);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateFladtBeslag() {
//        System.out.println("calculateFladtBeslag");
//        double length = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateFladtBeslag(length);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateSkråtBeslag() {
//        System.out.println("calculateSkr\u00e5tBeslag");
//        double length = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateSkråtBeslag(length);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateSkruerStern() {
//        System.out.println("calculateSkruerStern");
//        double length = 0.0;
//        double width = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateSkruerStern(length, width);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateFladtSkruerBeslag() {
//        System.out.println("calculateFladtSkruerBeslag");
//        double length = 0.0;
//        double width = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateFladtSkruerBeslag(length, width);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateBræddebolt() {
//        System.out.println("calculateBr\u00e6ddebolt");
//        double length = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateBræddebolt(length);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateFirkantskiver() {
//        System.out.println("calculateFirkantskiver");
//        double length = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateFirkantskiver(length);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateLøsholter() {
//        System.out.println("calculateL\u00f8sholter");
//        double width = 0.0;
//        double skurLength = 0.0;
//        double skurWidth = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateLøsholter(width, skurLength, skurWidth);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateBeklædningSkur() {
//        System.out.println("calculateBekl\u00e6dningSkur");
//        double height = 0.0;
//        double skurLength = 0.0;
//        double skurWidth = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateBeklædningSkur(height, skurLength, skurWidth);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testCalculateBeklædningGavl() {
//        System.out.println("calculateBekl\u00e6dningGavl");
//        double width = 0.0;
//        double katete = 0.0;
//        CalculatorImpl instance = new CalculatorImpl();
//        BillOfMaterials expResult = null;
//        BillOfMaterials result = instance.calculateBeklædningGavl(width, katete);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//    
}
