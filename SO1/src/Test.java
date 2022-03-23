import java.util.Random;

public class Test {
    public static int[] losuj(int n, int max){
        int[] tab = new int[n];
        Random r = new Random();
        for(int i=0; i<n; i++){
            tab[i] = r.nextInt(max-1) + 1;
        }
        return tab;
    }
    public static void main(String[] args) {
        int[] tab = losuj(30000, 10);
        int[] arrival = losuj(30000, 4);

        FCFS.test(tab);
        SJF.test(tab);

        SRTF.test(tab, arrival);
        RR.test(tab,arrival,11);
    }
}
