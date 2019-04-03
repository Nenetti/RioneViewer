package entities;


public class Step {

    private int[] xs;
    private int[] ys;

    private int[] shortXs;
    private int[] shortYs;

    private int resize = 20;

    public Step (int[] steps) {
        int length = steps.length / 2;
        int[] xs = new int[length];
        int[] ys = new int[length];
        for (int i = 0; i < length; i++) {
            xs[i] = steps[i * 2];
            ys[i] = steps[i * 2 + 1];
        }
        this.xs = xs;
        this.ys = ys;

        int cycle = length / resize;
        int[] shortXs = new int[resize];
        int[] shortYs = new int[resize];
        for (int i = 0; i < resize; i++) {
            shortXs[i] = xs[i * cycle];
            shortYs[i] = ys[i * cycle + 1];
        }
        shortXs[resize - 1] = xs[xs.length - 1];
        shortYs[resize - 1] = ys[ys.length - 1];
        this.shortXs = shortXs;
        this.shortYs = shortYs;
    }


    public int[] getXs () {
        return xs;
    }

    public int[] getYs () {
        return ys;
    }

    public int[] getShortXs () {
        return shortXs;
    }

    public int[] getShortYs () {
        return shortYs;
    }

    public int getX (double t) {
        return xs[(int) (xs.length * t)];
    }

    public int getY (double t) {
        return ys[(int) (ys.length * t)];
    }
}
