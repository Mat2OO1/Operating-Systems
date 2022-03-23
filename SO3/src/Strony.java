import java.util.*;

public class Strony {

    public static void FIFO(ArrayList<Integer> pages, int n, int capacity)
    {
        HashSet<Integer> s = new HashSet<>(capacity);

        Queue<Integer> indexes = new LinkedList<>() ;

        int page_faults = 0;
        for (int i=0; i<n; i++)
        {
            if (s.size() < capacity)
            {
                if (!s.contains(pages.get(i)))
                {
                    s.add(pages.get(i));
                    page_faults++;
                    indexes.add(pages.get(i));
                }
            }
            else
            {
                if (!s.contains(pages.get(i)))
                {
                    int val = indexes.peek();

                    indexes.poll();
                    s.remove(val);
                    s.add(pages.get(i));
                    indexes.add(pages.get(i));
                    page_faults++;
                }
            }
        }
        System.out.println("FIFO: ");
        System.out.println("Bledy strony: " + page_faults);
    }
    static boolean search(int key, ArrayList<Integer> frames)
    {
        for (int i = 0; i < frames.size(); i++)
            if (frames.get(i) == key)
                return true;
        return false;
    }

    static int predict(ArrayList<Integer> array, ArrayList<Integer> frames, int n, int index)
    {
        int res = -1, farthest = index;
        for (int i = 0; i < frames.size(); i++) {
            int j;
            for (j = index; j < n; j++) {
                if (frames.get(i) == array.get(j)) {
                    if (j > farthest) {
                        farthest = j;
                        res = i;
                    }
                    break;
                }
            }

            if (j == n)
                return i;
        }
        if(res == -1)
            return 0 ;
        else
            return res;
    }

    public static void OPT(ArrayList<Integer> array, int n, int capacity)
    {
        ArrayList<Integer> frames = new ArrayList<>();
        int hit = 0;
        for (int i = 0; i < n; i++) {
            if (search(array.get(i), frames)) {
                hit++;
                continue;
            }
            if (frames.size() < capacity) {
               frames.add(array.get(i));
            }
            else {
                int j = predict(array, frames, n, i + 1);
                frames.set(j, array.get(i));
            }
        }
        System.out.println("OPT: ");
        System.out.println("Bledy strony: " + (n-hit));
    }

    static void LRU(ArrayList<Integer> pages, int n, int capacity)
    {
        HashSet<Integer> s = new HashSet<>(capacity);
        HashMap<Integer, Integer> indexes = new HashMap<>();
        int page_faults = 0;
        for (int i=0; i<n; i++)
        {
            if (s.size() < capacity)
            {
                if (!s.contains(pages.get(i)))
                {
                    s.add(pages.get(i));
                    page_faults++;
                }
                indexes.put(pages.get(i), i);
            }
            else
            {
                if (!s.contains(pages.get(i)))
                {
                    int lru = Integer.MAX_VALUE, val=Integer.MIN_VALUE;
                    Iterator<Integer> itr = s.iterator();

                    while (itr.hasNext()) {
                        int temp = itr.next();
                        if (indexes.get(temp) < lru)
                        {
                            lru = indexes.get(temp);
                            val = temp;
                        }
                    }
                    s.remove(val);
                    indexes.remove(val);
                    s.add(pages.get(i));
                    page_faults++;
                }
                indexes.put(pages.get(i), i);
            }
        }
        System.out.println("LRU: ");
        System.out.println("Bledy strony: " + page_faults);
    }
    public static void RAND(ArrayList<Integer> pages, int n, int capacity) {
        int faults = 0;

        Random r = new Random();

        int[] array = new int[capacity];
        int pointer = r.nextInt(capacity);

        for(int i=0; i<pages.size(); i++) {
            if (!(isInFrames(pages.get(i), capacity, array))) {
                faults++;
                array[pointer] = pages.get(i);
                pointer = r.nextInt(capacity);
            }
        }

        System.out.println("RANDOM: ");
        System.out.println("Bledy strony: " + faults);
    }
    public static boolean isInFrames(int value, int frames, int[] array) {

        for (int i = 0; i < frames; i++){
            if (array[i] == value){
                return true;
            }
        }
        return false;
    }
    public static ArrayList<Integer> losuj(int n, int max){
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
        for(int i=0; i<tmp.size(); i++)
            System.out.println(tmp.get(i));
        Collections.shuffle(tmp);
        return tmp;
    }




    public static void main(String args[])
    {
        ArrayList<Integer> pages = losuj(10000,300);
        int capacity = 500;
        FIFO(pages, pages.size(), capacity);
        OPT(pages,pages.size(), capacity);
        LRU(pages, pages.size(), capacity);
        RAND(pages,pages.size(), capacity);
    }
}

