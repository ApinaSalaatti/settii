package settii;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 *
 * @author Merioksan Mikko
 */
public class HighScores {
    public static ArrayList<Score> get() {
        try {
            String data = "action=" + URLEncoder.encode("get", "UTF-8");
            String result = connect(data);
            return createScores(result);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static void send(String name, int score) {
        try {
            String data = "action=" + URLEncoder.encode("send", "UTF-8") + "&name=" + URLEncoder.encode(name, "UTF-8") + "&score=" + URLEncoder.encode(score+"", "UTF-8");
            String result = connect(data);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String connect(String data) {
        try {
            URL url = new URL("http://merioksa.users.cs.helsinki.fi/cockblocker/scores.php");
            String result = "";
            
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
            dataOut.writeBytes(data);
            dataOut.flush();
            dataOut.close();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while((line = in.readLine()) != null) {
                result+= line;
            }
            in.close();
            
            return result;
            
        } catch(Exception e) {
            return null;
        }
        
    }
    
    private static ArrayList<Score> createScores(String scores) {
        if(scores == null) {
            return null;
        }
        
        ArrayList<Score> list = new ArrayList<Score>();
        String[] scoreList = scores.split(";");
        
        for(String s : scoreList) {
            String[] s2 = s.split(":");
            Score score = new Score(s2[0], s2[1]);
            list.add(score);
        }
        
        return list;
    }
}
