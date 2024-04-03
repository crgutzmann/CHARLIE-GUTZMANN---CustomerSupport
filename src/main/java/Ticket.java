import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ticket {
    private String customerName;
    private String subject;
    private String body;
    private Map<Integer, Attachment> attachments;

    public Ticket() {
        this.attachments = new HashMap<>();
    }

    public Ticket(String customerName, String subject, String body) {
        this.customerName = customerName;
        this.subject = subject;
        this.body = body;
        this.attachments = new HashMap<>();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addAttachment(Attachment attachment) {
        int index = attachments.size();
        attachments.put(index, attachment);
    }

    public int getNumberOfAttachments() {
        return attachments.size();
    }

    public Attachment getAttachment(int index) {
        return attachments.get(index);
    }

    public List<Attachment> getAllAttachments() {
        return new ArrayList<>(attachments.values());
    }
}
