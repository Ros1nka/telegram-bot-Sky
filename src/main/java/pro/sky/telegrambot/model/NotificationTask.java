package pro.sky.telegrambot.model;

import jdk.jfr.Enabled;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class NotificationTask {

    LocalDateTime dateTime;
}
