/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.servlets;

import com.sv.udb.controllers.LibroController;
import com.sv.udb.controllers.PrestamoController;
import com.sv.udb.controllers.UsuarioController;
import com.sv.udb.models.Libro;
import com.sv.udb.models.Prestamo;
import com.sv.udb.models.Usuario;
import com.sv.udb.utilities.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Estudiante
 */
@WebServlet(name = "PrestamoServlet", urlPatterns = {"/PrestamoServlet"})
public class PrestamoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            boolean isPost = request.getMethod().equals("POST");
            String message = "";
            boolean error = false;
            
            if(!isPost) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
            
            else {
                String CRUD = request.getParameter("presBtn");
                if(CRUD.equals("Prestar")) {
                    Usuario usuario = new UsuarioController().get(Integer.parseInt(request.getParameter("usuario")));
                    Libro libro = new LibroController().get(Integer.parseInt(request.getParameter("libro")));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm");
                    Date fecha = format.parse(request.getParameter("fecha"));
                    
                    if (new PrestamoController().save(libro, usuario, fecha)) {
                        
                        //Actualizando disponibilidad de libro
                        if (new LibroController().update(Integer.parseInt(request.getParameter("libro")), LibroController.OCUPADO)) {
                            message = "Libro prestado";
                        }
                        else {
                            message = "Préstamo guardado; error al actualizar libro";
                            error = true;
                        }
                    }
                    else {
                        message = "Error al guardar";
                        error = true;
                    }
                }
                else if (CRUD.equals("Consultar")) {
                    int code = Integer.parseInt(request.getParameter("codiPres") == null ? "-1" : request.getParameter("codiPres"));
                    Prestamo pres = new PrestamoController().get(code);
                    if (pres != null) {
                        request.setAttribute("codi_pres", pres.getCodi_pres());
                        request.setAttribute("libr", pres.getLibr());
                        request.setAttribute("usua", pres.getUsua());
                        request.setAttribute("fech_pres", pres.getFech_pres());
                        if (pres.getFech_devo() != null) {
                            request.setAttribute("fech_devo", pres.getFech_devo());
                        }

                        message = "Información consultada";

                        request.setAttribute("update", "true");
                        
                    }
                    else {
                        message = "Error al consultar";
                        error = true;
                    }
                }
                else if(CRUD.equals("Devolver")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm");
                    Date fecha = format.parse(request.getParameter("fecha"));
                    
                    if (new PrestamoController().update(id, fecha)) {
                        
                        Prestamo pres = new PrestamoController().get(id);
                        
                        //Actualizando disponibilidad de libro
                        if (new LibroController().update(pres.getLibr().getCodi_libr(), LibroController.DISPONIBLE)) {
                            message = "Libro devuelto";
                        }
                        else {
                            message = "Préstamo concluido; error al actualizar libro";
                            error = true;
                        }
                    }
                    else {
                        message = "Error al guardar";
                        error = true;
                    }
                }
                
                request.setAttribute("tab", 1);
                request.setAttribute("message", message);
                request.setAttribute("error", error);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (ParseException ex) {
            Logger.getLogger(PrestamoServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //throw new ServletException(e);
            request.setAttribute("message", "Llene todos los campos con datos válidos");
            request.setAttribute("error", true);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
