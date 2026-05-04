package interfaces;

import java.util.List;
import model.Siparis;

public interface ISiparisIslemleri {

    int siparisEkle(Siparis siparis);

    boolean siparisGuncelle(int id, Siparis siparis);

    Siparis siparisGetir(String siparisKodu);
    
    boolean siparisSil(int id);

    List<Siparis> tumSiparisleriGetir();
}