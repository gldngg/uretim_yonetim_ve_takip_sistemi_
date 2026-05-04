package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.Veritabani;
import interfaces.IRaporIslemleri;
import model.DurusKayip;

public class RaporService implements IRaporIslemleri {

    @Override
    public void raporOlustur() {
        System.out.println("Rapor oluşturuldu.");
    }

    public int toplamMakineSayisi() {
        return sayiGetir("SELECT COUNT(*) FROM machines");
    }

    public int toplamSiparisSayisi() {
        return sayiGetir("SELECT COUNT(*) FROM siparisler");
    }

    public int toplamDurusKayipSayisi() {
        return sayiGetir("SELECT COUNT(*) FROM downtimes");
    }

    public int toplamIsEmriSayisi() {
        return sayiGetir("SELECT COUNT(*) FROM is_emirleri");
    }

    public List<DurusKayip> durusKayipRaporuGetir() {
        List<DurusKayip> liste = new ArrayList<>();

        String sql = "SELECT * FROM downtimes ORDER BY id DESC";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DurusKayip durus = new DurusKayip(
                        rs.getInt("id"),
                        rs.getString("makine_tipi"),
                        rs.getString("makine_kodu"),
                        rs.getString("baslangic"),
                        rs.getString("bitis"),
                        rs.getString("sure"),
                        rs.getString("durus_turu"),
                        rs.getString("durus_nedeni"),
                        rs.getString("aciklama")
                );

                liste.add(durus);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.err.println("Rapor listeleme hatası: " + e.getMessage());
        }

        return liste;
    }
    
    public int planliDurusSayisi() {
        return sayiGetir("SELECT COUNT(*) FROM downtimes WHERE durus_turu = 'Planlı Duruş'");
    }

    public int plansizDurusSayisi() {
        return sayiGetir("SELECT COUNT(*) FROM downtimes WHERE durus_turu = 'Plansız Duruş'");
    }

    public String enCokDurusNedeni() {
        String sql = "SELECT durus_nedeni, COUNT(*) AS adet "
                + "FROM downtimes "
                + "GROUP BY durus_nedeni "
                + "ORDER BY adet DESC "
                + "LIMIT 1";

        try {
            PreparedStatement pstmt = Veritabani.getInstance()
                    .getConnection()
                    .prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String neden = rs.getString("durus_nedeni");
                int adet = rs.getInt("adet");

                rs.close();
                pstmt.close();

                return neden + " (" + adet + ")";
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.err.println("En çok duruş nedeni alma hatası: " + e.getMessage());
        }

        return "Veri yok";
    }

    private int sayiGetir(String sql) {
        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            int sayi = 0;

            if (rs.next()) {
                sayi = rs.getInt(1);
            }

            rs.close();
            pstmt.close();

            return sayi;

        } catch (Exception e) {
            System.err.println("Rapor sayı alma hatası: " + e.getMessage());
            return 0;
        }
    }
}