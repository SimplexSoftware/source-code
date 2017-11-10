package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.dao.SpeakerDAO;
import ru.simplex_software.source_code.model.Speaker;
import ru.simplex_software.source_code.security.AuthService;

public class SpeakerProfilePage extends SuperPage{

    @SpringBean
    private AuthService authService;

    @SpringBean
    private SpeakerDAO speakerDAO;

    private CompoundPropertyModel<Speaker> speakerModel;

    public SpeakerProfilePage() {

        Speaker speaker = this.authService.getLogginedUserOrRedirect();

        this.speakerModel = new CompoundPropertyModel<>(new HibernateModel<Speaker>(speaker));

        setDefaultModel(this.speakerModel);

        add(new Label("fio"));
        add(new Label("raiting"));
        add(new FeedbackPanel("message"));

        Form subscribeForm = new Form<Void>("profileForm") {
            @Override
            protected void onSubmit() {
                speakerDAO.update(speakerModel.getObject());
                success("Данные изменены");
            }
        };

        subscribeForm.add(new CheckBox("subscriber"));

        subscribeForm.add(new EmailTextField("email"));

        add(subscribeForm);
    }

    @Override
    public void detachModels() {
        super.detachModels();
        this.speakerModel.detach();
    }
}
