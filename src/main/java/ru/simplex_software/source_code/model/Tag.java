package ru.simplex_software.source_code.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.simplex_software.zkutils.entity.LongIdPersistentEntity;

import javax.persistence.Entity;

/**
 * .
 */
@Entity
public class Tag extends LongIdPersistentEntity {
    private static final Logger LOG = LoggerFactory.getLogger(Tag.class);

}
