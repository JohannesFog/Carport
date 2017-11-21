<%-- 
    Document   : confirmationpage
    Created on : 21-11-2017, 11:34:35
    Author     : lene_
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ordrebekræftelse</title>
    </head>
    <body>
        <h1>Hej <%=(String)session.getAttribute("name")%></h1><br>
        <h2>Mange tak for din ordre, du vil inden for kort tid modtage en ordrebekræftelse fra en af vores medarbejdere</h2>
        <br><br><br>
        
        <a href="index.jsp">Home</a>
    </body>
</html>
