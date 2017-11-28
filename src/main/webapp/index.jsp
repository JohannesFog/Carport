<%-- 
    Document   : index
    Created on : Aug 22, 2017, 2:01:06 PM
    Author     : kasper
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome page</title>
    </head>
    <body>

        <h2>Vælg hvilken type carport du kunne tænke dig</h2>

        <form name="GetCarport" action="FrontController" method="POST">
            <input type="hidden" name="command" value="GetCarport">
            <input type="submit" name="cpType" value="Carport med fladt tag"><br><br>
            <input type="submit" name="cpType" value="Carport med rejsning">
        </form>


        <h2> Login </h2>
        <form name="Login" action="FrontController" method="POST">
            <input type="hidden" name="command" value="Login">
            Email:
            <input type="email" name="email" value="">
            Password:
            <input type="password" name="password" value="">
            <input type="submit" name="Login" value="Login">
        </form>

        
         <% String error = (String) request.getAttribute("error");
                        if (error != null) {%>
        <H2>Error!</H2>
        <p><%= error%></p>
        <%}%>
        
    </body>
</html>
