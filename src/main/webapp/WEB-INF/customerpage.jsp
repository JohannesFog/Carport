<%-- 
    Document   : customerpage
    Created on : Aug 22, 2017, 2:33:37 PM
    Author     : kasper
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer home page</title>
    </head>
    <body>
        <h1>Hello <%=request.getParameter("email")%> </h1>


        <h2>Vælg hvilken type carport du kunne tænke dig</h2>

        <form name="GetCarport" action="FrontController" method="POST">
            <input type="hidden" name="command" value="GetCarport">
            <input type="submit" name="cpType" value="Carport med fladt tag"><br><br>
            <input type="submit" name="cpType" value="Carport med rejsning">
        </form>
        
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="GetODetails">
            <input type="hidden" name="from" value="customerpage">
            <input type="submit" name="submit" value="Mine Ordre">
        </form>




    </body>
</html>
