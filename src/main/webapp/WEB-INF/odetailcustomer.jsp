<%-- 
    Document   : odetailcustomer
    Created on : 07-12-2017, 10:36:50
    Author     : Mikkel Lindstrøm <Mikkel.Lindstrøm>
--%>

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
        <% User user = (User) session.getAttribute("user"); %>
        <h1>Hello <%=user.getName()%>!</h1>
        <%render.getOrderlistTableUser(user);%>
        
        
        
        
        
        
    </body>
</html>
