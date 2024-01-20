import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        //Input matkul
        MataKuliah jarkom = new MataKuliah("JARINGAN KOMPUTER", "CIF61007", true, 4);
        MataKuliah basdat = new MataKuliah("BASIS DATA", "CIF61009", true, 4);
        MataKuliah imk = new MataKuliah("INTERAKSI MANUSIA DAN KOMPUTER", "CIF61012", false, 3);
        MataKuliah kb = new MataKuliah("KECERDASAN BUATAN", "CIF61011", false, 3);
        //Input Kelas
        Kelas jarkomAP = new Kelas("A", jarkom, 0);
        Kelas jarkomA = new Kelas("A", jarkom, jarkomAP, 0);
        Kelas jarkomBP = new Kelas("B", jarkom, 0);
        Kelas jarkomB = new Kelas("B", jarkom, jarkomBP, 0);
        Kelas basdatAP = new Kelas("A", basdat, 0);
        Kelas basdatA = new Kelas("A", basdat, basdatAP, 0);
        Kelas basdatBP = new Kelas("B", basdat, 0);
        Kelas basdatB = new Kelas("B", basdat, basdatBP, 0);
        Kelas imkA = new Kelas("A", imk, 0);
        Kelas imkB = new Kelas("B", imk, 0);
        Kelas kbA = new Kelas("A", kb, 0);
        Kelas kbB = new Kelas("B", kb, 0);
        //Input JadwalKelas
        JadwalKelas jadwalJarkomAP = new JadwalKelas(Hari.KAMIS, LocalTime.of(9, 40), jarkomAP, null);
        JadwalKelas jadwalJarkomA = new JadwalKelas(Hari.SELASA, LocalTime.of(8, 0), jarkomA, jadwalJarkomAP, null);
        JadwalKelas jadwalJarkomBP = new JadwalKelas(Hari.SENIN, LocalTime.of(8, 0), jarkomBP, null);
        JadwalKelas jadwalJarkomB = new JadwalKelas(Hari.JUMAT, LocalTime.of(12, 50), jarkomB, jadwalJarkomBP, null);
        JadwalKelas jadwalBasdatAP = new JadwalKelas(Hari.KAMIS, LocalTime.of(7, 0), basdatAP, null);
        JadwalKelas jadwalBasdatA = new JadwalKelas(Hari.SELASA, LocalTime.of(8, 20), basdatA, jadwalBasdatAP, null);
        JadwalKelas jadwalBasdatBP = new JadwalKelas(Hari.RABU, LocalTime.of(8, 30), basdatBP, null);
        JadwalKelas jadwalBasdatB = new JadwalKelas(Hari.SENIN, LocalTime.of(7, 40), basdatB, jadwalBasdatBP, null);
        JadwalKelas jadwalImkA = new JadwalKelas(Hari.JUMAT, LocalTime.of(7, 0), imkA, null);
        JadwalKelas jadwalImkB = new JadwalKelas(Hari.SELASA, LocalTime.of(7, 0), imkB, null);
        JadwalKelas jadwalKbA = new JadwalKelas(Hari.RABU, LocalTime.of(9, 0), kbA, null);
        JadwalKelas jadwalKbB = new JadwalKelas(Hari.KAMIS, LocalTime.of(7, 0), kbB, null);
        //Jadwal List
        List<JadwalKelas> jadwalList = new ArrayList<>();
        //Add to List
        jadwalList.add(jadwalJarkomA);
        jadwalList.add(jadwalJarkomB);
        jadwalList.add(jadwalBasdatA);
        jadwalList.add(jadwalBasdatB);
        jadwalList.add(jadwalImkA);
        jadwalList.add(jadwalImkB);
        jadwalList.add(jadwalKbA);
        jadwalList.add(jadwalKbB);

        Scheduler scheduler = new Scheduler(jadwalList, 4, 180);
        Set<MataKuliah> mataKuliahTerpilih = scheduler.getMataKuliahTerpilih();
        
        HashMap<MataKuliah, List<JadwalKelas>> jadwalOrderByMatkul = scheduler.getJadwalOrderByMataKuliah(mataKuliahTerpilih, jadwalList);

        // for (Map.Entry<MataKuliah, List<JadwalKelas>> entry : jadwalOrderByMatkul.entrySet()) {
        //     MataKuliah matkul = entry.getKey();
        //     List<JadwalKelas> jadwalKelasList = entry.getValue();

        //     System.out.println("Mata Kuliah: " + matkul);
            
        //     // Iterate over the JadwalKelas in the list and print details
        //     for (JadwalKelas jadwalKelas : jadwalKelasList) {
        //         System.out.println("  Jadwal: " + jadwalKelas);
        //         if (jadwalKelas.getJadwalPraktikum()!=null) {
        //             System.out.println("\t  "+jadwalKelas.getJadwalPraktikum());
        //         }
        //     }

        //     System.out.println();  // Separate entries with an empty line
        // }

        List<List<JadwalKelas>> hasilBacktrack = scheduler.backtrack();
        List<List<JadwalKelas>> hasilSorted = scheduler.hasilSorted(hasilBacktrack);
        for (List<JadwalKelas> hasil : hasilSorted) {
            double score = scheduler.evaluateSchedule(hasil);
            System.out.println("Score : " + score);
            for (JadwalKelas jadwal : hasil) {
                System.out.println("  Jadwal: " + jadwal);
            }
        }
    }
}