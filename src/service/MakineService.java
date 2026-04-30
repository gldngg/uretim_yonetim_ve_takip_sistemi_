package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.Veritabani;
import interfaces.IMakineIslemleri;
import model.Makine;

public class MakineService implements IMakineIslemleri {

    @Override
    public boolean makineEkle(Makine makine) {
    	String sql = "INSERT INTO machines(makine_tipi, makine_kodu, bolum, kapasite, bakim_periyodu, lokasyon) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);

            pstmt.setString(1, makine.getMakineTipi());
            pstmt.setString(2, makine.getMakineKodu());
            pstmt.setString(3, makine.getBolum());
            pstmt.setString(4, makine.getKapasite());
            pstmt.setString(5, makine.getBakimPeriyodu());
            pstmt.setString(6, makine.getLokasyon());

            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (Exception e) {
            System.err.println("Makine kayıt hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean makineGuncelle(String eskiMakineKodu, Makine makine) {
        String sql = "UPDATE machines SET makine_tipi = ?, makine_kodu = ?, bolum = ?, kapasite = ?, "
                + " bakim_periyodu = ?, lokasyon = ? WHERE makine_kodu = ?";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);

            pstmt.setString(1, makine.getMakineTipi());
            pstmt.setString(2, makine.getMakineKodu());
            pstmt.setString(3, makine.getBolum());
            pstmt.setString(4, makine.getKapasite());
            pstmt.setString(5, makine.getBakimPeriyodu());
            pstmt.setString(6, makine.getLokasyon());
            pstmt.setString(7, eskiMakineKodu);

            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (Exception e) {
            System.err.println("Makine güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean makineSil(String makineKodu) {
        String sql = "DELETE FROM machines WHERE makine_kodu = ?";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);

            pstmt.setString(1, makineKodu);
            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (Exception e) {
            System.err.println("Makine silme hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Makine> tumMakineleriGetir() {
        List<Makine> makineler = new ArrayList<>();

        String sql = "SELECT * FROM machines";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Makine makine = new Makine(
                        rs.getString("makine_tipi"),
                        rs.getString("makine_kodu"),
                        rs.getString("bolum"),
                        rs.getString("kapasite"),
                        rs.getString("bakim_periyodu"),
                        rs.getString("lokasyon")
                );

                makineler.add(makine);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.err.println("Makine listeleme hatası: " + e.getMessage());
        }

        return makineler;
    }
}
