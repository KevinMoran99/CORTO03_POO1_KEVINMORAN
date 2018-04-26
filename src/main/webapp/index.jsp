<%-- 
    Document   : index
    Created on : 04-25-2018, 04:52:05 PM
    Author     : Estudiante
--%>

<%@page import="com.sv.udb.controllers.UsuarioController"%>
<%@page import="com.sv.udb.controllers.LibroController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
        <script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
        <script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#tab-01" aria-controls="tab-01" role="tab" data-toggle="tab">Préstamos</a></li>
                <li role="presentation"><a href="#tab-02" aria-controls="tab-02" role="tab" data-toggle="tab">Tab 02</a></li>
                <li role="presentation"><a href="#tab-03" aria-controls="tab-03" role="tab" data-toggle="tab">Tab 03</a></li>
            </ul>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="tab-01">
                    <div class="row">
                        <h1>Préstamos</h1>
                        <div class="col-md-5">
                            <div class="panel panel-primary">
                                <div class="panel-heading">Formulario</div>
                                <div class="panel-body">
                                    <c:choose>
                                        <c:when test="${error}">
                                            <div class="alert alert-danger">
                                                ${message}
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="alert alert-success">
                                                ${message}
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <form method="POST" action="PrestamoServlet" name="Demo">
                                        <input type="hidden" name="id" id="id" value="${id}"/>
                                        <div class="form-group">
                                            <label for="usuario">Proveedor:</label>
                                            <select class="form-control" name="usuario" id="usuario">
                                                <c:forEach var="usuarioItem" items="<%=new UsuarioController().getAll()%>">
                                                    <c:choose>
                                                        <c:when test="${usuarioItem.getCodi_usua() == usuario}">
                                                            <option value="${usuarioItem.getCodi_usua()}" selected>${usuarioItem}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${usuarioItem.getCodi_usua()}">${usuarioItem}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="libro">Libro:</label>
                                            <select class="form-control" name="libro" id="libro">
                                                <c:forEach var="libroItem" items="<%=new LibroController().getAll()%>">
                                                    <c:choose>
                                                        <c:when test="${libroItem.getCodi_libr() == libro}">
                                                            <option value="${libroItem.getCodi_libr()}" selected>${libroItem}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${libroItem.getCodi_libr()}">${libroItem}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="fecha">Fecha de préstamo</label>
                                            <input type="datetime-local" class="form-control" name="fecha" id="fecha" required/>
                                        </div>
                                        

                                        <c:choose>
                                            <c:when test="${update == null}">
                                                <input type="submit" class="btn btn-default" name="presBtn" value="Prestar"/>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="submit" class="btn btn-default" name="presBtn" value="Nuevo"/>
                                                <input type="submit" class="btn btn-primary" name="presBtn" value="Modificar"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="tab-02">
                    content of taba
                </div>
                <div role="tabpanel" class="tab-pane" id="tab-03">
                    content of tabe
                </div>
            </div>
        </div>
    </body>
</html>
