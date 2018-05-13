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
    String makineId;

    public IsMakinesi(String name, MakineTuru makineTuru) {
        this.name = name;
        this.makineTuru = makineTuru;
    }

    public IsMakinesi(String name, Scanner scanner, PrintWriter writer) {
        this.name = name;
        this.scanner = scanner;
        this.writer = writer;
    }

    public void doWork(){

    }
}
