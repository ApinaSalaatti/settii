package settii;

/**
 *
 * @author Merioksan Mikko
 */
public class Score {
    private String name;
    private String score;
    
    public Score(String n, String s) {
        name = n;
        score = s;
    }
    
    @Override
    public String toString() {
        return name + " " + score;
    }
}
