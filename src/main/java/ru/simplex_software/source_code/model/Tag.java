package ru.simplex_software.source_code.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.simplex_software.entity.LongIdPersistentEntity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyJoinColumn;
import java.util.HashMap;
import java.util.Map;

/**
 * Interesting tag.
 */
@Entity
public class Tag extends LongIdPersistentEntity {
    private static final Logger LOG = LoggerFactory.getLogger(Tag.class);

    private String name;
    private int rating = 0;

    @ElementCollection
    @CollectionTable
    @Column
    @MapKeyJoinColumn
    private Map<Speaker, Boolean> whoLikedIt = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Map<Speaker, Boolean> getWhoLikedIt() {
        return whoLikedIt;
    }

    public void setWhoLikedIt(Map<Speaker, Boolean> whoLikedIt) {
        this.whoLikedIt = whoLikedIt;
    }
}
