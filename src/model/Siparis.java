package model;

public class Siparis {

    private int id;
    private String siparisAdi;
    private String siparisKodu;
    private String musteri;
    private String urunAdi;
    private int miktar;
    private String terminTarihi;

    public Siparis(String siparisAdi, String siparisKodu, String musteri,
                   String urunAdi, int miktar, String terminTarihi) {
        this.siparisAdi = siparisAdi;
        this.siparisKodu = siparisKodu;
        this.musteri = musteri;
        this.urunAdi = urunAdi;
        this.miktar = miktar;
        this.terminTarihi = terminTarihi;
    }

    public Siparis(int id, String siparisAdi, String siparisKodu, String musteri,
                   String urunAdi, int miktar, String terminTarihi) {
        this.id = id;
        this.siparisAdi = siparisAdi;
        this.siparisKodu = siparisKodu;
        this.musteri = musteri;
        this.urunAdi = urunAdi;
        this.miktar = miktar;
        this.terminTarihi = terminTarihi;
    }

    public int getId() {
        return id;
    }

    public String getSiparisAdi() {
        return siparisAdi;
    }

    public String getSiparisKodu() {
        return siparisKodu;
    }

    public String getMusteri() {
        return musteri;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public int getMiktar() {
        return miktar;
    }

    public String getTerminTarihi() {
        return terminTarihi;
    }
}