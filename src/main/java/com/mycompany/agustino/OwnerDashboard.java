package com.mycompany.agustino;

import com.mycompany.agustino.dao.ProcurementDAO;
import com.mycompany.agustino.model.ProcurementHPP;
import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OwnerDashboard extends JFrame {

    private final ProcurementDAO procurementDAO;
    private JTextField txtNamaItem, txtJumlah, txtHargaTotal;
    private JLabel lblTotalModal;
    private DefaultTableModel modelTabel;

    public OwnerDashboard(ProcurementDAO procurementDAO) {
        this.procurementDAO = procurementDAO;
        initUI();
        loadData();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        setTitle("Dasbor Owner - Pencatatan Modal FB & Kalkulasi HPP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 480);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Form Input Procurement FB
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Input Pembelian Stok (Facebook)"));
        txtNamaItem = new JTextField();
        txtJumlah = new JTextField();
        txtHargaTotal = new JTextField();
        JButton btnSimpan = new JButton("Hitung HPP & Simpan");

        formPanel.add(new JLabel("Nama Item (mis. Kitsune Fruit):"));
        formPanel.add(txtNamaItem);
        formPanel.add(new JLabel("Jumlah Beli (Unit):"));
        formPanel.add(txtJumlah);
        formPanel.add(new JLabel("Total Harga Beli (Rp):"));
        formPanel.add(txtHargaTotal);
        formPanel.add(new JLabel(""));
        formPanel.add(btnSimpan);

        // Tabel Riwayat HPP
        String[] kolom = {"ID", "Nama Item", "Jumlah", "Total Modal (Rp)", "HPP / Unit (Rp)", "Waktu Input"};
        modelTabel = new DefaultTableModel(kolom, 0);
        JTable tabel = new JTable(modelTabel);

        // Ringkasan Total Modal & Tombol Laporan Akun
        lblTotalModal = new JLabel("Total Modal Keluar: Rp 0");
        lblTotalModal.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnLaporan = new JButton("Laporan Akun");
        btnLaporan.addActionListener(e -> CetakLaporan.tampilkanLaporan());
        bottomPanel.add(lblTotalModal, BorderLayout.WEST);
        bottomPanel.add(btnLaporan, BorderLayout.EAST);

        btnSimpan.addActionListener(e -> simpanData());

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(tabel), BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    private void simpanData() {
        try {
            String nama = txtNamaItem.getText().trim();
            int qty = Integer.parseInt(txtJumlah.getText().trim());
            BigDecimal total = new BigDecimal(txtHargaTotal.getText().trim());

            if (nama.isEmpty() || qty <= 0) {
                JOptionPane.showMessageDialog(this, "Nama item dan jumlah harus valid!");
                return;
            }

            procurementDAO.save(new ProcurementHPP(nama, qty, total));
            txtNamaItem.setText("");
            txtJumlah.setText("");
            txtHargaTotal.setText("");
            loadData();
            JOptionPane.showMessageDialog(this, "Stok modal & HPP berhasil dihitung otomatis!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid: " + ex.getMessage());
        }
    }

    private void loadData() {
        modelTabel.setRowCount(0);
        for (ProcurementHPP p : procurementDAO.findAll()) {
            modelTabel.addRow(new Object[]{
                p.getId(), p.getNamaItem(), p.getJumlah(),
                p.getHargaBeliTotal(), p.getHppPerUnit(), p.getTanggalInput().toString()
            });
        }
        lblTotalModal.setText("Total Modal Keluar: Rp " + procurementDAO.getTotalModal());
    }
}