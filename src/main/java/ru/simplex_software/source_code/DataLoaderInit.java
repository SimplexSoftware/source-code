package ru.simplex_software.source_code;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class DataLoaderInit implements InitializingBean {

    @Autowired
    DataLoader dl;

    @Override
    public void afterPropertiesSet() throws Exception {
        dl.load();
    }
}
