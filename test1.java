
import java.io.*;
public class Main {
    public static void main(String... args) {
        onLineTest();
        randomTest();

    }
    public static void onLineTest(){
        PrintWriter writer1 = new PrintWriter("onLine.txt", "UTF-8");
        for(int i = 0; i < 1700; i++){
            double rand1 = (Math.random() * 10000);
            writer1.println(0.0 +" "+ rand1);
        }
        writer1.close();
    }

    public static void randomTest(){
        PrintWriter writer2 = new PrintWriter("random.txt", "UTF-8");
        for(int i = 0; i < 1700; i++){
            double rand1 = (Math.random() * 10000);
            double rand2 = (Math.random() * 1000);

            writer2.println(rand2 +" "+ rand1);
        }
        writer2.close();
    }
}
