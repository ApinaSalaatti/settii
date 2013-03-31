package settii.logic.messaging;

/**
 *
 * @author Merioksan Mikko
 */
public class Message {
    private String topic;
    private String content;
    private boolean priority;
    private long delay;
    
    public Message(String topic, String content) {
        this(topic, content, false);
    }
    public Message(String topic, String content, boolean priority) {
        this.topic = topic;
        this.content = content;
        this.delay = 100 * (content.length() + topic.length());
        if(this.delay > 8000) {
            this.delay = 8000;
        }
        this.priority = priority;
    }
    
    public String getTopic() {
        return topic;
    }
    public String getContent() {
        return content;
    }
    
    public void update(long deltaMs) {
        delay -= deltaMs;
    }
    
    public boolean finished() {
        return delay <= 0;
    }
    public long timeLeft() {
        return delay;
    }
    
    public boolean priority() {
        return priority;
    }
}
