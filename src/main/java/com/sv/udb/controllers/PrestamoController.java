/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controllers;

import com.sv.udb.models.Libro;
import com.sv.udb.models.Prestamo;
import com.sv.udb.models.Usuario;
import com.sv.udb.resources.ConnectionDB;
import com.sv.udb.utilities.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Estudiante
 */
public class PrestamoController {
    Connection conn;

    public PrestamoController() {
        conn = new ConnectionDB().getConn();
    }
    
    public List<Prestamo> getAll() {
        List<Prestamo> resp = new ArrayList<>();
        try {
            PreparedStatement cmd = this.conn.prepareStatement("SELECT * FROM prestamos ORDER BY fech_devo, codi_pres DESC");
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                resp.add(new Prestamo(
                        rs.getInt(1), 
                        new LibroController().get(rs.getInt(2)), 
                        new UsuarioController().get(rs.getInt(3)),
                        rs.getTimestamp(4),
                        rs.getTimestamp(5)
                ));
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar préstamos : " + ex.getMessage());
        } finally {
            try {
                if (this.conn != null) {
                    if (!this.conn.isClosed()) {
                        this.conn.close();
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión al consultar préstamos : " + ex.getMessage());
            }
        }
        return resp;
    }
    
    public Prestamo get(int id) {
        Prestamo resp = null;
        try {
            PreparedStatement cmd = this.conn.prepareStatement("SELECT * FROM prestamos WHERE codi_pres = ?");
            cmd.setInt(1, id);
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                resp = new Prestamo(
                        rs.getInt(1), 
                        new LibroController().get(rs.getInt(2)), 
                        new UsuarioController().get(rs.getInt(3)),
                        rs.getTimestamp(4),
                        rs.getTimestamp(5)
                );
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar préstamo : " + ex.getMessage());
        } finally {
            try {
                if (this.conn != null) {
                    if (!this.conn.isClosed()) {
                        this.conn.close();
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión al consultar préstamo : " + ex.getMessage());
            }
        }
        return resp;
    }
    
    public boolean save(Libro libr, Usuario usua, Date fech_pres) {
        boolean resp = false;
        
        try {
            PreparedStatement cmd = this.conn.prepareStatement("INSERT INTO prestamos VALUES(null, ?, ?, ?, null)");
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
    
    public boolean update(int id, Date fecha) {
        boolean resp = false;
        
        try {
            PreparedStatement cmd = this.conn.prepareStatement("UPDATE prestamos SET fech_devo = ? WHERE codi_pres = ?");
            cmd.setString(1, Utils.formatDate(fecha, Utils.DATE_DB));
            cmd.setInt(2, id);
            cmd.executeUpdate();
            resp = true;
        } catch (Exception ex) {
            System.err.println("Error al modificar préstamo " + ex.getMessage());
        } finally {
            try {
                if (this.conn != null) {
                    if (!this.conn.isClosed()) {
                        this.conn.close();
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion al modificar préstamo : " + e.getMessage());
            }
        }
        
        return resp;
    }
}
