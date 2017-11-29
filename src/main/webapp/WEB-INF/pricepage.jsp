<%-- 
    Document   : pricepage
    Created on : 14-11-2017, 10:18:17
    Author     : Mikkel Lindstrøm <Mikkel.Lindstrøm>
--%>

<%@page import="FunctionLayer.User"%>
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

        <% String role = (String) session.getAttribute("role"); %>
        <% if (role == null || role.equals("employee")) { %>
            <form name="Order" action="FrontController" method="POST">
            <input type="hidden" name="command" value="Order">
            <h3>Navn:</h3>
            <input type="text" name="name" value="">
            <h3>Addresse:</h3>
            <input type="text" name="address" value="">
            <h3>Postnummer:</h3>
            <input type="text" name="zip" value="">
            <h3>Telefon:</h3>
            <input type="text" name="tlf" value="">
            <h3>Email</h3>
            <input type="text" name="email" value="">
            <h3>Bemærkninger</h3>
            <input type="text" name="notice" value="">
            <input type="submit" name="order" value="Send forespørgelse">
            </form> 
            <br>
        <%} else {%>
            <form name="Order" action="FrontController" method="POST">
            <input type="hidden" name="command" value="Order">
            <h2>Indtast dit telefonnummer:</h2>
            <input type="text" name="name" value="">
            <input type="submit" name="order" value="Bestil Carport">
            </form>
            <br>
        <%}%>



        <%= renderDrawing.createDrawing((String) session.getAttribute("draw"))%>

        <a href="index.jsp">Home</a>

        <% String error = (String) request.getAttribute("error");
            if (error != null) {%>
        <H2>Error!</H2>
        <p><%= error%></p>
        <%}%> 

    </body>
</html>
