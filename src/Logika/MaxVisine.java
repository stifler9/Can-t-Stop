package Logika;

public class MaxVisine {
    public static int MaxVisina(int i) throws Exception {
        if ((i < 2) | (i > 12)) {
            throw new Exception("Izven polja!");
        }

        if (i < 7) {
            return ((i - 1) * 2) + 1;
        } else {
            return ((13 - i) * 2) + 1;
        }
    }
}
