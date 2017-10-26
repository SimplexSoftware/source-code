package ru.simplex_software.source_code.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.VoteUtils;
import ru.simplex_software.source_code.model.Tag;

/**
 * Line for interesting tag.
 * Contains of the tag name, it's rating and buttons for like and dislike.
 */
public class InterestingTagPanel extends Panel {

    @SpringBean
    private VoteUtils voteUtils;

    public InterestingTagPanel(String id, IModel<Tag> model) {
        super(id, model);

        add(new Label("tag", model.getObject().getName()));

        Link<Void> link = new Link<Void>("link") {
            @Override
            public void onClick() {
            }
        };
        Label textInLink = new Label("link-text",
            new PropertyModel<>(model, "rating"));
        link.add(textInLink);
        add(link);

        add(new Link<Void>("like") {
            @Override
            public void onClick() {
                voteUtils.valueTag(model, VoteUtils.ValueStrategy.LIKE);
            }
        });
        add(new Link<Void>("dislike") {
            @Override
            public void onClick() {
                voteUtils.valueTag(model, VoteUtils.ValueStrategy.DISLIKE);
            }
        });
    }
}
