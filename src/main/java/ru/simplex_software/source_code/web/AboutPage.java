package ru.simplex_software.source_code.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.dao.MeetingDAO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AboutPage extends SuperPage {

    static final SimpleDateFormat enterDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    @SpringBean
    private MeetingDAO meetingDAO;

    public AboutPage() {
        Date date = meetingDAO.findNearestMeetingDate(new Date());

        add(new Label("nearestMeetingDate", date==null? "":enterDateFormat.format(date)));
    }
}
