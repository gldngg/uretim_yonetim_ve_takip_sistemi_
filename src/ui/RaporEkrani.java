package ui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


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
        
        JPanel panelAnaliz = new JPanel();
        panelAnaliz.setBounds(30, 285, 1040, 155);
        panelAnaliz.setBackground(PANEL_BEYAZ);
        panelAnaliz.setLayout(null);
        contentPane.add(panelAnaliz);

        JPanel panelAnalizBaslik = new JPanel();
        panelAnalizBaslik.setBounds(0, 0, 1040, 40);
        panelAnalizBaslik.setBackground(ACIK_GRI);
        panelAnalizBaslik.setLayout(null);
        panelAnaliz.add(panelAnalizBaslik);

        JLabel lblAnaliz = new JLabel("Duruş Analizi");
        lblAnaliz.setBounds(20, 7, 250, 25);
        lblAnaliz.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblAnaliz.setForeground(new Color(60, 60, 60));
        panelAnalizBaslik.add(lblAnaliz);

        int analizKartW = 300;
        int analizKartH = 75;
        int analizKartY = 60;
        int analizBosluk = (1040 - (3 * analizKartW)) / 4;

        JPanel kartPlanli = kucukKartOlustur("Planlı Duruş", raporService.planliDurusSayisi());
        kartPlanli.setBounds(analizBosluk, analizKartY, analizKartW, analizKartH);
        panelAnaliz.add(kartPlanli);

        JPanel kartPlansiz = kucukKartOlustur("Plansız Duruş", raporService.plansizDurusSayisi());
        kartPlansiz.setBounds(analizBosluk * 2 + analizKartW, analizKartY, analizKartW, analizKartH);
        panelAnaliz.add(kartPlansiz);

        JPanel kartNeden = metinKartOlustur("En Çok Duruş Nedeni", raporService.enCokDurusNedeni());
        kartNeden.setBounds(analizBosluk * 3 + analizKartW * 2, analizKartY, analizKartW, analizKartH);
        panelAnaliz.add(kartNeden);

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

        int ozetKartW = 240;
        int ozetKartH = 75;
        int ozetKartY = 65;
        int ozetBosluk = (1040 - (4 * ozetKartW)) / 5;

        JPanel kartMakine = kartOlustur("Toplam Makine", raporService.toplamMakineSayisi());
        kartMakine.setBounds(ozetBosluk, ozetKartY, ozetKartW, ozetKartH);
        panelOzet.add(kartMakine);

        JPanel kartSiparis = kartOlustur("Toplam Sipariş", raporService.toplamSiparisSayisi());
        kartSiparis.setBounds(ozetBosluk * 2 + ozetKartW, ozetKartY, ozetKartW, ozetKartH);
        panelOzet.add(kartSiparis);

        JPanel kartDurus = kartOlustur("Toplam Duruş/Kayıp", raporService.toplamDurusKayipSayisi());
        kartDurus.setBounds(ozetBosluk * 3 + ozetKartW * 2, ozetKartY, ozetKartW, ozetKartH);
        panelOzet.add(kartDurus);

        JPanel kartIsEmri = kartOlustur("Toplam İş Emri", raporService.toplamIsEmriSayisi());
        kartIsEmri.setBounds(ozetBosluk * 4 + ozetKartW * 3, ozetKartY, ozetKartW, ozetKartH);
        panelOzet.add(kartIsEmri);

        JPanel panelTablo = new JPanel();
        panelTablo.setBounds(30, 460, 1040, 220);
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
        scrollPane.setBounds(15, 55, 1010, 125);
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

    

   
    private JPanel kartOlustur(String baslik, int sayi) {
        JPanel kart = new JPanel();
        kart.setBackground(KART_MAVI);
        kart.setLayout(null);

        JLabel lblBaslik = new JLabel(baslik);
        lblBaslik.setBounds(0, 8, 240, 22);
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        kart.add(lblBaslik);

        JLabel lblSayi = new JLabel(String.valueOf(sayi));
        lblSayi.setBounds(0, 30, 240, 26);
        lblSayi.setForeground(Color.WHITE);
        lblSayi.setFont(new Font("Tahoma", Font.BOLD, 24));
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
    private JPanel metinKartOlustur(String baslik, String metin) {
        JPanel kart = new JPanel();
        kart.setBackground(KART_MAVI);
        kart.setLayout(null);

        JLabel lblBaslik = new JLabel(baslik);
        lblBaslik.setBounds(0, 12, 300, 25);
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        kart.add(lblBaslik);

        JLabel lblMetin = new JLabel(metin);
        lblMetin.setBounds(0, 38, 300, 30);
        lblMetin.setForeground(Color.WHITE);
        lblMetin.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblMetin.setHorizontalAlignment(SwingConstants.CENTER);
        kart.add(lblMetin);

        return kart;
    }
    
    private JPanel kucukKartOlustur(String baslik, int sayi) {
        JPanel kart = new JPanel();
        kart.setBackground(KART_MAVI);
        kart.setLayout(null);

        JLabel lblBaslik = new JLabel(baslik);
        lblBaslik.setBounds(0, 12, 300, 25);
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        kart.add(lblBaslik);

        JLabel lblSayi = new JLabel(String.valueOf(sayi));
        lblSayi.setBounds(0, 38, 300, 30);
        lblSayi.setForeground(Color.WHITE);
        lblSayi.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblSayi.setHorizontalAlignment(SwingConstants.CENTER);
        kart.add(lblSayi);

        return kart;
    }
    public static void main(String[] args) {
        new RaporEkrani().setVisible(true);
    }
}