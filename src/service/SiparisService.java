package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.Veritabani;
import interfaces.ISiparisIslemleri;
import model.Siparis;

public class SiparisService implements ISiparisIslemleri {

    @Override
    public int siparisEkle(Siparis siparis) {
        String sql = "INSERT INTO siparisler "
                + "(siparis_adi, siparis_kodu, musteri, urun_adi, miktar, termin_tarihi) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection()
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, siparis.getSiparisAdi());
            pstmt.setString(2, siparis.getSiparisKodu());
            pstmt.setString(3, siparis.getMusteri());
            pstmt.setString(4, siparis.getUrunAdi());
            pstmt.setInt(5, siparis.getMiktar());
            pstmt.setString(6, siparis.getTerminTarihi());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                rs.close();
                pstmt.close();
                return id;
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.err.println("Sipariş kayıt hatası: " + e.getMessage());
        }

        return -1;
    }

    @Override
    public boolean siparisGuncelle(int id, Siparis siparis) {
        String sql = "UPDATE siparisler SET siparis_adi = ?, siparis_kodu = ?, musteri = ?, "
                + "urun_adi = ?, miktar = ?, termin_tarihi = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);

            pstmt.setString(1, siparis.getSiparisAdi());
            pstmt.setString(2, siparis.getSiparisKodu());
            pstmt.setString(3, siparis.getMusteri());
            pstmt.setString(4, siparis.getUrunAdi());
            pstmt.setInt(5, siparis.getMiktar());
            pstmt.setString(6, siparis.getTerminTarihi());
            pstmt.setInt(7, id);

            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (Exception e) {
            System.err.println("Sipariş güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Siparis siparisGetir(String siparisKodu) {
        String sql = "SELECT * FROM siparisler WHERE siparis_kodu = ?";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);
            pstmt.setString(1, siparisKodu);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Siparis siparis = new Siparis(
                        rs.getInt("id"),
                        rs.getString("siparis_adi"),
                        rs.getString("siparis_kodu"),
                        rs.getString("musteri"),
                        rs.getString("urun_adi"),
                        rs.getInt("miktar"),
                        rs.getString("termin_tarihi")
                );

                rs.close();
                pstmt.close();

                return siparis;
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.err.println("Sipariş getirme hatası: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Siparis> tumSiparisleriGetir() {
        List<Siparis> siparisler = new ArrayList<>();

        String sql = "SELECT * FROM siparisler ORDER BY id DESC";

        try {
            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Siparis siparis = new Siparis(
                        rs.getInt("id"),
                        rs.getString("siparis_adi"),
                        rs.getString("siparis_kodu"),
                        rs.getString("musteri"),
                        rs.getString("urun_adi"),
                        rs.getInt("miktar"),
                        rs.getString("termin_tarihi")
                );

                siparisler.add(siparis);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.err.println("Sipariş listeleme hatası: " + e.getMessage());
        }

        return siparisler;
    }
}