public class RR {

    public static void test(int[] tab, int[] at, int k){
        int czas_wyk[] = new int[tab.length];
        for(int i=0; i<tab.length; i++){
            czas_wyk[i] = tab[i];
        }
        int n = tab.length;
        int is_done = 0;
        int[] endt = new int[n];
        int rem_bt[] = new int[tab.length];
        for(int i=0; i< tab.length; i++)
        {
            rem_bt[i] = tab[i];
        }
        int procesy[] = new int[n];
        for(int i=0; i<n; i++)
            procesy[i] = i +1;
        int wt[] = new int[n];
        for(int i =0; i< wt.length; i++)
            wt[i] =0;
        int x=0, j = 0, st =0, tot =0;
        int tbt = 0, twt = 0;
        while(true) {
            if (is_done == n)
                break;
            if (at[j] <= st && rem_bt[j] > 0)
                x = 0;
            {
                if (at[j] >= k) {
                    rem_bt[j] -= k;
                    st += k;
                } else {
                    st += rem_bt[j];
                    rem_bt[j] = 0;
                }

                if (rem_bt[j] <= 0) {
                    is_done += 1;
                    tot++;
                    endt[j] = st;
                    czas_wyk[j] = endt[j] - at[j];
                    wt[j] = czas_wyk[j] - tab[j];
                } else
                    x++;
            }
            j++;
            j = j % n;
            if (x % n == 0 && x > 0) {
                x = x % n;
                st++;
            }
        }
        System.out.println("RR");
        System.out.println("Proces    Czas Przybycia     Czas wykonania     Czas oczekiwania ");
            for(int  i = 0 ; i< n;  i++)
            {
                twt+= wt[i];
                System.out.println(procesy[i] + "          " + at[i] + "             " + czas_wyk[i] + "            " + wt[i]) ;
            }
        System.out.println("Sredni czas oczekiwania: " + (twt/n));
        System.out.println();


    }
}
