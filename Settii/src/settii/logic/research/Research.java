package settii.logic.research;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import settii.eventManager.events.researchEvents.*;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
/**
 *
 * @author Merioksan Mikko
 */
public class Research {
    private HashMap<String, ResearchItem> allResearch;
    private ArrayList<ResearchItem> treeRoots;
    
    public Research() {
        allResearch = new HashMap<String, ResearchItem>();
        treeRoots = new ArrayList<ResearchItem>();
    }
    
    private void createTree() {
        /*
        ResearchItem ri1 = new ResearchItem(
                "Cannon update #1",
                "Increases the basic cannons\ndamage by 10",
                new UpdateDamageEvent("assets/data/actors/cannon.xml", 10),
                "assets/graphics/research/research.png",
                1000
        );
        
        ResearchItem ri2 = new ResearchItem(
                "Cannon update #2",
                "Further increases the basic\ncannons damage by 10",
                new UpdateDamageEvent("assets/data/actors/cannon.xml", 10),
                "assets/graphics/research/research.png",
                1000
        );
        
        ResearchItem ri3 = new ResearchItem(
                "Cannon update #3",
                "Even further increases the\nbasic cannons damage by 10",
                new UpdateDamageEvent("assets/data/actors/cannon.xml", 10),
                "assets/graphics/research/research.png",
                1000
        );
        
        ResearchItem ri4 = new ResearchItem(
                "Cannon update #4",
                "Finally, increases the basic\ncannons damage once more by 10",
                new UpdateDamageEvent("assets/data/actors/cannon.xml", 10),
                "assets/graphics/research/research.png",
                1000
        );
        
        ri1.addFollower(ri2);
        ri2.addFollower(ri3);
        ri2.addFollower(ri4);
        
        allResearch.put(ri1.getName(), ri1);
        allResearch.put(ri2.getName(), ri2);
        
        treeRoots.add(ri1);
        */
    }
    
    public Collection<ResearchItem> getRoots() {
        return treeRoots;
    }
    
    public void createFromXML(NodeList research) {
        Node n = research.item(0);
        while(n != null) {
            if(n.getNodeType() == Node.ELEMENT_NODE) {
                if(n.getNodeName().equalsIgnoreCase("ResearchItem")) {
                    createResearchItem(n);
                }
                else if(n.getNodeName().equalsIgnoreCase("ResearchTree")) {
                    createTreeRoot(n);
                }
            }
            n = n.getNextSibling();
        }
    }
    
    public void createResearchItem(Node item) {
        String name = "";
        String description = "";
        ResearchEvent event = null;
        String sprite = null;
        int cost = 0;
        
        Node attr = item.getFirstChild();
        while(attr != null) {
            if(attr.getNodeType() == Node.ELEMENT_NODE) {
                if(attr.getNodeName().equalsIgnoreCase("Name")) {
                    name = attr.getFirstChild().getNodeValue();
                }
                else if(attr.getNodeName().equalsIgnoreCase("Description")) {
                    description = attr.getFirstChild().getNodeValue();
                }
                else if(attr.getNodeName().equalsIgnoreCase("Event")) {
                    event = createEvent(attr);
                }
                else if(attr.getNodeName().equalsIgnoreCase("Sprite")) {
                    sprite = attr.getFirstChild().getNodeValue();
                }
                else if(attr.getNodeName().equalsIgnoreCase("Cost")) {
                    cost = Integer.parseInt(attr.getFirstChild().getNodeValue());
                }
            }
            attr = attr.getNextSibling();
        }
        
        allResearch.put(name, new ResearchItem(name, description, event, sprite, cost));
    }
    
    public void createTreeRoot(Node n) {
        Node root = n.getFirstChild();
        while(root != null) {
            if(root.getNodeType() == Node.ELEMENT_NODE) {
                ResearchItem rootItem = allResearch.get(root.getAttributes().item(0).getNodeValue());
                treeRoots.add(rootItem);
                Node child = root.getFirstChild();
                while(child != null) {
                    if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("Item")) {
                        rootItem.addFollower(allResearch.get(child.getAttributes().item(0).getNodeValue()));
                        createTree(child);
                    }
                    child = child.getNextSibling();
                }
            }
            root = root.getNextSibling();
        }
    }
    
    public void createTree(Node root) {
        if(root.getNodeType() == Node.ELEMENT_NODE) {
            ResearchItem rootItem = allResearch.get(root.getAttributes().item(0).getNodeValue());
            Node child = root.getFirstChild();
            while(child != null) {
                if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("Item")) {
                    rootItem.addFollower(allResearch.get(child.getAttributes().item(0).getNodeValue()));
                    createTree(child);
                }
                child = child.getNextSibling();
            }
        }
    }
    
    public ResearchEvent createEvent(Node e) {
        String name = "";
        String res = "";
        int effect = 0;
        
        Node attr = e.getFirstChild();
        while(attr != null) {
            if(attr.getNodeType() == Node.ELEMENT_NODE) {
                if(attr.getNodeName().equalsIgnoreCase("Name")) {
                    name = attr.getFirstChild().getNodeValue();
                }
                else if(attr.getNodeName().equalsIgnoreCase("Resource")) {
                    res = attr.getFirstChild().getNodeValue();
                }
                else if(attr.getNodeName().equalsIgnoreCase("Effect")) {
                    effect = Integer.parseInt(attr.getFirstChild().getNodeValue());
                }
            }
            attr = attr.getNextSibling();
        }
        
        if(name.equalsIgnoreCase("Update Damage")) {
            return new UpdateDamageEvent(res, effect);
        }
        
        return null;
    }
}
