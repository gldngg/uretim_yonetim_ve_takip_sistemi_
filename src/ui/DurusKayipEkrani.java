package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import database.Session;
import model.DurusKayip;
import model.Makine;
import service.DurusKayipService;
import service.MakineService;

public class DurusKayipEkrani extends OrtakEkran {

    private static final long serialVersionUID = 1L;


    private JComboBox<String> cmbMakineTipi;
    private JComboBox<String> cmbMakineKodu;
    private JTextField txtBaslangic;
    private JTextField txtBitis;
    private JTextField txtSure;
    private JComboBox<String> cmbDurusTuru;
    private JComboBox<String> cmbDurusNedeni;
    private JTextField txtAciklama;

    private JTable table;
    private DefaultTableModel model;

    private JButton btnKaydet;
    private JButton btnTemizle;

    private int secilenSatir = -1;
    private int secilenId = -1;
    private boolean duzenlemeModu = false;
    
    private static final DateTimeFormatter TARIH_SAAT_FMT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private DurusKayipService durusKayipService = new DurusKayipService();
    private MakineService makineService = new MakineService();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DurusKayipEkrani frame = new DurusKayipEkrani();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public DurusKayipEkrani() {
    	super("Duruş/Kayıp");

    	System.out.println("Aktif kullanıcı: " + Session.aktifKullanici);

    	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    	ustMenuOlustur("DURUŞ/KAYIP");

        JPanel panelForm = new JPanel();
        panelForm.setBounds(20, 100, 1040, 320);
        panelForm.setBackground(Color.WHITE);
        panelForm.setLayout(null);
        contentPane.add(panelForm);

        JPanel panelBaslik = new JPanel();
        panelBaslik.setBackground(new Color(245, 245, 245));
        panelBaslik.setBounds(0, 0, 1040, 55);
        panelBaslik.setLayout(null);
        panelForm.add(panelBaslik);

        JLabel lblFormBaslik = new JLabel("Duruş/Kayıp Girişi");
        lblFormBaslik.setForeground(new Color(60, 60, 60));
        lblFormBaslik.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblFormBaslik.setBounds(20, 12, 250, 30);
        panelBaslik.add(lblFormBaslik);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 55, 1040, 2);
        panelForm.add(separator);

        JLabel lblMakineTipi = new JLabel("Makine Tipi:");
        lblMakineTipi.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblMakineTipi.setBounds(25, 75, 120, 20);
        panelForm.add(lblMakineTipi);

        cmbMakineTipi = new JComboBox<>();
        cmbMakineTipi.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cmbMakineTipi.setBounds(25, 100, 270, 30);
        panelForm.add(cmbMakineTipi);

        JLabel lblMakineKodu = new JLabel("Makine Kodu:");
        lblMakineKodu.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblMakineKodu.setBounds(380, 75, 120, 20);
        panelForm.add(lblMakineKodu);

        cmbMakineKodu = new JComboBox<>();
        cmbMakineKodu.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cmbMakineKodu.setBounds(380, 100, 270, 30);
        panelForm.add(cmbMakineKodu);

        JLabel lblBaslangic = new JLabel("Başlangıç:");
        lblBaslangic.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblBaslangic.setBounds(735, 75, 120, 20);
        panelForm.add(lblBaslangic);

        txtBaslangic = new JTextField();
        txtBaslangic.setBounds(735, 100, 170, 30);
        txtBaslangic.setEditable(false);
        panelForm.add(txtBaslangic);

        JButton btnBaslangicSec = new JButton("Seç");
        btnBaslangicSec.setBounds(915, 100, 90, 30);
        btnBaslangicSec.setBackground(new Color(52, 152, 219));
        btnBaslangicSec.setForeground(Color.WHITE);
        btnBaslangicSec.setFocusPainted(false);
        btnBaslangicSec.setBorderPainted(false);
        panelForm.add(btnBaslangicSec);

        btnBaslangicSec.addActionListener(e -> {
            tarihSaatSec(txtBaslangic);
            sureHesapla();
        });

        JLabel lblBitis = new JLabel("Bitiş:");
        lblBitis.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblBitis.setBounds(25, 150, 120, 20);
        panelForm.add(lblBitis);

        txtBitis = new JTextField();
        txtBitis.setBounds(25, 175, 170, 30);
        txtBitis.setEditable(false);
        panelForm.add(txtBitis);

        JButton btnBitisSec = new JButton("Seç");
        btnBitisSec.setBounds(205, 175, 90, 30);
        btnBitisSec.setBackground(new Color(52, 152, 219));
        btnBitisSec.setForeground(Color.WHITE);
        btnBitisSec.setFocusPainted(false);
        btnBitisSec.setBorderPainted(false);
        panelForm.add(btnBitisSec);

        btnBitisSec.addActionListener(e -> {
            tarihSaatSec(txtBitis);
            sureHesapla();
        });
        JLabel lblSure = new JLabel("Süre:");
        lblSure.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSure.setBounds(380, 150, 120, 20);
        panelForm.add(lblSure);

        txtSure = new JTextField();
        txtSure.setBounds(380, 175, 270, 30);
        txtSure.setEditable(false);
        panelForm.add(txtSure);

        JLabel lblDurusTuru = new JLabel("Duruş Türü:");
        lblDurusTuru.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblDurusTuru.setBounds(735, 150, 120, 20);
        panelForm.add(lblDurusTuru);

        cmbDurusTuru = new JComboBox<>();
        cmbDurusTuru.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cmbDurusTuru.setModel(new DefaultComboBoxModel<>(new String[] {
                "Planlı Duruş",
                "Plansız Duruş"
        }));
        cmbDurusTuru.setBounds(735, 175, 270, 30);
        panelForm.add(cmbDurusTuru);

        JLabel lblDurusNedeni = new JLabel("Duruş Nedeni:");
        lblDurusNedeni.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblDurusNedeni.setBounds(25, 225, 150, 20);
        panelForm.add(lblDurusNedeni);

        cmbDurusNedeni = new JComboBox<>();
        cmbDurusNedeni.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cmbDurusNedeni.setModel(new DefaultComboBoxModel<>(new String[] {
                "Arıza",
                "Bakım",
                "Malzeme Eksikliği",
                "Operatör Kaynaklı",
                "Enerji Kesintisi",
                "Temizlik",
                "Diğer"
        }));
        cmbDurusNedeni.setBounds(25, 250, 270, 30);
        panelForm.add(cmbDurusNedeni);

        JLabel lblAciklama = new JLabel("Açıklama:");
        lblAciklama.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblAciklama.setBounds(380, 225, 120, 20);
        panelForm.add(lblAciklama);

        txtAciklama = new JTextField();
        txtAciklama.setBounds(380, 250, 270, 30);
        panelForm.add(txtAciklama);

        btnKaydet = new JButton("Kaydet");
        btnKaydet.setBounds(735, 245, 120, 35);
        btnKaydet.setBackground(new Color(52, 152, 219));
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnKaydet.setFocusPainted(false);
        btnKaydet.setBorderPainted(false);
        btnKaydet.setContentAreaFilled(false);
        btnKaydet.setOpaque(true);
        panelForm.add(btnKaydet);

        btnTemizle = new JButton("Temizle");
        btnTemizle.setForeground(Color.WHITE);
        btnTemizle.setBackground(new Color(220, 53, 69));
        btnTemizle.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnTemizle.setBounds(885, 245, 120, 35);
        btnTemizle.setFocusPainted(false);
        btnTemizle.setBorderPainted(false);
        btnTemizle.setContentAreaFilled(false);
        btnTemizle.setOpaque(true);
        panelForm.add(btnTemizle);

        JPanel panelTablo = new JPanel();
        panelTablo.setBounds(20, 440, 1040, 190);
        panelTablo.setBackground(Color.WHITE);
        panelTablo.setLayout(null);
        contentPane.add(panelTablo);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 1020, 170);
        panelTablo.add(scrollPane);

        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(230, 235, 250));
        table.setBackground(Color.WHITE);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {
                "ID",
                "Makine Tipi",
                "Makine Kodu",
                "Başlangıç",
                "Bitiş",
                "Süre",
                "Duruş Türü",
                "Duruş Nedeni",
                "Açıklama",
                "İşlem"
        });

        table.setModel(model);
        scrollPane.setViewportView(table);

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        btnKaydet.addActionListener(e -> kaydetVeyaGuncelle());

        btnTemizle.addActionListener(e -> {
            if (duzenlemeModu && secilenSatir != -1) {
                sil();
            } else {
                formTemizle();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int satir = table.getSelectedRow();
                int sutun = table.getSelectedColumn();

                if (satir != -1 && sutun == 9) {
                    secilenSatir = satir;
                    duzenlemeModu = true;
                    secilenId = Integer.parseInt(model.getValueAt(satir, 0).toString());

                    cmbMakineTipi.setSelectedItem(model.getValueAt(satir, 1).toString());
                    cmbMakineKodu.setSelectedItem(model.getValueAt(satir, 2).toString());
                    txtBaslangic.setText(model.getValueAt(satir, 3).toString());
                    txtBitis.setText(model.getValueAt(satir, 4).toString());
                    txtSure.setText(model.getValueAt(satir, 5).toString());
                    cmbDurusTuru.setSelectedItem(model.getValueAt(satir, 6).toString());
                    cmbDurusNedeni.setSelectedItem(model.getValueAt(satir, 7).toString());
                    txtAciklama.setText(model.getValueAt(satir, 8).toString());

                    btnKaydet.setText("Güncelle");
                    btnTemizle.setText("Sil");
                }
            }
        });

        makineleriComboYukle();
        duruslariYukle();
    }

    private void makineleriComboYukle() {
        cmbMakineTipi.removeAllItems();
        cmbMakineKodu.removeAllItems();

        List<Makine> makineler = makineService.tumMakineleriGetir();

        for (Makine makine : makineler) {
            cmbMakineTipi.addItem(makine.getMakineTipi());
            cmbMakineKodu.addItem(makine.getMakineKodu());
        }
    }

    private void kaydetVeyaGuncelle() {
        if (cmbMakineTipi.getSelectedItem() == null || cmbMakineKodu.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Önce makine girişi yapmalısınız.");
            return;
        }

        String makineTipi = cmbMakineTipi.getSelectedItem().toString();
        String makineKodu = cmbMakineKodu.getSelectedItem().toString();
        String baslangic = txtBaslangic.getText().trim();
        String bitis = txtBitis.getText().trim();
        String sure = txtSure.getText().trim();
        String durusTuru = cmbDurusTuru.getSelectedItem().toString();
        String durusNedeni = cmbDurusNedeni.getSelectedItem().toString();
        String aciklama = txtAciklama.getText().trim();

        if (baslangic.isEmpty() || bitis.isEmpty() || sure.isEmpty() || aciklama.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lütfen boş alan bırakmayın.");
            return;
        }

        DurusKayip durusKayip = new DurusKayip(
                makineTipi,
                makineKodu,
                baslangic,
                bitis,
                sure,
                durusTuru,
                durusNedeni,
                aciklama
        );

        if (!duzenlemeModu) {
            boolean sonuc = durusKayipService.durusKayipEkle(durusKayip);

            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Duruş/Kayıp kaydedildi.");
                duruslariYukle();
                formTemizle();
            } else {
                JOptionPane.showMessageDialog(null, "Kayıt hatası!");
            }

        } else {
            boolean sonuc = durusKayipService.durusKayipGuncelle(secilenId, durusKayip);

            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Duruş/Kayıp güncellendi.");
                duruslariYukle();
                formTemizle();
            } else {
                JOptionPane.showMessageDialog(null, "Güncelleme hatası!");
            }
        }
    }
    
    private void tarihSaatSec(JTextField hedefAlan) {
        JComboBox<Integer> gunBox = new JComboBox<>();
        JComboBox<Integer> ayBox = new JComboBox<>();
        JComboBox<Integer> yilBox = new JComboBox<>();
        JComboBox<Integer> saatBox = new JComboBox<>();
        JComboBox<Integer> dakikaBox = new JComboBox<>();

        for (int i = 1; i <= 12; i++) {
            ayBox.addItem(i);
        }

        for (int i = 2025; i <= 2035; i++) {
            yilBox.addItem(i);
        }

        for (int i = 0; i <= 23; i++) {
            saatBox.addItem(i);
        }

        for (int i = 0; i <= 59; i++) {
            dakikaBox.addItem(i);
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
        panel.add(new JLabel("Saat:"));
        panel.add(saatBox);
        panel.add(new JLabel("Dakika:"));
        panel.add(dakikaBox);

        int sonuc = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Tarih ve Saat Seç",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (sonuc == JOptionPane.OK_OPTION) {
            int gun = (int) gunBox.getSelectedItem();
            int ay = (int) ayBox.getSelectedItem();
            int yil = (int) yilBox.getSelectedItem();
            int saat = (int) saatBox.getSelectedItem();
            int dakika = (int) dakikaBox.getSelectedItem();

            hedefAlan.setText(String.format(
                    "%02d.%02d.%04d %02d:%02d",
                    gun, ay, yil, saat, dakika
            ));
        }
    }

    private void gunleriGuncelle(JComboBox<Integer> gunBox,
                                 JComboBox<Integer> ayBox,
                                 JComboBox<Integer> yilBox) {

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
        	if(java.time.Year.isLeap(yil)) {
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

    private void sureHesapla() {
        String baslangic = txtBaslangic.getText().trim();
        String bitis = txtBitis.getText().trim();

        if (baslangic.isEmpty() || bitis.isEmpty()) {
            return;
        }

        try {
            LocalDateTime baslangicZamani = LocalDateTime.parse(baslangic, TARIH_SAAT_FMT);
            LocalDateTime bitisZamani = LocalDateTime.parse(bitis, TARIH_SAAT_FMT);

            if (bitisZamani.isBefore(baslangicZamani)) {
                JOptionPane.showMessageDialog(this, "Bitiş zamanı başlangıçtan önce olamaz.");
                txtSure.setText("");
                return;
            }

            Duration fark = Duration.between(baslangicZamani, bitisZamani);

            long toplamDakika = fark.toMinutes();
            long saat = toplamDakika / 60;
            long dakika = toplamDakika % 60;

            txtSure.setText(saat + " saat " + dakika + " dakika");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tarih/saat hesaplama hatası.");
            txtSure.setText("");
        }
    }

    private void duruslariYukle() {
        model.setRowCount(0);

        List<DurusKayip> duruslar = durusKayipService.tumDurusKayiplariGetir();

        for (DurusKayip durus : duruslar) {
            model.addRow(new Object[] {
                    durus.getId(),
                    durus.getMakineTipi(),
                    durus.getMakineKodu(),
                    durus.getBaslangic(),
                    durus.getBitis(),
                    durus.getSure(),
                    durus.getDurusTuru(),
                    durus.getDurusNedeni(),
                    durus.getAciklama(),
                    "Düzenle"
            });
        }
    }

    private void sil() {
        if (secilenId == -1) {
            return;
        }

        int cevap = JOptionPane.showOptionDialog(
                this,
                "Bu duruş/kayıp kaydı silinsin mi?",
                "Silme Onayı",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Evet", "Hayır"},
                "Hayır"
        );
        
       
   

        if (cevap == JOptionPane.YES_OPTION) {
            boolean sonuc = durusKayipService.durusKayipSil(secilenId);

            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Duruş/Kayıp silindi.");
                duruslariYukle();
                formTemizle();
            } else {
                JOptionPane.showMessageDialog(null, "Silme hatası!");
            }
        }
    }

    private void formTemizle() {
        if (cmbMakineTipi.getItemCount() > 0) {
            cmbMakineTipi.setSelectedIndex(0);
        }

        if (cmbMakineKodu.getItemCount() > 0) {
            cmbMakineKodu.setSelectedIndex(0);
        }

        txtBaslangic.setText("");
        txtBitis.setText("");
        txtSure.setText("");
        cmbDurusTuru.setSelectedIndex(0);
        cmbDurusNedeni.setSelectedIndex(0);
        txtAciklama.setText("");

        secilenSatir = -1;
        secilenId = -1;
        duzenlemeModu = false;

        btnKaydet.setText("Kaydet");
        btnTemizle.setText("Temizle");
        table.clearSelection();
    
  
    }

    
}