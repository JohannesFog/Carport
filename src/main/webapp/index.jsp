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
                    <select name="bredde">
                    <%
                            int b = 240;
                            for (int i = 0; i<18; i++) {
                                %>
                                    <option value="<%= b%>"><%= b%></option>
                                
                            <% 
                                b+=30;
                            }

                    %>
                    </select>
                    
                    
                    <select name="laengde">
                    <%
                            int l = 240;
                            for (int i = 0; i<19; i++) {
                                %>
                                    <option value="<%= l%>"><%= l%></option>
                                
                            <% 
                                l+=30;
                            }

                    %>
                    
                    
                    </select>
                    
                    <input type="submit" value="Submit">
                    </form>
                </td>
                
            </tr>
        </table>
        
                    <% String error = (String) request.getAttribute("error");
                    if (error != null) {%>
                        <H2>Error!</H2>
                        <p><%= error%></p>
                    <%}%>            
                    
        <%-- 
        <input type="text" name="bredde" value="">
        <input type="text" name="laengde" value="">
        
        <select>
            <option value="" disabled="disabled" selected="selected">Please select a name</option>
            <option value="1">One</option>
            <option value="2">Two</option>
        </select>
        --%>
    </body>
</html>
