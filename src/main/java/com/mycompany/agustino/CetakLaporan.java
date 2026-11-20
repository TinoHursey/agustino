package com.mycompany.agustino;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class CetakLaporan {

    public static void tampilkanLaporan() {
        String url = "jdbc:mysql://localhost:3306/minierp";
        try (Connection conn = DriverManager.getConnection(url, "root", "")) {
            InputStream reportStream = CetakLaporan.class.getResourceAsStream("/report.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(reportStream);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Laporan Data Pengguna");
            viewer.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat laporan: " + e.getMessage());
            e.printStackTrace();
        }
    }
}