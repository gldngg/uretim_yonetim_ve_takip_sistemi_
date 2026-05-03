package model;

public class Admin extends Kullanici {

    public Admin(int id, String kullaniciAdi, String rol) {
        super(id, kullaniciAdi, rol);
    }

    @Override
    public String yetkiBilgisi() {
        return "Admin yetkisine sahip kullanıcı";
    }
}