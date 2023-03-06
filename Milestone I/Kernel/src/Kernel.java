import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

class process1 extends Kernel implements Runnable{
    @Override
    public void run() {
        String filename = takeInput();
        BufferedReader br = read(filename);
        String output;
        try {
            while ((output = br.readLine()) != null)
                print(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class process2 extends Kernel implements Runnable{
    @Override
    public void run() {
        String filename = takeInput();
        String data = takeInput();
        write(filename, data);
    }
}

class process3 extends Kernel implements Runnable{
    @Override
    public void run() {
        for (int i=0; i<=300; i++)
            print(i);
    }
}

class process4 extends Kernel implements Runnable{
    @Override
    public void run() {
        for (int i=500; i<=1000; i++)
            print(i);
    }
}

class process5 extends Kernel implements Runnable{
    @Override
    public void run() {
        int small;
        int large;
        String st1 = takeInput();
        String st2 = takeInput();
        try {
            int n1 = Integer.parseInt(st1);
            int n2 = Integer.parseInt(st2);
            if (n1 <= n2) {
                small = n1;
                large = n2;
            } else {
                small = n2;
                large = n1;
            }
            //***********************************************************************
            int counter = 1;
            String path = null;
            File newFile = new File("newFile" + counter + ".txt");
            if (newFile.exists()){
                boolean flag = true;
                while (flag) {
                    counter++;
                    newFile = new File("newFile" + counter + ".txt");
                    if (!newFile.exists()) {
                        flag = false;
                        path = "newFile" + counter + ".txt";
                        try {
                            newFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                path = "newFile" + counter + ".txt";
                try {
                    newFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //***********************************************************************
            String numberConverted;
            for (int i=small; i<=large; i++) {
                numberConverted = Integer.toString(i);
                write(path, numberConverted);
            }
        } catch (NumberFormatException e) {
            print(">> Input must be number format.");
        }
    }
}

//<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

public class Kernel {  // Our OS !!

    public static BufferedReader read(String location) {  // System call #1
        File file = new File(location);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return br;
    }

    public static void write(String location, String input) {  // System call #2
        try {
            Files.write(Paths.get(location), input.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void print(Object data) {  // System call #3
        System.out.println(data);
    }

    public static String takeInput() {  // System call #4
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static void main(String[] args) {
        Thread t = new Thread(new process5());
        t.start();
    }

}
