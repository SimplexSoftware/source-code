package ru.simplex_software.source_code.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ru.simplex_software.security.ulogin.ULoginAuthToken;
import ru.simplex_software.security.ulogin.ULoginUser;
import ru.simplex_software.source_code.dao.SpeakerDAO;
import ru.simplex_software.source_code.model.Speaker;

/** Класс, который предоставляет доступ к. */
public class AuthService {

    @Autowired
    private SpeakerDAO  speakerDAO;

    /**
     * Метод возвращает ссылку на учётную запись зашедшего пользователя.
     * @return Учётную запись текущего зашедшего пользователя.
     */
    public Speaker getLoginnedAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Speaker account;
        if (auth instanceof ULoginAuthToken) {

            ULoginAuthToken uLoginAuth = (ULoginAuthToken) auth;
            ULoginUser uLoginUser = uLoginAuth.getULoginUser();
            String id = uLoginUser.getIdentity();
            Speaker speaker = speakerDAO.get(id);
            if(id==null){
                speaker = new Speaker();
                speaker.setFio(uLoginUser.getFirstName()+ " "+ uLoginUser.getLastName());
                speaker.setSocialId(id);
                speakerDAO.saveOrUpdate(speaker);

            }
            return speaker;
        }
        return null;
    }

}
