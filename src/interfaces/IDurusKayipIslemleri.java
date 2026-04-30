package interfaces;

import java.util.List;
import model.DurusKayip;

public interface IDurusKayipIslemleri {

    boolean durusKayipEkle(DurusKayip durusKayip);

    boolean durusKayipGuncelle(int id, DurusKayip durusKayip);

    boolean durusKayipSil(int id);

    List<DurusKayip> tumDurusKayiplariGetir();
}
