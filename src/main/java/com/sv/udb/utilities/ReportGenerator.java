/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.utilities;

import com.sv.udb.resources.ConnectionDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author kevin
 */
public class ReportGenerator {
    
    public static String LIBRO = "Libros";
    public static String PRESTAMO = "Prestamos";
    
    public static byte[] makeReport(ServletContext ctx, String fileName) throws SQLException {
        HashMap map = new HashMap();
        byte[] bytes = null;
        //Conexion
        Connection conn = new ConnectionDB().getConn();
        
        try {
            String jasperFileName = ctx.getRealPath("/reports/" + fileName + ".jasper");
            
            //seteando los parametros que recibe el reporte
            /*map = new HashMap();
            map.put("fromDate",from);
            map.put("toDate", to);*/
            
            //Para generar al reporte directamente con una conexión y query (debería ser suficiente para reportes basados en tablas)
            bytes = JasperRunManager.runReportToPdf(jasperFileName, map, conn);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        finally {
            if(!conn.isClosed())
            {
                conn.close();
            }
            
            return bytes;
        }
    }
}
