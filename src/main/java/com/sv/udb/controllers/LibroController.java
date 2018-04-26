/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controllers;

import com.sv.udb.models.Libro;
import com.sv.udb.resources.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiante
 */
public class LibroController {
    Connection conn;
    
    public static int OCUPADO = 0;
    public static int DISPONIBLE = 1;

    public LibroController() {
        conn = new ConnectionDB().getConn();
    }
    
    public List<Libro> getAll() {
        List<Libro> resp = new ArrayList<>();
        try {
            PreparedStatement cmd = this.conn.prepareStatement("SELECT * FROM libros");
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                resp.add(new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar libros: " + ex.getMessage());
        } finally {
            try {
                if (this.conn != null) {
                    if (!this.conn.isClosed()) {
                        this.conn.close();
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión al consultar libros: " + ex.getMessage());
            }
        }
        return resp;
    }
    
    public List<Libro> getByState(int esta_libr) {
        List<Libro> resp = new ArrayList<>();
        try {
            PreparedStatement cmd = this.conn.prepareStatement("SELECT * FROM libros WHERE esta_libr = ?");
            cmd.setInt(1, esta_libr);
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                resp.add(new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar libros: " + ex.getMessage());
        } finally {
            try {
                if (this.conn != null) {
                    if (!this.conn.isClosed()) {
                        this.conn.close();
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión al consultar libros: " + ex.getMessage());
            }
        }
        return resp;
    }
    
    public Libro get(int id) {
        Libro resp = null;
        try {
            PreparedStatement cmd = this.conn.prepareStatement("SELECT * FROM libros WHERE codi_libr = ?");
            cmd.setInt(1, id);
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                resp = new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar libro: " + ex.getMessage());
        } finally {
            try {
                if (this.conn != null) {
                    if (!this.conn.isClosed()) {
                        this.conn.close();
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión al consultar libro: " + ex.getMessage());
            }
        }
        return resp;
    }
    
    public boolean update(int id, int esta_libr) {
        boolean resp = false;
        
        try {
            PreparedStatement cmd = this.conn.prepareStatement("UPDATE libros SET esta_libr = ? WHERE codi_libr = ?");
            cmd.setInt(1, esta_libr);
            cmd.setInt(2, id);
            cmd.executeUpdate();
            resp = true;
        } catch (Exception ex) {
            System.err.println("Error al modificar libro " + ex.getMessage());
        } finally {
            try {
                if (this.conn != null) {
                    if (!this.conn.isClosed()) {
                        this.conn.close();
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion al modificar libro : " + e.getMessage());
            }
        }
        
        return resp;
    }
}
