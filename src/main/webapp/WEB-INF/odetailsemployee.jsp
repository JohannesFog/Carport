<%-- 
    Document   : odetailsemployee
    Created on : 05-12-2017, 10:24:29
    Author     : lene_
--%>

<%@page import="FunctionLayer.User"%>
<%@page import="FunctionLayer.LogicFacade"%>
<%@page import="FunctionLayer.BillOfMaterials"%>
<%@page import="PresentationLayer.Rendering"%>
<%@page import="FunctionLayer.Order"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Ordredetaljer</title>

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/shop-item.css" rel="stylesheet">
        <link href="css/mycss.css" rel="stylesheet">

    </head>
    <body>
        <%Order order = (Order) session.getAttribute("order");%>
        <%Rendering render = new Rendering();%>

        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
            <div class="container">
                <a class="navbar-brand" href="index.jsp">Johannes Fog</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="index.jsp">Hjem
                                <span class="sr-only">(current)</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Om</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Serviceydelser</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Kontakt</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container">
            <div class="row">


                <div class="col-lg-3">
                    <h1 class="my-4">Johannes Fog Carporte</h1>
                    <div class="list-group">


                        <!--<a href="#" class="list-group-item active">Fladt tag</a> -->
                        <!--<a href="#" class="list-group-item">Rejst tag</a>-->


                        <form name="GetCarport" action="FrontController" method="POST">
                            <input type="hidden" name="command" value="GetCarport">
                            <input class="list-group-item active" type="submit" name="cpType" value="Fladt tag">
                            <input class="list-group-item" type="submit" name="cpType" value="Rejst tag">
                        </form>


                    </div>
                    <br> 
                    <br>

                    <%User user = (User) session.getAttribute("user");%>
                    <%if (user == null) {%>
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

                        <div class="card-body">
                            <h3 class="card-title">Detaljer for ordre med id: <%=order.getoId()%></h3>
                            <!-- <h4>$24.99</h4> -->
                            <p class="card-text">
                                <%=render.showOrderDetails(order)%>
                                <br>

                                <%BillOfMaterials bom = LogicFacade.getBillOfMaterials(order);%>
                                <%double price = LogicFacade.getCarportPrice(bom);%>


                                <%=render.showPrice(price)%>
                                <br><br>
                                <%=render.showBillOfMaterials(bom)%>
                            </p>
                            <!--  <span class="text-warning">&#9733; &#9733; &#9733; &#9733; &#9734;</span> -->
                            <!-- 4.0 stars -->
                        </div>
                    </div>



                    <div class="card card-outline-secondary my-4">
                        <div class="card-header">
                            Tegning
                        </div>
                        <div class="card-body">
                            <p>
                                <%=LogicFacade.getDrawing(bom, order)%>
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

        <!-- /.container -->

        <!-- Footer -->
        <footer class="py-5 bg-dark">
            <div class="container">
                <p class="m-0 text-center text-white">Copyright &copy; Johannes Fog 2017</p>
            </div>
            <!-- /.container -->
        </footer>

        <!-- Bootstrap core JavaScript -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


    </body>
</html>
