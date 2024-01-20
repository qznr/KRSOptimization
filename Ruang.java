class Ruang {
    private String namaRuang;
    private int kapasitas;

    public Ruang(String namaRuang, int kapasitas) {
        this.namaRuang = namaRuang;
        this.kapasitas = kapasitas;
    }
    public String getNamaRuang() {
        return namaRuang;
    }
    public void setNamaRuang(String namaRuang) {
        this.namaRuang = namaRuang;
    }
    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }
}