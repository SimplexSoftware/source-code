package ru.simplex_software.source_code.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import net.sf.autodao.Named;
import ru.simplex_software.source_code.model.Meeting;

@AutoDAO
public interface MeetingDAO extends Dao<Meeting, Long>{

    @Finder(query = "FROM Meeting m WHERE m.date >= :currentDate")
    Meeting getReportsForNewMeeting(@Named("currentDate")Date currentDate);
}
