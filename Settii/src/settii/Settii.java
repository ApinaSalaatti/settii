/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
        // TODO code application logic here
        Application app = Application.get();
        if(!app.init()) {
            System.out.println("Error with initialization!");
            System.exit(-1);
        }
        app.run();
    }
}
