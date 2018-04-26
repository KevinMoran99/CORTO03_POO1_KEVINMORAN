<%-- 
    Document   : index
    Created on : 04-25-2018, 04:52:05 PM
    Author     : Estudiante
--%>

<%@page import="com.sv.udb.models.Prestamo"%>
<%@page import="com.sv.udb.controllers.PrestamoController"%>
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
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/pdfjs/build/pdf.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/js/pdfobject.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/js/report.js"></script>
        <style>
            /*
            PDFObject appends the classname "pdfobject-container" to the target element.
            This enables you to style the element differently depending on whether the embed was successful.
            In this example, a successful embed will result in a large box.
            A failed embed will not have dimensions specified, so you don't see an oddly large empty box.
            */
            .pdfobject-container {
                    width: 100%;
                    height: 600px;
                    margin: 2em 0;
            }
            .pdfobject { border: solid 1px #666; }
        </style>
    </head>
    <body>
        <div class="container">
            <ul class="nav nav-tabs" role="tablist">
                <c:choose>
                    <c:when test="${tab == 1 || tab == null}">
                        <li role="presentation" class="active"><a href="#tab-01" class="selectedTab" aria-controls="tab-01" role="tab" data-toggle="tab">Préstamos</a></li>
                    </c:when>
                    <c:otherwise>
                    <li role="presentation"><a href="#tab-01" id="firstTab" aria-controls="tab-01" role="tab" data-toggle="tab">Préstamos</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${tab == 2}">
                        <li role="presentation" class="active"><a href="#tab-02" class="selectedTab" aria-controls="tab-02" role="tab" data-toggle="tab">Libros</a></li>
                    </c:when>
                    <c:otherwise>
                        <li role="presentation"><a href="#tab-02" aria-controls="tab-02" role="tab" data-toggle="tab">Libros</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${tab == 3}">
                        <li role="presentation" class="active"><a href="#tab-03" class="selectedTab" aria-controls="tab-03" role="tab" data-toggle="tab">Reportes</a></li>
                    </c:when>
                    <c:otherwise>
                        <li role="presentation"><a href="#tab-03" aria-controls="tab-03" role="tab" data-toggle="tab">Reportes</a></li>
                    </c:otherwise>
                </c:choose>
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
                                        <input type="hidden" name="id" id="id" value="${codi_pres}"/>
                                        <div class="form-group">
                                            <label for="usuario">Usuario:</label>
                                            <c:choose>
                                                <c:when test="${update}">
                                                    <p>${usua}</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <select class="form-control" name="usuario" id="usuario">
                                                        <c:forEach var="usuarioItem" items="<%=new UsuarioController().getAll()%>">
                                                            <option value="${usuarioItem.getCodi_usua()}">${usuarioItem}</option>
                                                        </c:forEach>
                                                    </select>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="form-group">
                                            <label for="libro">Libro:</label>
                                            <c:choose>
                                                <c:when test="${update}">
                                                    <p>${libr}</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <select class="form-control" name="libro" id="libro">
                                                        <c:forEach var="libroItem" items="<%=new LibroController().getByState(LibroController.DISPONIBLE)%>">
                                                            <option value="${libroItem.getCodi_libr()}">${libroItem}</option>
                                                        </c:forEach>
                                                    </select>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>Fecha de préstamo</label>
                                            <c:choose>
                                                <c:when test="${update}">
                                                    <p><fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${fech_pres}" /></p>
                                                    
                                                    <div class="form-group">
                                                        <label>Fecha de devolución</label>
                                                        <c:choose>
                                                            <c:when test="${fech_devo != null}">
                                                                <p><fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${fech_devo}" /></p>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="datetime-local" class="form-control" name="fecha" id="fecha"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="datetime-local" class="form-control" name="fecha" id="fecha" required/>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        

                                        <c:choose>
                                            <c:when test="${update == null}">
                                                <input type="submit" class="btn btn-default" name="presBtn" value="Prestar"/>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="submit" class="btn btn-default" name="presBtn" value="Nuevo"/>
                                                <c:choose>
                                                    <c:when test="${fech_devo == null}">
                                                        <input type="submit" class="btn btn-primary" name="presBtn" value="Devolver"/>
                                                    </c:when>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-7">
                            <div class="panel panel-primary">
                                <div class="panel-heading">Tabla</div>
                                <div class="panel-body">
                                    <form method="POST" action="PrestamoServlet" name="Tabl">
                                        <display:table id="tablPres" name="<%= new PrestamoController().getAll()%>">
                                            <display:column title="Cons">
                                                <input type="radio" name="codiPres" value="${tablPres.codi_pres}"/>
                                            </display:column>
                                            <display:column property="libr" title="Libro" sortable="true" />
                                            <display:column property="usua" title="Usuario" sortable="true" />
                                            <display:column property="fech_pres" title="Fecha de préstamo" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
                                            <display:column property="fech_devo" title="Fecha de devolución" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
                                        </display:table>
                                        <input type="submit" class="btn btn-success" name="presBtn" value="Consultar"/>
                                    </form>
                                </div>
                            </div>
                        </div>              
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="tab-02">
                    <div class="row">
                        <h1>Libros</h1>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <form method="POST" action="LibroServlet">
                                <label for="tipo">Ver libros:</label>
                                <select id="tipo" class="form-control" name="tipo" onchange="this.form.submit()">
                                    <c:choose>
                                        <c:when test="${estado == 1}">
                                            <option value="0">Prestados</option>
                                            <option value="1" selected>No prestados</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="0" selected>Prestados</option>
                                            <option value="1">No prestados</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </form>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Tabla</div>
                            <div class="panel-body">
                                <c:choose>
                                    <c:when test="${estado == 1}">
                                        <display:table id="tablPres" name='<%= new LibroController().getByState(1)%>'>
                                            <display:column property="nomb_libr" title="Nombre" sortable="true" />
                                            <display:column property="auto_libr" title="Autor" sortable="true" />
                                            <display:column property="gene_libr" title="Género" sortable="true" />
                                            <display:column property="anio_libr" title="Año" sortable="true" />
                                        </display:table>
                                    </c:when>
                                    <c:otherwise>
                                        <display:table id="tablPres" name='<%= new LibroController().getByState(0)%>'>
                                            <display:column property="nomb_libr" title="Nombre" sortable="true" />
                                            <display:column property="auto_libr" title="Autor" sortable="true" />
                                            <display:column property="gene_libr" title="Género" sortable="true" />
                                            <display:column property="anio_libr" title="Año" sortable="true" />
                                        </display:table>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="tab-03">
                    <div class="row">
                        <h1>Reportes</h1>
                        <div class="panel panel-primary">
                            <div class="panel-heading">Generar reporte</div>
                                <div class="panel-body">
                                    <form id="reportForm" data-ctxt="${pageContext.request.contextPath}" data-report="Este atributo se llenara al hacer click en un boton">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <input id="btnReport" type="submit" class="btn btn-success col-sm-2" name="reportBtn" value="Libros"/>
                                            </div>
                                        </div>
                                        <br>
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <input id="btnReport2" type="submit" class="btn btn-success col-sm-2" name="reportBtn" value="Prestamos"/>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                        
        <!-- Modal -->
        <div class="modal fade" id="modalReport" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title" id="myModalLabel">Reporte</h4>
                        </div>
                    <div class="modal-body">
                        <div id="pdfViewer"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Salir</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>
        $(document).ready(function(){
            $("#firstTab").click();
            $(".selectedTab").click();
        });
    </script>
</html>
