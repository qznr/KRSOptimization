import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Scheduler { 
    private List<JadwalKelas> jadwalKelasList;
    private EnumSet<Hari> pilihanHari; // C 
    private int sksPerHari; // L_d
    private int maxInterval; // Maximum total interval (M)

    public Scheduler(List<JadwalKelas> jadwalKelasList, EnumSet<Hari> pilihanHari, int sksPerHari, int maxInterval) {
        this.jadwalKelasList = jadwalKelasList;
        this.pilihanHari = pilihanHari;
        this.sksPerHari = sksPerHari;
        this.maxInterval = maxInterval;
    }

    public Scheduler(List<JadwalKelas> jadwalKelasList, int sksPerHari, int maxInterval) {
        this.jadwalKelasList = jadwalKelasList;
        this.pilihanHari = EnumSet.allOf(Hari.class);
        this.sksPerHari = sksPerHari;
        this.maxInterval = maxInterval;
    }

    public Set<MataKuliah> getMataKuliahTerpilih(){
        Set<MataKuliah> mataKuliahTerpilih = new HashSet<>();
        for (JadwalKelas jadwalKelas : jadwalKelasList) {
            Kelas kelas = jadwalKelas.getKelas();
            MataKuliah matkul = kelas.getMataKuliah();
            mataKuliahTerpilih.add(matkul);
        }
        return mataKuliahTerpilih;
    }

    public Stack<MataKuliah> getMataKuliahStack(Set<MataKuliah> setMatkul) {
        Stack<MataKuliah> stackMatkul = new Stack<>();
        for (MataKuliah mataKuliah : setMatkul) {
            stackMatkul.push(mataKuliah);
        }
        return stackMatkul;
    }

    public Stack<JadwalKelas> getJadwalStack(List<JadwalKelas> jadwalMatkul){
        Stack<JadwalKelas> stackJadwalKelas = new Stack<>();
        for (JadwalKelas jadwalKelas : jadwalMatkul) {
            stackJadwalKelas.push(jadwalKelas);
        }
        return stackJadwalKelas;
    }

    public HashMap<MataKuliah, List<JadwalKelas>> getJadwalOrderByMataKuliah(Set<MataKuliah> setMataKuliah, List<JadwalKelas> jadwalKelasList) {
        HashMap<MataKuliah, List<JadwalKelas>> jadwalByMatkul = new HashMap<>();

        for (JadwalKelas jadwalKelas : jadwalKelasList) {
            Kelas kelas = jadwalKelas.getKelas();
            MataKuliah matkul = kelas.getMataKuliah();

            if (setMataKuliah.contains(matkul)) {
                if (jadwalByMatkul.containsKey(matkul)) {
                    jadwalByMatkul.get(matkul).add(jadwalKelas);
                } else {
                    List<JadwalKelas> newList = new ArrayList<>();
                    newList.add(jadwalKelas);
                    jadwalByMatkul.put(matkul, newList);
                }
            }
        }
        return jadwalByMatkul;
    }

    public List<JadwalKelas> getJadwalByMataKuliah(MataKuliah matkul, HashMap<MataKuliah, List<JadwalKelas>> allJadwalByMatkul) {
        return allJadwalByMatkul.get(matkul);
    }

    public List<List<JadwalKelas>> backtrack() {
        // Implement your backtracking algorithm (DFS) here
        // This method should iterate through possible combinations of schedules
        List<List<JadwalKelas>> hasilSemuaKombinasiJadwalValid = new ArrayList<>();
        Set<MataKuliah> setMataKuliah = getMataKuliahTerpilih();
        HashMap<MataKuliah, List<JadwalKelas>> jadwalByMatkul = getJadwalOrderByMataKuliah(setMataKuliah, jadwalKelasList);
        // Create an empty schedule
        List<JadwalKelas> currentSchedule = new ArrayList<>();
        //Node
        Stack<NodeMatkul> nodeMatkuls = new Stack<>();
        for (MataKuliah matkul : setMataKuliah) {
            List<JadwalKelas> jadwalKelas = jadwalByMatkul.get(matkul);
            Stack<JadwalKelas> stackJadwalKelas = getJadwalStack(jadwalKelas);
            NodeMatkul node = new NodeMatkul(matkul, stackJadwalKelas);
            nodeMatkuls.push(node);
        }
        int jumlahKelasPraktikum = 0;
        hasilSemuaKombinasiJadwalValid = backtrack(hasilSemuaKombinasiJadwalValid, currentSchedule, nodeMatkuls, setMataKuliah, jumlahKelasPraktikum);
        return hasilSemuaKombinasiJadwalValid;
    }

    public List<List<JadwalKelas>> backtrack(List<List<JadwalKelas>> hasilSemuaKombinasiJadwalValid, List<JadwalKelas> currentSchedule, Stack<NodeMatkul> nodeMatkuls, Set<MataKuliah> setMataKuliah, int jumlahKelasPraktikum) {
        Stack<NodeMatkul> newNodeMatkuls = new Stack<>();
        newNodeMatkuls.addAll(nodeMatkuls);
        while(!newNodeMatkuls.isEmpty()) {
            NodeMatkul node = newNodeMatkuls.pop();
            Stack<JadwalKelas> stackJadwalKelas = new Stack<>();
            stackJadwalKelas.addAll(node.jadwalStack);
            while (!stackJadwalKelas.isEmpty()) {
                JadwalKelas jadwal = stackJadwalKelas.pop();
                // Add the jadwal to the current schedule (Node B1)
                if (jadwal.getJadwalPraktikum()!=null) {
                    currentSchedule.add(jadwal);
                    currentSchedule.add(jadwal.getJadwalPraktikum());
                    jumlahKelasPraktikum++;
                } else {
                    currentSchedule.add(jadwal);
                }
                // Evaluate the current schedule (Node C1)
                if (meetsCriteria(currentSchedule)) {
                    // Check if all mata kuliah are scheduled
                    // System.out.println("  Jadwal Valid: " + jadwal);
                    if (currentSchedule.size() == setMataKuliah.size() + jumlahKelasPraktikum) {
                        hasilSemuaKombinasiJadwalValid.add(new ArrayList<>(currentSchedule));
                    } else {
                        backtrack(hasilSemuaKombinasiJadwalValid, currentSchedule, newNodeMatkuls, setMataKuliah, jumlahKelasPraktikum);
                    }
                }
                // Backtrack to the previous state (Node D2)                
                if (jadwal.getJadwalPraktikum()!=null) {
                    currentSchedule.remove(jadwal);
                    currentSchedule.remove(jadwal.getJadwalPraktikum());
                    jumlahKelasPraktikum--;
                } else {
                    currentSchedule.remove(jadwal);
                }
            }    
        }
        return hasilSemuaKombinasiJadwalValid;
    }

    private boolean meetsCriteria(List<JadwalKelas> jadwalKelasList) {
        if (jadwalKelasList.size() == 1) return true;
        return !hasClassConflicts(jadwalKelasList) && isConsistentWithStudentPreferences() && doesNotExceedClassCapacity();
    }

    private boolean checkTimeOverlap(JadwalKelas a, JadwalKelas b) {
        if (a.getHari() != b.getHari()) return false;
        LocalTime mulaiA = a.getWaktuMulai();
        LocalTime mulaiB = b.getWaktuMulai();
        LocalTime selesaiA = a.getWaktuSelesai();
        LocalTime selesaiB = b.getWaktuSelesai();
        return mulaiA.isBefore(selesaiB) && selesaiA.isAfter(mulaiB);
    }

    private boolean hasClassConflicts(List<JadwalKelas> jadwalKelasList) {
        int size = jadwalKelasList.size();
        for (int i = 0; i < size - 1; i++) {
            JadwalKelas jadwalA = jadwalKelasList.get(i);
            for (int j = i + 1; j < size; j++) {
                JadwalKelas jadwalB = jadwalKelasList.get(j);
                if (checkTimeOverlap(jadwalA, jadwalB)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Check consistency with student preferences
    private boolean isConsistentWithStudentPreferences() {
        // Implement logic to check if the schedule is consistent with student preferences
        // Use the information about selected days
        // Return true if consistent, otherwise false
        return true; // Placeholder, replace with actual logic
    }

    // Check if the class capacity is not exceeded
    private boolean doesNotExceedClassCapacity() {
        // Implement logic to check if the class capacity is not exceeded
        // Use information about class capacities and enrolled students
        // Return true if within capacity, otherwise false
        return true; // Placeholder, replace with actual logic
    }

    // Evaluate the schedule based on the criteria described in PENILAIAN JADWAL
    public double evaluateSchedule(List<JadwalKelas> jadwalKelasList) {
        double classDistributionScore = calculateClassDistributionScore(jadwalKelasList);
        double gapDurationScore = calculateGapDurationScore(jadwalKelasList);
        // System.out.println("C Distribution\t: " + classDistributionScore);
        // System.out.println("Gap Score\t: " + gapDurationScore);
        return 0.7 * classDistributionScore + 0.3 * gapDurationScore;
    }

    // Calculate the class distribution score
    private double calculateClassDistributionScore(List<JadwalKelas> jadwalKelasList) {
        final double[] totalScore = {0.0};  // Declare totalScore as an array to make it mutable
    
        EnumSet.allOf(Hari.class).forEach(hari -> {
            // Retrieve N_d, L_d values for the current day
            int Nd = calculateTotalScheduledTimeForDay(hari, jadwalKelasList);
            int Ld = sksPerHari; // Assuming sksPerHari is the same for all days
            // Calculate the score for the current day
            double score = 1.0 - ((double) Nd / Ld);
            totalScore[0] += Math.abs(score);
        });
    
        // Calculate the overall class distribution score
        double classDistributionScore = totalScore[0] / EnumSet.allOf(Hari.class).size();
    
        return classDistributionScore;
    }
    
    private int calculateTotalScheduledTimeForDay(Hari hari, List<JadwalKelas> jadwalKelasList) {
        // Iterate through jadwalKelasList and calculate total scheduled time for the given day
        int Nd = 0;
        for (JadwalKelas jadwalKelas : jadwalKelasList) {
            if (jadwalKelas.getHari() != hari) continue;
            Nd += jadwalKelas.calculateSks();
        }
        return Nd;
    }

    // Calculate the gap duration score
    private double calculateGapDurationScore(List<JadwalKelas> jadwalKelasList) {
        final double[] totalScore = {0.0};  // Declare totalScore as an array to make it mutable
    
        pilihanHari.forEach(hari -> {
            // Retrieve Gd, M values for the current day
            int Gd = calculateTotalIntervalForDay(hari, jadwalKelasList);
            int M = maxInterval;
    
            // Calculate the score for the current day using the formula g(Gd, M)
            double score;
            if (Gd <= M) {
                score = 1.0 + 1.0 / (1.0 + Math.exp(0.05 * (Gd - M / 2.0)));
            } else {
                score = (double) M / (double) Gd;
            }
    
            totalScore[0] += score;
        });
    
        // Calculate the overall gap duration score
        double gapDurationScore = totalScore[0] / pilihanHari.size();
    
        return gapDurationScore;
    }
    
    private int calculateTotalIntervalForDay(Hari hari, List<JadwalKelas> jadwalKelasList) {
        // Iterate through jadwalKelasList and calculate total interval for the given day
        int Gd = 0;
        List<JadwalKelas> jadwalKelasHari = new ArrayList<>();
        for (JadwalKelas jadwalKelas : jadwalKelasList) {
            if (jadwalKelas.getHari() != hari) continue;
            jadwalKelasHari.add(jadwalKelas);
        }
        Gd = calculateInterval(jadwalKelasHari);

        return Gd;
    }

    private int calculateInterval(List<JadwalKelas> jadwalKelasList) {
        Duration timeInterval = Duration.ofMinutes(0);
        for (int i = 0; i < jadwalKelasList.size() - 1; i++) {
            JadwalKelas curr = jadwalKelasList.get(i);
            JadwalKelas next = jadwalKelasList.get(i+1);
            
            timeInterval = timeInterval.plus(Duration.between(curr.getWaktuSelesai(), next.getWaktuMulai()));

        }
        return (int) timeInterval.toMinutes();
    }

    public List<List<JadwalKelas>> hasilSorted(List<List<JadwalKelas>> hasilBacktrack) {
        Collections.sort(hasilBacktrack, new Comparator<List<JadwalKelas>>() {
            @Override
            public int compare(List<JadwalKelas> list1, List<JadwalKelas> list2) {
                double score1 = evaluateSchedule(list1);
                double score2 = evaluateSchedule(list2);
                
                // Change the order based on your sorting preference (ascending or descending)
                return Double.compare(score2, score1); // Descending order
            }
        });

        return hasilBacktrack;
    }
}
