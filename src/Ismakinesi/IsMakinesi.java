package Ismakinesi;

import java.io.PrintWriter;
import java.util.Scanner;

public class IsMakinesi {
    public String name;
    public Scanner scanner;
    public PrintWriter writer;
    public MakineTuru makineTuru;
    public int birimhiz;
    public String statu;
    public int makineId;
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
