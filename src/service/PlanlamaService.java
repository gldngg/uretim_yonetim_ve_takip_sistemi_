package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.Veritabani;
import interfaces.IPlanlamaIslemleri;
import model.Planlama;

public class PlanlamaService implements IPlanlamaIslemleri {

    @Override
    public boolean planlamaEkle(Planlama planlama) {
        String sql = "INSERT INTO is_emirleri "
                + "(siparis_id, gorev, makine, plan_tarihi, durum) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);

            pstmt.setInt(1, planlama.getSiparisId());
            pstmt.setString(2, planlama.getGorev());
            pstmt.setString(3, planlama.getMakine());
            pstmt.setString(4, planlama.getPlanTarihi());
            pstmt.setString(5, planlama.getDurum());

            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (Exception e) {
            System.err.println("Planlama kayıt hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean planlamaGuncelle(int id, String makine, String planTarihi, String durum) {
        String sql = "UPDATE is_emirleri SET makine = ?, plan_tarihi = ?, durum = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);

            pstmt.setString(1, makine);
            pstmt.setString(2, planTarihi);
            pstmt.setString(3, durum);
            pstmt.setInt(4, id);

            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (Exception e) {
            System.err.println("Planlama güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean siparisPlanlamalariniSil(int siparisId) {
        String sql = "DELETE FROM is_emirleri WHERE siparis_id = ?";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);
            pstmt.setInt(1, siparisId);
            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (Exception e) {
            System.err.println("Planlama silme hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Planlama> siparisPlanlamalariniGetir(int siparisId) {
        List<Planlama> liste = new ArrayList<>();

        String sql = "SELECT ie.*, s.siparis_kodu, s.urun_adi "
                + "FROM is_emirleri ie "
                + "LEFT JOIN siparisler s ON ie.siparis_id = s.id "
                + "WHERE ie.siparis_id = ?";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);
            pstmt.setInt(1, siparisId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                liste.add(new Planlama(
                        rs.getInt("id"),
                        rs.getInt("siparis_id"),
                        rs.getString("siparis_kodu"),
                        rs.getString("urun_adi"),
                        rs.getString("gorev"),
                        rs.getString("makine"),
                        rs.getString("plan_tarihi"),
                        rs.getString("durum")
                ));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.err.println("Sipariş planlamaları getirme hatası: " + e.getMessage());
        }

        return liste;
    }

    @Override
    public List<Planlama> tumPlanlamalariGetir() {
        List<Planlama> liste = new ArrayList<>();

        String sql = "SELECT ie.*, s.siparis_kodu, s.urun_adi "
                + "FROM is_emirleri ie "
                + "LEFT JOIN siparisler s ON ie.siparis_id = s.id "
                + "ORDER BY ie.id DESC";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                liste.add(new Planlama(
                        rs.getInt("id"),
                        rs.getInt("siparis_id"),
                        rs.getString("siparis_kodu"),
                        rs.getString("urun_adi"),
                        rs.getString("gorev"),
                        rs.getString("makine"),
                        rs.getString("plan_tarihi"),
                        rs.getString("durum")
                ));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.err.println("Tüm planlamaları getirme hatası: " + e.getMessage());
        }

        return liste;
    }

    @Override
    public int makineAdediGetir() {
        String sql = "SELECT COUNT(*) FROM machines";

        try {
            Statement stmt = Veritabani.getInstance().getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            int sayi = 0;

            if (rs.next()) {
                sayi = rs.getInt(1);
            }

            rs.close();
            stmt.close();

            return sayi;

        } catch (Exception e) {
            System.err.println("Makine sayısı alma hatası: " + e.getMessage());
            return 0;
        }
    }
    public boolean planlamaTarihDurumGuncelle(int id, String planTarihi, String durum) {
        String sql = "UPDATE is_emirleri SET plan_tarihi = ?, durum = ? WHERE id = ?";

        try {
            java.sql.PreparedStatement pstmt = database.Veritabani.getInstance()
                    .getConnection()
                    .prepareStatement(sql);

            pstmt.setString(1, planTarihi);
            pstmt.setString(2, durum);
            pstmt.setInt(3, id);

            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (Exception e) {
            System.err.println("Planlama tarih/durum güncelleme hatası: " + e.getMessage());
            return false;
        }
    }
}
