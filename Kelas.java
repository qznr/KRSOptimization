class Kelas {
    private String namaKelas;
    private MataKuliah mataKuliah;
    private Kelas kelasPraktikum;
    private boolean isPraktikum;
    private int terisi;

    public Kelas(String namaKelas, MataKuliah mataKuliah, Kelas kelasPraktikum, int terisi) {
        this.namaKelas = namaKelas;
        this.mataKuliah = mataKuliah;
        this.kelasPraktikum = kelasPraktikum;
        this.terisi = terisi;
        this.kelasPraktikum.setPraktikum(true);
    }

    public Kelas(String namaKelas, MataKuliah mataKuliah, int terisi) {
        this.namaKelas = namaKelas;
        this.mataKuliah = mataKuliah;
        this.terisi = terisi;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public MataKuliah getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(MataKuliah mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public int getTerisi() {
        return terisi;
    }

    public void setTerisi(int terisi) {
        this.terisi = terisi;
    }

    public Kelas getKelasPraktikum() {
        return kelasPraktikum;
    }

    public void setKelasPraktikum(Kelas kelasPraktikum) {
        this.kelasPraktikum = kelasPraktikum;
    }

    @Override
    public String toString() {
        return mataKuliah + " Kelas " + namaKelas;
    }

    public boolean isPraktikum() {
        return isPraktikum;
    }

    public void setPraktikum(boolean isPraktikum) {
        this.isPraktikum = isPraktikum;
    }
}
