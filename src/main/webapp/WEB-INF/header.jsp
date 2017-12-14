<%-- 
    Document   : header
    Created on : 14-12-2017, 09:20:08
    Author     : Mikkel Lindstrøm <Mikkel.Lindstrøm>
--%>

        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
            <div class="container">
                <a class="navbar-brand" href="index.jsp">Johannes Fog</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <form class ="nav-link" action="FrontController" method="POST">
                             <input type="hidden" name="command" value="GetBack">
                             <input type="hidden" name="from" value="home">
                             <input type="submit" name="home" value="Hjem">
                            </form> 
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
                        <li class="nav-item">
                             <form class ="nav-link" action="FrontController" method="POST">
                             <input type="hidden" name="command" value="GetBack">
                             <input type="hidden" name="from" value="logout">
                             <input type="submit" name="home" value="Log out">
                            </form> 
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
