/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionLayer;

import java.util.ArrayList;

/**
 *
 * @author lene_
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
