package prog_part_3;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private List<String> messagesSent = new ArrayList<>();
    private List<String> disregardedMessages = new ArrayList<>();
    private List<String> storedMessages = new ArrayList<>();
    private List<String> messageHashes = new ArrayList<>();
    private List<String> messageIDs = new ArrayList<>();
    private int totalMessages = 0;

    private static int messageCounter = 0;

    public boolean checkMessageID(String msgId) {
        return msgId.length() <= 10;
    }

    public boolean checkRecipientCell(String recipient) {
        return recipient.startsWith("+27") && recipient.length() <= 12 && recipient.substring(3).matches("\\d{9,10}");
    }

    public String generateMessageID() {
        return "MSG" + String.format("%03d", messageCounter++);
    }

    public String createMessageHash(String msgId, String message) {
        String[] words = message.split("\\s+");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "";
        String lastWord = words.length > 0 ? words[words.length - 1].toUpperCase() : "";
        String hash = msgId.substring(0, 2) + ":0:" + firstWord + lastWord;
        messageHashes.add(hash);
        return hash;
    }

    public String sendMessage(String msgId, String recipient, String message, String msgHash, int action) {
        if (message.length() > 250) {
            return "Message exceeds 250 characters by " + (message.length() - 250) + ", please reduce size.";
        }
        totalMessages++;
        messageIDs.add(msgId);
        String messageDetails = "MessageID: " + msgId + ", Message Hash: " + msgHash + ", Recipient: " + recipient + ", Message: " + message;
        switch (action) {
            case 1:
                messagesSent.add(messageDetails);
                return "Message successfully sent.";
            case 2:
                disregardedMessages.add(messageDetails);
                return "OK to delete message.";
            case 3:
                storedMessages.add(messageDetails);
                return storeMessage(messageDetails);
            default:
                return "Invalid option.";
        }
    }

    public String storeMessage(String message) {
        return "Message successfully stored.";
    }

    public String printMessages() {
        return String.join("\n", messagesSent);
    }

    public int returnTotalMessages() {
        return totalMessages;
    }

    public String displaySenderRecipient() {
        StringBuilder result = new StringBuilder();
        for (String msg : messagesSent) {
            String[] parts = msg.split(", ");
            for (String part : parts) {
                if (part.startsWith("Recipient: ")) {
                    result.append(part).append("\n");
                }
            }
        }
        return result.toString();
    }

    public String displayLongestMessage() {
        String longest = "";
        for (String msg : messagesSent) {
            String[] parts = msg.split(", ");
            for (String part : parts) {
                if (part.startsWith("Message: ")) {
                    String currentMsg = part.substring(9);
                    if (currentMsg.length() > longest.length()) {
                        longest = currentMsg;
                    }
                }
            }
        }
        return longest.isEmpty() ? "" : "Longest Message: " + longest;
    }

    public String searchByMessageID(String msgId) {
        for (String msg : messagesSent) {
            if (msg.contains("MessageID: " + msgId)) {
                return msg;
            }
        }
        return "";
    }

    public String searchByRecipient(String recipient) {
        StringBuilder result = new StringBuilder();
        for (String msg : messagesSent) {
            if (msg.contains("Recipient: " + recipient)) {
                result.append(msg).append("\n");
            }
        }
        return result.toString().trim();
    }

    public String deleteMessageByHash(String msgHash) {
        for (int i = 0; i < messagesSent.size(); i++) {
            if (messagesSent.get(i).contains("Message Hash: " + msgHash)) {
                messagesSent.remove(i);
                return "Message with hash " + msgHash + " successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    public String displayReport() {
        if (messagesSent.isEmpty()) return "";
        StringBuilder report = new StringBuilder("==== Sent Messages Report ====\n");
        report.append(String.format("%-12s | %-20s | %-15s | %-50s\n", "Message ID", "Hash", "Recipient", "Message"));
        report.append("---------------------------------------------------------------------------------------------\n");
        for (String msg : messagesSent) {
            String[] parts = msg.split(", ");
            String id = parts[0].split(": ")[1];
            String hash = parts[1].split(": ")[1];
            String recipient = parts[2].split(": ")[1];
            String message = parts[3].split(": ")[1];
            report.append(String.format("%-12s | %-20s | %-15s | %-50s\n", id, hash, recipient, message));
        }
        return report.toString().trim();
    }
}
