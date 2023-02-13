package unite;

public enum Unite {
    LITRE(1),
    GRAMME(2),
    KG(3),
    BOUTEILLE(4);

    private int val = 1;

    private Unite(int v) {
        val = v;
    }

    public int getVal() {
        return val;
    }
}
