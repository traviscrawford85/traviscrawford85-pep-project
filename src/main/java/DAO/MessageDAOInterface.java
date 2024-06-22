package DAO;

import java.util.List;
import java.util.Optional;

import Model.Message;

public interface MessageDAOInterface {

    // Create a new message
    Optional<Message> createMessage(Message message);

    // Retrieve all messages
    List<Message> getAllMessages();

    // Retrieve a message by its ID
    Optional<Message> getMessageById(int messageId);

    // Delete a message by its ID
    boolean deleteMessageById(int messageId);

    // Update a message by its ID
    Optional<Message> updateMessage(int messageId, String messageText);

    // Retrieve all messages by a user's ID
    List<Message> getMessagesByUserId(int userId);

}