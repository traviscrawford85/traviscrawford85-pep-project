package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;
import java.util.Optional;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

public class SocialMediaController {
    private MessageService messageService;
    private AccountService accountService;

    // Constructor with service dependencies
    public SocialMediaController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService = accountService;
    }

    // No-argument constructor initializing with default services
    public SocialMediaController() {
        this(new MessageService(), new AccountService());
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.get("example-endpoint", this::exampleHandler);
        
        // Register the new endpoints
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        if (account.getUsername() == null || account.getUsername().isBlank() ||
            account.getPassword() == null || account.getPassword().length() < 4) {
            ctx.status(400);
            return;
        }
        Optional<Account> registeredAccount = accountService.registerAccount(account);
        if (registeredAccount.isPresent()) {
            ctx.json(registeredAccount.get());
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Optional<Account> loggedInAccount = accountService.loginAccount(account.getUsername(), account.getPassword());
        if (loggedInAccount.isPresent()) {
            ctx.json(loggedInAccount.get());
            ctx.status(200);
        } else {
            ctx.status(401);
        }
    }

    private void createMessageHandler(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Optional<Message> createdMessage = messageService.createMessage(message);
        if (createdMessage.isPresent()) {
            ctx.json(createdMessage.get());
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }

    private void getMessageByIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Optional<Message> message = messageService.getMessageById(messageId);
        if (message.isPresent()) {
            ctx.json(message.get());
        } else {
            ctx.result(""); // Return an empty response body
        }
        ctx.status(200); // Always return status 200
    }

    private void deleteMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        boolean isDeleted = messageService.deleteMessage(messageId);
        ctx.result(""); // Return an empty response body
        ctx.status(200); // Always return status 200
    }

    private void updateMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = ctx.bodyAsClass(Message.class);
        Optional<Message> updatedMessage = messageService.updateMessage(messageId, message.getMessage_text());
        if (updatedMessage.isPresent()) {
            ctx.json(updatedMessage.get());
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void getMessagesByUserHandler(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUserId(userId);
        ctx.json(messages);
        ctx.status(200);
    }
}
