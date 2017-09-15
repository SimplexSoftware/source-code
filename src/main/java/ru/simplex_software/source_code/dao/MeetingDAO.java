package ru.simplex_software.source_code.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import net.sf.autodao.Named;
import ru.simplex_software.source_code.model.Meeting;

import java.util.Date;
import java.util.List;

@AutoDAO
public interface MeetingDAO extends Dao<Meeting, Long>{
    @Finder(query = "FROM Meeting WHERE date >= :date ORDER by date")
    List<Meeting> findNewMeeting(@Named("date")Date date);

    @Finder(query = "SELECT count(m) FROM  Meeting m WHERE date<:date")
    long getCountPastMetting(@Named("date") Date date);

    @Finder(query = " FROM Meeting WHERE date<:date ORDER BY date DESC")
    List<Meeting> findPastMeeting(@Named("date") Date date);

    @Finder(query = "SELECT min(date) FROM Meeting WHERE date >= :date")
    Date findNearestMeetingDate(@Named("date") Date date);
}
