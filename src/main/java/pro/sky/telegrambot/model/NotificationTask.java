package pro.sky.telegrambot.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification_task")
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "notification_text")
    private String text;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "is_sent")
    private boolean isSent;
}
