package paradigms.clase8;

public class Reader {
    public static void read(Integer value) {
        try {
            System.out.printf("Leyendo valor: %s\n", value);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
