package Ismakinesi;

import java.io.PrintWriter;
import java.util.Scanner;
enum MakineTuru{
    CNC,
    DOKUM,
    KILIF,
    KAPLAMA
}
public class IsMakinesi {
    String name;
    Scanner scanner;
    PrintWriter writer;
    MakineTuru makineTuru;
    int birimhiz;
    String statu;
    String makineId;

    public IsMakinesi(String name, MakineTuru makineTuru) {
        this.name = name;
        int k = birimhiz;
        this.makineTuru = makineTuru;
    }

    public IsMakinesi(String name, Scanner scanner, PrintWriter writer) {
        this.name = name;
        this.scanner = scanner;
        this.writer = writer;
    }

    public void doWork(){

    }

    @Override
    public String toString() {
        return String.format("%s %s %d %d", name, makineTuru.name(), birimhiz, makineId);
    }
}
