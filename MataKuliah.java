class MataKuliah {
    private String nama;
    private String kode;
    private boolean adaPraktikum;
    private int jumlahSks;
    private int jumlahSksPraktikum;

    public MataKuliah(String nama, String kode, boolean adaPraktikum, int jumlahSks) {
        this.nama = nama;
        this.kode = kode;
        this.adaPraktikum = adaPraktikum;
        this.jumlahSks = jumlahSks;
        if(adaPraktikum) {
            jumlahSksPraktikum = 2;
        }
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public boolean isAdaPraktikum() {
        return adaPraktikum;
    }

    public void setAdaPraktikum(boolean adaPraktikum) {
        this.adaPraktikum = adaPraktikum;
    }

    public int getJumlahSks() {
        return jumlahSks;
    }

    public void setJumlahSks(int jumlahSks) {
        this.jumlahSks = jumlahSks;
    }

    public int getJumlahSksPraktikum() {
        return jumlahSksPraktikum;
    }

    public void setJumlahSksPraktikum(int jumlahSksPraktikum) {
        this.jumlahSksPraktikum = jumlahSksPraktikum;
    }

    @Override
    public String toString() {
        return nama;
    }
}