package pro.sky.telegrambot.service.Impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.MessageService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final TelegramBot telegramBot;

    private final NotificationTaskRepository notificationTaskRepository;

    @Override
    public void sendWelcomeMessage(Long chatId) {

        String text = "Привет! Отправь напоминание в формате: 01.01.2022 20:00 Сделать домашнюю работу";
        sendMessage(chatId, text);
    }

    @Override
    public void processReminder(long chatId, String messageText) {

        Pattern reminderPattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})\\s+(.+)");
        Matcher matcher = reminderPattern.matcher(messageText);

        if (matcher.matches()) {
            String dateTimeStr = matcher.group(1);
            String reminderText = matcher.group(2);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

            saveReminder(chatId, dateTime, reminderText);
            sendMessage(chatId, "Напоминание создано успешно!");
        } else {
            sendMessage(chatId, "Неверный формат напоминания. Пример: 01.01.2022 20:00 Сделать домашнюю работу");
        }
    }

    private void saveReminder(long chatId, LocalDateTime dateTime, String text) {
        NotificationTask task = new NotificationTask();

        task.setChatId(chatId);
        task.setDateTime(dateTime);
        task.setText(text);
        notificationTaskRepository.save(task);
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        telegramBot.execute(sendMessage);
    }
}
