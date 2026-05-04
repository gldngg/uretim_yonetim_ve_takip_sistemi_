package model;

public class Planlama {

    private int id;
    private int siparisId;
    private String siparisKodu;
    private String urunAdi;
    private String gorev;
    private String makine;
    private String planTarihi;
    private String durum;

    public Planlama(int siparisId, String gorev, String makine) {
        this.siparisId = siparisId;
        this.gorev = gorev;
        this.makine = makine;
        this.planTarihi = "";
        this.durum = "Bekliyor";
    }

    public Planlama(int id, int siparisId, String siparisKodu, String urunAdi,
                    String gorev, String makine, String planTarihi, String durum) {
        this.id = id;
        this.siparisId = siparisId;
        this.siparisKodu = siparisKodu;
        this.urunAdi = urunAdi;
        this.gorev = gorev;
        this.makine = makine;
        this.planTarihi = planTarihi;
        this.durum = durum;
    }

    public int getId() { return id; }
    public int getSiparisId() { return siparisId; }
    public String getSiparisKodu() { return siparisKodu; }
    public String getUrunAdi() { return urunAdi; }
    public String getGorev() { return gorev; }
    public String getMakine() { return makine; }
    public String getPlanTarihi() { return planTarihi; }
    public String getDurum() { return durum; }
}