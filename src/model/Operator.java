package model;

public class Operator extends Kullanici {

    public Operator(int id, String kullaniciAdi, String rol) {
        super(id, kullaniciAdi, rol);
    }

    @Override
    public String yetkiBilgisi() {
        return "Operatör yetkisine sahip kullanıcı";
    }
}