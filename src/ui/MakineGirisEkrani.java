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


import model.Makine;
import service.MakineService;



public class MakineGirisEkrani extends OrtakEkran {

    private static final long serialVersionUID = 1L;
  

    private JTextField txtMakineKodu;
    private JTextField txtKapasite;
    private JTextField txtBakimPeriyodu;

    private JTable table;
    private DefaultTableModel model;

    private int secilenSatir = -1;
    private boolean duzenlemeModu = false;

    private JComboBox<String> cmbMakineTipi;
    private JComboBox<String> cmbBolum;
    private JComboBox<String> cmbLokasyon;

    private JButton btnKaydet;
    private JButton btnTemizle;

    private MakineService makineService = new MakineService();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MakineGirisEkrani frame = new MakineGirisEkrani();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MakineGirisEkrani() {
    	super("Makine Girişi");

    	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    	ustMenuOlustur("MAKİNE GİRİŞİ");

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

        JLabel lblFormBaslik = new JLabel("Makine Girişi");
        lblFormBaslik.setForeground(new Color(60, 60, 60));
        lblFormBaslik.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblFormBaslik.setBounds(20, 12, 200, 30);
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
        cmbMakineTipi.setModel(new DefaultComboBoxModel<>(new String[] {
                "Karıştırma Makinesi",
                "Dolum Makinesi",
                "Kapak Kapatma Makinesi",
                "Etiketleme Makinesi",
                "Paketleme Makinesi"
        }));
        cmbMakineTipi.setBounds(25, 100, 270, 30);
        panelForm.add(cmbMakineTipi);

        JLabel lblMakineKodu = new JLabel("Makine Kodu:");
        lblMakineKodu.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblMakineKodu.setBounds(380, 75, 120, 20);
        panelForm.add(lblMakineKodu);

        txtMakineKodu = new JTextField();
        txtMakineKodu.setBounds(380, 100, 270, 30);
        panelForm.add(txtMakineKodu);

        JLabel lblKapasite = new JLabel("Çalışma Kapasitesi:");
        lblKapasite.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblKapasite.setBounds(735, 75, 150, 20);
        panelForm.add(lblKapasite);

        txtKapasite = new JTextField();
        txtKapasite.setBounds(735, 100, 270, 30);
        panelForm.add(txtKapasite);

        JLabel lblBolum = new JLabel("Bölüm:");
        lblBolum.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblBolum.setBounds(25, 150, 120, 20);
        panelForm.add(lblBolum);

        cmbBolum = new JComboBox<>();
        cmbBolum.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cmbBolum.setModel(new DefaultComboBoxModel<>(new String[] {
                "Karışım Bölümü",
                "Dolum Bölümü",
                "Kapak Kapatma Bölümü",
                "Etiketleme Bölümü",
                "Paketleme Bölümü",
                "Depo",
                "Bakım Atölyesi"
        }));
        cmbBolum.setBounds(25, 175, 270, 30);
        panelForm.add(cmbBolum);

        JLabel lblLokasyon = new JLabel("Lokasyon:");
        lblLokasyon.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblLokasyon.setBounds(380, 150, 120, 20);
        panelForm.add(lblLokasyon);

        cmbLokasyon = new JComboBox<>();
        cmbLokasyon.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cmbLokasyon.setModel(new DefaultComboBoxModel<>(new String[] {
                "Hat 1",
                "Hat 2",
                "Hat 3"
        }));
        cmbLokasyon.setBounds(380, 175, 270, 30);
        panelForm.add(cmbLokasyon);

        JLabel lblBakimPeriyodu = new JLabel("Bakım Periyodu:");
        lblBakimPeriyodu.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblBakimPeriyodu.setBounds(735, 150, 150, 20);
        panelForm.add(lblBakimPeriyodu);

        txtBakimPeriyodu = new JTextField();
        txtBakimPeriyodu.setBounds(735, 175, 270, 30);
        panelForm.add(txtBakimPeriyodu);

        btnKaydet = new JButton("Kaydet");
        btnKaydet.setBounds(410, 250, 120, 35);
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
        btnTemizle.setBounds(570, 250, 120, 35);
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
                "Makine Tipi",
                "Makine Kodu",
                "Bölüm",
                "Çalışma Kapasitesi",
                "Bakım Periyodu",
                "Lokasyon",
                "İşlem"
        });

        table.setModel(model);
        scrollPane.setViewportView(table);

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

                if (satir != -1 && sutun == 6) {
                    secilenSatir = satir;
                    duzenlemeModu = true;

                    cmbMakineTipi.setSelectedItem(model.getValueAt(satir, 0).toString());
                    txtMakineKodu.setText(model.getValueAt(satir, 1).toString());
                    cmbBolum.setSelectedItem(model.getValueAt(satir, 2).toString());
                    txtKapasite.setText(model.getValueAt(satir, 3).toString());
                    txtBakimPeriyodu.setText(model.getValueAt(satir, 4).toString());
                    cmbLokasyon.setSelectedItem(model.getValueAt(satir, 5).toString());

                    btnKaydet.setText("Güncelle");
                    btnTemizle.setText("Sil");
                }
            }
        });

        makineleriYukle();
    }
   
    

    private void kaydetVeyaGuncelle() {
        String makineTipi = cmbMakineTipi.getSelectedItem().toString();
        String makineKodu = txtMakineKodu.getText().trim();
        String bolum = cmbBolum.getSelectedItem().toString();
        String kapasite = txtKapasite.getText().trim();
        String bakimPeriyodu = txtBakimPeriyodu.getText().trim();
        String lokasyon = cmbLokasyon.getSelectedItem().toString();

        if (makineKodu.isEmpty() || kapasite.isEmpty() || bakimPeriyodu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lütfen boş alan bırakmayın.");
            return;
        }

        Makine makine = new Makine(
                makineTipi,
                makineKodu,
                bolum,
                kapasite,
                bakimPeriyodu,
                lokasyon
        );

        if (!duzenlemeModu) {
            boolean sonuc = makineService.makineEkle(makine);

            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Makine kaydedildi.");
                makineleriYukle();
                formTemizle();
            } else {
                JOptionPane.showMessageDialog(null, "Kayıt hatası! Makine kodu kullanımda olabilir.");
            }

        } else {
            String eskiMakineKodu = model.getValueAt(secilenSatir, 1).toString();

            boolean sonuc = makineService.makineGuncelle(eskiMakineKodu, makine);

            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Makine güncellendi.");
                makineleriYukle();
                formTemizle();
            } else {
                JOptionPane.showMessageDialog(null, "Güncelleme hatası!");
            }
        }
    }

    private void makineleriYukle() {
        model.setRowCount(0);

        List<Makine> makineler = makineService.tumMakineleriGetir();

        for (Makine makine : makineler) {
            model.addRow(new Object[] {
                    makine.getMakineTipi(),
                    makine.getMakineKodu(),
                    makine.getBolum(),
                    makine.getKapasite(),
                    makine.getBakimPeriyodu(),
                    makine.getLokasyon(),
                    "Düzenle"
            });
        }
    }

    private void sil() {
        if (secilenSatir == -1) {
            return;
        }

        String makineKodu = model.getValueAt(secilenSatir, 1).toString();

        int cevap = JOptionPane.showOptionDialog(
                this,
                "Bu makine kaydı silinsin mi?",
                "Silme Onayı",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Evet", "Hayır"},
                "Hayır"
        );

        if (cevap == JOptionPane.YES_OPTION) {
            boolean sonuc = makineService.makineSil(makineKodu);

            if (sonuc) {
                JOptionPane.showMessageDialog(null, "Makine silindi.");
                makineleriYukle();
                formTemizle();
            } else {
                JOptionPane.showMessageDialog(null, "Silme hatası!");
            }
        }
    }

    private void formTemizle() {
        cmbMakineTipi.setSelectedIndex(0);
        txtMakineKodu.setText("");
        cmbBolum.setSelectedIndex(0);
        cmbLokasyon.setSelectedIndex(0);

        txtKapasite.setText("");
        txtBakimPeriyodu.setText("");

        secilenSatir = -1;
        duzenlemeModu = false;

        btnKaydet.setText("Kaydet");
        btnTemizle.setText("Temizle");
        table.clearSelection();
    }
}