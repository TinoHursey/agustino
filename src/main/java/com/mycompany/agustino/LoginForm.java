package com.mycompany.agustino;

import com.mycompany.agustino.dao.ProcurementDAO;
import com.mycompany.agustino.dao.RedfingerSlotDAO;
import com.mycompany.agustino.dao.UserDAO;
import com.mycompany.agustino.model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.springframework.context.ApplicationContext;

public class LoginForm extends javax.swing.JFrame {

    private final ApplicationContext context;

    public LoginForm(ApplicationContext context) {
        this.context = context;
        initComponents();
        setTitle("Ayabe Store Management - ERP Login");
        setResizable(false);
        setLocationRelativeTo(null);
        // Agar menekan Enter langsung memicu tombol Sign In
        getRootPane().setDefaultButton(btnLogin);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Panel Utama dengan latar belakang bersih
        jPanelMain.setBackground(new Color(248, 250, 252));

        // Judul Utama ERP
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(30, 41, 59));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setText("Ayabe Store Management");

        // Sub-judul
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(100, 116, 139));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitle.setText("Enterprise Resource Planning System");

        // Label Username di atas field
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUsername.setForeground(new Color(51, 65, 85));
        lblUsername.setText("Username");

        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Label Password di atas field
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPassword.setForeground(new Color(51, 65, 85));
        lblPassword.setText("Password");

        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Tombol Login
        btnLogin.setBackground(new Color(37, 99, 235));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setText("Sign In");
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        // Pengaturan Layout Presisi
        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(lblSubtitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword)
                    .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubtitle)
                .addGap(28, 28, 28)
                .addComponent(lblUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi!");
            return;
        }

        try {
            UserDAO userDAO = context.getBean(UserDAO.class);
            User user = userDAO.findByUsernameAndPassword(username, password);

            if (user != null) {
                // Memanggil dialog sukses kustom yang modern
                tampilkanDialogSukses(user);

                // Routing ke Dashboard sesuai Role
                if (user.getRole() == User.Role.OWNER) {
                    new OwnerDashboard(context.getBean(ProcurementDAO.class)).setVisible(true);
                } else {
                    new WorkerDashboard(context.getBean(RedfingerSlotDAO.class)).setVisible(true);
                }
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Spring DAO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void tampilkanDialogSukses(User user) {
        JDialog dialog = new JDialog(this, true);
        dialog.setUndecorated(true); // Menghilangkan bar abu-abu kaku bawaan Windows
        dialog.setSize(340, 210);
        dialog.setLocationRelativeTo(this);

        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)
        ));

        // Bagian Atas: Judul Sukses & Sapaan
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        topPanel.setOpaque(false);

        JLabel lblIconTitle = new JLabel("✓ Login Berhasil", SwingConstants.CENTER);
        lblIconTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblIconTitle.setForeground(new Color(22, 163, 74)); // Hijau Sukses

        JLabel lblWelcome = new JLabel(
                "Selamat datang, " + user.getUsername(),
                SwingConstants.CENTER
        );
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblWelcome.setForeground(new Color(71, 85, 105));

        topPanel.add(lblIconTitle);
        topPanel.add(lblWelcome);

        // Bagian Tengah: Lencana (Badge) Hak Akses
        JPanel badgeWrapper = new JPanel();
        badgeWrapper.setOpaque(false);
        JLabel lblRole = new JLabel("HAK AKSES: " + user.getRole());
        lblRole.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblRole.setOpaque(true);
        lblRole.setBackground(new Color(239, 246, 255));
        lblRole.setForeground(new Color(37, 99, 235));
        lblRole.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(191, 219, 254), 1),
                BorderFactory.createEmptyBorder(5, 14, 5, 14)
        ));
        badgeWrapper.add(lblRole);

        // Bagian Bawah: Tombol Lanjut
        JButton btnLanjut = new JButton("Buka Workspace");
        btnLanjut.setBackground(new Color(37, 99, 235));
        btnLanjut.setForeground(Color.WHITE);
        btnLanjut.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLanjut.setFocusPainted(false);
        btnLanjut.setBorderPainted(false);
        btnLanjut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLanjut.setPreferredSize(new Dimension(0, 36));
        btnLanjut.addActionListener(e -> dialog.dispose());

        // Enter otomatis menekan tombol Buka Workspace
        dialog.getRootPane().setDefaultButton(btnLanjut);

        card.add(topPanel, BorderLayout.NORTH);
        card.add(badgeWrapper, BorderLayout.CENTER);
        card.add(btnLanjut, BorderLayout.SOUTH);

        dialog.setContentPane(card);
        dialog.setVisible(true);
    }

    // Variables declaration
    private javax.swing.JButton btnLogin;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
}