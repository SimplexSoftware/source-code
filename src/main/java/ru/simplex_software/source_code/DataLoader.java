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
import java.util.*;

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

            Meeting meeting;
            Speaker speaker;
            Report report;
            List<Report> reportList;
            Calendar instance;

//            предстоящие новые встречи
//            1-я встреча
            instance = Calendar.getInstance();

            meeting = new Meeting();

            speaker = new Speaker();
            speaker.setSocialId("http://vk.com/dendrito");
            speaker.setFio("Гузиков Денис Сергеевич");
            speakerDAO.saveOrUpdate(speaker);

            reportList = new ArrayList<>();

            report = new Report();
            report.setTitle("Современная WEB-разработка");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Алгоритмы. Олимпиадное программирование");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Основы программирования на языке Java");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            meeting.setReports(reportList);

            instance.add(Calendar.DAY_OF_MONTH, 3);
            meeting.setDate(instance.getTime());

            meetingDAO.saveOrUpdate(meeting);

//            2-я встреча
            meeting = new Meeting();

            speaker = new Speaker();
            speaker.setSocialId("http://vk.com/serglager1212121");
            speaker.setFio("Рылов Сергей Андреевич");
            speakerDAO.saveOrUpdate(speaker);

            reportList = new ArrayList<>();

            report = new Report();
            report.setTitle("Шаблоны и антишаблоны проектирования. Обзор");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Эргономика сайта. Главные законы.");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Аспектно-ориентированное программирование. Подводные камни");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            meeting.setReports(reportList);

            instance.add(Calendar.DAY_OF_MONTH, 5);
            meeting.setDate(instance.getTime());

            meetingDAO.saveOrUpdate(meeting);

//            предыдущие встречи
//            1-я встреча
            instance = Calendar.getInstance();

            meeting = new Meeting();

            speaker = new Speaker();
            speaker.setSocialId("http://vk.com/drjevsky12345");
            speaker.setFio("Ржевский Дмитрий Валерьевич");
            speakerDAO.saveOrUpdate(speaker);

            reportList = new ArrayList<>();

            report = new Report();
            report.setTitle("Spring Core. Понимание основ");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Популярные шаблоны проектирования на практике");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Протокол HTTP - базовые понятия");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            meeting.setReports(reportList);

            instance.add(Calendar.DAY_OF_MONTH, -3);
            meeting.setDate(instance.getTime());

            meetingDAO.saveOrUpdate(meeting);

            //            2-я встреча
            meeting = new Meeting();

            SimpleDateFormat enterDateFormat = new SimpleDateFormat("MM/d/yy", Locale.ENGLISH);
            Date date = enterDateFormat.parse("08/20/2017");
            speaker = new Speaker();
            speaker.setSocialId("http://vk.com/nahov12345");
            speaker.setFio("Нахов Пётр Валерьевич");
            speakerDAO.saveOrUpdate(speaker);

            reportList = new ArrayList<>();

            report = new Report();
            report.setTitle("Как достигается чистый Java-код?");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("От теории к практике - пишем программу для гадания по руке для новичков");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Ajile - вред или польза? Обзор.");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            meeting.setReports(reportList);

            instance.add(Calendar.DAY_OF_MONTH, -5);
            meeting.setDate(instance.getTime());

            meetingDAO.saveOrUpdate(meeting);
        }
    }
}
