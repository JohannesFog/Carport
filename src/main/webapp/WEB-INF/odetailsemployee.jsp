<%-- 
    Document   : odetailsemployee
    Created on : 05-12-2017, 10:24:29
    Author     : lene_
--%>

<%@page import="FunctionLayer.LogicFacade"%>
<%@page import="FunctionLayer.BillOfMaterials"%>
<%@page import="PresentationLayer.Rendering"%>
<%@page import="FunctionLayer.Order"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ordredetaljer</title>
    </head>
    <body>
        <%Order order = (Order) session.getAttribute("order");%>
        <%Rendering render = new Rendering();%>
        <%=render.showOrderDetails(order)%>
        <br><br>
        
        <%BillOfMaterials bom = LogicFacade.getBillOfMaterials(order);%>
        <%double price = LogicFacade.getCarportPrice(bom);%>

        
        <%=render.showPrice(price)%>
        
        <%=render.showBillOfMaterials(bom)%>
        
        <h2>Tegning for valgte ordre</h2>
        
        <%=LogicFacade.getDrawing(bom, order)%>
        
        <%-- !TODO: indsæt tegning af ordren --%>
        
    </body>
</html>
