package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.Session;
import model.Planlama;
import service.PlanlamaService;

public class PlanlamaEkrani extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;

    private JTextField txtSiparisKodu;
    private JTextField txtGorev;
    private JTextField txtMakine;
    private JTextField txtPlanTarihi;
    private JComboBox<String> cmbDurum;

    private int secilenId = -1;

    private PlanlamaService planlamaService = new PlanlamaService();

    private final Color ARKA_PLAN = new Color(142, 155, 213);
    private final Color HEADER = new Color(63, 81, 181);
    private final Color PANEL_BEYAZ = Color.WHITE;
    private final Color ACIK_GRI = new Color(245, 245, 245);
    private final Color BUTON_MAVI = new Color(52, 152, 219);
    private final Color TEMIZLE_GRI = new Color(220, 220, 220);

    public static void main(String[] args) {
        new PlanlamaEkrani().setVisible(true);
    }

    public PlanlamaEkrani() {

        setTitle("ÜRETİM YÖNETİM SİSTEMİ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 760);
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(ARKA_PLAN);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JPanel panelUstMenu = new JPanel();
        panelUstMenu.setBounds(30, 20, 1040, 70);
        panelUstMenu.setBackground(HEADER);
        panelUstMenu.setLayout(null);
        contentPane.add(panelUstMenu);

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/ui/logo.png"));
        Image logoImg = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        JLabel lblLogo = new JLabel(new ImageIcon(logoImg));
        lblLogo.setBounds(20, 10, 50, 50);
        panelUstMenu.add(lblLogo);

        JLabel lblBaslik = new JLabel("PLANLAMA");
        lblBaslik.setBounds(85, 18, 220, 30);
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 22));
        panelUstMenu.add(lblBaslik);

        JButton btnAnaSayfa = menuButonu("Ana Sayfa");
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

        JButton btnDurusKayip = menuButonu("Duruş/Kayıp");
        btnDurusKayip.setBounds(360, 20, 120, 25);
        panelUstMenu.add(btnDurusKayip);

        btnDurusKayip.addActionListener(e -> {
            new DurusKayipEkrani().setVisible(true);
            dispose();
        });

        JButton btnMakineGirisi = menuButonu("Makine Girişi");
        btnMakineGirisi.setBounds(480, 20, 120, 25);
        panelUstMenu.add(btnMakineGirisi);

        btnMakineGirisi.addActionListener(e -> {
            new MakineGirisEkrani().setVisible(true);
            dispose();
        });

        JButton btnSiparis = menuButonu("Sipariş");
        btnSiparis.setBounds(600, 20, 100, 25);
        panelUstMenu.add(btnSiparis);

        btnSiparis.addActionListener(e -> {
            new SiparisEkrani().setVisible(true);
            dispose();
        });

        JButton btnRapor = menuButonu("Rapor");
        btnRapor.setBounds(700, 20, 80, 25);
        panelUstMenu.add(btnRapor);

        btnRapor.addActionListener(e -> {
            new RaporEkrani().setVisible(true);
            dispose();
        });

        JLabel lblUser = new JLabel(Session.aktifKullanici);
        lblUser.setBounds(840, 20, 100, 25);
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 12));
        panelUstMenu.add(lblUser);

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

        JPanel panelForm = new JPanel();
        panelForm.setBounds(30, 110, 1040, 210);
        panelForm.setBackground(PANEL_BEYAZ);
        panelForm.setLayout(null);
        contentPane.add(panelForm);

        JPanel panelFormBaslik = new JPanel();
        panelFormBaslik.setBounds(0, 0, 1040, 45);
        panelFormBaslik.setBackground(ACIK_GRI);
        panelFormBaslik.setLayout(null);
        panelForm.add(panelFormBaslik);

        JLabel lblFormBaslik = new JLabel("İş Emri Planlama");
        lblFormBaslik.setBounds(20, 8, 250, 30);
        lblFormBaslik.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblFormBaslik.setForeground(new Color(60, 60, 60));
        panelFormBaslik.add(lblFormBaslik);

        JLabel lblSiparisKodu = new JLabel("Sipariş Kodu:");
        lblSiparisKodu.setBounds(25, 65, 150, 25);
        lblSiparisKodu.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelForm.add(lblSiparisKodu);

        txtSiparisKodu = new JTextField();
        txtSiparisKodu.setBounds(25, 95, 220, 30);
        txtSiparisKodu.setEditable(false);
        panelForm.add(txtSiparisKodu);

        JLabel lblGorev = new JLabel("Görev:");
        lblGorev.setBounds(280, 65, 150, 25);
        lblGorev.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelForm.add(lblGorev);

        txtGorev = new JTextField();
        txtGorev.setBounds(280, 95, 220, 30);
        txtGorev.setEditable(false);
        panelForm.add(txtGorev);

        JLabel lblMakine = new JLabel("Makine:");
        lblMakine.setBounds(535, 65, 150, 25);
        lblMakine.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelForm.add(lblMakine);

        txtMakine = new JTextField();
        txtMakine.setBounds(535, 95, 220, 30);
        panelForm.add(txtMakine);

        JLabel lblPlanTarihi = new JLabel("Plan Tarihi:");
        lblPlanTarihi.setBounds(790, 65, 150, 25);
        lblPlanTarihi.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelForm.add(lblPlanTarihi);

        txtPlanTarihi = new JTextField();
        txtPlanTarihi.setText("gg.aa.yyyy");
        txtPlanTarihi.setBounds(790, 95, 220, 30);
        panelForm.add(txtPlanTarihi);

        JLabel lblDurum = new JLabel("Durum:");
        lblDurum.setBounds(25, 140, 150, 25);
        lblDurum.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelForm.add(lblDurum);

        cmbDurum = new JComboBox<>();
        cmbDurum.setModel(new DefaultComboBoxModel<>(new String[] {
                "Bekliyor",
                "Planlandı",
                "Üretimde",
                "Tamamlandı"
        }));
        cmbDurum.setBounds(25, 165, 220, 30);
        panelForm.add(cmbDurum);

        JButton btnGuncelle = new JButton("Planlamayı Güncelle");
        btnGuncelle.setBounds(790, 155, 220, 35);
        btnGuncelle.setBackground(BUTON_MAVI);
        btnGuncelle.setForeground(Color.WHITE);
        btnGuncelle.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnGuncelle.setFocusPainted(false);
        btnGuncelle.setBorderPainted(false);
        panelForm.add(btnGuncelle);

        btnGuncelle.addActionListener(e -> planlamaGuncelle());

        JButton btnTemizle = new JButton("Temizle");
        btnTemizle.setBounds(535, 155, 220, 35);
        btnTemizle.setBackground(TEMIZLE_GRI);
        btnTemizle.setForeground(Color.BLACK);
        btnTemizle.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnTemizle.setFocusPainted(false);
        btnTemizle.setBorderPainted(false);
        panelForm.add(btnTemizle);

        btnTemizle.addActionListener(e -> formTemizle());

        JPanel panelTablo = new JPanel();
        panelTablo.setBounds(30, 340, 1040, 350);
        panelTablo.setBackground(PANEL_BEYAZ);
        panelTablo.setLayout(null);
        contentPane.add(panelTablo);

        JPanel panelTabloBaslik = new JPanel();
        panelTabloBaslik.setBounds(0, 0, 1040, 45);
        panelTabloBaslik.setBackground(ACIK_GRI);
        panelTabloBaslik.setLayout(null);
        panelTablo.add(panelTabloBaslik);

        JLabel lblTabloBaslik = new JLabel("Siparişlerden Oluşturulan İş Emirleri");
        lblTabloBaslik.setBounds(20, 8, 400, 30);
        lblTabloBaslik.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTabloBaslik.setForeground(new Color(60, 60, 60));
        panelTabloBaslik.add(lblTabloBaslik);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(15, 60, 1010, 270);
        panelTablo.add(scrollPane);

        table = new JTable();
        table.setRowHeight(27);
        table.getTableHeader().setBackground(new Color(230, 235, 250));
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {
                "ID",
                "Sipariş Kodu",
                "Görev",
                "Makine",
                "Plan Tarihi",
                "Durum"
        });

        table.setModel(model);
        scrollPane.setViewportView(table);

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int satir = table.getSelectedRow();

                if (satir != -1) {
                    secilenId = Integer.parseInt(model.getValueAt(satir, 0).toString());
                    txtSiparisKodu.setText(model.getValueAt(satir, 1).toString());
                    txtGorev.setText(model.getValueAt(satir, 2).toString());
                    txtMakine.setText(model.getValueAt(satir, 3).toString());
                    txtPlanTarihi.setText(model.getValueAt(satir, 4) == null ? "" : model.getValueAt(satir, 4).toString());
                    cmbDurum.setSelectedItem(model.getValueAt(satir, 5) == null ? "Bekliyor" : model.getValueAt(satir, 5).toString());
                }
            }
        });

        planlamalariYukle();
    }

    private JButton menuButonu(String text) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 13));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        return btn;
    }

    private void planlamalariYukle() {
        model.setRowCount(0);

        List<Planlama> planlamalar = planlamaService.tumPlanlamalariGetir();

        for (Planlama p : planlamalar) {
            model.addRow(new Object[] {
                    p.getId(),
                    p.getSiparisKodu(),
                    p.getGorev(),
                    p.getMakine(),
                    p.getPlanTarihi(),
                    p.getDurum()
            });
        }
    }

    private void planlamaGuncelle() {
        if (secilenId == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen tablodan bir iş emri seçin.");
            return;
        }

        String makine = txtMakine.getText().trim();
        String planTarihi = txtPlanTarihi.getText().trim();
        String durum = cmbDurum.getSelectedItem().toString();

        if (makine.isEmpty() || planTarihi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Makine ve plan tarihi boş bırakılamaz.");
            return;
        }

        boolean sonuc = planlamaService.planlamaGuncelle(secilenId, makine, planTarihi, durum);

        if (sonuc) {
            JOptionPane.showMessageDialog(this, "Planlama güncellendi.");
            planlamalariYukle();
            formTemizle();
        } else {
            JOptionPane.showMessageDialog(this, "Planlama güncellenemedi.");
        }
    }

    private void formTemizle() {
        secilenId = -1;
        txtSiparisKodu.setText("");
        txtGorev.setText("");
        txtMakine.setText("");
        txtPlanTarihi.setText("gg.aa.yyyy");
        cmbDurum.setSelectedIndex(0);
        table.clearSelection();
    }
}