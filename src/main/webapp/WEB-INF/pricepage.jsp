<%-- 
    Document   : pricepage
    Created on : 14-11-2017, 10:18:17
    Author     : Mikkel Lindstrøm <Mikkel.Lindstrøm>
--%>

<%@page import="FunctionLayer.Entities.*"%>

<%@page import="PresentationLayer.Rendering"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Prisoverslag</title>

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


                        <!--<a href="#" class="list-group-item active">Fladt tag</a> -->
                        <!--<a href="#" class="list-group-item">Rejst tag</a>-->


                        <form name="GetCarport" action="FrontController" method="POST">
                            <input type="hidden" name="command" value="GetCarport">
                            <input id ="tagknap" class="list-group-item active" type="submit" name="cpType" value="Fladt tag">
                            <input id ="tagknap" class="list-group-item" type="submit" name="cpType" value="Rejst tag">
                        </form>


                    </div>


                </div>
                <!-- /.col-lg-3 -->

                <div class="col-lg-9">

                    <div class="card mt-4">
                        <div class="card-body">
                            <h3 class="card-title">Prisoverslag og tegning</h3>
                            <!-- <h4>$24.99</h4> -->
                            <p class="card-text">
                                <%Rendering render = new Rendering();%>

                                <%=render.showPrice((double) session.getAttribute("price"))%>

                                <%  User user = (User) session.getAttribute("user");
                                    String role = "";
                                    if (user != null) {
                                        role = user.getRole();
                                    }
                                %>


                                <%=session.getAttribute("draw")%>

                            </p>
                            <!--  <span class="text-warning">&#9733; &#9733; &#9733; &#9733; &#9734;</span> -->
                            <!-- 4.0 stars -->
                        </div>
                    </div>

                    <!-- /.card -->

                    <div class="card card-outline-secondary my-4">
                         <div class="card-header">
                           Ønsker du denne carport?
                         </div>
                        <div class="card-body">
                            <p>
                                <% if (role == "" || role.equals("employee")) { %>
                            <form name="Order" action="FrontController" method="POST">
                                <input type="hidden" name="command" value="Order">
                                <p>Navn:</p>
                                <input class="input-login" type="text" name="name" value="">
                                <p>Addresse:</p>
                                <input class="input-login" type="text" name="address" value="">
                                <p>Postnummer:</p>
                                <input class="input-login" type="text" name="zip" value="">
                                <p>Telefon:</p>
                                <input class="input-login" type="text" name="tlf" value="">
                                <p>Email</p>
                                <input class="input-login" type="email" name="email" value="">
                                <p>Bemærkninger</p>
                                <input class="input-login" type="text" name="notice" value=""><br>
                                <input class="submit-login" type="submit" name="order" value="Bestil Carport">
                            </form> 
                            <br>
                            <%} else {%>
                            <form name="Order" action="FrontController" method="POST">
                                <input type="hidden" name="command" value="Order">
                                <p>Indtast dit telefonnummer:</p>
                                <input class="input-login" type="text" name="tlf" value=""><br>
                                <input class="submit-login" type="submit" name="order" value="Bestil Carport">
                            </form>
                            <br>
                            <%}%>
                            </p>

                        </div>
                    </div>

                    <!-- /.card -->


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
