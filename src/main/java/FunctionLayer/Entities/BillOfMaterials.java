/*
 */
package FunctionLayer.Entities;

import java.util.ArrayList;

/**
 *
 * @author Christian, Gert, Lene & Mikkel
 */
public class BillOfMaterials {
    ArrayList<LineItem> bomList;

    public BillOfMaterials() {
        this.bomList = new ArrayList<>();
    }
    
    public void addLineItem(LineItem item){
        bomList.add(item);
    }
    
    public void mergeBom(BillOfMaterials bom){
        bomList.addAll(bom.getBomList());
    }

    public ArrayList<LineItem> getBomList() {
        return bomList;
    }

    
    
    
    
    
    
    
}
