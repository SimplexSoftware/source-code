package ru.simplex_software.source_code.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import ru.simplex_software.source_code.model.Speaker;

@AutoDAO
public interface SpeakerDAO extends Dao<Speaker,String>{
    @Finder(query = "select count(s) from  Speaker s")
    long countAllSpeakers();
}
