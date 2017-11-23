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
        
    </body>
</html>
