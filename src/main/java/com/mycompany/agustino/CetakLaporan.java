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
        cetakDariResource("/report.jrxml");
    }

    public static void tampilkanLaporanPembelian() {
        cetakDariResource("/laporan_pembelian.jrxml");
    }

    public static void tampilkanLaporanPenjualan() {
        cetakDariResource("/laporan_penjualan.jrxml");
    }

    private static void cetakDariResource(String pathJrxml) {
        String url = "jdbc:mysql://localhost:3306/minierp?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            InputStream is = CetakLaporan.class.getResourceAsStream(pathJrxml);
            if (is == null) {
                JOptionPane.showMessageDialog(null, "File template tidak ditemukan: " + pathJrxml);
                return;
            }
            JasperReport jr = JasperCompileManager.compileReport(is);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mencetak laporan: " + e.getMessage());
            e.printStackTrace();
        }
    }
}