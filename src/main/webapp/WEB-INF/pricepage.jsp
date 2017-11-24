<%-- 
    Document   : pricepage
    Created on : 14-11-2017, 10:18:17
    Author     : Mikkel Lindstrøm <Mikkel.Lindstrøm>
--%>

<%@page import="PresentationLayer.RenderDrawing"%>
<%@page import="PresentationLayer.Rendering"%>
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

        <%Rendering render = new Rendering();%>
        <%RenderDrawing renderDrawing = new RenderDrawing();%>

        <%=render.getNicerHTML((double) session.getAttribute("price"),
                (BillOfMaterials) session.getAttribute("bom"))%>

        <form name="Order" action="FrontController" method="POST">
            <input type="hidden" name="command" value="Order">
            <h2>Indtast dit navn:</h2>
            <input type="text" name="name" value="">
            <input type="submit" name="order" value="Bestil Carport">
        </form>
 

   
        
        <%= renderDrawing.createDrawing((String) session.getAttribute("draw"))%>
        
        <a href="index.jsp">Home</a>
        
         <% String error = (String) request.getAttribute("error");
                        if (error != null) {%>
        <H2>Error!</H2>
        <p><%= error%></p>
        <%}%> 

    </body>
</html>
