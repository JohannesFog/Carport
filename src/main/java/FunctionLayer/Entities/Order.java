/*
 */
package FunctionLayer.Entities;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
 */
public class Order {

    private int oId;
    private int phone;
    private double length;
    private double width;
    private double height;
    private String roof;
    private double roofAngle;
    private double shedWidth;
    private double shedLength;
    private String orderDate;
    private String status;

       
       //Bruges når ordren bliver oprettet i systemet
       public Order(double length, double width, double height,
            double roofAngle, double shedWidth, double shedLength, String status) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.roofAngle = roofAngle;
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.status = status;
    }
    
    //Bruges når ordren bliver hentet i databasen
    public Order(int oId, double length, double width, double height,
            double roofAngle, double shedWidth, double shedLength, String orderDate, int phone,String status) {
        this.oId = oId;
        this.length = length;
        this.width = width;
        this.height = height;
        this.roofAngle = roofAngle;
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.orderDate = orderDate;
        this.phone = phone;
        this.status = status;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setStatus(String stat) {
        this.status = stat;
    }

    public double getRoofAngle() {
        return roofAngle;
    }

    public double getShedWidth() {
        return shedWidth;
    }

    public double getShedLength() {
        return shedLength;
    }

    public int getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    
    
    
}
