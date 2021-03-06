package ru.simplex_software.source_code.model;

import net.sf.autodao.PersistentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Докладчик.
 */
@Entity
public class Speaker implements PersistentEntity<String>{
    private static final Logger LOG = LoggerFactory.getLogger(Speaker.class);

    @Id
    private String socialId;
    private String fio;
    private String email;
    private double raiting;
    private boolean subscriber;

    @Override
    public String getPrimaryKey() {
        return socialId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speaker speaker = (Speaker) o;
        return socialId.equals(speaker.socialId);
    }

    @Override
    public int hashCode() {
        return socialId.hashCode();
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public double getRaiting() {
        return raiting;
    }

    public void setRaiting(double raiting) {
        this.raiting = raiting;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSubscriber() {
        return subscriber;
    }

    public void setSubscriber(boolean subscriber) {
        this.subscriber = subscriber;
    }
}
