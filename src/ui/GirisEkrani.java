package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.Session;
import model.Kullanici;
import service.KullaniciService;

public class GirisEkrani extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JPanel loginPanel;
    private JTextField textfUserName;
    private JPasswordField pfPassword;
    private JRadioButton rbtnAdmin;
    private JRadioButton rbtnOperator;
    private JLabel lblMessage;

    public static void main(String[] args) {
        GirisEkrani frame = new GirisEkrani();
        frame.setVisible(true);
    }

    public GirisEkrani() {

        setTitle("ÜRETİM YÖNETİM SİSTEMİ(ÜYS)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1100, 760);
        setLocationRelativeTo(null);

        setResizable(false);
        setMinimumSize(getSize());
        setMaximumSize(getSize());

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(142, 155, 213));
        setContentPane(contentPane);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setBounds(0, 0, 1100, 100);
        contentPane.add(headerPanel);

        loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBounds(150, 150, 800, 400);
        contentPane.add(loginPanel);

        JLabel lblTitle = new JLabel("Sisteme Giriş Ekranı");
        lblTitle.setBounds(0, 25, 1100, 50);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblTitle);

        JLabel lblUserName = new JLabel("Kullanıcı Adı:");
        lblUserName.setBounds(100, 30, 150, 35);
        lblUserName.setFont(new Font("Tahoma", Font.BOLD, 20));
        loginPanel.add(lblUserName);

        textfUserName = new JTextField();
        textfUserName.setBounds(280, 30, 400, 35);
        loginPanel.add(textfUserName);

        JLabel lblPassword = new JLabel("Şifre:");
        lblPassword.setBounds(100, 90, 150, 35);
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
        loginPanel.add(lblPassword);

        pfPassword = new JPasswordField();
        pfPassword.setBounds(280, 90, 400, 35);
        loginPanel.add(pfPassword);

        JLabel lblRole = new JLabel("Rol:");
        lblRole.setBounds(100, 150, 150, 35);
        lblRole.setFont(new Font("Tahoma", Font.BOLD, 20));
        loginPanel.add(lblRole);

        rbtnAdmin = new JRadioButton("Admin");
        rbtnOperator = new JRadioButton("Operatör");

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbtnAdmin);
        bg.add(rbtnOperator);

        rbtnAdmin.setBounds(280, 150, 130, 35);
        rbtnAdmin.setFont(new Font("Tahoma", Font.BOLD, 16));
        rbtnAdmin.setOpaque(false);
        loginPanel.add(rbtnAdmin);

        rbtnOperator.setBounds(420, 150, 130, 35);
        rbtnOperator.setFont(new Font("Tahoma", Font.BOLD, 16));
        rbtnOperator.setOpaque(false);
        loginPanel.add(rbtnOperator);

        JButton btnLogin = new JButton("Giriş Yap");
        btnLogin.setBounds(280, 210, 150, 35);
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnLogin.setBackground(new Color(63, 81, 181));
        btnLogin.setForeground(Color.WHITE);
        loginPanel.add(btnLogin);

        JButton btnClear = new JButton("Temizle");
        btnClear.setBounds(450, 210, 150, 35);
        btnClear.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnClear.setBackground(new Color(63, 81, 181));
        btnClear.setForeground(Color.WHITE);
        loginPanel.add(btnClear);

        JLabel messagebox = new JLabel("Durum Mesajı:");
        messagebox.setBounds(100, 270, 200, 35);
        messagebox.setFont(new Font("Tahoma", Font.BOLD, 20));
        loginPanel.add(messagebox);

        lblMessage = new JLabel("");
        lblMessage.setBounds(280, 270, 400, 35);
        lblMessage.setFont(new Font("Tahoma", Font.BOLD, 16));
        loginPanel.add(lblMessage);

        JLabel lblRegister = new JLabel("Hesabınız yok mu?");
        lblRegister.setBounds(200, 330, 200, 35);
        lblRegister.setFont(new Font("Tahoma", Font.BOLD, 20));
        loginPanel.add(lblRegister);

        JButton btnRegister = new JButton("Kayıt Ol");
        btnRegister.setBounds(425, 330, 150, 35);
        btnRegister.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnRegister.setBackground(new Color(63, 81, 181));
        btnRegister.setForeground(Color.WHITE);
        loginPanel.add(btnRegister);

        btnLogin.addActionListener(e -> girisYap());

        btnClear.addActionListener(e -> {
            textfUserName.setText("");
            pfPassword.setText("");
            lblMessage.setText("");
            bg.clearSelection();
        });

        btnRegister.addActionListener(e -> {
            KayitEkrani kayit = new KayitEkrani();
            kayit.setVisible(true);
            dispose();
        });
    }

    private void girisYap() {

        String username = textfUserName.getText().trim();
        String password = new String(pfPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Kullanıcı adı veya şifre boş bırakılamaz!");
            return;
        }

        String role = null;

        if (rbtnAdmin.isSelected()) {
            role = "Admin";
        } else if (rbtnOperator.isSelected()) {
            role = "Operator";
        }

        if (role == null) {
            lblMessage.setText("Rol seçiniz!");
            return;
        }

        KullaniciService kullaniciService = new KullaniciService();
        Kullanici kullanici = kullaniciService.girisYap(username, password, role);

        if (kullanici != null) {
            Session.aktifKullanici = username;
            Session.aktifRol = role;

            lblMessage.setText("Giriş başarılı!");
            System.out.println(kullanici.yetkiBilgisi());

            if (role.equals("Admin")) {
                new AdminMenuEkrani().setVisible(true);
            } else {
                new OperatorMenuEkrani().setVisible(true);
            }

            dispose();

        } else {
            lblMessage.setText("Hatalı kullanıcı adı, şifre veya rol!");
        }
    }
}