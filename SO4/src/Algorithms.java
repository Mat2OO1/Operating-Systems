import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


class Algorithms {

    public int frame_size;
    public int num_pages;
    public int procesy;
    public int interval;
    public ArrayList<Page> Procesy;
    public Proces[] procesyTab;

    ArrayList<Page> PageReferences = new ArrayList<Page>();
    ArrayList<Page> Frame = new ArrayList<Page>();
    public int faults = 0;


    public Algorithms(int frame_size, int num_pages, int interval, int procesy) {
        this.frame_size = frame_size;
        this.num_pages = num_pages;
        this.procesy = procesy;
        this.interval = interval;
        procesyTab = new Proces[procesy];

        //randomizing
        for (int i = 0; i < num_pages; i++) {
            int k = (int) (Math.random() * procesy);
            int r = (int) (Math.random() * interval);
            PageReferences.add(new Page(r, 0, k));

        }
        //creating table of processes
        for (int w = 0; w < procesy; w++) {
            procesyTab[w] = new Proces(new ArrayList(), 0);
            for (int s = 0; s < PageReferences.size(); s++) {
                if ((PageReferences.get(s)).proces == w) {
                    Proces a = procesyTab[w];
                    a.proces.add(PageReferences.get(s));
                }
            }
        }


    }
    public int equal() {
        //copying references
        Proces[] ProcesyTab1 = new Proces[procesy];
        int faults = 0;
        int frame_size = this.frame_size / (procesyTab.length);
        //adds all page faults
        for (int k = 0; k < procesyTab.length; k++) {
            ProcesyTab1[k] = new Proces(procesyTab[k]);
            int p = LRU(ProcesyTab1[k].proces, frame_size);
            faults += p;

        }

        return faults;
    }

    public int proportional() {
        int frame_size = this.frame_size / procesyTab.length;
        Proces[] ProcesyTab2 = new Proces[procesy];
        int faults = 0;
        for (int k = 0; k < procesyTab.length; k++) {
            ProcesyTab2[k] = new Proces(procesyTab[k]);
        }
        for (int j = 0; j < ProcesyTab2.length; j++) {

            frame_size = ProcesyTab2[j].proces.size() * this.frame_size / num_pages;
            if (frame_size == 0) {
                frame_size = 3;
            }
            int p = LRU(ProcesyTab2[j].proces, frame_size);
            faults += p;
        }
        return faults;
    }

    public int fault() {
        int faultsMax =(int)0.6* num_pages;
        int frame_size = this.frame_size / procesyTab.length;
        Proces[] ProcesyTab3 = new Proces[procesy];
        for (int i = 0; i < procesyTab.length; i++) {
            ProcesyTab3[i] = new Proces(procesyTab[i]);
            ProcesyTab3[i].setFrame(frame_size);
        }
        int freeFrames = 0;
        boolean allDone = false;
        int size = procesy;
        int faults = 0;
        while (size != 0) {
            int min = interval;
            int max = 0;
            int minI = 0;
            int maxI = 0;
            for (int i = 0; i < ProcesyTab3.length; i++) {
                Proces t = ProcesyTab3[i];
                if (t != null && t.proces.size() != 0) {
                    if (size == 1) {
                        ProcesyTab3[i].setFrame(ProcesyTab3[i].frame_size + freeFrames);
                        freeFrames = 0;
                    }
                    int pf = t.PPF;
                    int pfsingle = t.LRU(t.proces);
                    if (pf > max) {
                        max = pf;
                        maxI = i;
                    }
                    if (pf < min) {
                        min = pf;
                        minI = i;
                    }
                    t.proces.remove(0);
                    faults += pfsingle;
                } else if (t != null) {

                    if (ProcesyTab3[maxI] != null && maxI != i) {
                        ProcesyTab3[maxI].setFrame(ProcesyTab3[maxI].frame_size + ProcesyTab3[i].frame_size);
                    } else {
                        freeFrames += ProcesyTab3[i].frame_size;
                    }
                    ProcesyTab3[i] = null;
                    size--;
                }

            }
            if (ProcesyTab3[minI] != null && ProcesyTab3[maxI] != null && ProcesyTab3[minI].PFrame != 1
                    &&max>faultsMax ) {
                if (ProcesyTab3[minI].frame_size > 3) {
                    ProcesyTab3[minI].setFrame(ProcesyTab3[minI].frame_size - 1);
                    ProcesyTab3[maxI].setFrame(ProcesyTab3[maxI].frame_size + 1 + freeFrames);
                    freeFrames = 0;
                }
            }
        }

        return faults;
    }

    public int zone(int zone) {
        int faults = 0;
        int freeFrames = frame_size;
        int allDone = -1;
        Proces[] ProcessesTabCopy = new Proces[procesy];
        for (int k = 0; k < procesyTab.length; k++) {
            ProcessesTabCopy[k] = new Proces(procesyTab[k]);
            int frame_size = numberOfDuplications(ProcessesTabCopy[k].proces, zone);
            ProcessesTabCopy[k].setFrame(frame_size);

        }
        do {
            for (int k = allDone + 1; k < procesyTab.length; k++) {
                if (freeFrames > ProcessesTabCopy[k].frame_size) {
                    allDone++;
                    int w = ProcessesTabCopy[k].frame_size;
                    freeFrames-=w;
                    if(ProcessesTabCopy[k].frame_size != 0){
                        int h = LRU(ProcessesTabCopy[k].proces, ProcessesTabCopy[k].frame_size);

                        faults +=h ;
                    }

                }


            }
            freeFrames = frame_size;
        }
        while (allDone != procesy -1);

        return faults;
    }

    public int LRU(ArrayList<Page> PagesRef, int frame_size) {
        faults = 0;
        ArrayList<Page> Pages2 = new ArrayList<>();
        for (Page p : PagesRef) {
            Pages2.add(new Page(p));
        }
        if (Pages2.size() == 0) {
            return 0;
        }
        Page n;
        for (int i = 0; i < Pages2.size(); i++) {
            n = Pages2.get(i);
            loop:
            if (Frame.size() < frame_size) {
                for (Page p : Frame) {
                    if (p.nr == n.nr) {
                        p.setRef(p.ref + 1);
                        break loop;
                    }
                }
                faults++;
                Frame.add(n);
            } else {
                for (Page p : Frame) {
                    if (p.nr == n.nr) {
                        p.setRef(p.ref + 1);
                        break loop;
                    }
                }

                Collections.sort(Frame, Page.refComparator);

                Frame.remove(0);
                Frame.add(n);
                faults++;

            }
        }
        Frame.clear();
        return faults;
    }

    public int numberOfDuplications(ArrayList<Page> a, int zone)   {
        HashSet h = new HashSet();
        if(zone>a.size())
        {
            zone = a.size();
        }
        for(int i =0; i<zone; i++)
        {
            h.add(a.get(i).nr);
        }
        return h.size();
    }

}