<%-- 
    Document   : pricepage
    Created on : 14-11-2017, 10:18:17
    Author     : Mikkel Lindstrøm <Mikkel.Lindstrøm>
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="FunctionLayer.LineItem"%>
<%@page import="FunctionLayer.BillOfMaterials"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%=session.getAttribute("price")%> <p>kr.</p>
        <%BillOfMaterials bom = (BillOfMaterials) session.getAttribute("bom");%>
        <%ArrayList<LineItem> items = bom.getBomList();
        for (LineItem li: items) {%>
            <%= li.toString()%>
        <%}%>
        
    </body>
</html>
