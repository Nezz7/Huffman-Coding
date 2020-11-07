
import java.io.BufferedWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void code(String inputFile, String outputFile) {
        if (outputFile.length() == 0) {
            outputFile = inputFile + ".code";
        }
        Scanner in;
        File file = new File(inputFile);
        TreeMap<Character, Integer> freqMap = new TreeMap<>();
        try {
            in = new Scanner(file);
            while (in.hasNextLine()) {
                String s = in.nextLine();
                if (in.hasNextLine()) {
                    s += "\n";
                }
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (freqMap.containsKey(c)) {
                        int v = freqMap.get(c);
                        freqMap.put(c, v + 1);
                    } else {
                        freqMap.put(c, 1);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("\nFile Not Found !!");
        }
        HuffmanTree huffmanTree = new HuffmanTree(freqMap);
        huffmanTree.build();
        huffmanTree.code();
        try {
            in = new Scanner(file);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                freqMap.entrySet().forEach((cur) -> {
                    try {
                        writer.write((int) cur.getKey() + " ");
                        writer.write((int) cur.getValue() + " ");
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                writer.write("\n");
                while (in.hasNextLine()) {
                    String s = in.nextLine();
                    if (in.hasNextLine()) {
                        s += "\n";
                    }
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        String out = huffmanTree.getCode(c);
                        writer.write(out);
                    }
                }
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("File Not Found !!");
        }
    }

    public static void decode(String inputFile, String outputFile) {
        if (outputFile.length() == 0) {
            outputFile = inputFile + ".decode";
        }
        Scanner in;
        File file = new File(inputFile);
        TreeMap<Character, Integer> freqMap = new TreeMap<>();
        String code = "";
        try {
            in = new Scanner(file);
            if (in.hasNextLine()) {
                String s = in.nextLine();
                String[] values = s.split(" ", 0);
                for (int i = 0; i < values.length; i += 2) {
                    int c = Integer.parseInt(values[i]);
                    freqMap.put((char) c, Integer.parseInt(values[i + 1]));
                }
                if (in.hasNextLine()) {
                    code = in.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("\nFile Not Found !!");
        }
        HuffmanTree huffmanTree = new HuffmanTree(freqMap);
        huffmanTree.build();
        String decode = huffmanTree.decode(code);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(decode);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        if (args.length < 3) {
            System.out.println("Invalid arguments");
        } else {
            String function = args[0];
            String option = args[1];
            String inputFile = args[2];
            String outputFile = "";
            if (!function.equals("-huff")) System.out.println("Invalid arguments");
            else {
                if (args.length == 4) {
                    outputFile = args[3];
                }
                switch (option) {
                    case "-c":
                        code(inputFile, outputFile);
                        break;
                    case "-d":
                        decode(inputFile, outputFile);
                        break;
                    default:
                        System.out.println("Invalid arguments");
                        break;
                }
            }
        }

    }
}
