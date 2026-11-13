package com.mycompany.agustino;

import com.mycompany.agustino.dao.RedfingerSlotDAO;
import com.mycompany.agustino.model.RedfingerSlot;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WorkerDashboard extends javax.swing.JFrame {

    private final RedfingerSlotDAO slotDAO;
    private JTable tabelSlot;
    private DefaultTableModel modelTabel;
    private JButton btnRefresh;
    private JButton btnTambahDummy;

    public WorkerDashboard(RedfingerSlotDAO slotDAO) {
        this.slotDAO = slotDAO;
        initComponentsCustom();
        loadDataSlot();
        setLocationRelativeTo(null); // Tampil di tengah layar
    }

    private void initComponentsCustom() {
        setTitle("Dasbor Worker - Monitoring Slot Cloud Redfinger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 420);

        JPanel panel = new JPanel(new java.awt.BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header Label
        JLabel lblTitle = new JLabel("Pusat Kendali Worker: Slot Redfinger & Akun Roblox", SwingConstants.CENTER);
        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        panel.add(lblTitle, java.awt.BorderLayout.NORTH);

        // Model Tabel Kolom Sesuai Kebutuhan Modul 2
        String[] kolom = {"ID", "No. Cloud", "No. Aplikasi", "Akun Roblox", "Sisa Slot", "Status / Jam Selesai"};
        modelTabel = new DefaultTableModel(kolom, 0);
        tabelSlot = new JTable(modelTabel);
        panel.add(new JScrollPane(tabelSlot), java.awt.BorderLayout.CENTER);

        // Tombol Aksi di Bawah
        JPanel panelBawah = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        btnTambahDummy = new JButton("+ Tambah Akun Demo");
        btnRefresh = new JButton("Refresh Data");

        // Action: Tambah 1 data dummy langsung ke database via Spring DAO
        btnTambahDummy.addActionListener(e -> tambahSlotDemo());
        // Action: Muat ulang data dari database
        btnRefresh.addActionListener(e -> loadDataSlot());

        panelBawah.add(btnTambahDummy);
        panelBawah.add(btnRefresh);
        panel.add(panelBawah, java.awt.BorderLayout.SOUTH);

        setContentPane(panel);
    }

    // Mengambil data RedfingerSlot via Spring DAO dan dimasukkan ke tabel GUI
    public void loadDataSlot() {
        modelTabel.setRowCount(0); // Bersihkan tabel
        try {
            for (RedfingerSlot s : slotDAO.findAll()) {
                modelTabel.addRow(new Object[]{
                    s.getId(),
                    s.getNomorCloud(),
                    s.getNomorAplikasi(),
                    s.getAkunRoblox(),
                    s.getSisaSlot() + " Slot",
                    s.getJamSelesaiJoki() != null ? s.getJamSelesaiJoki().toString() : "Tersedia"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat tabel: " + ex.getMessage());
        }
    }

    // Helper untuk menambah data akun cloud ke database via Spring DAO
    private void tambahSlotDemo() {
        try {
            RedfingerSlot demo = new RedfingerSlot();
            demo.setNomorCloud("Cloud-" + (modelTabel.getRowCount() + 1));
            demo.setNomorAplikasi(1);
            demo.setAkunRoblox("RobloxPlayer_" + (int)(Math.random() * 900 + 100));
            demo.setSisaSlot(4); // default 4 slot
            demo.setJamSelesaiJoki(java.time.LocalDateTime.now().plusHours(2));

            slotDAO.save(demo);
            loadDataSlot(); // Tampilkan langsung di tabel
            JOptionPane.showMessageDialog(this, "Berhasil menambah akun virtual cloud baru ke database!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal simpan: " + ex.getMessage());
        }
    }
}