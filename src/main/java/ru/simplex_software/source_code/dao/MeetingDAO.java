package ru.simplex_software.source_code.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import ru.simplex_software.source_code.model.Meeting;

@AutoDAO
public interface MeetingDAO extends Dao<Meeting, Long>{
}
