import java.util.ArrayList;
import java.util.Random;

class Proces {
    int wymagania;
    public Proces(int w){
        wymagania = w;
    }

}
class Procesor{
    ArrayList<Proces> procesy = new ArrayList<>();
    public static int obciazenie;
    public int obliczObciazenie(){
        for(int i=0; i<procesy.size(); i++){
            obciazenie += procesy.get(i).wymagania;
        }
        return obciazenie;
    }
    }
class Symulacja{
    int p; ///prog
    int r; ///min
    int z; ///ilosc losowan
    public static int zapytania;

    public void setP(int p) {
        this.p = p;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int strategia1(ArrayList<Procesor> procesory, Proces pr, Procesor x) {
        for(int i=0; i<z; i++) {
            zapytania++;
            Random r = new Random();
            int y = r.nextInt(procesory.size());
            if (procesory.get(y).obliczObciazenie() < p) {
                procesory.get(y).procesy.add(pr);
                return 0;
            }
        }
           x.procesy.add(pr);
        return 1;
    }
    public void strategia2(ArrayList<Procesor> procesory, Proces pr, Procesor x) {
        if (x.obliczObciazenie() > p) {
            int licznik = 0;
            while (true) {
                zapytania++;
                Random r = new Random();
                int y = r.nextInt(procesory.size());
                if (licznik == procesory.size()) {
                    if (procesory.get(y).obliczObciazenie() < p) {
                        procesory.get(y).procesy.add(pr);
                        break;
                    }
                } else
                    break;
            }
        }
        else
            x.procesy.add(pr);

    }

    public int srObciazenie(ArrayList<Procesor> procesory){
        int ob =0;
        int licznik=0;
        for(int i=0; i< procesory.size(); i++){
            for(int j=0; j< procesory.get(i).procesy.size(); j++){
                ob += procesory.get(i).procesy.get(j).wymagania;
                licznik++;
            }
        }
        return ob/licznik;
    }
    public double odchylenie(ArrayList<Procesor> procesory){
        int suma = 0;
        int srednia = srObciazenie(procesory);
        int licznik =0;
        for(int i=0; i< procesory.size(); i++){
            for(int j=0; j< procesory.get(i).procesy.size(); j++){
                double roznica = Math.pow(procesory.get(i).procesy.get(j).wymagania - srednia, 2);
                suma += roznica;
                licznik++;
            }
        }
        return Math.sqrt(suma)/licznik;
    }

    public static void main(String[] args) {
        int N= 2;
        int procesy = 10;
        int max = 80;
        Random r = new Random();
        Symulacja s = new Symulacja();
        s.setP(10); /// prog
        s.setR(30); ///min
        s.setZ(5); ///ilosc losowan
        ArrayList<Procesor> procesory = new ArrayList<>();
        for(int i =0; i<N; i++){
            procesory.add(new Procesor());
        }
        ArrayList<Procesor> procesory2 = new ArrayList<>();
        for(int i=0; i<procesory.size(); i++){
            procesory2.add(procesory.get(i));
        }
        ArrayList<Proces> procesTab = new ArrayList<>();
        for(int i=0; i<procesy; i++){
            procesTab.add(new Proces(r.nextInt(max)));
        }

        for(int i=0; i< procesy; i++){
            s.strategia1(procesory, procesTab.get(i), procesory2.get(r.nextInt(N)));
        }
        System.out.println("STRATEGIA 1 SR. OBCIAZENIE: " + s.srObciazenie(procesory));
        System.out.println("STRATEGIA 1 SR. ODCHYLENIE: " +s.odchylenie(procesory));
        System.out.println("STRATEGIA 1 ILOSC ZAPYTAN: " + s.zapytania);


        for(int i=0; i< procesy; i++){
            s.strategia2(procesory2, procesTab.get(i), procesory2.get(r.nextInt(N)));
        }
        System.out.println("STRATEGIA 2 SR. OBCIAZENIE: " + s.srObciazenie(procesory2));
        System.out.println("STRATEGIA 2 SR. ODCHYLENIE: " +s.odchylenie(procesory2));
        System.out.println("STRATEGIA 2 ILOSC ZAPYTAN: " + s.zapytania);


    }

}
