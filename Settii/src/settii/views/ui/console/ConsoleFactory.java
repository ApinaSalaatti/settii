package settii.views.ui.console;

/**
 *
 * @author Merioksan Mikko
 */
public class ConsoleFactory {
    private static Console c = null;
    
    public static Console create() {
        if(c == null) {
            c = new Console();
            c.addScreenItem(new TextFieldInput());
        }
        
        return c;
    }
    
    public static Console get() {
        return c;
    }
}
