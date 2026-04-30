package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.Veritabani;
import interfaces.IDurusKayipIslemleri;
import model.DurusKayip;

public class DurusKayipService implements IDurusKayipIslemleri {

    @Override
    public boolean durusKayipEkle(DurusKayip durusKayip) {
        String sql = "INSERT INTO downtimes "
                + "(makine_tipi, makine_kodu, baslangic, bitis, sure, durus_turu, durus_nedeni, aciklama) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);

            pstmt.setString(1, durusKayip.getMakineTipi());
            pstmt.setString(2, durusKayip.getMakineKodu());
            pstmt.setString(3, durusKayip.getBaslangic());
            pstmt.setString(4, durusKayip.getBitis());
            pstmt.setString(5, durusKayip.getSure());
            pstmt.setString(6, durusKayip.getDurusTuru());
            pstmt.setString(7, durusKayip.getDurusNedeni());
            pstmt.setString(8, durusKayip.getAciklama());

            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (Exception e) {
            System.err.println("Duruş/kayıp kayıt hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean durusKayipGuncelle(int id, DurusKayip durusKayip) {
        String sql = "UPDATE downtimes SET makine_tipi = ?, makine_kodu = ?, baslangic = ?, bitis = ?, sure = ?, "
                + "durus_turu = ?, durus_nedeni = ?, aciklama = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);

            pstmt.setString(1, durusKayip.getMakineTipi());
            pstmt.setString(2, durusKayip.getMakineKodu());
            pstmt.setString(3, durusKayip.getBaslangic());
            pstmt.setString(4, durusKayip.getBitis());
            pstmt.setString(5, durusKayip.getSure());
            pstmt.setString(6, durusKayip.getDurusTuru());
            pstmt.setString(7, durusKayip.getDurusNedeni());
            pstmt.setString(8, durusKayip.getAciklama());
            pstmt.setInt(9, id);

            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (Exception e) {
            System.err.println("Duruş/kayıp güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean durusKayipSil(int id) {
        String sql = "DELETE FROM downtimes WHERE id = ?";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (Exception e) {
            System.err.println("Duruş/kayıp silme hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<DurusKayip> tumDurusKayiplariGetir() {
        List<DurusKayip> duruslar = new ArrayList<>();

        String sql = "SELECT * FROM downtimes";

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

                duruslar.add(durus);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.err.println("Duruş/kayıp listeleme hatası: " + e.getMessage());
        }

        return duruslar;
    }
}
