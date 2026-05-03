package interfaces;

import java.util.List;
import model.Makine;

public interface IMakineIslemleri {

    boolean makineEkle(Makine makine);

    boolean makineGuncelle(String eskiMakineKodu, Makine makine);

    boolean makineSil(String makineKodu);

    List<Makine> tumMakineleriGetir();
}