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
    public BillOfMaterials bomCalculator(double length, double width, double height, String tagtype, double skurLength, double skurWidth) {
        BillOfMaterials totalBom = new BillOfMaterials();

        if (tagtype.equals("fladt") ) {
                totalBom.mergeBom(bomCalculatorFladtTag(length, width, height, skurLength, skurWidth));
        } else {
                totalBom.mergeBom(bomCalculatorSkråtTag(length, width, height));
        }
        if (skurLength != 0 && skurWidth != 0) {
                totalBom.mergeBom(bomCalculatorSkur(length, width, height, skurLength, skurWidth));
        }
     
        
        return totalBom;
    }

    @Override
    public BillOfMaterials bomCalculatorSkråtTag(double length, double width, double height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BillOfMaterials bomCalculatorSkur(double length, double width, double height, double skurLength, double skurWidth) {
        BillOfMaterials totalBom = new BillOfMaterials();
            totalBom.mergeBom(calculateLøsholter(skurLength, skurWidth));
        return totalBom;

    }

    @Override
    public BillOfMaterials bomCalculatorFladtTag(double length, double width, double height, double skurLength, double skurWidth) {
        BillOfMaterials totalBom = new BillOfMaterials();

        totalBom.mergeBom(calculateStern(length, width));
        totalBom.mergeBom(calculateRemme(length));
        totalBom.mergeBom(calculateSpær(length, width));
        totalBom.mergeBom(calculateStolper(length, width, height, skurLength, skurWidth));
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
    public BillOfMaterials calculateStolper(double length, double width, double height, double skurLength, double skurWidth) {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (((int) length) / 240) * 2 + 2;
        if (skurLength != 0 && skurWidth != 0) {
            if (skurWidth > 400) {
                quantity += 5;
            } else {
                quantity += 3;
            }
            if (skurWidth != width-30) {
                quantity += 2;
            }
        }
        int newHeight = (int) height + 90;
        if (newHeight % 60 != 0) {
            newHeight += 30;
        }
        double[] price = {83.85, 100.63, 117.39, 134.16};
        int index = (newHeight - 300) / 60;
        bom.addLineItem(new LineItem("97x97mm trykimp. stolpe", newHeight, quantity, "stk", "Stolper nedgraves 90cm i jord.", price[index]));
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
        bom.addLineItem(new LineItem("Hulbånd 1x20mm 10meter", 0, 2, "ruller", "Til vindkryds på spær", 189.00));

        return bom;
    }

    @Override
    public BillOfMaterials calculateBeslag(double length) {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (int) (Math.ceil(length / 55.0));
        bom.addLineItem(new LineItem("Universal 190mm højre", 0, quantity, "stk", "Til montering af spær på rem", 21.95));
        bom.addLineItem(new LineItem("Universal 190mm venstre", 0, quantity, "stk", "Til montering af spær på rem", 21.95));
        return bom;
    }

    @Override
    public BillOfMaterials calculateSkruerStern(double length, double width) {
        BillOfMaterials bom = new BillOfMaterials();
        int brætLængdeTotal = 0;
        int skrueAntal = 0;
        int skruePakkerAntal = 0;
        BillOfMaterials stern = calculateStern(length, width);
        for (LineItem li : stern.getBomList()) {
            brætLængdeTotal += li.getLength();
        }
        skrueAntal = brætLængdeTotal / 25; // 1 skrue pr. 25 centimeter
        skruePakkerAntal = (skrueAntal / 200) + 1; // hvis antallet af skruer passer præcist, købes en ekstra pakke
        bom.addLineItem(new LineItem("4,5 x 60 mm. skruer 200 stk.", 0, skruePakkerAntal, "pakker", "Til montering af stern & vandbrædt", 209));
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
        for (LineItem li : beslag.getBomList()) {
            beslagStk += li.getQuantity();
        }
        for (LineItem li : hulbånd.getBomList()) {
            hilbåndStk += li.getQuantity();
        }
        //jeg regner med at der går 1 pakke til 30 stk. beslag (højre+venstre)
        skruePakkerAntal += (beslagStk / 30) + 1;
        // jeg regner med at der går 1 pakke pr. rulle hulbånd.
        skruePakkerAntal += hilbåndStk;
        bom.addLineItem(new LineItem("4,0 x 50 mm. beslagskruer 250 stk.", 0, skruePakkerAntal, "pakker", "Til montering af universalbeslag + hulbånd", 239));
        return bom;
    }

    @Override
    public BillOfMaterials calculateBræddebolt(double length) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        BillOfMaterials bom = new BillOfMaterials();
        int bræddebolte = 0;
        BillOfMaterials rem = calculateRemme(length);
        for (LineItem li : rem.getBomList()) {
            bræddebolte += (li.getQuantity() * 9);//jeg regner med 9 stk, bræddebolte pr. rem.
        }
        bom.addLineItem(new LineItem("bræddebolte 10*120 mm.", 0, bræddebolte, "stk", "Til montering af rem på stolper", 32.83));
        return bom;
    }

    @Override
    public BillOfMaterials calculateFirkantskiver(double length) {
        BillOfMaterials bom = new BillOfMaterials();
        int firkantskiver = 0;
        BillOfMaterials rem = calculateRemme(length);
        for (LineItem li : rem.getBomList()) {
            firkantskiver += (li.getQuantity() * 6);//jeg regner med 6 stk, firkantskiver pr. rem.
        }
        bom.addLineItem(new LineItem("firkantskiver 40x40x11 mm.", 0, firkantskiver, "stk", "Til montering af rem på stolpers", 9.41));
        return bom;
    }

    @Override
    public BillOfMaterials calculateLøsholter(double skurLength, double skurWidth) {
        BillOfMaterials bom = new BillOfMaterials();
        double[] price = {80.91, 94.40, 107.88, 121.36, 134.85, 148.34, 161.83, 175.30, 188.79, 202.28, 215.76, 229.25, 242.73};
        
        int reglarLength = (int) skurLength;
        int antalSide = 4;
        if (reglarLength > 540) {
            if (reglarLength%60 != 0) {
                reglarLength += 30;
            }
            reglarLength = reglarLength/2;
            antalSide = 8;
        }
        int indexSide = (reglarLength - 180) / 30;
        bom.addLineItem(new LineItem("45x95 Reglar ubh.", reglarLength, antalSide, "stk", "Løsholter i siderne af skur", price[indexSide]));
        
        int reglarWidth = (int) skurWidth;
        int antalGavl = 6;
        if (reglarWidth > 540) {
                        if (reglarWidth%60 != 0) {
                reglarWidth += 30;
            }
            reglarWidth = reglarWidth/2;
            antalGavl = 12;
        }
        int indexGavl = (reglarWidth - 180) / 30;
        bom.addLineItem(new LineItem("45x95 Reglar ubh.", reglarWidth, antalGavl, "stk", "Løsholter i gavle af skur", price[indexGavl]));
        
        return bom;
    }
    
    
    

}
