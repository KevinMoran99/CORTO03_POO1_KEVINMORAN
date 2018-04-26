/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controllers;

import com.sv.udb.models.Usuario;
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
public class UsuarioController {
    Connection conn;

    public UsuarioController() {
        conn = new ConnectionDB().getConn();
    }
    
    public List<Usuario> getAll() {
        List<Usuario> resp = new ArrayList<>();
        try {
            PreparedStatement cmd = this.conn.prepareStatement("SELECT * FROM usuarios");
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                resp.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar usuarios : " + ex.getMessage());
        } finally {
            try {
                if (this.conn != null) {
                    if (!this.conn.isClosed()) {
                        this.conn.close();
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión al consultar usuarios : " + ex.getMessage());
            }
        }
        return resp;
    }
    
    public Usuario get(int id) {
        Usuario resp = null;
        try {
            PreparedStatement cmd = this.conn.prepareStatement("SELECT * FROM usuarios WHERE codi_usua = ?");
            cmd.setInt(1, id);
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                resp = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar usuario: " + ex.getMessage());
        } finally {
            try {
                if (this.conn != null) {
                    if (!this.conn.isClosed()) {
                        this.conn.close();
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión al consultar usuario: " + ex.getMessage());
            }
        }
        return resp;
    }
}
