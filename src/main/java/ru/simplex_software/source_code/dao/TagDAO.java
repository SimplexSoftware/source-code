package ru.simplex_software.source_code.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import ru.simplex_software.source_code.model.Tag;

import java.util.List;

@AutoDAO
public interface TagDAO extends Dao<Tag, Long> {
    @Finder(query = "FROM Tag ORDER BY name")
    List<Tag> findAllTags();
}
