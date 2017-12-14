<%-- 
   Document   : index
   Created on : Aug 22, 2017, 2:01:06 PM
   Author     : kasper
--%>

<%@page import="FunctionLayer.Entities.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Welcome page</title>

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/shop-item.css" rel="stylesheet">
        <link href="css/mycss.css" rel="stylesheet">

    </head>
    <body>

       <%@ include file = "WEB-INF/header.jsp" %>

        <div class="container">
            <div class="row">


                <div class="col-lg-3">
                    <h1 class="my-4">Johannes Fog Carporte</h1>
                    <div class="list-group">
                        <form name="GetCarport" action="FrontController" method="POST">
                            <input type="hidden" name="command" value="GetCarport">
                            <input id ="tagknap" class="list-group-item active" type="submit" name="cpType" value="Fladt tag">
                            <input id ="tagknap" class="list-group-item" type="submit" name="cpType" value="Rejst tag">
                        </form>
                    </div>
                    <br> 
                    <br>

                    <%User user = (User) session.getAttribute("user");%>
                    <%if(user == null) {%>
                    <form name="Login" action="FrontController" method="POST">
                        <input type="hidden" name="command" value="Login">
                        Email:
                        <input class="input-login" type="email" name="email" value="">
                        Password:
                        <input class="input-login" type="password" name="password" value="">
                        <input class="submit-login" type="submit" name="Login" value="Login">
                    </form>
                    <%}%>

                </div>
                <!-- /.col-lg-3 -->

                <div class="col-lg-9">

                    <div class="card mt-4">
                        <img class="card-img-top img-fluid" src="pictures/carport.JPG" alt="">
                        <div class="card-body">
                            <h3 class="card-title">Carport</h3>
                            <!-- <h4>$24.99</h4> -->
                            <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis molestias iure, ducimus!</p>
                            
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

        <!-- /.container -->

        <%@ include file = "WEB-INF/footer.jsp" %>

        <!-- Bootstrap core JavaScript -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


    </body>
</html>

