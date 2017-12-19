<%-- 
    Document   : rejsttag
    Created on : 22-11-2017, 13:32:28
    Author     : Christian, Gert, Lene & Mikkel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Carport med rejst tag</title>

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/shop-item.css" rel="stylesheet">
        <link href="css/mycss.css" rel="stylesheet">

    </head>
    <body>
        <%@ include file = "header.jsp" %>
        <div class="container">
            <div class="row">

                <div class="col-lg-3">
                    <h1 class="my-4">Johannes Fog Carporte</h1>
                    <div class="list-group">
                        <form name="GetCarport" action="FrontController" method="POST">
                            <input type="hidden" name="command" value="GetCarport">
                            <input id ="tagknap" class="list-group-item" type="submit" name="cpType" value="Fladt tag">
                            <input id ="tagknap" class="list-group-item active" type="submit" name="cpType" value="Rejst tag">
                        </form>
                    </div>

                </div>
                <!-- /.col-lg-3 -->

                <div class="col-lg-9">
                    <div class="card mt-4">
                        <div class="card-body">
                            <h3 class="card-title">Carport med rejst tag</h3>
                            <!-- <h4>$24.99</h4> -->
                            <p class="card-text">
                            <form name="GetPrice" action="FrontController" method="POST">
                                <input type="hidden" name="command" value="GetPrice">
                                <input type="hidden" name="tagtype" value="skraat">
                                <br> Bredde i cm <br>
                                <select name="bredde">
                                    <%
                                        int b = 240;
                                        for (int i = 0; i < 17; i++) {
                                    %>
                                    <option value="<%= b%>"><%= b%></option>

                                    <%
                                            b += 30;
                                        }
                                    %>
                                </select><br>

                                <br>Længde i cm <br>
                                <select name="laengde">
                                    <%                        int l = 240;
                                        for (int i = 0; i < 17; i++) {
                                    %>
                                    <option value="<%= l%>"><%= l%></option>

                                    <%
                                            l += 30;
                                        }
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
                                            h += 30;
                                        }
                                    %>
                                </select><br>            

                                <br>Vælg tagmateriale<br>
                                <select name="tagmateriale">
                                    <option value="betontagsten">Betontagsten</option>

                                </select><br>
                                <br>Vælg taghældning<br>
                                <select name="vinkel">
                                    <%int v = 15;
                                        for (int i = 0; i < 7; i++) {%>
                                    <option value="<%=v%>"><%=v%></option>
                                    <% v += 5;
                                        }%>
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
                                            sb += 30;
                                        }
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
                                            sl += 30;
                                        }
                                    %>
                                </select><br>

                                <br><br>
                                <input class="submit-login" type="submit" value="Submit">
                            </form>

                            </p>
                        </div>
                    </div>
                </div>
                <!-- /.col-lg-9 -->
                
                <% String error = (String) request.getAttribute("error");
                    if (error != null) {%>
                <H2>Error!</H2>
                <p><%= error%></p>
                <%}%>
            </div>
        </div>

    <%@ include file = "footer.jsp" %>

        <!-- Bootstrap core JavaScript -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
