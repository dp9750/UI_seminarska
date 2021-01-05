package UI_seminarska;

/** Par števil, ki predstavljata argumente ukaza 'move' */
public class Ukaz {

    private int p, r;

    /**
     * @param p move from
     * @param r move to
     */
    public Ukaz(int p, int r) {
        this.p = p;
        this.r = r;
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", this.p, this.r);
    }
}
