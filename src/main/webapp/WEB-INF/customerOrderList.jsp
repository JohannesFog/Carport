<%-- 
    Document   : customerOrderList
    Created on : 08-12-2017, 12:27:31
    Author     : Mikkel Lindstrøm <Mikkel.Lindstrøm>
--%>

<%@page import="PresentationLayer.Rendering"%>
<%@page import="FunctionLayer.User"%>
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
        
        <%=render.getOrderlistTableUser(user)%>
        
        
        
        
    </body>
</html>
