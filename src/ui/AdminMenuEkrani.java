package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

import javax.swing.*;

import javax.swing.border.EmptyBorder;

import database.Session;

public class AdminMenuEkrani extends JFrame {
	
	private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    public AdminMenuEkrani() {

        setTitle("Admin Menü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(142, 155, 213));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // HEADER (DÜZELTİLDİ)
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setBounds(0, 0, 1100, 80); // 100 → 80
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);

        JLabel lblTitle = new JLabel("Hoşgeldiniz, " + Session.aktifKullanici + "!");
        lblTitle.setBounds(0, 20, 1100, 40);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22)); // 24 → 22
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblTitle);

        JButton btnCikis = new JButton("ÇIKIŞ");
        btnCikis.setBounds(950, 25, 100, 30);
        headerPanel.add(btnCikis);

        btnCikis.addActionListener(e -> {
            Session.aktifKullanici = "";
            Session.aktifRol = "";
            new GirisEkrani().setVisible(true);
            dispose();
        });

        // ORTA ALAN
        int startX = 170;
        int iconY = 220;
        int labelY = 350;
        int gap = 220;
        
        // ICONLAR 
        ImageIcon raporIcon = iconOlustur("/ui/raporlar.png");
        ImageIcon makineIcon = iconOlustur("/ui/makineGirisi.png");
        ImageIcon planIcon = iconOlustur("/ui/planlama.png");
        ImageIcon siparisIcon = iconOlustur("/ui/siparis.png");

        // RAPOR
        JButton btnRapor = butonOlustur(startX, iconY, raporIcon);
        contentPane.add(btnRapor);

        JLabel lblRapor = labelOlustur("Raporlar", startX, labelY);
        contentPane.add(lblRapor);
        menuButonEfekti(btnRapor);

        btnRapor.addActionListener(e -> {
            new RaporEkrani().setVisible(true);
            dispose();
        });

        // MAKİNE
        JButton btnMakine = butonOlustur(startX + gap, iconY, makineIcon);
        contentPane.add(btnMakine);

        JLabel lblMakine = labelOlustur("Makine Girişi", startX + gap, labelY);
        contentPane.add(lblMakine);
        menuButonEfekti(btnMakine);
        

        btnMakine.addActionListener(e -> {
            new MakineGirisEkrani().setVisible(true);
            dispose();
        });

        // PLANLAMA
        JButton btnPlan = butonOlustur(startX + gap * 2, iconY, planIcon);
        contentPane.add(btnPlan);

        JLabel lblPlan = labelOlustur("Planlama", startX + gap * 2, labelY);
        contentPane.add(lblPlan);
        menuButonEfekti(btnPlan);

        btnPlan.addActionListener(e -> {
            new PlanlamaEkrani().setVisible(true);
            dispose();
        });

        // SİPARİŞ
        JButton btnSiparis = butonOlustur(startX + gap * 3, iconY, siparisIcon);
        contentPane.add(btnSiparis);
        menuButonEfekti(btnSiparis);

        JLabel lblSiparis = labelOlustur("Sipariş Girişi", startX + gap * 3, labelY);
        contentPane.add(lblSiparis);

        btnSiparis.addActionListener(e -> {
            new SiparisEkrani().setVisible(true);
            dispose();
        });
    }

    // ICON SCALE (100px → tüm projeyle uyumlu)
    private ImageIcon iconOlustur(String path) {
        URL url = getClass().getResource(path);

        if (url == null) {
            System.err.println("Resim bulunamadı: " + path);
            return new ImageIcon();
        }

        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private JButton butonOlustur(int x, int y, ImageIcon icon) {
        JButton btn = new JButton(icon);
        btn.setBounds(x, y, 150, 120);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        return btn;
    }

    private JLabel labelOlustur(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, 150, 30);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 18));
        lbl.setForeground(new Color(63, 81, 181));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        return lbl;
    }
    
    private void menuButonEfekti(JButton btn) {
        Color normalRenk = new Color(142, 155, 213);
        Color hoverRenk = new Color(160, 172, 225);
        Color basiliRenk = new Color(120, 135, 200);

        btn.setBackground(normalRenk);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setFocusPainted(false);

        btn.setBorderPainted(false);
        btn.setBorder(null);

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
    }

    public static void main(String[] args) {
        new AdminMenuEkrani().setVisible(true);
    }
}