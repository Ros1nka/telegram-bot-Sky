package pro.sky.telegrambot.service;

public interface MessageService {
    void sendWelcomeMessage(Long chatId);

    void processReminder(long chatId, String messageText);

    void sendMessage(Long chatId, String text);
}
