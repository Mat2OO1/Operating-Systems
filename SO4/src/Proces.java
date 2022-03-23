import java.util.ArrayList;
import java.util.Collections;

public class Proces {

    ArrayList proces;
    int PFrame;
    int PPF = 0;
    int frame_size;
    ArrayList<Page> Frame = new ArrayList<Page>();
    public Proces(ArrayList proces, int PFrame)
    {
        this.PFrame = PFrame;
        this.proces = proces;
        this.PPF = PPF;

    }
    public Proces(Proces p)
    {
        this.proces = p.proces;
        this.PFrame = p.PFrame;
    }
    public void setFrame(int frame) {
        frame_size = frame;
    }

    public void setPPF(int PPF) {
        this.PPF = PPF;
    }

    public void setProces(ArrayList proces) {
        this.proces = proces;
    }

    public int PF = 0;
    public int LRU(ArrayList<Page> PageReferences ) {
        int PF = 0;
        ArrayList<Page> Pages2 = new ArrayList<>();
        for (Page p : PageReferences) {
            Pages2.add(new Page(p));
        }

        Page n;

        n = Pages2.get(0);
        loop:
        if (Frame.size() < frame_size) {
            for (Page p : Frame) {
                if (p.nr == n.nr) {
                    p.setRef(p.ref + 1);
                    break loop;
                }
            }
            PF++;
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
            PF++;

        }
        setPPF(PPF+PF);
        return  PF;
    }
}