/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j2p;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import sun.security.x509.DistributionPointName;
/*
for Jasper Report compilation following packages/libraries are required:
    jasperreports-5.6.0
    common-beanutils-1.8.2.jar
    common-collections-3.2.1.jar
    common-digester-2.1.jar
    common-logging-1.1.jar
    iText-2.1.7.js2.jar
    path is: iReport5.6.0\irpoer\modules\
*/



/**
 *
 * @author dell
 */
public class J2P {
  
//    private String dbname,user,pass;

    public static void Print(String dbname,String user,String pass,Map map) throws SQLException, JRException, IOException {
        // TODO code application logic here
        String fileNameJrxml = "group_report.jrxml"; //path of jasper report file
        String output = "group_report_output.pdf";
        Connection connInstance = getDatabaseConnection(dbname, user, pass);
        System.out.println("Loading the JRXML...");
        JasperDesign jasperDesign = JRXmlLoader.load(fileNameJrxml);
        System.out.println("Compiling the JRXML....");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        System.out.println("Filling the jasper report...");
   
        JasperPrint jasperPrint = (JasperPrint) JasperFillManager.fillReport(jasperReport,map , connInstance);
        System.out.println("Print output to PDF");
        JasperExportManager.exportReportToPdfFile(jasperPrint, output);
        connInstance.close();
        if (Desktop.isDesktopSupported()) {
    try {
        File myFile = new File(output);
        Desktop.getDesktop().open(myFile);
    } catch (IOException ex) {
        // no application registered for PDFs
    }
}
    }
    
    public static Connection getDatabaseConnection(String dbase, String user, String pass) throws SQLException{
        Connection conn = DriverManager.getConnection(dbase,user,pass);
        return conn;
        
    }
    
}
