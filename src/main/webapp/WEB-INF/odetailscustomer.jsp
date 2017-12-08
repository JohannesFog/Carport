<%-- 
    Document   : odetailcustomer
    Created on : 07-12-2017, 10:36:50
    Author     : Mikkel Lindstrøm <Mikkel.Lindstrøm>
--%>

<%@page import="FunctionLayer.LogicFacade"%>
<%@page import="FunctionLayer.BillOfMaterials"%>
<%@page import="FunctionLayer.Order"%>
<%@page import="FunctionLayer.User"%>
<%@page import="PresentationLayer.Rendering"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%Rendering render = new Rendering();%>
        <% Order order = (Order) session.getAttribute("order"); %>
        
        <%=render.showOrderDetails(order)%>
        
        <% if(order.getStatus().equals("confirmed")){ 
        
            BillOfMaterials bom = LogicFacade.getBillOfMaterials(order);
            double price = LogicFacade.getCarportPrice(bom); %>
            
            <%=render.showBillOfMaterials(bom)%>
            
            <%=render.showPrice(price)%>
        
        <% } %>
        
        
        
        
        
        
        
        
        
    </body>
</html>
