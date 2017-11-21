/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionLayer;

/**
 *
 * @author lene_
 */
public class Order {
    private int oId;
    private double length;
    private double width;
    private double height;
    private String roof;
    private String shed;
    private String orderDate;
    private String uName;

    public Order(double length, double width, double height, String roof, String shed, String orderDate, String uName) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.roof = roof;
        this.shed = shed;
        this.orderDate = orderDate;
        this.uName = uName;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getoId() {
        return oId;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getRoof() {
        return roof;
    }

    public String getShed() {
        return shed;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getuName() {
        return uName;
    }  
}