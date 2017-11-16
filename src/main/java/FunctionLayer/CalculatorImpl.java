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
        totalBom.mergeBom(calculateRemme(length));
        totalBom.mergeBom(calculateSpær(length, width));
        totalBom.mergeBom(calculateStolper(length, width));
        totalBom.mergeBom(calculateTagplader(length, width));
        totalBom.mergeBom(calculateHulbånd(width));
        totalBom.mergeBom(calculateBeslag(length));
        totalBom.mergeBom(calculateSkruerStern(length, width));
        totalBom.mergeBom(calculateSkruerBeslag(length, width));
        totalBom.mergeBom(calculateBræddebolt(length));
        totalBom.mergeBom(calculateFirkantskiver(length));

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
    public BillOfMaterials calculateRemme(double length) {
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

    @Override
    public BillOfMaterials calculateSkruerStern(double length, double width) {
        BillOfMaterials bom = new BillOfMaterials();
        int brætLængdeTotal = 0;
        int skrueAntal = 0;
        int skruePakkerAntal = 0;
        BillOfMaterials stern = calculateStern(length, width);
        for (LineItem li: stern.getBomList()) {
            brætLængdeTotal += li.getLength();
        }
        skrueAntal = brætLængdeTotal/25; // 1 skrue pr. 25 centimeter
        skruePakkerAntal = (skrueAntal/200)+1; // hvis antallet af skruer passer præcist, købes en ekstra pakke
        bom.addLineItem(new LineItem("4,5 x 60 mm. skruer 200 stk.", 0, skruePakkerAntal, "pakke(r)", "1 skrue pr. 25 centimeter", 209));
        return bom;
    }

    @Override
    public BillOfMaterials calculateSkruerBeslag(double length, double width) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        BillOfMaterials bom = new BillOfMaterials();
        int skruePakkerAntal = 0;
        BillOfMaterials hulbånd = calculateHulbånd(width);
        BillOfMaterials beslag = calculateBeslag(length);
        int beslagStk = 0;
        int hilbåndStk = 0;
        for (LineItem li: beslag.getBomList()) {
            beslagStk += li.getQuantity();
        }
        for (LineItem li: hulbånd.getBomList()) {
            hilbåndStk += li.getQuantity();
        }
        //jeg regner med at der går 1 pakke til 30 stk. beslag (højre+venstre)
        skruePakkerAntal += (beslagStk/30) +1;
        // jeg regner med at der går 1 pakke pr. rulle hulbånd.
        skruePakkerAntal += hilbåndStk;
        bom.addLineItem(new LineItem("4,0 x 50 mm. beslagskruer 250 stk.", 0, skruePakkerAntal, "pakke(r)", "1 pakke pr. rulle hulbånd.", 239));
        return bom;
    }

    @Override
    public BillOfMaterials calculateBræddebolt(double length) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        BillOfMaterials bom = new BillOfMaterials();
        int bræddebolte = 0;
        BillOfMaterials rem = calculateRemme(length);
        for (LineItem li: rem.getBomList()) {
            bræddebolte += (li.getQuantity() * 9);//jeg regner med 9 stk, bræddebolte pr. rem.
        }
        bom.addLineItem(new LineItem("bræddebolte 10*120 mm.", 0, bræddebolte, "stk.", "9 stk, bræddebolte pr. rem.", 32.83));
        return bom;
    }

    @Override
    public BillOfMaterials calculateFirkantskiver(double length) {
        BillOfMaterials bom = new BillOfMaterials();
        int firkantskiver = 0;
        BillOfMaterials rem = calculateRemme(length);
        for (LineItem li: rem.getBomList()) {
            firkantskiver += (li.getQuantity() * 6);//jeg regner med 6 stk, firkantskiver pr. rem.
        }
        bom.addLineItem(new LineItem("firkantskiver 40x40x11 mm.", 0, firkantskiver, "stk.", "6 stk, firkantskiver pr. rem.", 9.41));
        return bom;
    }

    
}
