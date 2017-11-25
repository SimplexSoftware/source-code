package ru.simplex_software.source_code.web;


import net.sf.wicketautodao.model.HibernateQueryDataProvider;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.util.StringUtils;
import ru.simplex_software.source_code.dao.TagDAO;
import ru.simplex_software.source_code.model.Tag;
import ru.simplex_software.source_code.security.AuthService;

/**
 * Panel that displays all interesting tags for user.
 */
public class WishesPanel extends Panel {

    @SpringBean
    private AuthService authService;

    @SpringBean
    private TagDAO tagDAO;

    public WishesPanel(String id, IModel<?> model) {
        super(id, model);

        HibernateQueryDataProvider<Tag, Long> hqDataProviderInterestingTags
            = new HibernateQueryDataProvider(TagDAO.class, "findAllTags");

        add(new DataView<Tag>("interestingTag", hqDataProviderInterestingTags) {
            @Override
            protected void populateItem(Item<Tag> item) {
                item.add(new InterestingTagPanel(
                    "interestingTagPanel", item.getModel()));
            }
        });

        Fragment formFragment;
        if (authService.getLoginnedAccount() != null) {
            formFragment = new Fragment("fragmentContent", "form", this);

            formFragment.add(new FeedbackPanel("message"));

            TextField textField = new TextField("wishes-input", Model.of(""));

            Form wishesForm = new Form<Void>("wishes-form") {
                @Override
                protected void onSubmit() {
                    String tagName = (String) textField.getModel().getObject();
                    if (!StringUtils.hasLength(tagName)) {
                        info("Имя темы не может быть пустым");
                        return;
                    }
                    if (tagDAO.getTag(tagName) == null) {
                        Tag tag = new Tag();
                        tag.setName(tagName);
                        tagDAO.saveOrUpdate(tag);
                    }else {
                        info("Тема " + tagName + " уже содержится");
                    }
                }
            };
            wishesForm.add(textField);
            formFragment.add(wishesForm);

        }else {
            formFragment = new Fragment("fragmentContent", "empty", this);
        }
        add(formFragment);

    }
}
