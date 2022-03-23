ublic class SRTF {
    public static void test(int [] tab, int [] arrival) {
        int n = tab.length;
        int at[] = arrival;
        int czasWyk[] = tab;
        int endt[] = new int[n];
        int bt[] = new int[n];
        int wt[] = new int[n];
        int isDone[] = new int[n];
        int fOczek = 0;
        int st=0, is_done = 0;
        int rem_bt[] = new int [n];
        int procesy[] = new int[n];
        for(int i=0; i<n; i++)
            procesy[i] = i +1;

        for(int i=0; i<n; i++)
            rem_bt[i] = czasWyk[i];

        while(true) {
            int j=n, min=100000000;
            if (is_done == n)
                break;
            for (int i=0; i<n; i++)
            {
                if ((at[i] <= st) && (isDone[i] == 0) && (rem_bt[i]<min))
                {
                    min=rem_bt[i];
                    j=i;
                }
            }
            if (j==n)
                st++;
            else
            {
                st++;
                rem_bt[j]--;
                if(rem_bt[j]==0) {
                    endt[j] = st;
                    isDone[j] = 1;
                    is_done++;
                    bt[j]=endt[j]-at[j];
                    wt[j]=bt[j]-czasWyk[j];
                }
            }
        }

        System.out.println("SRTF");
        System.out.println("Proces  Czas przybycia    Czas wykonania  Czas oczekiwania  Czas realizacji");
        for(int  i = 0 ; i< n;  i++)
        {
            fOczek+= wt[i];
            System.out.println(procesy[i] + "           " + at[i] + "               " + czasWyk[i] + "             " + wt[i] + "              " + bt[i] ) ;
        }
        System.out.println("Średni czas oczekiwania: "+ (fOczek/n));
    }
}
