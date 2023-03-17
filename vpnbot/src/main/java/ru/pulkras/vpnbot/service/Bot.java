package ru.pulkras.vpnbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.pulkras.vpnbot.config.BotConfig;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

//    @Autowired
//    private UserRepository userRepository;
    final BotConfig config;

    static final String HELPING_TEXT = "this bot was created to use vpn\n" +
            "you can use a menu to launch some commands or do it manually. \n" +
            "you can press /start to get a welcome message\n" +
            "you can press /help to see this message))\n" +
            "you can press /settings to see current settings or set yours\n" +
            "or you can just write author to https://t.me/pulkras";

    public Bot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/help", "explaining how to use this bot"));
        listOfCommands.add(new BotCommand("/settings", "see settings or set your preferences"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch(TelegramApiException tae) {
            log.error("Error setting bot's command list: " + tae.getMessage());
        }
    }

    @Override
    public String getBotToken() {
        return config.getKey();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch(messageText) {
                case "/start":
//                    userRegistration(update.getMessage());
                    reactionToStartCommand(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    sendMessage(chatId, HELPING_TEXT);
                    break;
                default:
                    sendMessage(chatId, "Sorry, command was not recognized");
            }
        }
    }

//    private void userRegistration(Message message) {
//        if (userRepository.findById(message.getChatId()).isEmpty()) {
//            Long chatId = message.getChatId();
//
//            Chat chat = message.getChat();
//
//            User user = new User();
//
//            user.setChatId(chatId);
//            user.setFirstName(chat.getFirstName());
//            user.setSurname(chat.getLastName());
//            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
//
//            userRepository.save(user);
//
//            log.info("user saved: " + user);
//        }
//    }
    private void reactionToStartCommand(long chatId, String name) {

        String answer = "Hello, " + name + " and welcome to our vpn telegram bot!\n" +
                "now we are working on it. we hope we finish soon!";

        log.info("replied to user " + name);

        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch(TelegramApiException tae) {
            log.error("Error occurrred. " + tae.getMessage());
        }
    }
}
