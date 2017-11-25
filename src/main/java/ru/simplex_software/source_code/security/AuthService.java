package ru.simplex_software.source_code.security;

import org.apache.wicket.RestartResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.simplex_software.security.ulogin.ULoginAuthToken;
import ru.simplex_software.security.ulogin.ULoginUser;
import ru.simplex_software.source_code.dao.SpeakerDAO;
import ru.simplex_software.source_code.model.Speaker;
import ru.simplex_software.source_code.web.LoginPage;

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
        if (auth instanceof ULoginAuthToken) {

            ULoginAuthToken uLoginAuth = (ULoginAuthToken) auth;
            ULoginUser uLoginUser = uLoginAuth.getULoginUser();
            String id = uLoginUser.getIdentity();
            Speaker speaker = speakerDAO.get(id);
            if(speaker==null){
                speaker = new Speaker();
                speaker.setFio(uLoginUser.getFirstName()+ " "+ uLoginUser.getLastName());
                speaker.setSocialId(id);
                speakerDAO.saveOrUpdate(speaker);

            }
            return speaker;
        }
        return null;
    }
    @PreAuthorize("isAuthenticated()")
    public Speaker getLogginedUserOrRedirect(){
        Speaker loginnedAccount = getLoginnedAccount();
        if(loginnedAccount==null){
            throw new RestartResponseException(LoginPage.class);
        }
        return loginnedAccount;

    }

}
