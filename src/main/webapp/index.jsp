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
        
        <table>
            <tr><td>Udfyld felterne</td>
                <td>
                    <br>
                    <form name="GetPrice" action="FrontController" method="POST">
                        <input type="hidden" name="command" value="GetPrice">
                        Bredde<br>
                        <input type="text" name="bredde" value="">
                        <br>
                        Længde<br>
                        <input type="text" name="laengde" value="">
                        <input type="submit" value="Submit">
                    </form>
                </td>
                
            </tr>
        </table>
    </body>
</html>
