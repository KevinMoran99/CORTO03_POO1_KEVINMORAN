/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controllers;

import com.sv.udb.models.Libro;
import com.sv.udb.models.Usuario;
import com.sv.udb.resources.ConnectionDB;
import com.sv.udb.utilities.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Estudiante
 */
public class PrestamoController {
    Connection conn;

    public PrestamoController() {
        conn = new ConnectionDB().getConn();
    }
    
    public boolean save(Libro libr, Usuario usua, Date fech_pres) {
        boolean resp = false;
        
        try {
            PreparedStatement cmd = this.conn.prepareStatement("INSERT INTO prestamos VALUES(null, ?, ?, ?, null)");
            System.err.println("fecha: " +fech_pres.toString());
            cmd.setInt(1, libr.getCodi_libr());
            cmd.setInt(2, usua.getCodi_usua());
            cmd.setString(3, Utils.formatDate(fech_pres, Utils.DATE_DB));
            cmd.executeUpdate();
            resp = true;
        } catch (Exception ex) {
            System.err.println("Error al guardar préstamo " + ex.getMessage());
        } finally {
            try {
                if (this.conn != null) {
                    if (!this.conn.isClosed()) {
                        this.conn.close();
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion al guardar préstamo : " + e.getMessage());
            }
        }
        
        return resp;
    }
}
