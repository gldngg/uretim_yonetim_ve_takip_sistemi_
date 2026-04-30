package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.Session;
import model.DurusKayip;
import model.Makine;
import service.DurusKayipService;
import service.MakineService;

public class DurusKayipEkrani extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

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
        System.out.println("Aktif kullanıcı: " + Session.aktifKullanici);
        setTitle("Duruş/Kayıp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 760);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(142, 155, 213));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelUstMenu = new JPanel();
        panelUstMenu.setBounds(20, 20, 1040, 70);
        panelUstMenu.setBackground(new Color(63, 81, 181));
        panelUstMenu.setLayout(null);
        contentPane.add(panelUstMenu);

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/ui/logo.png"));
        Image logoImg = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        JLabel lblLogo = new JLabel(new ImageIcon(logoImg));
        lblLogo.setBounds(20, 10, 50, 50);
        panelUstMenu.add(lblLogo);

        JLabel lblBaslik = new JLabel("DURUŞ/KAYIP");
        lblBaslik.setBounds(85, 18, 220, 30);
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 22));
        panelUstMenu.add(lblBaslik);

        JButton btnAnaSayfa = new JButton("Ana Sayfa");
        btnAnaSayfa.setContentAreaFilled(false);
        btnAnaSayfa.setBorderPainted(false);
        btnAnaSayfa.setFocusPainted(false);
        btnAnaSayfa.setForeground(Color.WHITE);
        btnAnaSayfa.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAnaSayfa.setBounds(250, 20, 100, 25);
        panelUstMenu.add(btnAnaSayfa);
        
        btnAnaSayfa.addActionListener(e -> {
            if ("Admin".equals(Session.aktifRol)) {
                new AdminMenuEkrani().setVisible(true);
            } else {
                new OperatorMenuEkrani().setVisible(true);
            }
            dispose();
        });

        JButton btnMakineGirisi = new JButton("Makine Girişi");
        btnMakineGirisi.setContentAreaFilled(false);
        btnMakineGirisi.setBorderPainted(false);
        btnMakineGirisi.setFocusPainted(false);
        btnMakineGirisi.setForeground(Color.WHITE);
        btnMakineGirisi.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnMakineGirisi.setBounds(360, 20, 130, 25);
        panelUstMenu.add(btnMakineGirisi);

        btnMakineGirisi.addActionListener(e -> {
            new MakineGirisEkrani().setVisible(true);
            dispose();
        });

        JButton btnSiparis = new JButton("Sipariş");
        btnSiparis.setContentAreaFilled(false);
        btnSiparis.setBorderPainted(false);
        btnSiparis.setFocusPainted(false);
        btnSiparis.setForeground(Color.WHITE);
        btnSiparis.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnSiparis.setBounds(500, 20, 90, 25);
        panelUstMenu.add(btnSiparis);
        
        btnSiparis.addActionListener(e -> {
            new SiparisEkrani().setVisible(true);
            dispose();
        });

        JButton btnPlanlama = new JButton("Planlama");
        btnPlanlama.setContentAreaFilled(false);
        btnPlanlama.setBorderPainted(false);
        btnPlanlama.setFocusPainted(false);
        btnPlanlama.setForeground(Color.WHITE);
        btnPlanlama.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnPlanlama.setBounds(600, 20, 100, 25);
        panelUstMenu.add(btnPlanlama);
        
        btnPlanlama.addActionListener(e -> {
            new PlanlamaEkrani().setVisible(true);
            dispose();
        });

        JButton btnRapor = new JButton("Rapor");
        btnRapor.setContentAreaFilled(false);
        btnRapor.setBorderPainted(false);
        btnRapor.setFocusPainted(false);
        btnRapor.setForeground(Color.WHITE);
        btnRapor.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnRapor.setBounds(710, 20, 80, 25);
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
        btnCikis.setBounds(950, 18, 70, 30);
        panelUstMenu.add(btnCikis);

        btnCikis.addActionListener(e -> {
            Session.aktifKullanici = "";
            Session.aktifRol = "";
            new GirisEkrani().setVisible(true);
            dispose();
        });

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
        txtBaslangic.setBounds(735, 100, 270, 30);
        panelForm.add(txtBaslangic);

        JLabel lblBitis = new JLabel("Bitiş:");
        lblBitis.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblBitis.setBounds(25, 150, 120, 20);
        panelForm.add(lblBitis);

        txtBitis = new JTextField();
        txtBitis.setBounds(25, 175, 270, 30);
        panelForm.add(txtBitis);

        JLabel lblSure = new JLabel("Süre:");
        lblSure.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSure.setBounds(380, 150, 120, 20);
        panelForm.add(lblSure);

        txtSure = new JTextField();
        txtSure.setBounds(380, 175, 270, 30);
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
        btnTemizle.setForeground(Color.BLACK);
        btnTemizle.setBackground(new Color(220, 220, 220));
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

        int cevap = JOptionPane.showConfirmDialog(
                null,
                "Bu duruş/kayıp kaydı silinsin mi?",
                "Silme Onayı",
                JOptionPane.YES_NO_OPTION
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
