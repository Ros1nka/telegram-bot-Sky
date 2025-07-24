package pro.sky.telegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationSchedulerTest {

    @Mock
    private NotificationTaskRepository repository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private NotificationScheduler scheduler;

    @Test
    void checkNotifications_ShouldSendAndMarkAsSend() {
        NotificationTask task = new NotificationTask();
        task.setId(1L);
        task.setChatId(123L);
        task.setText("Тест");
        task.setDateTime(LocalDateTime.now().minusMinutes(1));
        task.setSent(false);

        when(repository.findByDateTimeLessThanEqualAndIsSentFalse(any())).thenReturn(List.of(task));

        scheduler.checkNotifications();

        verify(messageService).sendMessage(123L, "Тест");
        assertTrue(task.isSent());
        verify(repository).save(task);
    }
}
