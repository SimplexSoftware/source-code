package ru.simplex_software.source_code.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.simplex_software.zkutils.entity.LongIdPersistentEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Встреча.
 */
@Entity
public class Meeting extends LongIdPersistentEntity {
    private static final Logger LOG = LoggerFactory.getLogger(Meeting.class);
    private Date date = new Date();

    @OneToMany(mappedBy = "meeting")
    private List<Report> reports = new ArrayList<>();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
