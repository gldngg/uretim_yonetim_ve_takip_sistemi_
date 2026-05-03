package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import database.Session;
import java.awt.Image;
import javax.swing.ImageIcon;


public class OrtakEkran extends JFrame {

    private static final long serialVersionUID = 1L;

    protected JPanel contentPane;

    protected final Color ARKA_PLAN = new Color(142, 155, 213);
    protected final Color HEADER = new Color(63, 81, 181);
    protected final Color PANEL_BEYAZ = Color.WHITE;
    protected final Color ACIK_GRI = new Color(245, 245, 245);
    protected final Color BUTON_MAVI = new Color(52, 152, 219);

    public OrtakEkran(String baslik) {
        setTitle(baslik);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 760);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(ARKA_PLAN);
        setContentPane(contentPane);
    }

    protected boolean adminMi() {
        return "admin".equalsIgnoreCase(Session.aktifRol);
    }

    protected boolean operatorMu() {
        return "operator".equalsIgnoreCase(Session.aktifRol)
                || "operatör".equalsIgnoreCase(Session.aktifRol);
    }

    protected JButton menuButonu(String text) {
        JButton btn = new JButton(text);

        Color normalRenk = new Color(63, 81, 181);
        Color hoverRenk = new Color(92, 107, 192);
        Color basiliRenk = new Color(48, 63, 159);

        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setBackground(normalRenk);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(hoverRenk);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(normalRenk);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                btn.setBackground(basiliRenk);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                btn.setBackground(hoverRenk);
            }
        });

        return btn;
    }

    protected void kullaniciAdiEkle(JPanel panelUstMenu) {
        JLabel lblUser = new JLabel(Session.aktifKullanici);
        lblUser.setBounds(860, 24, 80, 20);
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblUser.setHorizontalAlignment(SwingConstants.CENTER);
        panelUstMenu.add(lblUser);
    }
    
    protected void ustMenuOlustur(String baslik) {

        JPanel panelUstMenu = new JPanel();
        panelUstMenu.setBackground(HEADER);
        panelUstMenu.setBounds(20, 20, 1040, 70);
        panelUstMenu.setLayout(null);
        contentPane.add(panelUstMenu);

        boolean adminMi = adminMi();
        boolean operatorMu = operatorMu();

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/ui/logo.png"));
        Image logoImg = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        JLabel lblLogo = new JLabel(new ImageIcon(logoImg));
        lblLogo.setBounds(20, 10, 50, 50);
        panelUstMenu.add(lblLogo);

        JLabel lblBaslik = new JLabel(baslik);
        lblBaslik.setBounds(85, 18, 220, 30);
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 22));
        panelUstMenu.add(lblBaslik);

        int x = 280;
        int y = 22;
        int h = 25;

        JButton btnAnaSayfa = menuButonu("Ana Sayfa");
        btnAnaSayfa.setBounds(x, y, 100, h);
        panelUstMenu.add(btnAnaSayfa);

        btnAnaSayfa.addActionListener(e -> {
            if (adminMi) {
                new AdminMenuEkrani().setVisible(true);
            } else {
                new OperatorMenuEkrani().setVisible(true);
            }
            dispose();
        });

        x += 110;

        if (!adminMi) {
            JButton btnDurusKayip = menuButonu("Duruş/Kayıp");
            btnDurusKayip.setBounds(x, y, 120, h);
            panelUstMenu.add(btnDurusKayip);

            btnDurusKayip.addActionListener(e -> {
                new DurusKayipEkrani().setVisible(true);
                dispose();
            });

            x += 130;
        }

        JButton btnMakine = menuButonu("Makine Girişi");
        btnMakine.setBounds(x, y, 135, h);
        panelUstMenu.add(btnMakine);

        btnMakine.addActionListener(e -> {
            new MakineGirisEkrani().setVisible(true);
            dispose();
        });

        x += 145;

        if (!operatorMu) {
            JButton btnSiparis = menuButonu("Sipariş");
            btnSiparis.setBounds(x, y, 90, h);
            panelUstMenu.add(btnSiparis);

            btnSiparis.addActionListener(e -> {
                new SiparisEkrani().setVisible(true);
                dispose();
            });

            x += 100;
        }

        JButton btnPlanlama = menuButonu("Planlama");
        btnPlanlama.setBounds(x, y, 100, h);
        panelUstMenu.add(btnPlanlama);

        btnPlanlama.addActionListener(e -> {
            new PlanlamaEkrani().setVisible(true);
            dispose();
        });

        x += 110;

        JButton btnRapor = menuButonu("Rapor");
        btnRapor.setBounds(x, y, 80, h);
        panelUstMenu.add(btnRapor);

        btnRapor.addActionListener(e -> {
            new RaporEkrani().setVisible(true);
            dispose();
        });

        JLabel lblKullanici = new JLabel(Session.aktifKullanici);
        lblKullanici.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblKullanici.setForeground(Color.WHITE);
        lblKullanici.setBounds(875, 24, 65, 20);
        lblKullanici.setHorizontalAlignment(SwingConstants.CENTER);
        panelUstMenu.add(lblKullanici);

        JButton btnCikis = new JButton("ÇIKIŞ");
        btnCikis.setForeground(Color.BLACK);
        btnCikis.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnCikis.setBounds(950, 20, 70, 30);
        panelUstMenu.add(btnCikis);

        btnCikis.addActionListener(e -> {
            Session.aktifKullanici = "";
            Session.aktifRol = "";
            new GirisEkrani().setVisible(true);
            dispose();
        });
    }
    
    
}