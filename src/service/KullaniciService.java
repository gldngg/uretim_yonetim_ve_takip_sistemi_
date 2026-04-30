package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Veritabani;
import interfaces.IKullaniciIslemleri;
import model.Admin;
import model.Kullanici;
import model.Operator;

public class KullaniciService implements IKullaniciIslemleri {

    @Override
    public boolean kayitOl(Kullanici kullanici, String sifre) {
        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";

        try {
            String sifrelenmisKullaniciAdi = Veritabani.sifrele(kullanici.getKullaniciAdi());
            String hashlenmisSifre = Veritabani.getInstance().hashPassword(sifre);

            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);

            pstmt.setString(1, sifrelenmisKullaniciAdi);
            pstmt.setString(2, hashlenmisSifre);
            pstmt.setString(3, kullanici.getRol());

            pstmt.executeUpdate();
            pstmt.close();

            return true;

        } catch (SQLException e) {
            System.err.println("Kullanıcı kayıt SQL hatası: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Kullanıcı kayıt hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Kullanici girisYap(String kullaniciAdi, String sifre, String rol) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";

        try {
            String sifrelenmisKullaniciAdi = Veritabani.sifrele(kullaniciAdi);
            String hashlenmisSifre = Veritabani.getInstance().hashPassword(sifre);

            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);

            pstmt.setString(1, sifrelenmisKullaniciAdi);
            pstmt.setString(2, hashlenmisSifre);
            pstmt.setString(3, rol);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");

                rs.close();
                pstmt.close();

                if (rol.equals("Admin")) {
                    return new Admin(id, kullaniciAdi, rol);
                } else {
                    return new Operator(id, kullaniciAdi, rol);
                }
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println("Giriş SQL hatası: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Giriş hatası: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean kullaniciAdiVarMi(String kullaniciAdi) {
        String sql = "SELECT id FROM users WHERE username = ?";

        try {
            String sifrelenmisKullaniciAdi = Veritabani.sifrele(kullaniciAdi);

            PreparedStatement pstmt = Veritabani.getInstance().getConnection().prepareStatement(sql);
            pstmt.setString(1, sifrelenmisKullaniciAdi);

            ResultSet rs = pstmt.executeQuery();

            boolean varMi = rs.next();

            rs.close();
            pstmt.close();

            return varMi;

        } catch (Exception e) {
            System.err.println("Kullanıcı adı kontrol hatası: " + e.getMessage());
            return false;
        }
    }
}
