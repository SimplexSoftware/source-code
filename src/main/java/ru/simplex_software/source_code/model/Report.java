package ru.simplex_software.source_code.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.simplex_software.zkutils.entity.LongIdPersistentEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Доклад.
 */
@Entity
public class Report extends LongIdPersistentEntity {
    private static final Logger LOG = LoggerFactory.getLogger(Report.class);
    @OneToOne
    private Speaker author;
    private String title;
    @Column
    private int likeCounter = 0;
    private int dislike = 0;
    @ManyToMany
    private Set<Speaker> setLikeSpeaker = new HashSet<>();

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

    public void setLikeCounter(int likeCounter){
        this.likeCounter=likeCounter;
    }

    public int getLikeCounter() {
        return likeCounter;
    }

    public void setDislike(int dlike) {
        this.dislike = dlike;
    }

    public int getDislike() {
        return dislike;
    }

    public Set<Speaker> getSetLikeSpeaker() {
        return setLikeSpeaker;
    }

    public void setSetLikeSpeaker(Set<Speaker> setLikeSpeaker) {
        this.setLikeSpeaker = setLikeSpeaker;
    }
}
