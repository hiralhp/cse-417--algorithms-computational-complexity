import java.util.*;

// I am using the in class convention
public class Nussinov {

    private static int opt[][];
    private static String sequence;

    private Nussinov(String input) {

        sequence = input;
        int n = sequence.toCharArray().length;
        opt = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                opt[i][j] = 0;
            }
        }
    }

    private static int nussinovsAlgorithm(int i, int j) {
        int max = 0;

        if (opt[i][j] != 0) {
            return opt[i][j];
        }

        if (i >= j-4) {
            opt[i][j] = 0;
            return 0;
        }

        else {
            max = Math.max(max, nussinovsAlgorithm(i, j - 1));
            if (isPair(sequence.charAt(i), sequence.charAt(j))) {
                max = Math.max(max, nussinovsAlgorithm(i + 1, j - 1) + 1);
            }
            for (int k = i; k < j - 4; k++) {
                if (k > 0) {
                    if (isPair(sequence.charAt(j), sequence.charAt(k))) {
                        max = Math.max(max, 1+nussinovsAlgorithm(i, k - 1) + nussinovsAlgorithm(k + 1, j - 1));
                    }
                }
            }
            opt[i][j] = max;
        }

        return max;
    }

    private static String traceback(int i, int j) {

        if (i == j) {
            return ".";
        }

        if (i > j) {
            return "";
        }

        if (nussinovsAlgorithm(i, j) == nussinovsAlgorithm(i + 1, j)) {
            return "." + traceback(i + 1, j);
        }

        if (nussinovsAlgorithm(i, j) == nussinovsAlgorithm(i, j - 1)) {
            return traceback(i, j - 1) + ".";
        }

        if (isPair(sequence.charAt(i), sequence.charAt(j)) && nussinovsAlgorithm(i, j) == nussinovsAlgorithm(i + 1, j - 1) + 1) {
            return "(" + traceback(i + 1, j - 1) + ")";
        }

        for (int k = i; k < j-4; k++) {
            if (k > 0) {
                if (nussinovsAlgorithm(i, j) == nussinovsAlgorithm(i, k) + nussinovsAlgorithm(k + 1, j)){
                    return traceback(i, k) + traceback(k +1, j);
                }
            }
        }

        return "fail";
    }

    private static boolean isPair(char x, char y) {
        return ((x == 'A' && y == 'U') || (x == 'U' && y == 'A') || (x == 'C' && y == 'G') || (x == 'G' && y == 'C'));
    }

    private static boolean invalidChar(String input) {
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) != 'U' && input.charAt(i) != 'C' && input.charAt(i) != 'A' && input.charAt(i) != 'G'){
                return true;
            }
        }

        return false;
    }

    public static String stringGen(double n){
        String s = "";
        for(double i = 0.0; i < n; i++){
            double rand = (Math.random() * 4);
            rand = Math.round(rand);
            if (rand == 0.0) {
                s += "A";
            } else if (rand == 1.0) {
                s += "U";
            } else if (rand == 2.0) {
                s += "C";
            } else {
                s += "G";
            }
        }
        return s;
    }

    public static void nussinovMain(String s){
        long start = System.currentTimeMillis();
        System.out.println(s);
        int pairs = 0;
        char[] charArr = s.toCharArray();
        if (charArr.length == 0 || invalidChar(s)) {
            System.out.println("invalid sequence");
        } else {
            Nussinov nussinov = new Nussinov(s);
            pairs = nussinovsAlgorithm(0, charArr.length - 1);
            String optimal = traceback(0, charArr.length - 1);
            System.out.println(optimal);
        }

        long end = System.currentTimeMillis();
        double time = ((end - start));
        System.out.println("Length = " + s.length() + ", Pairs = " + pairs + " Time = " + time + " milli seconds");
        if(sequence.length() < 25) {
            System.out.println("opt matrix: ");
            for (int m = 0; m < sequence.length(); m++) {
                for (int n = 0; n < sequence.length(); n++) {
                    System.out.print(opt[m][n]);
                }
                System.out.println("");
            }
        }
        System.out.println();
    }
    public static void main(String args[]) {
        String s = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("enter sequence: ");
        while(!sc.hasNext()) {
        }
        s = sc.nextLine();
        nussinovMain(s);

        boolean timeTest = false; //change to true for time test

        //this part is for the random tests
        if(timeTest) {
            for (int i = 4; i <= 12; i++) {
                for (int j = 0; j < 3; j++) {
                    s = stringGen(Math.pow(2, i));
                    nussinovMain(s);
                }
            }
        }
    }
}
