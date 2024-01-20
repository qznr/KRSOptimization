import java.util.Stack;

public class NodeMatkul {
    public MataKuliah matkul;
    public Stack<JadwalKelas> jadwalStack;

    public NodeMatkul(MataKuliah matkul, Stack<JadwalKelas> jadwalStack) {
        this.matkul = matkul;
        this.jadwalStack = jadwalStack;
    }

    public NodeMatkul(NodeMatkul nodeMatkul) {
        this.matkul = nodeMatkul.matkul;
        this.jadwalStack = nodeMatkul.jadwalStack;
    }

    @Override
    public String toString() {
        return matkul.toString();
    }
}
