package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.Session;
import model.DurusKayip;
import service.RaporService;

public class RaporEkrani extends OrtakEkran {

    private static final long serialVersionUID = 1L;


    private JTable table;
    private DefaultTableModel model;

    private RaporService raporService = new RaporService();

    private final Color KART_MAVI = new Color(52, 152, 219);

    public RaporEkrani() {

    	super("Raporlama");

    	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    	ustMenuOlustur("RAPORLAMA");

        JPanel panelOzet = new JPanel();
        panelOzet.setBounds(30, 110, 1040, 160);
        panelOzet.setBackground(PANEL_BEYAZ);
        panelOzet.setLayout(null);
        contentPane.add(panelOzet);

        JPanel panelOzetBaslik = new JPanel();
        panelOzetBaslik.setBounds(0, 0, 1040, 45);
        panelOzetBaslik.setBackground(ACIK_GRI);
        panelOzetBaslik.setLayout(null);
        panelOzet.add(panelOzetBaslik);

        JLabel lblOzet = new JLabel("Sistem Özeti");
        lblOzet.setBounds(20, 8, 250, 30);
        lblOzet.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblOzet.setForeground(new Color(60, 60, 60));
        panelOzetBaslik.add(lblOzet);

        JPanel kartMakine = kartOlustur("Toplam Makine", raporService.toplamMakineSayisi());
        kartMakine.setBounds(30, 65, 220, 75);
        panelOzet.add(kartMakine);

        JPanel kartSiparis = kartOlustur("Toplam Sipariş", raporService.toplamSiparisSayisi());
        kartSiparis.setBounds(290, 65, 220, 75);
        panelOzet.add(kartSiparis);

        JPanel kartDurus = kartOlustur("Toplam Duruş/Kayıp", raporService.toplamDurusKayipSayisi());
        kartDurus.setBounds(550, 65, 220, 75);
        panelOzet.add(kartDurus);

        JPanel kartIsEmri = kartOlustur("Toplam İş Emri", raporService.toplamIsEmriSayisi());
        kartIsEmri.setBounds(810, 65, 220, 75);
        panelOzet.add(kartIsEmri);

        JPanel panelTablo = new JPanel();
        panelTablo.setBounds(30, 295, 1040, 380);
        panelTablo.setBackground(PANEL_BEYAZ);
        panelTablo.setLayout(null);
        contentPane.add(panelTablo);

        JPanel panelTabloBaslik = new JPanel();
        panelTabloBaslik.setBounds(0, 0, 1040, 45);
        panelTabloBaslik.setBackground(ACIK_GRI);
        panelTabloBaslik.setLayout(null);
        panelTablo.add(panelTabloBaslik);

        JLabel lblTablo = new JLabel("Duruş/Kayıp Rapor Tablosu");
        lblTablo.setBounds(20, 8, 350, 30);
        lblTablo.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTablo.setForeground(new Color(60, 60, 60));
        panelTabloBaslik.add(lblTablo);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(15, 60, 1010, 300);
        panelTablo.add(scrollPane);

        table = new JTable();
        table.setRowHeight(27);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(230, 235, 250));

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {
                "Makine Tipi",
                "Makine Kodu",
                "Başlangıç",
                "Bitiş",
                "Süre",
                "Duruş Türü",
                "Duruş Nedeni",
                "Açıklama"
        });

        table.setModel(model);
        scrollPane.setViewportView(table);

        tabloyuDoldur();

        raporService.raporOlustur();
    }
    private void ustMenuOlustur(String baslik) {

        JPanel panelUstMenu = new JPanel();
        panelUstMenu.setBounds(30, 20, 1040, 70);
        panelUstMenu.setBackground(HEADER);
        panelUstMenu.setLayout(null);
        contentPane.add(panelUstMenu);

        boolean adminMi = "admin".equalsIgnoreCase(Session.aktifRol);
        boolean operatorMu = "operator".equalsIgnoreCase(Session.aktifRol)
                || "operatör".equalsIgnoreCase(Session.aktifRol);

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

        int x = 300;
        int y = 20;
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

        JButton btnMakine = menuButonu("Makine Girişi");
        btnMakine.setBounds(x, y, 135, h);
        panelUstMenu.add(btnMakine);

        btnMakine.addActionListener(e -> {
            new MakineGirisEkrani().setVisible(true);
            dispose();
        });

        x += 145;
        // Admin Duruş/Kayıp görmeyecek
        if (!adminMi) {
            JButton btnDurusKayip = menuButonu("Duruş/Kayıp");
            btnDurusKayip.setBounds(x, y, 120, h);
            panelUstMenu.add(btnDurusKayip);

            btnDurusKayip.addActionListener(e -> {
                new DurusKayipEkrani().setVisible(true);
                dispose();
            });

            x += 125;
        }

        // Operatör Sipariş görmeyecek
        if (!operatorMu) {
            JButton btnSiparis = menuButonu("Sipariş");
            btnSiparis.setBounds(x, y, 90, h);
            panelUstMenu.add(btnSiparis);

            btnSiparis.addActionListener(e -> {
                new SiparisEkrani().setVisible(true);
                dispose();
            });

            x += 95;
        }

        JButton btnPlanlama = menuButonu("Planlama");
        btnPlanlama.setBounds(x, y, 100, h);
        panelUstMenu.add(btnPlanlama);

        btnPlanlama.addActionListener(e -> {
            new PlanlamaEkrani().setVisible(true);
            dispose();
        });

        JLabel lblKullanici = new JLabel(Session.aktifKullanici);
        lblKullanici.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblKullanici.setForeground(Color.WHITE);
        lblKullanici.setBounds(850, 24, 90, 20);
        panelUstMenu.add(lblKullanici);

        JButton btnCikis = new JButton("ÇIKIŞ");
        btnCikis.setBounds(950, 18, 70, 30);
        btnCikis.setForeground(Color.BLACK);
        btnCikis.setFont(new Font("Tahoma", Font.BOLD, 11));
        panelUstMenu.add(btnCikis);

        btnCikis.addActionListener(e -> {
            Session.aktifKullanici = "";
            Session.aktifRol = "";
            new GirisEkrani().setVisible(true);
            dispose();
        });
    }

   
    private JPanel kartOlustur(String baslik, int sayi) {
        JPanel kart = new JPanel();
        kart.setBackground(KART_MAVI);
        kart.setLayout(null);

        JLabel lblBaslik = new JLabel(baslik);
        lblBaslik.setBounds(0, 10, 220, 25);
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        kart.add(lblBaslik);

        JLabel lblSayi = new JLabel(String.valueOf(sayi));
        lblSayi.setBounds(0, 35, 220, 35);
        lblSayi.setForeground(Color.WHITE);
        lblSayi.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblSayi.setHorizontalAlignment(SwingConstants.CENTER);
        kart.add(lblSayi);

        return kart;
    }

    private void tabloyuDoldur() {
        model.setRowCount(0);

        List<DurusKayip> duruslar = raporService.durusKayipRaporuGetir();

        for (DurusKayip durus : duruslar) {
            model.addRow(new Object[] {
                    durus.getMakineTipi(),
                    durus.getMakineKodu(),
                    durus.getBaslangic(),
                    durus.getBitis(),
                    durus.getSure(),
                    durus.getDurusTuru(),
                    durus.getDurusNedeni(),
                    durus.getAciklama()
            });
        }
    }

    public static void main(String[] args) {
        new RaporEkrani().setVisible(true);
    }
}
