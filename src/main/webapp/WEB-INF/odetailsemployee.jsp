<%-- 
    Document   : odetailsemployee
    Created on : 05-12-2017, 10:24:29
    Author     : lene_
--%>

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
        Herunder ses detaljer for den valgte ordre:<br>
        <%=render.showOrderDetails(order)%>
        <br><br>
        Styklisten for den valgte ordre:<br>
        <%BillOfMaterials bom = new BillOfMaterials();%>
        
        <%-- TODO: Her skal kaldes en beregning af bom med udgangspunkt i ordren --%>
        
        <%=render.showBillOfMaterials(bom)%>
        
    </body>
</html>
