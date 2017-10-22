package ru.simplex_software.source_code.web;


import net.sf.wicketautodao.model.HibernateQueryDataProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import ru.simplex_software.source_code.dao.TagDAO;
import ru.simplex_software.source_code.model.Tag;

/**
 * Panel that displays all interesting tags for user.
 */
public class WishesPanel extends Panel {

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
    }
}
