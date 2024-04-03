import static org.junit.Assert.*;

import org.junit.Test;

public class TicketTest {

    @Test
    public void testAddAttachmentAndGetNumberOfAttachments() {
        Ticket ticket = new Ticket("John Doe", "Issue", "Description");
        Attachment attachment1 = new Attachment();
        Attachment attachment2 = new Attachment();

        ticket.addAttachment(attachment1);
        ticket.addAttachment(attachment2);

        assertEquals(2, ticket.getNumberOfAttachments());
    }

    @Test
    public void testGetAttachment() {
        Ticket ticket = new Ticket("John Doe", "Issue", "Description");
        Attachment attachment = new Attachment();
        attachment.setName("Attachment1");

        ticket.addAttachment(attachment);

        assertEquals(attachment, ticket.getAttachment(0));
    }

    @Test
    public void testGetAllAttachments() {
        Ticket ticket = new Ticket("John Doe", "Issue", "Description");
        Attachment attachment1 = new Attachment();
        Attachment attachment2 = new Attachment();

        ticket.addAttachment(attachment1);
        ticket.addAttachment(attachment2);

        assertEquals(2, ticket.getAllAttachments().size());
    }

    @Test
    public void testTicketConstructor() {
        Ticket ticket = new Ticket("John Doe", "Issue", "Description");

        assertEquals("John Doe", ticket.getCustomerName());
        assertEquals("Issue", ticket.getSubject());
        assertEquals("Description", ticket.getBody());
        assertEquals(0, ticket.getNumberOfAttachments());
    }

    @Test
    public void testAttachmentGetterAndSetter() {
        Attachment attachment = new Attachment();
        attachment.setName("Attachment1");
        attachment.setContents(new byte[]{1, 2, 3});

        assertEquals("Attachment1", attachment.getName());
        assertArrayEquals(new byte[]{1, 2, 3}, attachment.getContents());
    }
}
