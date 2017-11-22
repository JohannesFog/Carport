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


        <h2>Udfyld felterne</h2>

        <form name="GetPrice" action="FrontController" method="POST">
            <input type="hidden" name="command" value="GetPrice">
            <br> Bredde i cm <br>
            <select name="bredde">
                <%
                    int b = 240;
                    for (int i = 0; i < 17; i++) {
                %>
                <option value="<%= b%>"><%= b%></option>

                <% 
                        b += 30; }
                %>
            </select><br>

            <br>Længde i cm <br>
            <select name="laengde">
                <%                        int l = 240;
                    for (int i = 0; i < 17; i++) {
                %>
                <option value="<%= l%>"><%= l%></option>

                <%
                        l += 30; }
                %>
            </select><br>
            
            <br>Højde i cm <br>
            <select name="hoejde">
                <%
                    int h = 210;
                    for (int i = 0; i < 7; i++) {
                %>
                <option value="<%= h%>"><%= h%></option>

                <%
                        h += 30; }
                %>
            </select><br>            
            
            
            <br>Vælg tagtype<br>
            <select name="tagtype">
                <option value="fladt">Fladt tag</option>
                <option value="skråt">Skråt tag</option>
            </select><br>
            <br>Redskabsskur<br>
            (NB! Der skal beregnes 15 cm tagudhæng på hver side af redskabsrummet)<br>
            <br> Skurbredde i cm <br>
            <select name="skurbredde">
                <option value="0">Uden redskabsskur</option>
                <%
                    int sb = 210;
                    for (int i = 0; i < 17; i++) {
                %>
                <option value="<%= sb%>"><%= sb%></option>

                <% 
                        sb += 30; }
                %>
            </select><br>

            <br>Skurlængde i cm <br>
            <select name="skurlaengde">
                <option value="0">Uden redskabsskur</option>
                <%                        
                    int sl = 210;
                    for (int i = 0; i < 17; i++) {
                %>
                <option value="<%= sl%>"><%= sl%></option>

                <%
                        sl += 30; }
                %>
            </select><br>

            <br><br>
            <input type="submit" value="Submit">
        </form>


        <% String error = (String) request.getAttribute("error");
                        if (error != null) {%>
        <H2>Error!</H2>
        <p><%= error%></p>
        <%}%>            

    </body>
</html>
