package pro.sky.telegrambot.service.Impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private NotificationTaskRepository repository;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    void sendWelcomeMessage_ShouldSendCorrectText() {
        Long chatId = 123L;
        ArgumentCaptor<SendMessage> messageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(mock(SendResponse.class));

        messageService.sendWelcomeMessage(chatId);

        verify(telegramBot).execute(messageCaptor.capture());
        SendMessage actual = messageCaptor.getValue();
        assertEquals(chatId, actual.getParameters().get("chat_id"));
        assertTrue(actual.getParameters().get("text").toString().contains("Привет!"));
    }

    @Test
    void processReminder_ShouldSaveTask() {
         Long chatId = 123L;
         String valideMessage = "24.05.2025 12:00 Тестовое напоминание";

         NotificationTask savedTask = new NotificationTask();

         when(repository.save(any(NotificationTask.class))).thenReturn(savedTask);

         messageService.processReminder(chatId, valideMessage);

         verify(repository).save(any(NotificationTask.class));
    }

    @Test
    void processReminder_ShouldNotSave() {
        // Given
        Long chatId = 123L;
        String invalidMessage = "неправильный формат";

        messageService.processReminder(chatId, invalidMessage);

        verify(repository, never()).save(any());
    }
}
