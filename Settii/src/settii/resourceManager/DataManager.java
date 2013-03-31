package settii.resourceManager;

import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author Merioksan Mikko
 */
public class DataManager {
    private HashMap<String, NodeList> data;
    
    public DataManager() {
        data = new HashMap<String, NodeList>();
    }
    
    public NodeList getData(String resource) {
        if(!data.containsKey(resource)) {
            loadData(resource);
        }
        
        return data.get(resource);
    }
    
    public void loadData(String resource) {
        File xmlFile = new File(resource);
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            
            Node root = doc.getDocumentElement();
            data.put(resource, root.getChildNodes());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void preload(String root) {
        File file = new File(root);
        File[] files = file.listFiles();
        for(File f : files) {
            if(f.isDirectory()) {
                preload(f.getPath());
            }
            else if(f.isFile()) {
                getData(f.getPath());
            }
        }
    }
}
