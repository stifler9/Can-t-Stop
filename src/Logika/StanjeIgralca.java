package Logika;

public class StanjeIgralca {
    public int[] visine;

    public StanjeIgralca() {
        visine = new int[11];
        for (int i = 0; i < 11; i++) {
            visine[i] = 0;
        }
    }

    public boolean premakni(int polje) throws Exception {
        if (naVrhu(polje)) {
            return false;
        }
        visine[polje - 2]++;
        return true;
    }

    protected boolean naVrhu(int polje) throws Exception {
        return (visine[polje - 2] == MaxVisine.MaxVisina(polje));
    }

    public void vrziDol(int polje) {
        visine[polje - 2] = 0;
    }
}