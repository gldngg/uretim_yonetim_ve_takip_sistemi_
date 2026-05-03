package model;

public class Makine {

    private int id;
    private String makineTipi;
    private String makineKodu;
    private String bolum;
    private String kapasite;
    private String bakimPeriyodu;
    private String lokasyon;

    public Makine(String makineTipi, String makineKodu, String bolum,
                  String kapasite, String bakimPeriyodu, String lokasyon) {

        this.makineTipi = makineTipi;
        this.makineKodu = makineKodu;
        this.bolum = bolum;
        this.kapasite = kapasite;
        this.bakimPeriyodu = bakimPeriyodu;
        this.lokasyon = lokasyon;
    }

    public int getId() {
        return id;
    }

    public String getMakineTipi() {
        return makineTipi;
    }

    public String getMakineKodu() {
        return makineKodu;
    }

    public String getBolum() {
        return bolum;
    }

    public String getKapasite() {
        return kapasite;
    }



    public String getBakimPeriyodu() {
        return bakimPeriyodu;
    }

    public String getLokasyon() {
        return lokasyon;
    }
}