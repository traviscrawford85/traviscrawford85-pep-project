package Service;

import DAO.MessageDAO;
import DAO.MessageDAOInterface;
import Model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageService {
    private MessageDAOInterface messageDAO;

    public MessageService() {
        this(new MessageDAO());
    }

    public MessageService(MessageDAOInterface messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Optional<Message> createMessage(Message message) {
        if (message.getPosted_by() > 0 && message.getMessage_text() != null && !message.getMessage_text().isEmpty()
                && message.getTime_posted_epoch() > 0) {
            return messageDAO.createMessage(message);
        }
        return Optional.empty();
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Optional<Message> getMessageById(int messageId) {
        if (messageId > 0) {
            return messageDAO.getMessageById(messageId);
        }
        return Optional.empty();
    }

    public boolean deleteMessage(int messageId) {
        if (messageId > 0) {
            return messageDAO.deleteMessageById(messageId);
        }
        return false;
    }

    public Optional<Message> updateMessage(int messageId, String messageText) {
        if (messageId > 0 && messageText != null && !messageText.isEmpty()) {
            return messageDAO.updateMessage(messageId, messageText);
        }
        return Optional.empty();
    }

    public List<Message> getMessagesByUserId(int userId) {
        if (userId > 0) {
            return messageDAO.getMessagesByUserId(userId);
        }
        return new ArrayList<>();
    }
}
