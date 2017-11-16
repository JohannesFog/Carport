package FunctionLayer;

import java.sql.Array;
import java.util.ArrayList;

/**
 *
 * @author GertLehmann
 */
public class CalculatorImpl implements Calculator {

    @Override
    public double calculatePrice(BillOfMaterials bom) {
        ArrayList<LineItem> list = bom.getBomList();
        double totalPrice = 0;
        for (LineItem lineItem : list) {
            totalPrice += lineItem.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public BillOfMaterials bomCalculator(double length, double width) {
        BillOfMaterials totalBom = new BillOfMaterials();

        totalBom.mergeBom(calculateStern(length, width));
        totalBom.mergeBom(calculateRemme(length, width));
        totalBom.mergeBom(calculateSpær(length, width));
        totalBom.mergeBom(calculateStolper(length, width));
        totalBom.mergeBom(calculateTagplader(length, width));
        totalBom.mergeBom(calculateHulbånd(width));
        totalBom.mergeBom(calculateBeslag(length));

        return totalBom;
    }

    @Override
    public BillOfMaterials calculateStolper(double length, double width) {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (((int) length) / 200) * 2;
        bom.addLineItem(new LineItem("97x97mm trykimp. stolpe", 300, quantity, "stk", "Stolper nedgraves 90cm i jord.", 83.85));
        return bom;
    }

    @Override
    public BillOfMaterials calculateTagplader(double length, double width) {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (int) Math.ceil(width / 100);
        bom.addLineItem(new LineItem("Plastmo Ecolite blåtonet", 600, quantity, "stk", "Tagplader monteres på spær", 330.00));
        if (length > 600) {
            bom.addLineItem(new LineItem("Plastmo Ecolite blåtonet", 360, quantity, "stk", "Tagplader monteres på spær", 139.00));
        }
        return bom;
    }

    @Override
    public BillOfMaterials calculateRemme(double length, double width) {
        BillOfMaterials bom = new BillOfMaterials();
        int newLength = (int) length;
        if (newLength < 300) {
            newLength = 300;
        }
        if (newLength % 60 != 0) {
            newLength += 30;
        }
        double[] price = {113.85, 136.63, 159.39, 182.16, 204.93, 287.7, 316.48, 345.24};
        int index = (newLength - 300) / 60;

        bom.addLineItem(new LineItem("45x95mm spærtræ ubh.", newLength, 2, "stk", "Remme i sider, sadles ned i stolper", price[index]));
        return bom;
    }

    @Override
    public BillOfMaterials calculateStern(double length, double width) {
        BillOfMaterials bom = new BillOfMaterials();
        bom.addLineItem(new LineItem("25x200mm trykimp. brædt", 360, 4, "stk", "Understern brædder til for og bag ende", 183.43));
        bom.addLineItem(new LineItem("25x200mm trykimp. brædt", 540, 4, "stk", "Understern brædder til siderne", 275.13));

        bom.addLineItem(new LineItem("25x125mm trykimp. brædt", 360, 2, "stk", "Overstern brædder til for enden", 107.83));
        bom.addLineItem(new LineItem("25x125mm trykimp. brædt", 540, 4, "stk", "Overstern brædder til siderne", 161.73));

        bom.addLineItem(new LineItem("19x100mm trykimp. brædt", 540, 4, "stk", "Vandbrædt på stern i sider", 69.95));
        bom.addLineItem(new LineItem("19x100mm trykimp. brædt", 360, 2, "stk", "Vandbrædt på stern i for enden", 46.63));

        return bom;
    }

    @Override
    public BillOfMaterials calculateSpær(double length, double width) {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (int) (Math.ceil(length / 55.0));
        int newWidth = (int) width;
        if (newWidth < 300) {
            newWidth = 300;
        }
        if (newWidth % 60 != 0) {
            newWidth += 30;
        }
        double[] price = {113.85, 136.63, 159.39, 182.16, 204.93, 287.7, 316.48, 345.24};
        int index = (newWidth - 300) / 60;
        bom.addLineItem(new LineItem("45x195mm spærtræ ubh.", newWidth, quantity, "stk", "Spær monteres på rem", price[index]));
        return bom;
    }

    @Override
    public BillOfMaterials calculateHulbånd(double width) {
        BillOfMaterials bom = new BillOfMaterials();
        bom.addLineItem(new LineItem("Hulbånd 1x20mm 10meter", 0, 2, "rulle", "Til vindkryds på spær", 189.00));

        return bom;
    }

    @Override
    public BillOfMaterials calculateBeslag(double length) {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (int) (Math.ceil(length / 55.0));
        bom.addLineItem(new LineItem("Universal 190mm højre", 0, quantity, "Stk", "Til montering af spær på rem", 21.95));
        bom.addLineItem(new LineItem("Universal 190mm venstre", 0, quantity, "Stk", "Til montering af spær på rem", 21.95));
        return bom;
    }

}
