package interfaces;

import java.util.List;
import model.Planlama;

public interface IPlanlamaIslemleri {

    boolean planlamaEkle(Planlama planlama);

    boolean planlamaGuncelle(int id, String makine, String planTarihi, String durum);

    boolean siparisPlanlamalariniSil(int siparisId);

    List<Planlama> siparisPlanlamalariniGetir(int siparisId);

    List<Planlama> tumPlanlamalariGetir();

    int makineAdediGetir();
}