package ru.simplex_software.source_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.simplex_software.source_code.dao.MeetingDAO;
import ru.simplex_software.source_code.dao.ReportDAO;
import ru.simplex_software.source_code.dao.SpeakerDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;
import ru.simplex_software.source_code.model.Speaker;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    @Autowired
    ReportDAO reportDAO;

    @Autowired
    SpeakerDAO speakerDAO;

    @Autowired
    MeetingDAO meetingDAO;

    @Transactional
    public void load() {
        if (speakerDAO.countAllSpeakers() == 0) {
            Speaker speaker = new Speaker();
            speaker.setSocialId("http://vk.com/dendrito");
            speaker.setFio("Нахов Пётр Валерьевич");
            speakerDAO.saveOrUpdate(speaker);

            List<Report> reportList = new ArrayList<>();

            Report report = new Report();
            report.setTitle("Современная WEB-разработка");
            report.setAuthor(speaker);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Алгоритмы. Олимпиадное программирование");
            report.setAuthor(speaker);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Основы программирования на языке Java");
            report.setAuthor(speaker);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            Meeting meeting = new Meeting();
            meeting.setReports(reportList);
            meetingDAO.saveOrUpdate(meeting);
        }
    }
}
