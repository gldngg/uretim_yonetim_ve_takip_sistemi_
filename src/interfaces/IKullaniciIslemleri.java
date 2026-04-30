package interfaces;

import model.Kullanici;

public interface IKullaniciIslemleri {

    boolean kayitOl(Kullanici kullanici, String sifre);

    Kullanici girisYap(String kullaniciAdi, String sifre, String rol);

    boolean kullaniciAdiVarMi(String kullaniciAdi);
}
