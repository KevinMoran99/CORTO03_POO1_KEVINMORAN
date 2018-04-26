/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.models;

import java.util.Date;

/**
 *
 * @author Estudiante
 */
public class Prestamo {
    private int codi_pres;
    private Libro libr;
    private Usuario usua;
    private Date fech_pres;
    private Date fech_devo;

    public Prestamo() {
    }

    public Prestamo(int codi_pres, Libro libr, Usuario usua, Date fech_pres, Date fech_devo) {
        this.codi_pres = codi_pres;
        this.libr = libr;
        this.usua = usua;
        this.fech_pres = fech_pres;
        this.fech_devo = fech_devo;
    }

    public int getCodi_pres() {
        return codi_pres;
    }

    public void setCodi_pres(int codi_pres) {
        this.codi_pres = codi_pres;
    }

    public Libro getLibr() {
        return libr;
    }

    public void setLibr(Libro libr) {
        this.libr = libr;
    }

    public Usuario getUsua() {
        return usua;
    }

    public void setUsua(Usuario usua) {
        this.usua = usua;
    }

    public Date getFech_pres() {
        return fech_pres;
    }

    public void setFech_pres(Date fech_pres) {
        this.fech_pres = fech_pres;
    }

    public Date getFech_devo() {
        return fech_devo;
    }

    public void setFech_devo(Date fech_devo) {
        this.fech_devo = fech_devo;
    }
    
    
}
