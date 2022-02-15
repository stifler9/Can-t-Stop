import UI.Okno;

public class App {
    public static void main(String[] args) throws Exception{
        int igralcev = 2;

        Okno okno = new Okno(igralcev);
        okno.toFront();
    }
}
