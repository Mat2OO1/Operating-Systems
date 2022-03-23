import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Drive {


    public void FCFS(int zgloszenia[], int head, int max) {
        int seek_count = 0;
        int distance, cur_track;

        for (int i = 0; i < zgloszenia.length; i++) {
            cur_track = zgloszenia[i];
            distance = Math.abs(cur_track - head);
            seek_count += distance;
            cur_track = head;
        }

        System.out.println("Ilosc przemieszczen (FCFS): " + seek_count);
        /*
        System.out.println("Kolejnosc: ");
        for (int i = 0; i < zgloszenia.length; i++) {
            System.out.println(zgloszenia[i]);
        }

         */


    }

    private void SSTFCalculateHowFar(int zgloszenia[], Elem tab[], int head) {
        for (int i = 0; i < tab.length; i++) {
            tab[i].howFar = Math.abs(zgloszenia[i] - head);
        }
    }

    private int SSTFFindMin(Elem tab[]) {
        int i = 0, minimum = 100000;
        for (int j = 0; j < tab.length; j++) {
            if (!tab[j].accessed && minimum > tab[j].howFar) {
                minimum = tab[j].howFar;
                i = j;
            }
        }
        return i;
    }

    public void SSTF(int zgloszenia[], int head, int max) {
        Elem tab[] = new Elem[zgloszenia.length];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = new Elem();
        }
        int seek_count = 0;
        int[] seek_sequence = new int[zgloszenia.length + 1];

        for (int i = 0; i < zgloszenia.length; i++) {
            seek_sequence[i] = head;
            SSTFCalculateHowFar(zgloszenia, tab, head);

            int index = SSTFFindMin(tab);

            tab[index].accessed = true;
            seek_count += tab[index].howFar;

            head = zgloszenia[index];
        }
        seek_sequence[seek_sequence.length - 1] = head;

        System.out.println("Ilosc przemieszczen (SSTF): " + seek_count);
        /*
        System.out.println("Kolejnosc: ");
        for (int i = 1; i < seek_sequence.length; i++)
            System.out.println(seek_sequence[i]);

         */
    }
    public static void SCAN(int zgloszenia[], int head, String direction, int max) {

        int size = zgloszenia.length;
        int disk_size = max;
            int seek_count = 0;
            int distance, cur_track;
            ArrayList<Integer> left = new ArrayList<>(), right = new ArrayList<>();
            ArrayList<Integer> seek_sequence = new ArrayList<>();
            if (direction == "left")
                left.add(0);
            else if (direction == "right")
                right.add(disk_size - 1);

            for (int i = 0; i < size; i++) {
                if (zgloszenia[i] < head)
                    left.add(zgloszenia[i]);
                if (zgloszenia[i] > head)
                    right.add(zgloszenia[i]);
            }
            Collections.sort(left);
            Collections.sort(right);

            int run = 2;
            while (run > 0) {
                if (direction == "left") {
                    for (int i = left.size() - 1; i >= 0; i--) {
                        cur_track = left.get(i);
                        seek_sequence.add(cur_track);
                        distance = Math.abs(cur_track - head);
                        seek_count += distance;
                        head = cur_track;
                    }
                    direction = "right"; }
                else if (direction == "right") {
                    for (int i = 0; i < right.size(); i++) {
                        cur_track = right.get(i);
                        seek_sequence.add(cur_track);
                        distance = Math.abs(cur_track - head);
                        seek_count += distance;
                        head = cur_track;
                    }
                    direction = "left";
                }
                run--;
            }
                System.out.println("Ilosc przemieszczen (SCAN):" + seek_count );

/*
            System.out.println("Kolejnosc: ");

            for (int i = 1; i < seek_sequence.size(); i++) {
                System.out.print(seek_sequence.get(i) + "\n");
            }

     */

        }


    public static void CSCAN(int zgloszenia[], int head, int max)
    {
        int size = zgloszenia.length;
        int disk_size = max;
        int seek_count = 0;
        int distance, cur_track;

        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        ArrayList<Integer> seek_sequence = new ArrayList<>();
        left.add(0);
        right.add(disk_size - 1);
        for (int i = 0; i < size; i++) {
            if (zgloszenia[i] < head)
                left.add(zgloszenia[i]);
            if (zgloszenia[i] > head)
                right.add(zgloszenia[i]);
        }
        Collections.sort(left);
        Collections.sort(right);
        for (int i = 0; i < right.size(); i++) {
            cur_track = right.get(i);
            seek_sequence.add(cur_track);
            distance = Math.abs(cur_track - head);
            seek_count += distance;
            head = cur_track;
        }
        head = 0;
        seek_count += (disk_size - 1);
        for (int i = 0; i < left.size(); i++) {
            cur_track = left.get(i);
            seek_sequence.add(cur_track);
            distance = Math.abs(cur_track - head);
            seek_count += distance;
            head = cur_track;
        }

        System.out.println("Ilosc przemieszczen (CSCAN): " + seek_count);
        /*
        System.out.println("Kolejnosc: ");
        for (int i = 1; i < seek_sequence.size(); i++) {
            System.out.println(seek_sequence.get(i));
            }

         */

    }
    public static void FDSCAN(int[] zgloszenia, int[] deadline, int head, int[] czas_przybycia) {
        int position = head;
        int direction = 1;
        int movements = 0;
        ArrayList<RealTimeTask> tasks = new ArrayList<>();
        for(int i=0; i< zgloszenia.length; i++){
            tasks.add(new RealTimeTask());
        }

        for(int i=0; i< zgloszenia.length; i++) {
            tasks.add(new RealTimeTask(zgloszenia[i], deadline[i], czas_przybycia[i]));
        }


        while (tasks.size() > 0) {
            tasks.sort(new TaskComparator());
            RealTimeTask target = tasks.get(0);
            if (target.getHowFar() >= position)
                direction = 1;
            else if (target.getHowFar() < position)
                direction = -1;

            RealTimeTask task = tasks.get(0);
            int minDistance = Integer.MAX_VALUE;
            for (RealTimeTask value : tasks) {
                int currDistance = (value.getHowFar() - position) * direction;
                if (currDistance < minDistance && currDistance >= 0) {
                    minDistance = currDistance;
                    task = value;
                }
            }
            tasks.remove(task);
            movements += Math.abs(position - task.getHowFar());
            position = task.getHowFar();
        }

        System.out.println("Ilosc przemieszczen (FDSCAN): " + movements);
    }

    public static void EDF(int[] zgloszenia, int[] deadline, int head, int[] czas_przybycia) {
        int position = head;
        int movements = 0;
        ArrayList<RealTimeTask> tasks = new ArrayList<>();
        for(int i=0; i< zgloszenia.length; i++){
            tasks.add(new RealTimeTask());
        }

        for(int i=0; i< zgloszenia.length; i++) {
            tasks.add(new RealTimeTask(zgloszenia[i], deadline[i], czas_przybycia[i]));
        }
        while (tasks.size() > 0) {
            tasks.sort(new TaskComparator());
            RealTimeTask task = tasks.get(0);
            tasks.remove(task);
            movements += Math.abs(position - task.getHowFar());
            position = task.getHowFar();
        }

        System.out.println("Ilosc przemieszczen (EDF): " + movements);
    }





    public static int[] losuj(int n, int max){
        int tab[] = new int[n];
        Random r = new Random();
        int polowa = n/2;
        for(int i=0; i< polowa; i++){
            tab[i] =r.nextInt(max/2);
        }
        for(int i = polowa; i<n; i++){
            tab[i] = r.nextInt(max/2) + max/2;
        }
        ArrayList<Integer> tmp = new ArrayList();
        for(int i=0; i<tab.length; i++){
            tmp.add(tab[i]);
        }
        Collections.shuffle(tmp);
        int tab1[] = new int[n];
        for(int i=0; i<tab1.length; i++){
            tab1[i] = tmp.get(i);
        }
        return tab;
    }

    
    public static void main(String[] args) {
        Drive drive = new Drive();
        int max = 230;
        int[] zgloszenia = losuj(200, max);
        int[] deadlines = losuj(200, 5);
        int[] czas_przybycia = losuj(200, 2);
        int head = 100;
        drive.FCFS(zgloszenia, head, max);
        drive.SSTF(zgloszenia, head, max);
        drive.SCAN(zgloszenia, head, "left", max);
        drive.CSCAN(zgloszenia,head, max);
        drive.EDF(zgloszenia, deadlines, head, czas_przybycia);
        drive.FDSCAN(zgloszenia, deadlines, head, czas_przybycia);

    }
}

class Elem{
   int howFar;
   boolean accessed ;
   public Elem(){
       howFar = 0;
       accessed = false;
   }
   public Elem(int position){
       this.howFar = position;
   }


    public int getHowFar() {
        return howFar;
    }
}

class RealTimeTask{
    int howFar;
    boolean accessed ;
    int deadline;
    int priority;
    public RealTimeTask(){
        howFar = 0;
        accessed = false;
    }
    public RealTimeTask(int position, int deadline, int priority){
        this.howFar = position;
        this.deadline = deadline;
        this.priority = priority;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getHowFar() {
        return howFar;
    }
    public int getPriority(){
        return priority;
    }
}

class TaskComparator implements Comparator<RealTimeTask> {

    @Override
    public int compare(RealTimeTask first, RealTimeTask second) {
        return Double.compare(first.getPriority(), second.getPriority());
    }
}

