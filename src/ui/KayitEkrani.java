package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.Kullanici;
import service.KullaniciService;

public class KayitEkrani extends JFrame {

    private JPanel contentPane;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JPasswordField passwordRepeatInput;
    private JRadioButton roleAdmin;
    private JRadioButton roleOperator;
    private JLabel messageBox;
    private ButtonGroup roleGroup;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                KayitEkrani frame = new KayitEkrani();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public KayitEkrani() {

        setTitle("Kayıt Ol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1100, 760);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(142, 155, 213));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setBounds(0, 0, 1100, 80);
        contentPane.add(headerPanel);
        headerPanel.setLayout(null);

        JLabel lblTitle = new JLabel("Kayıt Ol");
        lblTitle.setBounds(0, 20, 1100, 40);
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblTitle);

        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(75, 120, 950, 500);
        contentPane.add(formPanel);
        formPanel.setLayout(null);

        JLabel lblUser = new JLabel("Kullanıcı Adı :");
        lblUser.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblUser.setBounds(40, 35, 200, 55);
        formPanel.add(lblUser);

        usernameInput = new JTextField();
        usernameInput.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(2, 8, 2, 2)));
        usernameInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
        usernameInput.setBounds(300, 35, 590, 55);
        formPanel.add(usernameInput);

        JLabel lblPass = new JLabel("Şifre :");
        lblPass.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblPass.setBounds(40, 110, 200, 55);
        formPanel.add(lblPass);

        passwordInput = new JPasswordField();
        passwordInput.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(2, 8, 2, 2)));
        passwordInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
        passwordInput.setBounds(300, 110, 590, 55);
        formPanel.add(passwordInput);

        JLabel lblPassRep = new JLabel("Şifre Tekrarı :");
        lblPassRep.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblPassRep.setBounds(40, 185, 200, 55);
        formPanel.add(lblPassRep);

        passwordRepeatInput = new JPasswordField();
        passwordRepeatInput.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(2, 8, 2, 2)));
        passwordRepeatInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
        passwordRepeatInput.setBounds(300, 185, 590, 55);
        formPanel.add(passwordRepeatInput);

        roleAdmin = new JRadioButton("Admin");
        roleAdmin.setFont(new Font("Tahoma", Font.PLAIN, 20));
        roleAdmin.setBackground(Color.WHITE);
        roleAdmin.setBounds(300, 260, 160, 45);
        formPanel.add(roleAdmin);

        roleOperator = new JRadioButton("Operatör");
        roleOperator.setFont(new Font("Tahoma", Font.PLAIN, 20));
        roleOperator.setBackground(Color.WHITE);
        roleOperator.setBounds(520, 260, 180, 45);
        formPanel.add(roleOperator);

        roleGroup = new ButtonGroup();
        roleGroup.add(roleAdmin);
        roleGroup.add(roleOperator);

        JButton btnRegister = new JButton("KAYIT OL");
        btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnRegister.setBackground(new Color(63, 81, 181));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBounds(300, 330, 180, 55);
        formPanel.add(btnRegister);

        JButton btnLogin = new JButton("GİRİŞ EKRANINA DÖN");
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnLogin.setBackground(new Color(63, 81, 181));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBounds(520, 330, 370, 55);
        formPanel.add(btnLogin);

        JSeparator separator = new JSeparator();
        separator.setBounds(40, 410, 850, 5);
        formPanel.add(separator);

        messageBox = new JLabel("Durum :");
        messageBox.setFont(new Font("Tahoma", Font.PLAIN, 22));
        messageBox.setBounds(40, 425, 850, 55);
        formPanel.add(messageBox);

        btnRegister.addActionListener(e -> kayitIslemi());
        btnLogin.addActionListener(e -> goToLogin());
    }

    private void kayitIslemi() {

        String username = usernameInput.getText().trim();
        String password = new String(passwordInput.getPassword());
        String passwordRepeat = new String(passwordRepeatInput.getPassword());

        String role = null;

        if (roleAdmin.isSelected()) {
            role = "Admin";
        } else if (roleOperator.isSelected()) {
            role = "Operator";
        }

        try {

            if (username.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty() || role == null) {
                throw new IllegalArgumentException("Boşlukları doldurunuz!");
            }

            if (!password.equals(passwordRepeat)) {
                throw new IllegalArgumentException("Şifreyi kontrol ediniz!");
            }

            KullaniciService service = new KullaniciService();
            Kullanici kullanici = new Kullanici(0, username, role);

            boolean sonuc = service.kayitOl(kullanici, password);

            if (sonuc) {
                messageBox.setText("Durum: Kayıt başarılı!");

                Timer timer = new Timer(2000, (ActionEvent e) -> goToLogin());
                timer.setRepeats(false);
                timer.start();

            } else {
                messageBox.setText("Durum: Kullanıcı adı kullanımda!");
            }

        } catch (IllegalArgumentException e) {
            messageBox.setText("Durum: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            messageBox.setText("Durum: Hata oluştu!");
        }
    }

    private void goToLogin() {
        new GirisEkrani().setVisible(true);
        dispose();
    }
}