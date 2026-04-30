package ui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import database.Session;
import model.Planlama;
import model.Siparis;
import service.PlanlamaService;
import service.SiparisService;
import javax.swing.table.TableColumn;
import model.Makine;
import service.MakineService;

public class SiparisEkrani extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField txtSiparisAdi, txtTarih, txtSiparisKodu, txtMusteri, textUrun;
    private JSpinner spinnerMiktar;
    private JTable table;
    private JLabel lblDurum;

    private int seciliSiparisId = -1;
    private JList<String> siparisListesi;
    private DefaultListModel<String> listeModeli;

    private SiparisService siparisService = new SiparisService();
    private PlanlamaService planlamaService = new PlanlamaService();
    private MakineService makineService = new MakineService();

    private final Color ARKA_PLAN = new Color(142, 155, 213);
    private final Color HEADER = new Color(63, 81, 181);
    private final Color BUTON_MAVI = new Color(52, 152, 219);
    private final Color PANEL_BEYAZ = Color.WHITE;
    private final Color ACIK_GRI = new Color(245, 245, 245);
    private final Color TEMIZLE_GRI = new Color(220, 220, 220);

    public SiparisEkrani() {

        setTitle("Sipariş Girişi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(ARKA_PLAN);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelUstMenu = new JPanel();
        panelUstMenu.setBackground(HEADER);
        panelUstMenu.setBounds(0, 0, 1100, 70);
        contentPane.add(panelUstMenu);
        panelUstMenu.setLayout(null);

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/ui/logo.png"));
        Image logoImg = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        JLabel lblLogo = new JLabel(new ImageIcon(logoImg));
        lblLogo.setBounds(20, 10, 50, 50);
        panelUstMenu.add(lblLogo);

        JLabel lblBaslik = new JLabel("SİPARİŞ");
        lblBaslik.setBounds(85, 18, 220, 30);
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 22));
        panelUstMenu.add(lblBaslik);

        JButton btnAnaSayfa = menuButonu("Ana Sayfa");
        btnAnaSayfa.setBounds(250, 22, 100, 25);
        panelUstMenu.add(btnAnaSayfa);

        btnAnaSayfa.addActionListener(e -> {
            new AdminMenuEkrani().setVisible(true);
            dispose();
        });

        JButton btnDurusKayip = menuButonu("Duruş/Kayıp");
        btnDurusKayip.setBounds(360, 22, 120, 25);
        panelUstMenu.add(btnDurusKayip);

        btnDurusKayip.addActionListener(e -> {
            new DurusKayipEkrani().setVisible(true);
            dispose();
        });

        JButton btnMakine = menuButonu("Makine Giriş");
        btnMakine.setBounds(490, 22, 90, 25);
        panelUstMenu.add(btnMakine);

        btnMakine.addActionListener(e -> {
            new MakineGirisEkrani().setVisible(true);
            dispose();
        });

        JButton btnPlanlama = menuButonu("Planlama");
        btnPlanlama.setBounds(590, 22, 100, 25);
        panelUstMenu.add(btnPlanlama);

        btnPlanlama.addActionListener(e -> {
            new PlanlamaEkrani().setVisible(true);
            dispose();
        });

        JButton btnRapor = menuButonu("Rapor");
        btnRapor.setBounds(700, 22, 80, 25);
        panelUstMenu.add(btnRapor);

        btnRapor.addActionListener(e -> {
            new RaporEkrani().setVisible(true);
            dispose();
        });

        JLabel lblKullanici = new JLabel(Session.aktifKullanici);
        lblKullanici.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblKullanici.setForeground(Color.WHITE);
        lblKullanici.setBounds(850, 24, 160, 20);
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

        JPanel panelYan = new JPanel();
        panelYan.setBackground(PANEL_BEYAZ);
        panelYan.setBounds(20, 90, 225, 560);
        panelYan.setLayout(null);
        contentPane.add(panelYan);

        listeModeli = new DefaultListModel<>();
        siparisListesi = new JList<>(listeModeli);
        siparisListesi.setFixedCellHeight(40);
        siparisListesi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        siparisListesi.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        siparisListesi.addListSelectionListener(e -> {
            lblDurum.setText("Durum :");
            if (!e.getValueIsAdjusting()) {
                String secilenSatir = siparisListesi.getSelectedValue();

                if (secilenSatir != null) {
                    String secilenSiparisKodu = secilenSatir.split(" - ")[0];
                    formDoldur(secilenSiparisKodu);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(siparisListesi);
        scrollPane.setBorder(new CompoundBorder(
                new LineBorder(HEADER, 2, true),
                new TitledBorder(null, "Sipariş Listesi", TitledBorder.CENTER, TitledBorder.TOP, null, HEADER)
        ));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBounds(10, 50, 205, 300);
        scrollPane.setPreferredSize(new Dimension(200, 300));
        panelYan.add(scrollPane);

        JButton btnYeniSiparis = new JButton("+ Yeni Sipariş");
        btnYeniSiparis.setBorderPainted(false);
        btnYeniSiparis.setForeground(Color.WHITE);
        btnYeniSiparis.setBackground(BUTON_MAVI);
        btnYeniSiparis.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnYeniSiparis.setBounds(10, 10, 205, 30);
        panelYan.add(btnYeniSiparis);

        btnYeniSiparis.addActionListener(e -> {
            lblDurum.setText("Durum :");
            formuTemizle();
        });

        JPanel panelAna = new JPanel();
        panelAna.setBackground(PANEL_BEYAZ);
        panelAna.setBounds(270, 90, 795, 560);
        panelAna.setLayout(null);
        contentPane.add(panelAna);

        JPanel panelBaslik = new JPanel();
        panelBaslik.setBackground(ACIK_GRI);
        panelBaslik.setBounds(0, 0, 795, 45);
        panelBaslik.setLayout(null);
        panelAna.add(panelBaslik);

        JLabel lblSiparisDetaylari = new JLabel("Sipariş Detayları");
        lblSiparisDetaylari.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblSiparisDetaylari.setForeground(new Color(60, 60, 60));
        lblSiparisDetaylari.setBounds(10, 7, 250, 30);
        panelBaslik.add(lblSiparisDetaylari);

        JLabel lblSiparisAdi = new JLabel("Sipariş Adı");
        lblSiparisAdi.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblSiparisAdi.setBounds(10, 55, 200, 25);
        panelAna.add(lblSiparisAdi);

        txtSiparisAdi = textFieldOlustur();
        txtSiparisAdi.setBounds(10, 85, 375, 30);
        panelAna.add(txtSiparisAdi);

        JLabel lblMusteri = new JLabel("Müşteri");
        lblMusteri.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblMusteri.setBounds(10, 125, 200, 25);
        panelAna.add(lblMusteri);

        txtMusteri = textFieldOlustur();
        txtMusteri.setBounds(10, 154, 375, 30);
        panelAna.add(txtMusteri);

        JLabel lblMiktar = new JLabel("Miktar");
        lblMiktar.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblMiktar.setBounds(10, 195, 200, 25);
        panelAna.add(lblMiktar);

        spinnerMiktar = new JSpinner();
        spinnerMiktar.setBorder(new CompoundBorder(UIManager.getBorder("ComboBox.border"), new EmptyBorder(2, 2, 2, 2)));
        spinnerMiktar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        spinnerMiktar.setBounds(10, 225, 375, 30);
        panelAna.add(spinnerMiktar);

        JLabel lblSiparisKodu = new JLabel("Sipariş Kodu");
        lblSiparisKodu.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblSiparisKodu.setBounds(410, 55, 200, 25);
        panelAna.add(lblSiparisKodu);

        txtSiparisKodu = textFieldOlustur();
        txtSiparisKodu.setBounds(410, 85, 375, 30);
        panelAna.add(txtSiparisKodu);

        JLabel lblUrunAdi = new JLabel("Ürün Adı");
        lblUrunAdi.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblUrunAdi.setBounds(410, 125, 200, 25);
        panelAna.add(lblUrunAdi);

        textUrun = textFieldOlustur();
        textUrun.setBounds(410, 154, 375, 30);
        panelAna.add(textUrun);

        JLabel lblTarihi = new JLabel("Termin Tarihi");
        lblTarihi.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblTarihi.setBounds(410, 195, 200, 25);
        panelAna.add(lblTarihi);

        txtTarih = textFieldOlustur();
        txtTarih.setText("gg.aa.yyyy");
        txtTarih.setEditable(false);
        txtTarih.setBounds(410, 225, 255, 30);
        panelAna.add(txtTarih);

        JButton btnTarihSec = new JButton("Tarih Seç");
        btnTarihSec.setBounds(675, 225, 110, 30);
        btnTarihSec.setBackground(BUTON_MAVI);
        btnTarihSec.setForeground(Color.WHITE);
        btnTarihSec.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnTarihSec.setBorderPainted(false);
        btnTarihSec.setFocusPainted(false);
        panelAna.add(btnTarihSec);

        btnTarihSec.addActionListener(e -> tarihSec());

        lblDurum = new JLabel("Durum :");
        lblDurum.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblDurum.setBounds(10, 265, 775, 25);
        panelAna.add(lblDurum);

        String[] kolonIsimleri = {"İş Emri No", "Görev", "Makine"};
        DefaultTableModel tabloModeli = new DefaultTableModel(kolonIsimleri, 0);
        table = new JTable(tabloModeli);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(new Color(230, 235, 250));
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        
        makineKolonunuComboBoxYap();

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBounds(10, 300, 775, 210);
        panelAna.add(tableScroll);

        JButton btnYeniIsEmri = new JButton("+ Yeni İş Emri");
        btnYeniIsEmri.setForeground(Color.WHITE);
        btnYeniIsEmri.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnYeniIsEmri.setBorderPainted(false);
        btnYeniIsEmri.setBackground(BUTON_MAVI);
        btnYeniIsEmri.setBounds(10, 520, 205, 30);
        panelAna.add(btnYeniIsEmri);

        btnYeniIsEmri.addActionListener(e -> {
            lblDurum.setText("Durum :");
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{"İE-" + (model.getRowCount() + 101), "Görev Girin", "Makine Seçin"});
        });

        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnKaydet.setBorderPainted(false);
        btnKaydet.setBackground(BUTON_MAVI);
        btnKaydet.setBounds(585, 520, 100, 30);
        panelAna.add(btnKaydet);

        btnKaydet.addActionListener(e -> kaydetVeyaGuncelle());

        JButton btnTemizle = new JButton("Temizle");
        btnTemizle.setForeground(Color.BLACK);
        btnTemizle.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnTemizle.setBorderPainted(false);
        btnTemizle.setBackground(TEMIZLE_GRI);
        btnTemizle.setBounds(695, 520, 100, 30);
        panelAna.add(btnTemizle);

        btnTemizle.addActionListener(e -> formuTemizle());

        listeyiYenile();
    }

    private JButton menuButonu(String text) {
        JButton btn = new JButton(text);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 13));
        return btn;
    }

    private JTextField textFieldOlustur() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Tahoma", Font.PLAIN, 18));
        txt.setBorder(new CompoundBorder(new LineBorder(HEADER, 2, true), new EmptyBorder(2, 5, 2, 2)));
        return txt;
    }

    private void tarihSec() {
        JComboBox<Integer> gunBox = new JComboBox<>();
        JComboBox<Integer> ayBox = new JComboBox<>();
        JComboBox<Integer> yilBox = new JComboBox<>();

        for (int i = 1; i <= 12; i++) {
            ayBox.addItem(i);
        }

        for (int i = 2025; i <= 2035; i++) {
            yilBox.addItem(i);
        }

        ayBox.addActionListener(e -> gunleriGuncelle(gunBox, ayBox, yilBox));
        yilBox.addActionListener(e -> gunleriGuncelle(gunBox, ayBox, yilBox));

        gunleriGuncelle(gunBox, ayBox, yilBox);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Gün:"));
        panel.add(gunBox);
        panel.add(new JLabel("Ay:"));
        panel.add(ayBox);
        panel.add(new JLabel("Yıl:"));
        panel.add(yilBox);

        int sonuc = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Termin Tarihi Seç",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (sonuc == JOptionPane.OK_OPTION) {
            int gun = (int) gunBox.getSelectedItem();
            int ay = (int) ayBox.getSelectedItem();
            int yil = (int) yilBox.getSelectedItem();

            txtTarih.setText(String.format("%02d.%02d.%04d", gun, ay, yil));
        }
    }
    
    private void gunleriGuncelle(JComboBox<Integer> gunBox, JComboBox<Integer> ayBox, JComboBox<Integer> yilBox) {
        int seciliGun = gunBox.getSelectedItem() == null ? 1 : (int) gunBox.getSelectedItem();
        int ay = (int) ayBox.getSelectedItem();
        int yil = (int) yilBox.getSelectedItem();

        int gunSayisi = ayinGunSayisi(ay, yil);

        gunBox.removeAllItems();

        for (int i = 1; i <= gunSayisi; i++) {
            gunBox.addItem(i);
        }

        if (seciliGun <= gunSayisi) {
            gunBox.setSelectedItem(seciliGun);
        } else {
            gunBox.setSelectedItem(gunSayisi);
        }
    }

    private int ayinGunSayisi(int ay, int yil) {
        if (ay == 2) {
            if (yil % 4 == 0) {
                return 29;
            } else {
                return 28;
            }
        }

        if (ay == 4 || ay == 6 || ay == 9 || ay == 11) {
            return 30;
        }

        return 31;
    }

     

    private void kaydetVeyaGuncelle() {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        if (!formKontrol()) {
            return;
        }

        Siparis siparis = new Siparis(
                txtSiparisAdi.getText().trim(),
                txtSiparisKodu.getText().trim(),
                txtMusteri.getText().trim(),
                textUrun.getText().trim(),
                (int) spinnerMiktar.getValue(),
                txtTarih.getText().trim()
        );

        if (seciliSiparisId == -1) {
            int id = siparisService.siparisEkle(siparis);

            if (id != -1) {
                isEmirleriniKaydet(id);
                lblDurum.setText("Durum : Sipariş kaydedildi.");
                formuTemizle();
                listeyiYenile();
            } else {
                lblDurum.setText("Durum : Sipariş kaydedilemedi. Kod kullanımda olabilir.");
            }

        } else {
            boolean sonuc = siparisService.siparisGuncelle(seciliSiparisId, siparis);

            if (sonuc) {
                planlamaService.siparisPlanlamalariniSil(seciliSiparisId);
                isEmirleriniKaydet(seciliSiparisId);

                lblDurum.setText("Durum : Sipariş başarıyla güncellendi.");
                listeyiYenile();
            } else {
                lblDurum.setText("Durum : Güncelleme yapılamadı.");
            }
        }
    }

    private boolean formKontrol() {
        if (txtSiparisAdi.getText().trim().isEmpty()) {
            lblDurum.setText("Durum: Sipariş Adı boş bırakılamaz.");
            return false;
        }

        if (txtSiparisKodu.getText().trim().isEmpty()) {
            lblDurum.setText("Durum: Sipariş Kodu boş bırakılamaz.");
            return false;
        }

        if (txtMusteri.getText().trim().isEmpty()) {
            lblDurum.setText("Durum: Müşteri boş bırakılamaz.");
            return false;
        }

        if (textUrun.getText().trim().isEmpty()) {
            lblDurum.setText("Durum: Ürün Adı boş bırakılamaz.");
            return false;
        }

        if (txtTarih.getText().trim().equals("gg.aa.yyyy")) {
            lblDurum.setText("Durum: Termin tarihi seçiniz.");
            return false;
        }

        return true;
    }

    private void isEmirleriniKaydet(int siparisId) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            Object gorevObj = model.getValueAt(i, 1);
            Object makineObj = model.getValueAt(i, 2);

            if (gorevObj == null || gorevObj.toString().trim().isEmpty() || gorevObj.toString().equals("Görev Girin")) {
                continue;
            }

            String gorev = gorevObj.toString();
            String makine = "Belirtilmedi";

            if (makineObj != null && !makineObj.toString().equals("Makine Seçin")) {
                makine = makineObj.toString();
            }

            Planlama planlama = new Planlama(siparisId, gorev, makine);
            planlamaService.planlamaEkle(planlama);
        }
    }

    private void formDoldur(String kod) {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        Siparis siparis = siparisService.siparisGetir(kod);

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);

        if (siparis == null) {
            lblDurum.setText("Durum : Sipariş bulunamadı.");
            return;
        }

        seciliSiparisId = siparis.getId();

        txtSiparisAdi.setText(siparis.getSiparisAdi());
        txtSiparisKodu.setText(siparis.getSiparisKodu());
        txtMusteri.setText(siparis.getMusteri());
        textUrun.setText(siparis.getUrunAdi());
        spinnerMiktar.setValue(siparis.getMiktar());
        txtTarih.setText(siparis.getTerminTarihi());

        List<Planlama> planlamalar = planlamaService.siparisPlanlamalariniGetir(siparis.getId());

        int sayac = 101;

        for (Planlama planlama : planlamalar) {
            tableModel.addRow(new Object[]{
                    "İE-" + sayac,
                    planlama.getGorev(),
                    planlama.getMakine()
            });

            sayac++;
        }
    }

    private void formuTemizle() {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        seciliSiparisId = -1;
        txtSiparisAdi.setText("");
        txtMusteri.setText("");
        textUrun.setText("");
        txtSiparisKodu.setText("");
        spinnerMiktar.setValue(0);
        txtTarih.setText("gg.aa.yyyy");
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        siparisListesi.clearSelection();
    }

    private void listeyiYenile() {
        DefaultListModel<String> model = (DefaultListModel<String>) siparisListesi.getModel();
        model.removeAllElements();

        List<Siparis> siparisler = siparisService.tumSiparisleriGetir();

        for (Siparis siparis : siparisler) {
            model.addElement(siparis.getSiparisKodu() + " - " + siparis.getSiparisAdi());
        }
    }

    
    private void makineKolonunuComboBoxYap() {
        JComboBox<String> makineComboBox = new JComboBox<>();

        makineComboBox.addItem("Makine Seçin");

        List<Makine> makineler = makineService.tumMakineleriGetir();

        for (Makine makine : makineler) {
            makineComboBox.addItem(makine.getMakineKodu());
        }

        TableColumn makineColumn = table.getColumnModel().getColumn(2);
        makineColumn.setCellEditor(new javax.swing.DefaultCellEditor(makineComboBox));
    }
    
    public static void main(String[] args) {
        new SiparisEkrani().setVisible(true);
    }
}
