package ru.simplex_software.source_code.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import ru.simplex_software.source_code.model.Report;

@AutoDAO
public interface ReportDAO extends Dao<Report, Long>{
}
