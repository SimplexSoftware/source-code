package ru.simplex_software.source_code.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import net.sf.autodao.Named;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;

import java.util.Date;
import java.util.List;

@AutoDAO
public interface ReportDAO extends Dao<Report, Long>{

    @Finder(query = "FROM Report WHERE meeting = :meeting")
    List<Report> findReportsForNewMeeting(@Named("meeting") Meeting meeting);
}
