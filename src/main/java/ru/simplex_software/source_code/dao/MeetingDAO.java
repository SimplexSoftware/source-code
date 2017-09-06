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
    @Finder(query = "select count(m) from  Meeting m where date<:date")
    long getCountPastMetting(@Named("date") Date date);

    @Finder(query = " from  Meeting where date<:date")
    List<Meeting> findPastMeeting(@Named("date") Date date);

    @Finder(query = "FROM Meeting m WHERE m.date >= :currentDate")
    Meeting getReportsForNewMeeting(@Named("currentDate")Date currentDate);
}
