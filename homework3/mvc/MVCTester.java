public class MVCTester {

    public static void main(String[] args) {
        Model m = new Model();
        View v = new View(m);
        m.attach(v);
    }
}
