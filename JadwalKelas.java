import java.time.LocalTime;

class JadwalKelas {
    private Hari hari;
    private Kelas kelas;
    private LocalTime waktuMulai;
    private JadwalKelas jadwalPraktikum;
    private Ruang ruang;
    public static final int WAKTUSKS = 50;
    
    public JadwalKelas(Hari hari, LocalTime waktuMulai, Kelas kelas, Ruang ruang) {
        this.hari = hari;
        this.waktuMulai = waktuMulai;
        this.kelas = kelas;
        this.ruang = ruang;
    }

    public JadwalKelas(Hari hari, LocalTime waktuMulai, Kelas kelas, JadwalKelas jadwalPraktikum, Ruang ruang) {
        this.hari = hari;
        this.waktuMulai = waktuMulai;
        this.kelas = kelas;
        this.jadwalPraktikum = jadwalPraktikum;
        this.ruang = ruang;
    }

    public int calculateSks(){
        MataKuliah mataKuliah = kelas.getMataKuliah();
        if(kelas.getKelasPraktikum() != null) {
            int sks = mataKuliah.getJumlahSksPraktikum();
            return sks;
        } else {
            int sks = mataKuliah.getJumlahSks() - mataKuliah.getJumlahSksPraktikum();
            return sks;
        }
    }

    public LocalTime getWaktuSelesai(){
        int sks = calculateSks();
        LocalTime waktuSelesai = waktuMulai.plusMinutes( WAKTUSKS * sks);
        return waktuSelesai;
    }

    public static int getWaktusks() {
        return WAKTUSKS;
    }

    public Hari getHari() {
        return hari;
    }

    public void setHari(Hari hari) {
        this.hari = hari;
    }

    public Kelas getKelas() {
        return kelas;
    }

    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
    }

    public Ruang getRuang() {
        return ruang;
    }

    public void setRuang(Ruang ruang) {
        this.ruang = ruang;
    }

    public LocalTime getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(LocalTime waktuMulai) {
        this.waktuMulai = waktuMulai;
    }
    
    @Override
    public String toString() {

        // if (jadwalPraktikum!=null) {
        //     Kelas kelasPraktikum = jadwalPraktikum.getKelas();
        //     return kelas.toString() + " Hari " + hari + " Jam " + waktuMulai + "\n\t  " +
        //     kelasPraktikum.toString() + " Hari " + jadwalPraktikum.getHari() + " Jam " + jadwalPraktikum.getWaktuMulai() + " (Praktikum)";
        // }
        // return kelas.toString();

        return kelas.toString() + " Hari " + hari + " Jam " + waktuMulai + "-" + getWaktuSelesai() + (kelas.isPraktikum()? " (Praktikum)" : "");
    }

    public JadwalKelas getJadwalPraktikum() {
        return jadwalPraktikum;
    }

    public void setJadwalPraktikum(JadwalKelas jadwalPraktikum) {
        this.jadwalPraktikum = jadwalPraktikum;
    }
}

