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

        if (tagtype.equals("fladt")) {
            totalBom.mergeBom(bomCalculatorFladtTag(length, width, height, skurLength, skurWidth));
        } else {
            totalBom.mergeBom(bomCalculatorSkråtTag(length, width, height, skurLength, skurWidth));
        }
        if (skurLength != 0 && skurWidth != 0) {
            totalBom.mergeBom(bomCalculatorSkur(length, width, height, skurLength, skurWidth));
        }

        return totalBom;
    }

    @Override
    public BillOfMaterials bomCalculatorSkråtTag(double length, double width, double height, double skurLength, double skurWidth) {
        BillOfMaterials totalBom = new BillOfMaterials();

        totalBom.mergeBom(calculateRemme(length));
        totalBom.mergeBom(calculateStolper(length, width, height, skurLength, skurWidth));
        totalBom.mergeBom(calculateSkråtSpær(length, width));
        totalBom.mergeBom(calculateSkråtBeslag(length));

        return totalBom;
    }

    @Override
    public BillOfMaterials bomCalculatorSkur(double length, double width, double height, double skurLength, double skurWidth) {
        BillOfMaterials totalBom = new BillOfMaterials();
        totalBom.mergeBom(calculateLøsholter(width, skurLength, skurWidth));
        totalBom.mergeBom(calculateBeklædningSkur(height, skurLength, skurWidth));
        return totalBom;
    }

    @Override
    public BillOfMaterials bomCalculatorFladtTag(double length, double width, double height, double skurLength, double skurWidth) {
        BillOfMaterials totalBom = new BillOfMaterials();

        totalBom.mergeBom(calculateFladtStern(length, width));
        totalBom.mergeBom(calculateRemme(length));
        totalBom.mergeBom(calculateFladtSpær(length, width));
        totalBom.mergeBom(calculateStolper(length, width, height, skurLength, skurWidth));
        totalBom.mergeBom(calculateTagplader(length, width));
        totalBom.mergeBom(calculateHulbånd(width));
        totalBom.mergeBom(calculateFladtBeslag(length));
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
            if (skurWidth != width - 30) {
                quantity += 1;
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
    public BillOfMaterials calculateFladtStern(double length, double width) {
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
    public BillOfMaterials calculateFladtSpær(double length, double width) {
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
    public BillOfMaterials calculateSkråtSpær(double length, double width) {
        BillOfMaterials bom = new BillOfMaterials();
//        int spærLength = (int) length; 
//        int quantity = (spærLength-65)/89 + 1;
        int quantity = (int) (Math.ceil((length - 65) / 89.0));

//        int newWidth = (int) width;
//        if (newWidth < 300) {
//            newWidth = 300;
//        }
//        if (newWidth % 60 != 0) {
//            newWidth += 30;
//        }
//        double[] price = {113.85, 136.63, 159.39, 182.16, 204.93, 287.7, 316.48, 345.24};
//        int index = (newWidth - 300) / 60;
        double price = 500.0 * quantity;
        bom.addLineItem(new LineItem("Fædigskåret (byg-selv spær)", 0, 1, "sæt", "Byg-selv spær (skal samles) " + quantity + " stk.", price));
        return bom;
    }

    @Override
    public BillOfMaterials calculateHulbånd(double width) {
        BillOfMaterials bom = new BillOfMaterials();
        bom.addLineItem(new LineItem("Hulbånd 1x20mm 10meter", 0, 2, "ruller", "Til vindkryds på spær", 189.00));

        return bom;
    }

    @Override
    public BillOfMaterials calculateFladtBeslag(double length) {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (int) (Math.ceil(length / 55.0));
        bom.addLineItem(new LineItem("Universal 190mm højre", 0, quantity, "stk", "Til montering af spær på rem", 21.95));
        bom.addLineItem(new LineItem("Universal 190mm venstre", 0, quantity, "stk", "Til montering af spær på rem", 21.95));
        return bom;
    }

    @Override
    public BillOfMaterials calculateSkråtBeslag(double length) {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (int) (Math.ceil((length - 65) / 89.0));
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
        BillOfMaterials stern = calculateFladtStern(length, width);
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
        BillOfMaterials bom = new BillOfMaterials();
        int skruePakkerAntal = 0;
        BillOfMaterials hulbånd = calculateHulbånd(width);
        BillOfMaterials beslag = calculateFladtBeslag(length);
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
    public BillOfMaterials calculateLøsholter(double width, double skurLength, double skurWidth) {
        BillOfMaterials bom = new BillOfMaterials();
        double[] price = {80.91, 94.40, 107.88, 121.36, 134.85, 148.34, 161.83, 175.30, 188.79, 202.28, 215.76, 229.25, 242.73};

        int reglarLength = (int) skurLength;
        int antalSide = 4;
        if (skurWidth != width - 30) {
            antalSide += 1;
        }
        if (reglarLength > 540) {
            if (reglarLength % 60 != 0) {
                reglarLength += 30;
            }
            reglarLength = reglarLength / 2;
            antalSide = antalSide * 2;
        }
        int indexSide = (reglarLength - 180) / 30;
        bom.addLineItem(new LineItem("45x95 Reglar ubh.", reglarLength, antalSide, "stk", "Løsholter i siderne af skur", price[indexSide]));

        int reglarWidth = (int) skurWidth;
        int antalGavl = 6;
        if (reglarWidth > 540) {
            if (reglarWidth % 60 != 0) {
                reglarWidth += 30;
            }
            reglarWidth = reglarWidth / 2;
            antalGavl = 12;
        }
        int indexGavl = (reglarWidth - 180) / 30;
        bom.addLineItem(new LineItem("45x95 Reglar ubh.", reglarWidth, antalGavl, "stk", "Løsholter i gavle af skur", price[indexGavl]));

        int antalBeslag = (antalSide + antalGavl) * 2;
        bom.addLineItem(new LineItem("Vinkelbeslag", 0, antalBeslag, "stk", "Til montering af løsholter", 20.95));

        return bom;
    }

    @Override
    public BillOfMaterials calculateBeklædningSkur(double height, double skurLength, double skurWidth) {
        BillOfMaterials bom = new BillOfMaterials();
        double[] price = {14.60, 16.68, 18.76, 20.85, 25.03, 29.19};
        int brætHeight = (int) height;
        int index = 0;
        if (brætHeight < 330) {
            index = (brætHeight - 210) / 30;
        } else {
            if (brætHeight % 60 != 0) {
                brætHeight += 30;
            }
            index = (brætHeight - 360) / 60 + 4;
        }
        int beklædningLength = (int) skurLength;
        int beklædningWidth = (int) skurWidth;

        int quantity = (beklædningLength / 16) * 4 + (beklædningWidth / 16) * 4;
        bom.addLineItem(new LineItem("19x100 mm. trykimp. Bræt", brætHeight, quantity, "stk", "Beklædning af skur 1 på 2", price[index]));

        bom.addLineItem(new LineItem("38x73 mm. taglægte T1", 510, 1, "stk", "Til z på bagside af dør", 38.50));
        bom.addLineItem(new LineItem("Stalddørsgreb 50x75", 0, 1, "stk", "Til dør i skur", 189.0));
        bom.addLineItem(new LineItem("T-hængsel 390 mm.", 0, 2, "stk", "Til dør i skur", 109.0));

        int skruerInderst = quantity / 2 * 3;
        int kasserInderst = skruerInderst / 200 + 2;
        bom.addLineItem(new LineItem("4,5 x 50 mm. Skruer Climate TX20 - 200 stk.", 0, kasserInderst, "kasser", "Til montering af inderste bræt ved beklædning ", 129.0));

        int skruerYderst = quantity / 2 * 6;
        int kasserYderst = skruerYderst / 200 + 2;
        bom.addLineItem(new LineItem("4,5 x 70 mm. Skruer Climate TX20 - 200 stk.", 0, kasserYderst, "kasser", "Til montering af yderste bræt ved beklædning", 199.0));

        return bom;
    }

}
