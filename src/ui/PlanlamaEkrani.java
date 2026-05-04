package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import database.Session;
import model.Planlama;
import service.PlanlamaService;




public class PlanlamaEkrani extends OrtakEkran {

    private static final long serialVersionUID = 1L;

    private String takvimModu = "HAFTA";
    private LocalDate seciliTarih = LocalDate.now();
    private LocalDate haftaBasi;
    private JLabel lblHaftaAraligi;

    private JTable takvimTable;
    private DefaultTableModel takvimModel;

    private JTable isEmriTable;
    private DefaultTableModel isEmriModel;

    private JTextField txtPlanTarihi;
    private JComboBox<String> cmbDurum;
    private int secilenPlanlamaId = -1;

    private JProgressBar pbMakine;
    private JProgressBar pbIsci;
    private JPanel piePanel;

    private int pieStokta = 0;
    private int pieSipariste = 0;
    private int pieEksik = 100;

    private PlanlamaService planlamaService = new PlanlamaService();

    private static final DateTimeFormatter TR_FMT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final Locale TR_LOCALE = Locale.of("tr", "TR");

  

    public static void main(String[] args) {
        new PlanlamaEkrani().setVisible(true);
    }

    public PlanlamaEkrani() {
    	 super("Üretim Planlama");
        haftaBasi = LocalDate.now().with(DayOfWeek.MONDAY);
        ustMenuOlustur("PLANLAMA");

        boolean operatorMu = "operator".equalsIgnoreCase(Session.aktifRol)
                || "operatör".equalsIgnoreCase(Session.aktifRol);
        
        JPanel panelTakvim = new JPanel();
        panelTakvim.setBounds(30, 100, 1040, 250);
        panelTakvim.setBackground(PANEL_BEYAZ);
        panelTakvim.setLayout(new java.awt.BorderLayout());
        contentPane.add(panelTakvim);

        JPanel panelTakvimBaslik = new JPanel(null);
        panelTakvimBaslik.setBackground(ACIK_GRI);
        panelTakvimBaslik.setPreferredSize(new java.awt.Dimension(1040, 42));
        panelTakvim.add(panelTakvimBaslik, java.awt.BorderLayout.NORTH);

        JLabel lblTakvimBaslik = new JLabel("Üretim Takvimi");
        lblTakvimBaslik.setBounds(12, 8, 200, 26);
        lblTakvimBaslik.setFont(new Font("Tahoma", Font.BOLD, 16));
        panelTakvimBaslik.add(lblTakvimBaslik);

        JButton btnGun = new JButton("Gün");
        btnGun.setBounds(550, 8, 60, 26);
        panelTakvimBaslik.add(btnGun);

        JButton btnHafta = new JButton("Hafta");
        btnHafta.setBounds(615, 8, 70, 26);
        panelTakvimBaslik.add(btnHafta);

        JButton btnAy = new JButton("Ay");
        btnAy.setBounds(690, 8, 60, 26);
        panelTakvimBaslik.add(btnAy);

        JButton btnOnceki = new JButton("◀");
        btnOnceki.setBounds(790, 12, 43, 20);
        btnOnceki.setBackground(HEADER);
        btnOnceki.setForeground(Color.WHITE);
        btnOnceki.setFocusPainted(false);
        btnOnceki.setBorderPainted(false);
        panelTakvimBaslik.add(btnOnceki);

        lblHaftaAraligi = new JLabel("", SwingConstants.CENTER);
        lblHaftaAraligi.setBounds(830, 8, 160, 26);
        lblHaftaAraligi.setFont(new Font("Tahoma", Font.PLAIN, 11));
        panelTakvimBaslik.add(lblHaftaAraligi);

        JButton btnSonraki = new JButton("▶");
        btnSonraki.setBounds(992, 12, 43, 20);
        btnSonraki.setBackground(HEADER);
        btnSonraki.setForeground(Color.WHITE);
        btnSonraki.setFocusPainted(false);
        btnSonraki.setBorderPainted(false);
        panelTakvimBaslik.add(btnSonraki);

        takvimModel = new DefaultTableModel(new String[]{"Pzt", "Sal", "Çar", "Per", "Cum", "Cmt", "Paz"}, 8) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        takvimTable = new JTable(takvimModel);
        takvimTable.setRowHeight(26);
        takvimTable.setGridColor(new Color(230, 230, 230));
        takvimTable.setFillsViewportHeight(true);
        takvimTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        takvimTable.getTableHeader().setBackground(new Color(230, 235, 250));
        takvimTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane takvimScroll = new JScrollPane(takvimTable);
        panelTakvim.add(takvimScroll, java.awt.BorderLayout.CENTER);

        btnGun.addActionListener(e -> {
            takvimModu = "GUN";
            tabloyuAyarla();
            takvimGuncelle();
        });

        btnHafta.addActionListener(e -> {
            takvimModu = "HAFTA";
            tabloyuAyarla();
            takvimGuncelle();
        });

        btnAy.addActionListener(e -> {
            takvimModu = "AY";
            tabloyuAyarla();
            takvimGuncelle();
        });

        btnOnceki.addActionListener(e -> {
            if ("GUN".equals(takvimModu)) {
                seciliTarih = seciliTarih.minusDays(1);
            } else if ("HAFTA".equals(takvimModu)) {
                seciliTarih = seciliTarih.minusWeeks(1);
            } else {
                seciliTarih = seciliTarih.minusYears(1);
            }

            tabloyuAyarla();
            takvimGuncelle();
        });

        btnSonraki.addActionListener(e -> {
            if ("GUN".equals(takvimModu)) {
                seciliTarih = seciliTarih.plusDays(1);
            } else if ("HAFTA".equals(takvimModu)) {
                seciliTarih = seciliTarih.plusWeeks(1);
            } else {
                seciliTarih = seciliTarih.plusYears(1);
            }

            tabloyuAyarla();
            takvimGuncelle();
        });

        JPanel panelIsEmri = new JPanel();
        panelIsEmri.setBounds(30, 370, 650, 300);
        panelIsEmri.setBackground(PANEL_BEYAZ);
        panelIsEmri.setLayout(null);
        contentPane.add(panelIsEmri);

        JPanel panelBaslik = new JPanel();
        panelBaslik.setBackground(ACIK_GRI);
        panelBaslik.setBounds(0, 0, 650, 45);
        panelBaslik.setLayout(null);
        panelIsEmri.add(panelBaslik);

        JLabel lblFormBaslik = new JLabel("İş Emri Detayları");
        lblFormBaslik.setBounds(20, 10, 250, 25);
        lblFormBaslik.setForeground(new Color(60, 60, 60));
        lblFormBaslik.setFont(new Font("Tahoma", Font.BOLD, 16));
        panelBaslik.add(lblFormBaslik);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 45, 650, 1);
        panelIsEmri.add(separator);

        isEmriModel = new DefaultTableModel(
                new String[]{"ID", "İş Emri", "Sipariş", "Ürün / Görev", "Plan Tarihi", "Durum"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        isEmriTable = new JTable(isEmriModel);
        isEmriTable.getTableHeader().setReorderingAllowed(false);
        isEmriTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        isEmriTable.setRowHeight(28);
        isEmriTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        isEmriTable.getTableHeader().setBackground(new Color(230, 235, 250));

        isEmriTable.getColumnModel().getColumn(0).setMinWidth(0);
        isEmriTable.getColumnModel().getColumn(0).setMaxWidth(0);
        isEmriTable.getColumnModel().getColumn(0).setWidth(0);

        isEmriTable.getColumnModel().getColumn(5).setCellRenderer(new DurumRenderer());

        isEmriTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                if (operatorMu) {
                    return;
                }

                int satir = isEmriTable.getSelectedRow();

                if (satir != -1) {
                    secilenPlanlamaId = Integer.parseInt(isEmriModel.getValueAt(satir, 0).toString());

                    Object tarih = isEmriModel.getValueAt(satir, 4);
                    Object durum = isEmriModel.getValueAt(satir, 5);

                    txtPlanTarihi.setText(tarih == null || tarih.toString().isEmpty() ? "gg.aa.yyyy" : tarih.toString());
                    cmbDurum.setSelectedItem(durum == null || durum.toString().isEmpty() ? "Bekliyor" : durum.toString());
                }
            }
        });

        JScrollPane isEmriScroll = new JScrollPane(isEmriTable);
        isEmriScroll.setBounds(10, 55, 630, 175);
        panelIsEmri.add(isEmriScroll);

        JLabel lblPlanTarihi = new JLabel("Plan Tarihi:");
        lblPlanTarihi.setBounds(15, 240, 90, 25);
        panelIsEmri.add(lblPlanTarihi);

        txtPlanTarihi = new JTextField("gg.aa.yyyy");
        txtPlanTarihi.setBounds(100, 240, 100, 25);
        txtPlanTarihi.setEditable(false);
        panelIsEmri.add(txtPlanTarihi);

        JButton btnTarihSec = new JButton("Tarih Seç");
        btnTarihSec.setBounds(205, 240, 95, 25);
        btnTarihSec.setBackground(BUTON_MAVI);
        btnTarihSec.setForeground(Color.WHITE);
        btnTarihSec.setFocusPainted(false);
        btnTarihSec.setBorderPainted(false);
        panelIsEmri.add(btnTarihSec);

        btnTarihSec.addActionListener(e -> tarihSec());

        JLabel lblDurumSec = new JLabel("Durum:");
        lblDurumSec.setBounds(315, 240, 60, 25);
        panelIsEmri.add(lblDurumSec);

        cmbDurum = new JComboBox<>(new String[]{
                "Bekliyor",
                "Planlandı",
                "Üretimde",
                "Tamamlandı"
        });
        cmbDurum.setBounds(370, 240, 130, 25);
        panelIsEmri.add(cmbDurum);

        JButton btnPlanGuncelle = new JButton("Güncelle");
        btnPlanGuncelle.setBounds(515, 240, 120, 25);
        
        
        btnPlanGuncelle.setBackground(BUTON_MAVI);
        btnPlanGuncelle.setForeground(Color.WHITE);
        btnPlanGuncelle.setFocusPainted(false);
        btnPlanGuncelle.setBorderPainted(false);
        panelIsEmri.add(btnPlanGuncelle);

        btnPlanGuncelle.addActionListener(e -> planlamaGuncelle());


        
        JButton btnPlanSil = new JButton("Sil");
        btnPlanSil.setBounds(515, 270, 120, 25);
        btnPlanSil.setBackground(new Color(220, 53, 69));
        btnPlanSil.setForeground(Color.WHITE);
        btnPlanSil.setFocusPainted(false);
        btnPlanSil.setBorderPainted(false);
        panelIsEmri.add(btnPlanSil);

        btnPlanSil.addActionListener(e -> planlamaSil());
        
        if (operatorMu) {
            lblPlanTarihi.setVisible(false);
            txtPlanTarihi.setVisible(false);
            btnTarihSec.setVisible(false);

            lblDurumSec.setVisible(false);
            cmbDurum.setVisible(false);

            btnPlanGuncelle.setVisible(false);
            btnPlanSil.setVisible(false);
        }
        
        JPanel panelKaynak = new JPanel();
        panelKaynak.setBounds(700, 370, 370, 300);
        panelKaynak.setBackground(PANEL_BEYAZ);
        panelKaynak.setLayout(null);
        contentPane.add(panelKaynak);

        JPanel kaynakBaslik = new JPanel(null);
        kaynakBaslik.setBackground(ACIK_GRI);
        kaynakBaslik.setBounds(0, 0, 370, 45);
        panelKaynak.add(kaynakBaslik);

        JLabel lblKaynakBaslik = new JLabel("Kaynak Kullanımı");
        lblKaynakBaslik.setBounds(15, 10, 220, 25);
        lblKaynakBaslik.setFont(new Font("Tahoma", Font.BOLD, 16));
        kaynakBaslik.add(lblKaynakBaslik);

        JSeparator sepKaynak = new JSeparator();
        sepKaynak.setBounds(0, 45, 370, 1);
        panelKaynak.add(sepKaynak);

        JLabel lblMakineLbl = new JLabel("Makine Kullanımı");
        lblMakineLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblMakineLbl.setBounds(15, 55, 200, 18);
        panelKaynak.add(lblMakineLbl);

        pbMakine = new JProgressBar(0, 100);
        pbMakine.setBounds(15, 75, 265, 18);
        pbMakine.setStringPainted(true);
        pbMakine.setForeground(new Color(40, 167, 69));
        pbMakine.setFont(new Font("Tahoma", Font.BOLD, 10));
        panelKaynak.add(pbMakine);

        JLabel lblIsciLbl = new JLabel("İşçi Kullanımı");
        lblIsciLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblIsciLbl.setBounds(15, 103, 200, 18);
        panelKaynak.add(lblIsciLbl);

        pbIsci = new JProgressBar(0, 100);
        pbIsci.setBounds(15, 123, 265, 18);
        pbIsci.setStringPainted(true);
        pbIsci.setForeground(new Color(0, 123, 255));
        pbIsci.setFont(new Font("Tahoma", Font.BOLD, 10));
        panelKaynak.add(pbIsci);

        piePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int total = pieStokta + pieSipariste + pieEksik;
                if (total == 0) {
                    total = 1;
                }

                Color[] renkler = {
                        new Color(0, 123, 255),
                        new Color(255, 152, 0),
                        new Color(220, 53, 69)
                };

                int[] degerler = {pieStokta, pieSipariste, pieEksik};
                String[] etiket = {"Tamamlandı", "Planlandı", "Üretimde"};

                int startAngle = 0;
                int cx = 15;
                int cy = 10;
                int diameter = 100;

                for (int i = 0; i < 3; i++) {
                    int arc = (int) Math.round(360.0 * degerler[i] / total);
                    g2.setColor(renkler[i]);
                    g2.fillArc(cx, cy, diameter, diameter, startAngle, arc);
                    startAngle += arc;
                }

                for (int i = 0; i < 3; i++) {
                    g2.setColor(renkler[i]);
                    g2.fillRect(140, 20 + i * 28, 14, 14);
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    g2.drawString(etiket[i], 160, 32 + i * 28);
                }
            }
        };

        piePanel.setBackground(PANEL_BEYAZ);
        piePanel.setBounds(15, 152, 340, 135);
        panelKaynak.add(piePanel);

        tabloyuAyarla();
        verileriYukle();
    }

    

    private void tabloyuAyarla() {

        if ("GUN".equals(takvimModu)) {

            takvimModel.setColumnCount(1);
            takvimModel.setColumnIdentifiers(new String[]{
                    seciliTarih.format(DateTimeFormatter.ofPattern("dd MMMM EEEE", TR_LOCALE))
            });
            takvimModel.setRowCount(10);

            lblHaftaAraligi.setText(seciliTarih.format(TR_FMT));
        }

        else if ("HAFTA".equals(takvimModu)) {

            takvimModel.setColumnCount(7);

            haftaBasi = seciliTarih.with(DayOfWeek.MONDAY);

            String[] gunBasliklari = new String[7];

            for (int i = 0; i < 7; i++) {
                LocalDate gun = haftaBasi.plusDays(i);

                gunBasliklari[i] = gun.format(
                        DateTimeFormatter.ofPattern("dd MMM EEE", TR_LOCALE)
                );
            }

            takvimModel.setColumnIdentifiers(gunBasliklari);
            takvimModel.setRowCount(8);

            lblHaftaAraligi.setText(
                    haftaBasi.format(TR_FMT) + " - " + haftaBasi.plusDays(6).format(TR_FMT)
            );
        }

        else if ("AY".equals(takvimModu)) {

            takvimModel.setColumnCount(12);

            String[] ayBasliklari = {
                    "Ocak", "Şubat", "Mart", "Nisan",
                    "Mayıs", "Haziran", "Temmuz", "Ağustos",
                    "Eylül", "Ekim", "Kasım", "Aralık"
            };

            takvimModel.setColumnIdentifiers(ayBasliklari);
            takvimModel.setRowCount(8);

            lblHaftaAraligi.setText(String.valueOf(seciliTarih.getYear()));
        }

        for (int i = 0; i < takvimTable.getColumnCount(); i++) {
            takvimTable.getColumnModel().getColumn(i).setCellRenderer(new TakvimHucresiRenderer());
        }

        takvimTable.getTableHeader().resizeAndRepaint();
        takvimTable.repaint();
    }

    private void takvimGuncelle() {
        for (int r = 0; r < takvimModel.getRowCount(); r++) {
            for (int c = 0; c < takvimModel.getColumnCount(); c++) {
                takvimModel.setValueAt("", r, c);
            }
        }

        List<Planlama> planlamalar = planlamaService.tumPlanlamalariGetir();

        for (Planlama p : planlamalar) {
            String tarihStr = p.getPlanTarihi();

            if (tarihStr == null || tarihStr.trim().isEmpty()) {
                continue;
            }

            LocalDate tarih;

            try {
                tarih = LocalDate.parse(tarihStr, TR_FMT);
            } catch (Exception e) {
                continue;
            }

            String gorev = p.getUrunAdi() + " - " + p.getGorev();

            if ("GUN".equals(takvimModu)) {
                if (tarih.equals(seciliTarih)) {
                    uygunHucreyeYaz(0, gorev);
                }
            }

            else if ("HAFTA".equals(takvimModu)) {
                if (!tarih.isBefore(haftaBasi) && !tarih.isAfter(haftaBasi.plusDays(6))) {
                    int column = tarih.getDayOfWeek().getValue() - 1;
                    uygunHucreyeYaz(column, gorev);
                }
            }

            else {
                if (tarih.getYear() == seciliTarih.getYear()) {

                    int ayKolonu = tarih.getMonthValue() - 1;

                    String yazilacak = tarih.getDayOfMonth() + " " + p.getUrunAdi() + " - " + p.getGorev();

                    for (int r = 0; r < takvimModel.getRowCount(); r++) {
                        Object eski = takvimModel.getValueAt(r, ayKolonu);

                        if (eski == null || eski.toString().isEmpty()) {
                            takvimModel.setValueAt(yazilacak, r, ayKolonu);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void uygunHucreyeYaz(int column, String gorev) {
        for (int r = 0; r < takvimModel.getRowCount(); r++) {
            Object value = takvimModel.getValueAt(r, column);

            if (value == null || value.toString().isEmpty()) {
                takvimModel.setValueAt(gorev, r, column);
                return;
            }
        }
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
                "Plan Tarihi Seç",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (sonuc == JOptionPane.OK_OPTION) {
            int gun = (int) gunBox.getSelectedItem();
            int ay = (int) ayBox.getSelectedItem();
            int yil = (int) yilBox.getSelectedItem();

            txtPlanTarihi.setText(String.format("%02d.%02d.%04d", gun, ay, yil));
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

    private void planlamaGuncelle() {
        if (secilenPlanlamaId == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen tablodan bir iş emri seçin.");
            return;
        }

        String tarih = txtPlanTarihi.getText().trim();
        String durum = cmbDurum.getSelectedItem().toString();

        if (tarih.isEmpty() || tarih.equals("gg.aa.yyyy")) {
            JOptionPane.showMessageDialog(this, "Plan tarihi giriniz.");
            return;
        }

        try {
            LocalDate.parse(tarih, TR_FMT);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tarih formatı gg.aa.yyyy şeklinde olmalıdır.");
            return;
        }

        boolean sonuc = planlamaService.planlamaTarihDurumGuncelle(
                secilenPlanlamaId,
                tarih,
                durum
        );

        if (sonuc) {
            JOptionPane.showMessageDialog(this, "Planlama güncellendi.");
            verileriYukle();
            takvimGuncelle();

            secilenPlanlamaId = -1;
            txtPlanTarihi.setText("gg.aa.yyyy");
            cmbDurum.setSelectedIndex(0);
            isEmriTable.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Planlama güncellenemedi.");
        }
    }
    
    private void planlamaSil() {
        if (secilenPlanlamaId == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için tablodan bir iş emri seçin.");
            return;
        }

        int cevap = JOptionPane.showOptionDialog(
                this,
                "Seçili iş emri silinsin mi?",
                "İş Emri Sil",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Evet", "Hayır"},
                "Hayır"
        );

        if (cevap == JOptionPane.YES_OPTION) {
            boolean sonuc = planlamaService.planlamaSil(secilenPlanlamaId);

            if (sonuc) {
                JOptionPane.showMessageDialog(this, "İş emri silindi.");

                verileriYukle();
                takvimGuncelle();

                secilenPlanlamaId = -1;
                txtPlanTarihi.setText("gg.aa.yyyy");
                cmbDurum.setSelectedIndex(0);
                isEmriTable.clearSelection();

            } else {
                JOptionPane.showMessageDialog(this, "İş emri silinemedi.");
            }
        }
    }

    private void verileriYukle() {
        takvimGuncelle();
        isEmriModel.setRowCount(0);

        List<Planlama> planlamalar = planlamaService.tumPlanlamalariGetir();

        int toplam = planlamalar.size();
        int tamamlandi = 0;
        int uretimde = 0;
        int planlandi = 0;
        int counter = 101;

        for (Planlama p : planlamalar) {
            String durum = p.getDurum();

            if ("Tamamlandı".equals(durum)) {
                tamamlandi++;
            } else if ("Üretimde".equals(durum)) {
                uretimde++;
            } else if ("Planlandı".equals(durum)) {
                planlandi++;
            }

            isEmriModel.addRow(new Object[]{
                    p.getId(),
                    "İE-" + counter++,
                    p.getSiparisKodu(),
                    p.getUrunAdi() + " - " + p.getGorev(),
                    p.getPlanTarihi(),
                    durum
            });
        }

        int toplamMakine = planlamaService.makineAdediGetir();
        int kullanilanMakine = Math.min(toplam, toplamMakine);

        int makineYuzde = toplamMakine > 0 ? (int) Math.round(kullanilanMakine * 100.0 / toplamMakine) : 0;
        makineYuzde = Math.min(makineYuzde, 100);

        int isciYuzde = toplam > 0 ? (int) Math.round(uretimde * 100.0 / toplam) : 0;

        pbMakine.setValue(makineYuzde);
        pbMakine.setString(makineYuzde + "%");

        pbIsci.setValue(isciYuzde);
        pbIsci.setString(isciYuzde + "%");

        if (toplam > 0) {
            pieStokta = (int) Math.round(tamamlandi * 100.0 / toplam);
            pieSipariste = (int) Math.round(planlandi * 100.0 / toplam);
            pieEksik = 100 - pieStokta - pieSipariste;
        } else {
            pieStokta = 0;
            pieSipariste = 0;
            pieEksik = 100;
        }

        piePanel.repaint();
    }

    public class TakvimHucresiRenderer extends DefaultTableCellRenderer {
    	
    	private static final long serialVersionUID = 1L;
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(JLabel.CENTER);
            setFont(new Font("Tahoma", Font.BOLD, 11));

            if (value != null && !value.toString().isEmpty()) {
                String gorev = value.toString();

                if (gorev.contains("Paketleme")) {
                    c.setBackground(new Color(255, 152, 0));
                } else if (gorev.contains("Dolum")) {
                    c.setBackground(new Color(156, 39, 176));
                } else if (gorev.contains("Karışım")) {
                    c.setBackground(new Color(255, 87, 34));
                } else if (gorev.contains("Kapak")) {
                    c.setBackground(new Color(103, 58, 183));
                } else {
                    c.setBackground(new Color(52, 152, 219));
                }

                c.setForeground(Color.WHITE);
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }

            return c;
        }
    }

    public class DurumRenderer extends DefaultTableCellRenderer {
    	
    	private static final long serialVersionUID = 1L;
        @Override
        public Component getTableCellRendererComponent(JTable t, Object value,
                                                       boolean sel, boolean focus,
                                                       int row, int col) {

            JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, value, sel, focus, row, col);

            lbl.setHorizontalAlignment(JLabel.CENTER);
            lbl.setFont(new Font("Tahoma", Font.BOLD, 11));
            lbl.setOpaque(true);

            String v = value != null ? value.toString() : "";

            if ("Tamamlandı".equals(v)) {
                lbl.setBackground(new Color(0, 123, 255));
                lbl.setForeground(Color.WHITE);
            } else if ("Planlandı".equals(v)) {
                lbl.setBackground(new Color(255, 152, 0));
                lbl.setForeground(Color.WHITE);
            } else if ("Üretimde".equals(v)) {
                lbl.setBackground(new Color(220, 53, 69));
                lbl.setForeground(Color.WHITE);
            } else {
                lbl.setBackground(Color.WHITE);
                lbl.setForeground(Color.BLACK);
            }
            return lbl;
        }
    };


  }
