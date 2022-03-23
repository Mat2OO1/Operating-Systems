public class Test {

    public static void main (String [] args)
    {
        Algorithms algorithm = new Algorithms(1000, 4000, 250, 100);
        System.out.println("Przydzial proporcjonalny: " + algorithm.proportional());
        System.out.println("Przydzial rowny: " + algorithm.equal());
        System.out.println("Model strefowy: " + algorithm.zone(30));
        System.out.println("Sterowanie czestoscia bledow strony: " + algorithm.fault());


    }
}