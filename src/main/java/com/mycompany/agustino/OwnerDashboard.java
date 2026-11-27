package com.mycompany.agustino;

import com.mycompany.agustino.dao.ProcurementDAO;
import com.mycompany.agustino.dao.SalesDAO;
import com.mycompany.agustino.model.ProcurementHPP;
import com.mycompany.agustino.model.SalesTransaction;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class OwnerDashboard extends JFrame {

    private final ProcurementDAO procurementDAO;
    private final SalesDAO salesDAO;

    // Komponen Form Beli (Procurement)
    private JComboBox<String> cmbItemBeli;
    private JTextField txtJumlahBeli, txtHargaBeliTotal;

    // Komponen Form Jual (Sales & Pengurangan Stok)
    private JComboBox<String> cmbItemJual;
    private JTextField txtJumlahJual, txtHargaJualTotal;

    // Label Ringkasan & Alert
    private JLabel lblTotalModalValue, lblTotalProfitValue, lblAlertStockDetail, lblAlertSlotDetail;
    private DefaultTableModel modelTabelBeli, modelTabelJual, modelTabelStok;

    private final NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    public OwnerDashboard(ProcurementDAO procurementDAO) {
        this.procurementDAO = procurementDAO;
        this.salesDAO = new SalesDAO();
        initUI();
        loadData();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        setTitle("Ayabe Store Management — Owner Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 680);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 14));
        mainPanel.setBackground(new Color(248, 250, 252));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        // ================= 1. HEADER & DUAL ALERT =================
        JPanel topSection = new JPanel(new BorderLayout(0, 12));
        topSection.setOpaque(false);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)
        ));

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBox.setOpaque(false);
        JLabel lblTitle = new JLabel("Ayabe Store Management — Owner Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(30, 41, 59));

        JLabel lblSubtitle = new JLabel("Manajemen Modal Facebook, Penjualan Itemku, Sisa Stok & Laba Bersih");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(100, 116, 139));

        titleBox.add(lblTitle);
        titleBox.add(lblSubtitle);

        // Panel 3 Tombol Cetak JasperReports di Header Kanan
        JPanel actionButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionButtons.setOpaque(false);

        JButton btnLapBeli = createHeaderButton("Cetak Pembelian", new Color(37, 99, 235));
        btnLapBeli.addActionListener(e -> CetakLaporan.tampilkanLaporanPembelian());

        JButton btnLapJual = createHeaderButton("Cetak Penjualan", new Color(22, 163, 74));
        btnLapJual.addActionListener(e -> CetakLaporan.tampilkanLaporanPenjualan());

        JButton btnLaporan = createHeaderButton("Laporan Akun", new Color(30, 41, 59));
        btnLaporan.addActionListener(e -> CetakLaporan.tampilkanLaporan());

        actionButtons.add(btnLapBeli);
        actionButtons.add(btnLapJual);
        actionButtons.add(btnLaporan);

        headerPanel.add(titleBox, BorderLayout.WEST);
        headerPanel.add(actionButtons, BorderLayout.EAST);

        // Dual Alert Panel
        JPanel alertGrid = new JPanel(new GridLayout(1, 2, 14, 0));
        alertGrid.setOpaque(false);

        JPanel pnlAlertStock = new JPanel(new GridLayout(2, 1, 0, 4));
        pnlAlertStock.setBackground(new Color(254, 243, 199));
        pnlAlertStock.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(245, 158, 11), 1),
                BorderFactory.createEmptyBorder(9, 14, 9, 14)
        ));
        JLabel lblAlertStockTitle = new JLabel("PERINGATAN SISA STOK MENIPIS (RESTOCK FB)");
        lblAlertStockTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblAlertStockTitle.setForeground(new Color(146, 64, 14));

        lblAlertStockDetail = new JLabel("Memeriksa sisa stok aktual...");
        lblAlertStockDetail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAlertStockDetail.setForeground(new Color(120, 53, 15));
        pnlAlertStock.add(lblAlertStockTitle);
        pnlAlertStock.add(lblAlertStockDetail);

        JPanel pnlAlertSlot = new JPanel(new GridLayout(2, 1, 0, 4));
        pnlAlertSlot.setBackground(new Color(254, 226, 226));
        pnlAlertSlot.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(239, 68, 68), 1),
                BorderFactory.createEmptyBorder(9, 14, 9, 14)
        ));
        JLabel lblAlertSlotTitle = new JLabel("ALERT KAPASITAS SLOT PENAMPUNG (MERAH)");
        lblAlertSlotTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblAlertSlotTitle.setForeground(new Color(153, 27, 27));

        lblAlertSlotDetail = new JLabel("Sisa Slot Redfinger: Pantau kapasitas sebelum restock baru!");
        lblAlertSlotDetail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAlertSlotDetail.setForeground(new Color(127, 29, 29));
        pnlAlertSlot.add(lblAlertSlotTitle);
        pnlAlertSlot.add(lblAlertSlotDetail);

        alertGrid.add(pnlAlertStock);
        alertGrid.add(pnlAlertSlot);

        topSection.add(headerPanel, BorderLayout.NORTH);
        topSection.add(alertGrid, BorderLayout.SOUTH);

        // ================= 2. TABBED FORM INPUT (BELI & JUAL) =================
        JPanel centerSection = new JPanel(new BorderLayout(0, 12));
        centerSection.setOpaque(false);

        JTabbedPane formTabs = new JTabbedPane();
        formTabs.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formTabs.addTab("  + Input Pembelian Stok (FB)  ", createFormPembelianPanel());
        formTabs.addTab("  - Input Penjualan & Kurangi Stok  ", createFormPenjualanPanel());

        // ================= 3. TABBED DATA TABLES =================
        JTabbedPane tableTabs = new JTabbedPane();
        tableTabs.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Tab Tabel 1: Ringkasan Sisa Stok Aktual
        String[] kolomStok = {"Nama Item", "Total Beli", "Total Terjual", "Sisa Stok Aktual", "HPP Terakhir / Unit"};
        modelTabelStok = new DefaultTableModel(kolomStok, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tblStok = new JTable(modelTabelStok);
        styleTable(tblStok, new int[]{1, 2, 3}, new int[]{4});
        tableTabs.addTab("  Ringkasan Sisa Stok  ", wrapTable(tblStok));

        // Tab Tabel 2: Riwayat Pembelian (Modal)
        String[] kolomBeli = {"ID", "Nama Item", "Jumlah", "Total Modal", "HPP / Unit", "Waktu Input"};
        modelTabelBeli = new DefaultTableModel(kolomBeli, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tblBeli = new JTable(modelTabelBeli);
        styleTable(tblBeli, new int[]{0, 2}, new int[]{3, 4});
        tableTabs.addTab("  Riwayat Pembelian (Modal)  ", wrapTable(tblBeli));

        // Tab Tabel 3: Riwayat Penjualan & Keuntungan
        String[] kolomJual = {"ID", "Nama Item", "Terjual", "Harga Jual", "Modal (HPP)", "Keuntungan", "Waktu Jual"};
        modelTabelJual = new DefaultTableModel(kolomJual, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tblJual = new JTable(modelTabelJual);
        styleTable(tblJual, new int[]{0, 2}, new int[]{3, 4, 5});
        tableTabs.addTab("  Riwayat Penjualan & Profit  ", wrapTable(tblJual));

        centerSection.add(formTabs, BorderLayout.NORTH);
        centerSection.add(tableTabs, BorderLayout.CENTER);

        // ================= 4. FOOTER RINGKASAN FINANSIAL =================
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 16, 0));
        bottomPanel.setOpaque(false);

        // Kartu Total Modal
        JPanel cardModal = new JPanel(new BorderLayout());
        cardModal.setBackground(Color.WHITE);
        cardModal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)
        ));
        JLabel lblModalText = new JLabel("Total Modal Keluar:");
        lblModalText.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblModalText.setForeground(new Color(71, 85, 105));
        lblTotalModalValue = new JLabel("Rp 0");
        lblTotalModalValue.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTotalModalValue.setForeground(new Color(37, 99, 235));
        cardModal.add(lblModalText, BorderLayout.WEST);
        cardModal.add(lblTotalModalValue, BorderLayout.EAST);

        // Kartu Total Keuntungan
        JPanel cardProfit = new JPanel(new BorderLayout());
        cardProfit.setBackground(Color.WHITE);
        cardProfit.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)
        ));
        JLabel lblProfitText = new JLabel("Total Keuntungan Bersih:");
        lblProfitText.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblProfitText.setForeground(new Color(71, 85, 105));
        lblTotalProfitValue = new JLabel("Rp 0");
        lblTotalProfitValue.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTotalProfitValue.setForeground(new Color(22, 163, 74)); // Hijau Profit
        cardProfit.add(lblProfitText, BorderLayout.WEST);
        cardProfit.add(lblTotalProfitValue, BorderLayout.EAST);

        bottomPanel.add(cardModal);
        bottomPanel.add(cardProfit);

        mainPanel.add(topSection, BorderLayout.NORTH);
        mainPanel.add(centerSection, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JButton createHeaderButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(135, 36));
        return btn;
    }

    private JPanel createFormPembelianPanel() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 12);

        String[] items = {"Kitsune", "Dragon", "Leopard", "Dough", "T-Rex", "Mammoth", "Venom", "Spirit"};
        cmbItemBeli = new JComboBox<>(items);
        cmbItemBeli.setEditable(true);
        cmbItemBeli.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbItemBeli.setPreferredSize(new Dimension(200, 36));
        gbc.gridx = 0; gbc.weightx = 0.30;
        card.add(createLabeledField("Nama Item / Buah", cmbItemBeli), gbc);

        txtJumlahBeli = createStyledTextField();
        gbc.gridx = 1; gbc.weightx = 0.20;
        card.add(createLabeledField("Jumlah Beli (Unit)", txtJumlahBeli), gbc);

        txtHargaBeliTotal = createStyledTextField();
        gbc.gridx = 2; gbc.weightx = 0.25;
        card.add(createLabeledField("Total Harga Beli (Rp)", txtHargaBeliTotal), gbc);

        JButton btnSimpanBeli = new JButton("Hitung HPP & Tambah Stok");
        btnSimpanBeli.setBackground(new Color(37, 99, 235));
        btnSimpanBeli.setForeground(Color.WHITE);
        btnSimpanBeli.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSimpanBeli.setFocusPainted(false);
        btnSimpanBeli.setBorderPainted(false);
        btnSimpanBeli.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSimpanBeli.setPreferredSize(new Dimension(190, 36));
        btnSimpanBeli.addActionListener(e -> simpanPembelian());

        gbc.gridx = 3; gbc.weightx = 0.25; gbc.insets = new Insets(20, 0, 0, 0);
        card.add(btnSimpanBeli, gbc);
        return card;
    }

    private JPanel createFormPenjualanPanel() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 12);

        String[] items = {"Kitsune", "Dragon", "Leopard", "Dough", "T-Rex", "Mammoth", "Venom", "Spirit"};
        cmbItemJual = new JComboBox<>(items);
        cmbItemJual.setEditable(true);
        cmbItemJual.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbItemJual.setPreferredSize(new Dimension(200, 36));
        gbc.gridx = 0; gbc.weightx = 0.30;
        card.add(createLabeledField("Item Terjual", cmbItemJual), gbc);

        txtJumlahJual = createStyledTextField();
        gbc.gridx = 1; gbc.weightx = 0.20;
        card.add(createLabeledField("Jumlah Terjual (Unit)", txtJumlahJual), gbc);

        txtHargaJualTotal = createStyledTextField();
        gbc.gridx = 2; gbc.weightx = 0.25;
        card.add(createLabeledField("Total Harga Jual Bersih (Rp)", txtHargaJualTotal), gbc);

        JButton btnSimpanJual = new JButton("Catat Penjualan & Kurangi Stok");
        btnSimpanJual.setBackground(new Color(22, 163, 74));
        btnSimpanJual.setForeground(Color.WHITE);
        btnSimpanJual.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSimpanJual.setFocusPainted(false);
        btnSimpanJual.setBorderPainted(false);
        btnSimpanJual.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSimpanJual.setPreferredSize(new Dimension(210, 36));
        btnSimpanJual.addActionListener(e -> simpanPenjualan());

        gbc.gridx = 3; gbc.weightx = 0.25; gbc.insets = new Insets(20, 0, 0, 0);
        card.add(btnSimpanJual, gbc);
        return card;
    }

    private JScrollPane wrapTable(JTable tabel) {
        JScrollPane sp = new JScrollPane(tabel);
        sp.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        sp.getViewport().setBackground(Color.WHITE);
        return sp;
    }

    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(51, 65, 85));
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setPreferredSize(new Dimension(120, 36));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return tf;
    }

    private void styleTable(JTable tabel, int[] centerCols, int[] rightCols) {
        tabel.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabel.getTableHeader().setBackground(new Color(241, 245, 249));
        tabel.getTableHeader().setForeground(new Color(51, 65, 85));
        tabel.setRowHeight(28);
        tabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabel.setGridColor(new Color(226, 232, 240));
        tabel.setSelectionBackground(new Color(219, 234, 254));
        tabel.setSelectionForeground(new Color(30, 41, 59));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int col : centerCols) {
            tabel.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        }

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        for (int col : rightCols) {
            tabel.getColumnModel().getColumn(col).setCellRenderer(rightRenderer);
        }
    }

    private void simpanPembelian() {
        try {
            Object selected = cmbItemBeli.getEditor().getItem();
            String nama = selected != null ? selected.toString().trim() : "";
            int qty = Integer.parseInt(txtJumlahBeli.getText().trim());
            BigDecimal total = new BigDecimal(txtHargaBeliTotal.getText().trim());

            if (nama.isEmpty() || qty <= 0) {
                JOptionPane.showMessageDialog(this, "Nama item dan jumlah harus valid!");
                return;
            }

            procurementDAO.save(new ProcurementHPP(nama, qty, total));
            txtJumlahBeli.setText("");
            txtHargaBeliTotal.setText("");
            loadData();
            JOptionPane.showMessageDialog(this, "Stok berhasil ditambahkan & HPP dihitung otomatis!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input pembelian tidak valid: " + ex.getMessage());
        }
    }

    private void simpanPenjualan() {
        try {
            Object selected = cmbItemJual.getEditor().getItem();
            String nama = selected != null ? selected.toString().trim() : "";
            int qtyJual = Integer.parseInt(txtJumlahJual.getText().trim());
            BigDecimal hargaJual = new BigDecimal(txtHargaJualTotal.getText().trim());

            if (nama.isEmpty() || qtyJual <= 0) {
                JOptionPane.showMessageDialog(this, "Nama item dan jumlah terjual harus valid!");
                return;
            }

            int totalBeli = 0;
            BigDecimal hppTerakhir = BigDecimal.ZERO;
            for (ProcurementHPP p : procurementDAO.findAll()) {
                if (p.getNamaItem().equalsIgnoreCase(nama)) {
                    totalBeli += p.getJumlah();
                    if (hppTerakhir.compareTo(BigDecimal.ZERO) == 0) {
                        hppTerakhir = p.getHppPerUnit();
                    }
                }
            }

            int totalTerjual = 0;
            for (SalesTransaction s : salesDAO.findAll()) {
                if (s.getNamaItem().equalsIgnoreCase(nama)) {
                    totalTerjual += s.getJumlahJual();
                }
            }

            int sisaStok = totalBeli - totalTerjual;
            if (qtyJual > sisaStok) {
                JOptionPane.showMessageDialog(this,
                        "Stok " + nama + " tidak mencukupi!\nSisa stok saat ini: " + sisaStok + " unit.",
                        "Peringatan Stok", JOptionPane.WARNING_MESSAGE);
                return;
            }

            SalesTransaction transaksiBaru = new SalesTransaction(nama, qtyJual, hargaJual, hppTerakhir);
            salesDAO.save(transaksiBaru);

            txtJumlahJual.setText("");
            txtHargaJualTotal.setText("");
            loadData();

            JOptionPane.showMessageDialog(this,
                    "Penjualan berhasil dicatat!\nSisa stok " + nama + ": " + (sisaStok - qtyJual) + " unit" +
                    "\nKeuntungan transaksi ini: " + formatRupiah(transaksiBaru.getKeuntungan()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input penjualan tidak valid: " + ex.getMessage());
        }
    }

    private void loadData() {
        modelTabelBeli.setRowCount(0);
        modelTabelJual.setRowCount(0);
        modelTabelStok.setRowCount(0);

        Map<String, Integer> mapBeli = new HashMap<>();
        Map<String, Integer> mapJual = new HashMap<>();
        Map<String, BigDecimal> mapHpp = new HashMap<>();

        // 1. Muat Data Pembelian (Modal)
        for (ProcurementHPP p : procurementDAO.findAll()) {
            String waktu = p.getTanggalInput() != null ? p.getTanggalInput().format(dateFormatter) : "-";
            modelTabelBeli.addRow(new Object[]{
                p.getId(), p.getNamaItem(), p.getJumlah(),
                formatRupiah(p.getHargaBeliTotal()), formatRupiah(p.getHppPerUnit()), waktu
            });
            mapBeli.merge(p.getNamaItem(), p.getJumlah(), Integer::sum);
            mapHpp.putIfAbsent(p.getNamaItem(), p.getHppPerUnit());
        }

        // 2. Muat Data Penjualan & Profit
        for (SalesTransaction s : salesDAO.findAll()) {
            String waktu = s.getTanggalJual() != null ? s.getTanggalJual().format(dateFormatter) : "-";
            modelTabelJual.addRow(new Object[]{
                s.getId(), s.getNamaItem(), s.getJumlahJual(),
                formatRupiah(s.getHargaJualTotal()), formatRupiah(s.getHppSaatTerjual()),
                formatRupiah(s.getKeuntungan()), waktu
            });
            mapJual.merge(s.getNamaItem(), s.getJumlahJual(), Integer::sum);
        }

        // 3. Hitung Ringkasan Sisa Stok Aktual
        Map<String, Integer> sisaStokAktual = new HashMap<>();
        for (Map.Entry<String, Integer> entry : mapBeli.entrySet()) {
            String item = entry.getKey();
            int beli = entry.getValue();
            int jual = mapJual.getOrDefault(item, 0);
            int sisa = beli - jual;
            sisaStokAktual.put(item, sisa);

            modelTabelStok.addRow(new Object[]{
                item, beli + " unit", jual + " unit", sisa + " unit",
                formatRupiah(mapHpp.getOrDefault(item, BigDecimal.ZERO))
            });
        }

        lblTotalModalValue.setText(formatRupiah(procurementDAO.getTotalModal()));
        lblTotalProfitValue.setText(formatRupiah(salesDAO.getTotalProfit()));
        updateLowStockAlert(sisaStokAktual);
    }

    private void updateLowStockAlert(Map<String, Integer> sisaStokMap) {
        StringBuilder lowItems = new StringBuilder();
        for (Map.Entry<String, Integer> entry : sisaStokMap.entrySet()) {
            if (entry.getValue() <= 3) {
                if (lowItems.length() > 0) lowItems.append(", ");
                lowItems.append(entry.getKey()).append(" (Sisa ").append(entry.getValue()).append(")");
            }
        }
        if (lowItems.length() > 0) {
            lblAlertStockDetail.setText("Stok Kritis (<= 3 unit): " + lowItems + " — Segera cari suplai FB!");
        } else {
            lblAlertStockDetail.setText("Semua sisa stok buah berada di atas batas minimum aman.");
        }
    }

    private String formatRupiah(BigDecimal nominal) {
        if (nominal == null) return "Rp 0";
        return rupiahFormat.format(nominal).replace(",00", "");
    }
}