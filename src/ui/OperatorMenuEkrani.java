package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.Session;

public class OperatorMenuEkrani extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public OperatorMenuEkrani() {

        setTitle("Operatör Menü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(142, 155, 213));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setBounds(0, 0, 1100, 100);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);

        JLabel lblTitle = new JLabel("Hoşgeldiniz, " + Session.aktifKullanici + "!");
        lblTitle.setBounds(0, 25, 1100, 40);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblTitle);

        JButton btnCikis = new JButton("ÇIKIŞ");
        btnCikis.setBounds(950, 30, 100, 30);
        headerPanel.add(btnCikis);

        btnCikis.addActionListener(e -> {
            Session.aktifKullanici = "";
            Session.aktifRol = "";
            new GirisEkrani().setVisible(true);
            dispose();
        });

        int startX = 175;
        int iconY = 250;
        int labelY = 375;
        int gap = 220;

        ImageIcon raporIcon = new ImageIcon("src/ui/raporlar.png");
        Image raporImg = raporIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        JButton btnRapor = new JButton(new ImageIcon(raporImg));
        btnRapor.setBounds(startX, iconY, 150, 120);
        btnRapor.setContentAreaFilled(false);
        btnRapor.setBorderPainted(false);
        btnRapor.setFocusPainted(false);
        contentPane.add(btnRapor);

        JLabel lblRapor = new JLabel("Raporlar");
        lblRapor.setBounds(startX, labelY, 150, 30);
        lblRapor.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblRapor.setForeground(new Color(63, 81, 181));
        lblRapor.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblRapor);

        btnRapor.addActionListener(e -> {
            new RaporEkrani().setVisible(true);
            dispose();
        });

        ImageIcon makineIcon = new ImageIcon("src/ui/makineGirisi.png");
        Image makineImg = makineIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        JButton btnMakine = new JButton(new ImageIcon(makineImg));
        btnMakine.setBounds(startX + gap, iconY, 150, 120);
        btnMakine.setContentAreaFilled(false);
        btnMakine.setBorderPainted(false);
        btnMakine.setFocusPainted(false);
        contentPane.add(btnMakine);

        JLabel lblMakine = new JLabel("Makine Girişi");
        lblMakine.setBounds(startX + gap, labelY, 150, 30);
        lblMakine.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblMakine.setForeground(new Color(63, 81, 181));
        lblMakine.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblMakine);

        btnMakine.addActionListener(e -> {
            new MakineGirisEkrani().setVisible(true);
            dispose();
        });

        ImageIcon planIcon = new ImageIcon("src/ui/planlama.png");
        Image planImg = planIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        JButton btnPlan = new JButton(new ImageIcon(planImg));
        btnPlan.setBounds(startX + gap * 2, iconY, 150, 120);
        btnPlan.setContentAreaFilled(false);
        btnPlan.setBorderPainted(false);
        btnPlan.setFocusPainted(false);
        contentPane.add(btnPlan);

        JLabel lblPlan = new JLabel("Planlama");
        lblPlan.setBounds(startX + gap * 2, labelY, 150, 30);
        lblPlan.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblPlan.setForeground(new Color(63, 81, 181));
        lblPlan.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblPlan);

        btnPlan.addActionListener(e -> {
            new PlanlamaEkrani().setVisible(true);
            dispose();
        });

        ImageIcon durusIcon = new ImageIcon("src/ui/durusKayip.png");
        Image durusImg = durusIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        JButton btnDurus = new JButton(new ImageIcon(durusImg));
        btnDurus.setBounds(startX + gap * 3, iconY, 150, 120);
        btnDurus.setContentAreaFilled(false);
        btnDurus.setBorderPainted(false);
        btnDurus.setFocusPainted(false);
        contentPane.add(btnDurus);

        JLabel lblDurus = new JLabel("Duruş/Kayıp");
        lblDurus.setBounds(startX + gap * 3, labelY, 150, 30);
        lblDurus.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblDurus.setForeground(new Color(63, 81, 181));
        lblDurus.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblDurus);

        btnDurus.addActionListener(e -> {
            new DurusKayipEkrani().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        new OperatorMenuEkrani().setVisible(true);
    }
}