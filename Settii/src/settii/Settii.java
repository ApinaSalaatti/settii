package settii;

/**
 *
 * @author ApinaSalaatti
 */
public class Settii {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application app = Application.get();
        if(!app.init()) {
            System.out.println("Error with initialization! :(");
            System.exit(-1);
        }
        app.run();
    }
}
