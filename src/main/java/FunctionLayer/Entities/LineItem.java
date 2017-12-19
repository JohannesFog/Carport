package FunctionLayer.Entities;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
 */

public class LineItem {
    
    String name;
    int length;
    int quantity;
    String unit;
    String description;
    double unitPrice;

    public LineItem(String name, int length, int quantity, String unit, String description, double unitPrice) {
        this.name = name;
        this.length = length;
        this.quantity = quantity;
        this.unit = unit;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }
    
    public int getLength() {
        return length;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    
    public double getTotalPrice() {
        return unitPrice*quantity;
    }

    @Override
    public String toString() {
        return "LineItem{" + "name=" + name + ", length=" + length + ", quantity=" + quantity + ", unit=" + unit + ", description=" + description + '}';
    }
    
    
    
    
}
