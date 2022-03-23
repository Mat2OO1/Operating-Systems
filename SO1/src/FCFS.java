import java.sql.SQLOutput;

class FCFS {
    public static int[] czasOczekiwania(int procesy[], int czas_wyk[])
    {
        int[] czas_oczek = new int[procesy.length];
        int czas =0;
        for(int i=1;i<procesy.length;i++){
            czas_oczek[i] = czas_wyk[i-1] + czas;
            czas += czas_wyk[i-1];
        }
        return czas_oczek;
    }

    public static int[] czasRealizacji(int procesy[], int czas_wyk[], int czas_ocz[]){
        int[] czas_real = new int[procesy.length];
        for(int i=0;i<procesy.length;i++){
            czas_real[i] = czas_wyk[i] + czas_ocz[i];
        }
        return czas_real;
    }
    public static double sredniCzasOczek(int procesy[], int czas_oczek[] ){
        double srednia = 0;
        for(int i=0; i< procesy.length; i++)
        {
            srednia += czas_oczek[i];
        }
        return srednia/ procesy.length;
    }
    public static void test(int[] tab)
    {
        int n = tab.length;
        System.out.println("Ilosc procesow: " + n);
        int procesy[] = new int[n];
        for(int i=0; i<n; i++)
        {
            procesy[i] = i +1;
        }
        int czas_wyk[] = new int[n];
        for(int i=0; i<n; i++){
            czas_wyk[i] = tab[i];
        }
        int czas_oczek[] = czasOczekiwania(procesy, czas_wyk);
        int czas_real[] = czasRealizacji(procesy,czas_wyk,czas_oczek);
        System.out.println("FCFS");
        System.out.println("Proces  Czas wykonania  Czas oczekiwania  Czas realizacji");
        for(int i=0;i< procesy.length;i++){
            System.out.println(procesy[i] + "              " + czas_wyk[i] + "                " + czas_oczek[i] + "              " + czas_real[i]);
        }
        System.out.printf("Sredni czas oczekiwania: %.2f ",sredniCzasOczek(procesy,czas_oczek));
        System.out.println();

    }
}