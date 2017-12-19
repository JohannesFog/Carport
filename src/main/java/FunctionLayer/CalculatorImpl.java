package FunctionLayer;

import FunctionLayer.Entities.LineItem;
import FunctionLayer.Entities.BillOfMaterials;
import FunctionLayer.Entities.Order;
import FunctionLayer.Exceptions.DataMapperException;
import java.util.ArrayList;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
 */
public class CalculatorImpl implements Calculator {

    String materiale;
    double unitPrice;

    double meterPrice;
    double stkPrice;

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

    public BillOfMaterials bomCalculator(Order order) throws DataMapperException {

        double length = order.getLength();
        double width = order.getWidth();
        double height = order.getHeight();
        double angle = order.getRoofAngle();
        double skurLength = order.getShedLength();
        double skurWidth = order.getShedWidth();

        BillOfMaterials totalBom = new BillOfMaterials();

        if (angle == 0) {
            totalBom.mergeBom(bomCalculatorFladtTag(length, width, height, skurLength, skurWidth));
        } else {
            totalBom.mergeBom(bomCalculatorSkråtTag(length, width, height, angle, skurLength, skurWidth));
        }
        if (skurLength != 0 && skurWidth != 0) {
            totalBom.mergeBom(bomCalculatorSkur(length, width, height, skurLength, skurWidth));
        }

        return totalBom;
    }

    private double calculateHypotenuse(double width, double angle) {
        double angleInRadian = Math.toRadians(angle);
        double hypotenuse = (width / 2) / Math.cos(angleInRadian);
        return hypotenuse;
    }

    private double calculateKatete(double width, double angle) {
        double angleInRadian = Math.toRadians(angle);
        double katete = (width / 2) * Math.tan(angleInRadian);
        return katete;
    }

    @Override
    public BillOfMaterials bomCalculatorSkråtTag(double length, double width, double height,
            double angle, double skurLength, double skurWidth) throws DataMapperException {

        double hypotenuse = calculateHypotenuse(width, angle);
        double katete = calculateKatete(width, angle);

        BillOfMaterials totalBom = new BillOfMaterials();
        totalBom.mergeBom(calculateRemme(length - 60));
        totalBom.mergeBom(calculateStolper(length, width, height, skurLength, skurWidth));
        totalBom.mergeBom(calculateSkråtSpær(length, width));
        totalBom.mergeBom(calculateSkråtBeslag(length));

        totalBom.mergeBom(calculateTagMedSten(length, width, hypotenuse));

        totalBom.mergeBom(calculateBeklædningGavl(width, katete));
        totalBom.mergeBom(calculateVindskeder(hypotenuse));
        totalBom.mergeBom(calculateSkråtStern(length));
        totalBom.mergeBom(calculateBræddebolt(length - 60));
        totalBom.mergeBom(calculateFirkantskiver(length - 60));

        return totalBom;
    }

    @Override
    public BillOfMaterials bomCalculatorSkur(double length, double width, double height, double skurLength, double skurWidth) throws DataMapperException {
        BillOfMaterials totalBom = new BillOfMaterials();
        totalBom.mergeBom(calculateLøsholter(width, skurLength, skurWidth));
        totalBom.mergeBom(calculateBeklædningSkur(height, skurLength, skurWidth));
        return totalBom;
    }

    @Override
    public BillOfMaterials bomCalculatorFladtTag(double length, double width, double height, double skurLength, double skurWidth) throws DataMapperException {
        BillOfMaterials totalBom = new BillOfMaterials();

        totalBom.mergeBom(calculateFladtStern(length, width));
        totalBom.mergeBom(calculateRemme(length));
        totalBom.mergeBom(calculateFladtSpær(length, width));
        totalBom.mergeBom(calculateStolper(length, width, height, skurLength, skurWidth));
        totalBom.mergeBom(calculateTagplader(length, width));
        totalBom.mergeBom(calculateHulbånd(width));
        totalBom.mergeBom(calculateFladtBeslag(length));
        totalBom.mergeBom(calculateSkruerStern(length, width));
        totalBom.mergeBom(calculateFladtSkruerBeslag(length, width));
        totalBom.mergeBom(calculateBræddebolt(length));
        totalBom.mergeBom(calculateFirkantskiver(length));

        return totalBom;
    }

    @Override
    public BillOfMaterials calculateStolper(double length, double width, double height,
            double skurLength, double skurWidth) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = ((((int) length - 30) / 240) + 1) * 2;
        if (length < 270) {
            quantity += 2;
        }
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
        materiale = "97x97 mm. trykimp. stolpe";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * ((double) newHeight / 100);
        bom.addLineItem(new LineItem(materiale, newHeight, quantity,
                "stk", "Stolper nedgraves 90cm i jord.", unitPrice));
        return bom;
    }

    @Override
    public BillOfMaterials calculateTagplader(double length, double width) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (int) Math.ceil(width / 100);

        materiale = "Plastmo Ecolite blåtonet";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 600, quantity, "stk", "Tagplader monteres på spær", unitPrice));
        if (length > 600) {
            double newUnitPrice = unitPrice * 600.0 / 360.0;
            bom.addLineItem(new LineItem(materiale, 360, quantity, "stk", "Tagplader monteres på spær", newUnitPrice));
        }
        return bom;
    }

    @Override
    public BillOfMaterials calculateTagMedSten(double length, double width, double hypotenuse) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int rest = ((int) Math.ceil(hypotenuse)) * 10 - 380;
        int antalLægterSide;
        if (rest % 307 == 0) {
            antalLægterSide = rest / 307 + 2;
        } else {
            antalLægterSide = rest / 307 + 3;
        }
        int antalLægterTotal = antalLægterSide * 2;
        int lægteLength;
        if (length > 540) {
            if (length % 60 != 0) {
                length += 30;
            }
            lægteLength = (int) (length / 2);
            antalLægterTotal = antalLægterTotal * 2;
        } else {
            if (length < 300) {
                lægteLength = 300;
            } else {
                lægteLength = (int) length;
            }
        }

        materiale = "38x73 mm. taglægte C18";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * ((double) lægteLength / 100);

        if (length < 540) {
            bom.addLineItem(new LineItem(materiale, lægteLength, antalLægterTotal, "stk", "Til montering på spær, " + antalLægterSide + " rækker lægter på hver side", unitPrice));
            bom.addLineItem(new LineItem(materiale, lægteLength, 1, "stk", "Toplægte til montering af rygsten lægges i toplægte holder", unitPrice));
        } else {
            bom.addLineItem(new LineItem(materiale, lægteLength, antalLægterTotal, "stk", "Til montering på spær, " + antalLægterSide + " rækker lægter på hver side - 2 lægter samles", unitPrice));
            bom.addLineItem(new LineItem(materiale, lægteLength, 2, "stk", "Toplægte til montering af rygsten lægges i toplægte holder", unitPrice));
        }

        int antalLister = 2;
        int listeLength;
        if (length > 540) {
            if (length % 60 != 0) {
                length += 30;
            }
            listeLength = (int) (length / 2);
            antalLister = antalLister * 2;

        } else {
            if (length < 300) {
                listeLength = 300;
            } else {
                listeLength = (int) length;
            }
        }

        materiale = "25x50 mm. trykimp. bræt";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * ((double) lægteLength / 100);
        bom.addLineItem(new LineItem(materiale, listeLength, antalLister, "stk", "Til montering oven på tagfodslægte", unitPrice));

        int antalSpær = (int) (Math.ceil((length - 65) / 89.0));;
        int antalSkruer = antalLægterSide * 2 * antalSpær * 2;
        int antalPakkerSkruer = antalSkruer / 100 + 1;

        materiale = "5,0 x 100 mm. skruer 100 stk.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, antalPakkerSkruer, "kasser", "Til taglægter", unitPrice));

        materiale = "5,0 x 40 mm. beslagskruer 250 stk.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, 1, "stk", "Til montering af universalbeslag + toplægte", unitPrice));

        int antalStenOpad = antalLægterSide - 1;
        int antalStenHenad = (int) Math.ceil(length / 20.7);
        int antalStenTotal = (int) Math.ceil(antalStenOpad * antalStenHenad * 2 * 1.05);
        int rundOp = 50 - (antalStenTotal % 50);
        antalStenTotal += rundOp;
        int antalRygSten = (int) Math.ceil(length / 28.5);

        materiale = "Vingetagsten Gl. Dansk";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, antalStenTotal, "stk", "Monteres på taglægter " + antalStenOpad + " rækker af " + antalStenHenad + " sten på hver side af taget", unitPrice));

        materiale = "5,0 x 100 mm. skruer 100 stk.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, antalRygSten, "stk", "Monteres på toplægte med medfølgende beslag se tagstens vejledning", unitPrice));

        int antalToplægteHoldere = (int) (Math.ceil((length - 65) / 89.0));

        materiale = "Toplægte holder";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, antalToplægteHoldere, "stk", "Monteres på toppen af spæret (til toplægte)", unitPrice));

        materiale = "Rygstensbeslag";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, antalRygSten, "stk", "Til montering af rygsten", unitPrice));

        int antalBinderPakker;
        if (antalStenTotal % 200 == 0) {
            antalBinderPakker = (antalStenTotal / 200);
        } else {
            antalBinderPakker = (antalStenTotal / 200) + 1;
        }

        materiale = "Tagstens bindere & nakkekroge 200 stk.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, antalBinderPakker, "stk", "Til montering af tagsten, alle ydersten + hver anden fastgøres", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateRemme(double length) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int remLength = (int) length;
        if (remLength < 300) {
            remLength = 300;
        }
        if (remLength % 60 != 0) {
            remLength += 30;
        }

        materiale = "45x195 mm. spærtræ ubh.";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * ((double) remLength / 100);
        bom.addLineItem(new LineItem(materiale, remLength, 2, "stk", "Remme i sider, sadles ned i stolper", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateFladtStern(double length, double width) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();

        materiale = "25x200 mm. trykimp. bræt";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * 3.6;
        bom.addLineItem(new LineItem(materiale, 360, 4, "stk", "Understern brædder til for og bag ende", unitPrice));
        unitPrice = meterPrice * 5.4;
        bom.addLineItem(new LineItem(materiale, 540, 4, "stk", "Understern brædder til siderne", unitPrice));

        materiale = "25x125 mm. trykimp. bræt";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * 3.6;
        bom.addLineItem(new LineItem(materiale, 360, 2, "stk", "Overstern brædder til for enden", unitPrice));
        unitPrice = meterPrice * 5.4;
        bom.addLineItem(new LineItem(materiale, 540, 4, "stk", "Overstern brædder til siderne", unitPrice));

        materiale = "19x100 mm. trykimp. bræt";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * 3.6;
        bom.addLineItem(new LineItem(materiale, 540, 4, "stk", "Vandbrædt på stern i sider", unitPrice));
        unitPrice = meterPrice * 5.4;
        bom.addLineItem(new LineItem(materiale, 360, 2, "stk", "Vandbrædt på stern i for enden", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateSkråtStern(double length) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int rundOp = 60 - ((int) length) % 60;
        if (rundOp == 60) {
            rundOp = 0;
        }
        int sternLength = (int) (length + rundOp);
        int quantity = 2;
        if (sternLength <= 300) {
            quantity = 1;
            sternLength = sternLength * 2;
        } else {
            if (sternLength > 600) {
                sternLength = sternLength / 2;
                quantity = 4;
                if (sternLength % 60 != 0) {
                    sternLength += 30;
                }
            }
        }

        materiale = "25x150 mm. trykimp. bræt";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * ((double) sternLength / 100);
        bom.addLineItem(new LineItem(materiale, sternLength, quantity, "stk", "Sternbrædder til siderne", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateVindskeder(double hypotenuse) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int rundOp = 60 - ((int) hypotenuse) % 60;
        int vindskedeLength = (int) (hypotenuse + rundOp);
        int quantity;
        if (vindskedeLength <= 300) {
            quantity = 2;
            vindskedeLength = vindskedeLength * 2;
        } else {
            quantity = 4;
        }

        materiale = "25x150 mm. trykimp. bræt";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * ((double) vindskedeLength / 100);
        bom.addLineItem(new LineItem(materiale, vindskedeLength, quantity, "stk", "Vindskeder på rejsning ", unitPrice));

        int vandBrætLength = (int) vindskedeLength;
        int quantityVand = quantity;
        if (vandBrætLength > 420) {
            vandBrætLength = vandBrætLength / 2;
            quantityVand = quantityVand * 2;
        }

        materiale = "19x100 mm. trykimp. bræt";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * ((double) vandBrætLength / 100);
        bom.addLineItem(new LineItem(materiale, vandBrætLength, quantityVand, "stk", "Vandbræt på vindskeder ", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateFladtSpær(double length, double width) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (int) (Math.ceil(length / 55.0));
        int spærWidth = (int) width;
        if (spærWidth < 300) {
            spærWidth = 300;
        }
        if (spærWidth % 60 != 0) {
            spærWidth += 30;
        }

        materiale = "45x195 mm. spærtræ ubh.";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * ((double) spærWidth / 100);
        bom.addLineItem(new LineItem(materiale, spærWidth, quantity, "stk", "Spær monteres på rem", unitPrice));
        return bom;
    }

    @Override
    public BillOfMaterials calculateSkråtSpær(double length, double width) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (int) (Math.ceil((length - 65) / 89.0));

        materiale = "Færdigskåret (byg-selv spær)";
        stkPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = stkPrice * quantity;
        bom.addLineItem(new LineItem(materiale, 0, 1, "sæt", "Byg-selv spær (skal samles) " + quantity + " stk.", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateHulbånd(double width) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();

        materiale = "Hulbånd 1x20 mm. 10 meter";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, 2, "ruller", "Til vindkryds på spær", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateFladtBeslag(double length) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (int) (Math.ceil(length / 55.0));

        materiale = "Universal 190 mm. højre";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, quantity, "stk", "Til montering af spær på rem", unitPrice));

        materiale = "Universal 190 mm. venstre";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, quantity, "stk", "Til montering af spær på rem", unitPrice));
        return bom;
    }

    @Override
    public BillOfMaterials calculateSkråtBeslag(double length) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int quantity = (int) (Math.ceil((length - 65) / 89.0));

        materiale = "Universal 190 mm. højre";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, quantity, "stk", "Til montering af spær på rem", unitPrice));
        materiale = "Universal 190 mm. venstre";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, quantity, "stk", "Til montering af spær på rem", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateSkruerStern(double length, double width) throws DataMapperException {
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

        materiale = "4,5 x 60 mm. skruer 200 stk.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, skruePakkerAntal, "pakker", "Til montering af stern & vandbrædt", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateFladtSkruerBeslag(double length, double width) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int skruePakkerAntal = 0;

        BillOfMaterials beslag = calculateFladtBeslag(length);
        int beslagStk = 0;

        for (LineItem li : beslag.getBomList()) {
            beslagStk += li.getQuantity();
        }

//      jeg regner med at der går hvad der svarer til et beslag pakke pr. rulle hulbånd.
        beslagStk += 2;

//      jeg regner med at der går 1 pakke til 30 stk. beslag (højre+venstre)
        skruePakkerAntal += (beslagStk / 30) + 1;

        materiale = "4,0 x 50 mm. beslagskruer 250 stk.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, skruePakkerAntal, "pakker", "Til montering af universalbeslag + hulbånd", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateBræddebolt(double length) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int bræddebolte = 0;
        BillOfMaterials rem = calculateRemme(length);
        for (LineItem li : rem.getBomList()) {
            bræddebolte += (li.getQuantity() * 9);//jeg regner med 9 stk, bræddebolte pr. rem.
        }

        materiale = "Bræddebolte 10*120 mm.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, bræddebolte, "stk", "Til montering af rem på stolper", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateFirkantskiver(double length) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int firkantskiver = 0;
        BillOfMaterials rem = calculateRemme(length);
        for (LineItem li : rem.getBomList()) {
            firkantskiver += (li.getQuantity() * 6);//jeg regner med 6 stk, firkantskiver pr. rem.
        }

        materiale = "Firkantskiver 40x40x11 mm.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, firkantskiver, "stk", "Til montering af rem på stolpers", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateLøsholter(double width, double skurLength, double skurWidth) throws DataMapperException {
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

        materiale = "45x95 mm. reglar ubh.";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * ((double) reglarLength / 100);
        bom.addLineItem(new LineItem(materiale, reglarLength, antalSide, "stk", "Løsholter i siderne af skur", unitPrice));

        int reglarWidth = (int) skurWidth;
        int antalGavl = 6;
        if (reglarWidth > 540) {
            if (reglarWidth % 60 != 0) {
                reglarWidth += 30;
            }
            reglarWidth = reglarWidth / 2;
            antalGavl = 12;
        }

        unitPrice = meterPrice * ((double) reglarWidth / 100);
        bom.addLineItem(new LineItem(materiale, reglarWidth, antalGavl, "stk", "Løsholter i gavle af skur", unitPrice));

        int antalBeslag = (antalSide + antalGavl) * 2;
        materiale = "Vinkelbeslag";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, antalBeslag, "stk", "Til montering af løsholter", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateBeklædningSkur(double height, double skurLength, double skurWidth) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int brætHeight = (int) height;
        int beklædningLength = (int) skurLength;
        int beklædningWidth = (int) skurWidth;
        int quantity = (beklædningLength / 16) * 4 + (beklædningWidth / 16) * 4;

        materiale = "19x100 mm. trykimp. bræt";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * ((double) brætHeight / 100);
        bom.addLineItem(new LineItem(materiale, brætHeight, quantity, "stk", "Beklædning af skur 1 på 2", unitPrice));

        materiale = "38x73 mm. taglægte C18";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * 5.4;
        bom.addLineItem(new LineItem(materiale, 540, 1, "stk", "Til z på bagside af dør", unitPrice));

        materiale = "Stalddørsgreb 50x75 mm.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, 1, "stk", "Til dør i skur", unitPrice));

        materiale = "T-hængsel 390 mm.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, 2, "stk", "Til dør i skur", unitPrice));

        int skruerInderst = quantity / 2 * 3;
        int kasserInderst = skruerInderst / 200 + 2;
        materiale = "4,5 x 50 mm. Skruer Climate TX20 - 200 stk.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, kasserInderst, "kasser", "Til montering af inderste bræt ved beklædning ", unitPrice));

        int skruerYderst = quantity / 2 * 6;
        int kasserYderst = skruerYderst / 200 + 2;
        materiale = "4,5 x 70 mm. Skruer Climate TX20 - 200 stk.";
        unitPrice = LogicFacade.getMaterialPrice(materiale);
        bom.addLineItem(new LineItem(materiale, 0, kasserYderst, "kasser", "Til montering af yderste bræt ved beklædning", unitPrice));

        return bom;
    }

    @Override
    public BillOfMaterials calculateBeklædningGavl(double width, double katete) throws DataMapperException {
        BillOfMaterials bom = new BillOfMaterials();
        int rundOp = 30 - ((int) katete) % 30;
        int gavlheight = (int) (katete + rundOp);
        int faktor = 1;
        int brætHeight = gavlheight;
        if (gavlheight < 180) {
            if (gavlheight == 60) {
                brætHeight = 180;
                faktor = 3;
            } else {
                brætHeight = gavlheight * 2;
                faktor = 2;
            }
        }
        int quantity = (((int) width / 16) * 2) / faktor;

        materiale = "19x100 mm. trykimp. bræt";
        meterPrice = LogicFacade.getMaterialPrice(materiale);
        unitPrice = meterPrice * ((double) brætHeight / 100);
        bom.addLineItem(new LineItem(materiale, brætHeight, quantity, "stk", "Beklædning af gavle 1 på 2", unitPrice));

        return bom;
    }

}
