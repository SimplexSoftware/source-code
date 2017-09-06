package ru.simplex_software.source_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.simplex_software.source_code.dao.MeetingDAO;
import ru.simplex_software.source_code.dao.ReportDAO;
import ru.simplex_software.source_code.dao.SpeakerDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;
import ru.simplex_software.source_code.model.Speaker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataLoader {

    @Autowired
    ReportDAO reportDAO;

    @Autowired
    SpeakerDAO speakerDAO;

    @Autowired
    MeetingDAO meetingDAO;

    @Transactional
    public void load() throws Exception{
        if (speakerDAO.countAllSpeakers() == 0) {
            Speaker speaker = new Speaker();
            speaker.setSocialId("http://vk.com/dendrito");
            speaker.setFio("Гузиков Денис Сергеевич");
            speakerDAO.saveOrUpdate(speaker);

            List<Report> reportListNew = new ArrayList<>();

            Report report = new Report();
            report.setTitle("Современная WEB-разработка");
            report.setAuthor(speaker);
            reportDAO.saveOrUpdate(report);
            reportListNew.add(report);

            report = new Report();
            report.setTitle("Алгоритмы. Олимпиадное программирование");
            report.setAuthor(speaker);
            reportDAO.saveOrUpdate(report);
            reportListNew.add(report);

            report = new Report();
            report.setTitle("Основы программирования на языке Java");
            report.setAuthor(speaker);
            reportDAO.saveOrUpdate(report);
            reportListNew.add(report);

            Meeting meeting = new Meeting();
            meeting.setReports(reportListNew);
            meetingDAO.saveOrUpdate(meeting);

//            предыдущие встречи
            speaker = new Speaker();
            speaker.setSocialId("http://vk.com/nahov12345");
            speaker.setFio("Нахов Пётр Валерьевич");
            speakerDAO.saveOrUpdate(speaker);

            List<Report> reportListPrevious = new ArrayList<>();

            report = new Report();
            report.setTitle("Spring Core. Понимание основ");
            report.setAuthor(speaker);
            reportDAO.saveOrUpdate(report);
            reportListPrevious.add(report);

            report = new Report();
            report.setTitle("Популярные шаблоны проектирования");
            report.setAuthor(speaker);
            reportDAO.saveOrUpdate(report);
            reportListPrevious.add(report);

            report = new Report();
            report.setTitle("Протокол HTTP - базовые понятия");
            report.setAuthor(speaker);
            reportDAO.saveOrUpdate(report);
            reportListPrevious.add(report);

            meeting = new Meeting();

            SimpleDateFormat enterDateFormat = new SimpleDateFormat("MM/d/yy", Locale.ENGLISH);
            Date date = enterDateFormat.parse("09/20/2017");

            meeting.setDate(date);
            meeting.setReports(reportListPrevious);
            meetingDAO.saveOrUpdate(meeting);
        }
    }
}
