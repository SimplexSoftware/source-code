package ru.simplex_software.source_code.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.simplex_software.zkutils.entity.LongIdPersistentEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Доклад.
 */
@Entity
public class Report extends LongIdPersistentEntity implements Serializable{
    private static final Logger LOG = LoggerFactory.getLogger(Report.class);
    @OneToOne
    private Speaker author;
    private String title;

    @ManyToOne
    private Meeting meeting;

    public Speaker getAuthor() {
        return author;
    }

    public void setAuthor(Speaker author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
