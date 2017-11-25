package ru.simplex_software.source_code.mailService;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import ru.simplex_software.source_code.dao.MeetingDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;
import ru.simplex_software.source_code.model.Speaker;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class MailSender implements InitializingBean{
    private static final Logger LOG = LoggerFactory.getLogger(MailSender.class);

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private MeetingDAO meetingDAO;

    @Autowired
    private JavaMailSender meetingMailSender;

    @Value("${smtp.from}") @Resource
    String from;


    @Transactional
    public void sendEvent(){

        Date nextDay = new Date();
        long plusOneDay = nextDay.getTime() + 1000 * 60 * 60 * 24;
        nextDay.setTime(plusOneDay);

        List<Meeting> newMeetingList = meetingDAO.findFutureMeeting(new Date(), nextDay);

        for (Meeting meeting : newMeetingList) {

            meeting.setWasNotification(true);

            List<Speaker> authorList = meeting.getReports().stream()
                    .map(Report::getAuthor)
                    .filter(Speaker::isSubscriber)
                    .collect(Collectors.toList());

            for (Speaker author : authorList) {
                MimeMessage mimeMessage = meetingMailSender.createMimeMessage();
                try {
                    mimeMessage.setFrom(from);
                    mimeMessage.setSubject("Встрача участников source-code", "UTF-8");
                    mimeMessage.setRecipients(Message.RecipientType.TO, author.getEmail());
                    mimeMessage.setText(getTemplate(meeting.getDate()), "UTF-8", "HTML");
                    meetingMailSender.send(mimeMessage);
                    LOG.debug("Bee send!");
                }catch (MessagingException e) {
                    LOG.error(e.getMessage());
                }

            }
            meetingDAO.saveOrUpdate(meeting);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties properties = new Properties();
        properties.put("resource.loader", "class");
        properties.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init(properties);
    }

    private String getTemplate(Date date) {
        Template template;
        try {
            template = velocityEngine.getTemplate("emailTemplate/email.vm", "UTF-8");

            VelocityContext context = new VelocityContext();
            context.put("date", date);

            StringWriter stringWriter = new StringWriter();

            template.merge(context, stringWriter);

            return stringWriter.toString();

        }catch (Exception e){
            LOG.error(e.getMessage());
            return null;
        }
    }

}
