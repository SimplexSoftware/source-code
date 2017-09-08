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

            Date newDate;
            Calendar instance = Calendar.getInstance();
//            instance.setTime(date); //устанавливаем дату, с которой будет производить операции

            Meeting meeting;
            Speaker speaker;
            Report report;
            List<Report> reportList;


//            предстоящие новые встречи
//            1-я встреча
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
            newDate = instance.getTime();
            meeting.setDate(newDate);

            meetingDAO.saveOrUpdate(meeting);

//            2-я встреча
            meeting = new Meeting();

            speaker = new Speaker();
            speaker.setSocialId("http://vk.com/serglager1212121");
            speaker.setFio("Рылов Сергей Андреевич");
            speakerDAO.saveOrUpdate(speaker);

            reportList = new ArrayList<>();

            report = new Report();
            report.setTitle("Современная разработка на LoveView");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Квадрокоптеры. Полёты во сне и наяву");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Профессиональное фото без границ");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            meeting.setReports(reportList);

            instance.add(Calendar.DAY_OF_MONTH, 5);
            newDate = instance.getTime();
            meeting.setDate(newDate);

            meetingDAO.saveOrUpdate(meeting);

//            предыдущие встречи
//            1-я встреча
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
            report.setTitle("Популярные шаблоны проектирования");
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
            newDate = instance.getTime();
            meeting.setDate(newDate);

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
            report.setTitle("Как надо ремонтировать автомобили");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Гадание по руке для девушек");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            report = new Report();
            report.setTitle("Обсуждение клипа 'Злости нет!'");
            report.setAuthor(speaker);
            report.setMeeting(meeting);
            reportDAO.saveOrUpdate(report);
            reportList.add(report);

            meeting.setReports(reportList);

            instance.add(Calendar.DAY_OF_MONTH, -5);
            newDate = instance.getTime();
            meeting.setDate(newDate);

            meetingDAO.saveOrUpdate(meeting);
        }
    }
}
