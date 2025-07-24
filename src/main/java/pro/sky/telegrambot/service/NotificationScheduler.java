package pro.sky.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class NotificationScheduler {

    @Autowired
    private NotificationTaskRepository repository;

    @Autowired
    private MessageService messageService;

    @Scheduled(cron = "0 0/1 * * * *")
    public void checkNotifications() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        List<NotificationTask> tasks = repository.findByDateTimeLessThanEqualAndIsSentFalse(now);

        tasks.forEach(task -> {
            messageService.sendMessage(task.getChatId(), task.getText());
            task.setSent(true);
            repository.save(task);
        });
    }
}
