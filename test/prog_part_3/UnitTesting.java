package prog_part_3;

import prog_part_3.Message;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * GROK AI was used to help me fix complex bug/errors in my code,
 * and references were made to the following online resources:
 * - W3Schools Java Tutorial: https://www.w3schools.com/java
 * - freeCodeCamp - Java Unit Testing: https://www.freecodecamp.org/news/java-unit-testing/
 * - GeeksforGeeks Java Programming: https://www.geeksforgeeks.org/java/
 * - Oracle Java Documentation: https://docs.oracle.com/javase/tutorial/
 */

public class UnitTesting {
    Message message = new Message();

    @Test
    public void testAssertEquals() {
        message.sendMessage("MSG001", "+27834557896", "Did you get the cake?", message.createMessageHash("MSG001", "Did you get the cake?"), 1);
        message.sendMessage("MSG002", "0838884567", "It is dinner time!", message.createMessageHash("MSG002", "It is dinner time!"), 1);
        assertEquals("Message successfully sent.", message.sendMessage("MSG003", "+27838884567", "Where are you? You are late! I have asked you to be on time.", message.createMessageHash("MSG003", "Where are you? You are late! I have asked you to be on time."), 1));
        String output = message.printMessages();
        String[] messages = output.split("\n");
        assertTrue(output.contains("Did you get the cake?"));
        assertTrue(output.contains("It is dinner time!"));
        assertTrue(output.contains("Where are you? You are late! I have asked you to be on time."));
    }

    @Test
    public void testRecipientNumberFormat() {
        assertEquals("Message successfully sent.", message.sendMessage("MSG001", "+27718693002", "Hi Mike", message.createMessageHash("MSG001", "Hi Mike"), 1));
        assertEquals("Message successfully sent.", message.sendMessage("MSG002", "+27123456789", "Hi Mike", message.createMessageHash("MSG002", "Hi Mike"), 1));
        assertFalse(message.checkRecipientCell("+27123456")); // Too short
        assertFalse(message.checkRecipientCell("27718693002")); // Missing +
    }

    @Test
    public void testMessageHash() {
        String msg = "Hi Mike can you join us";
        String msgHash = message.createMessageHash("MSG001", msg);
        assertEquals("MS:0:HIUS", msgHash);
    }

    @Test
    public void testMessageIDCreated() {
        String msgId = "MSG001";
        assertTrue(message.checkMessageID(msgId));
        assertEquals("Message ID generated: MSG001", "Message ID generated: " + msgId);
        assertEquals("Message successfully sent.", message.sendMessage(msgId, "+27718693002", "Hi Mike", message.createMessageHash(msgId, "Hi Mike"), 1));
        assertEquals("OK to delete message.", message.sendMessage(msgId, "+27718693002", "Hi Mike", message.createMessageHash(msgId, "Hi Mike"), 2));
        assertEquals("Message successfully stored.", message.sendMessage(msgId, "+27718693002", "Hi Mike", message.createMessageHash(msgId, "Hi Mike"), 3));
    }

    @Test
    public void testDisplayLongestMessage() {
        message.sendMessage("MSG001", "+27834557896", "Did you get the cake?", message.createMessageHash("MSG001", "Did you get the cake?"), 1);
        message.sendMessage("MSG002", "+27838884567", "Where are you? You are late! I have asked you to be on time.", message.createMessageHash("MSG002", "Where are you? You are late! I have asked you to be on time."), 1);
        assertEquals("Longest Message: Where are you? You are late! I have asked you to be on time.", message.displayLongestMessage());
    }

    @Test
    public void testSearchByMessageID() {
        message.sendMessage("MSG001", "+27834557896", "Did you get the cake?", message.createMessageHash("MSG001", "Did you get the cake?"), 1);
        String result = message.searchByMessageID("MSG001");
        assertTrue(result.contains("Did you get the cake?"));
    }

    @Test
    public void testSearchByRecipient() {
        message.sendMessage("MSG001", "+27838884567", "It is dinner time!", message.createMessageHash("MSG001", "It is dinner time!"), 1);
        message.sendMessage("MSG002", "+27838884567", "Where are you? You are late! I have asked you to be on time.", message.createMessageHash("MSG002", "Where are you? You are late! I have asked you to be on time."), 1);
        String result = message.searchByRecipient("+27838884567");
        assertTrue(result.contains("It is dinner time!") && result.contains("Where are you? You are late! I have asked you to be on time."));
    }

    @Test
    public void testDeleteMessageByHash() {
        String msgHash = message.createMessageHash("MSG001", "Test message");
        message.sendMessage("MSG001", "+27718693002", "Test message", msgHash, 1);
        assertEquals("Message with hash " + msgHash + " successfully deleted.", message.deleteMessageByHash(msgHash));
    }
}