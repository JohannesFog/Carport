package FunctionLayer;

public class LineItem {
    
    String name;
    int length;
    int amount;
    String unit;
    String description;
    double unitPrice;

    public LineItem(String name, int length, int amount, String unit, String description, double unitPrice) {
        this.name = name;
        this.length = length;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
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
    
    public double getTotslPrice() {
        return unitPrice*amount;
    }
    
    
    
    
}
