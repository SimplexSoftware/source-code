package ru.simplex_software.source_code.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.simplex_software.zkutils.entity.LongIdPersistentEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Доклад.
 */
@Entity
public class Report extends LongIdPersistentEntity{
    private static final Logger LOG = LoggerFactory.getLogger(Report.class);
    @OneToOne
    private Speaker author;
    private String title;
    @ElementCollection
    @CollectionTable
    @Column
    @MapKeyJoinColumn
    private Map<Speaker,Boolean> whoLikedIt = new HashMap<>();

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

    public Map<Speaker, Boolean> getWhoLikedIt() {
        return whoLikedIt;
    }

    public void setWhoLikedIt(Map<Speaker, Boolean> whoLikedIt) {
        this.whoLikedIt = whoLikedIt;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
