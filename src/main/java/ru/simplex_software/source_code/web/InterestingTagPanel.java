package ru.simplex_software.source_code.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import ru.simplex_software.source_code.model.Tag;

/**
 * Line for interesting tag.
 * Contains of the tag name, it's raiting and buttons for like and dislike.
 */
public class InterestingTagPanel extends Panel {

    public InterestingTagPanel(String id, IModel<Tag> model) {
        super(id, model);

        Tag tag = model.getObject();

        add(new Label("tag", new PropertyModel<>(tag, "name")));

        Link<Void> link = new Link<Void>("link") {
            @Override
            public void onClick() {
                // TODO: Write a handler for click events.
            }
        };
        Label textInLink = new Label("link-text", Model.of(getLinkNumber(tag)));
        link.add(textInLink);
        add(link);
    }

    /**
     * Method for getting the number of corresponding articles for a link
     * @return number of articles
     **/
    private String getLinkNumber(Tag tag) {
        // TODO: Define a corresponding number in depend of the tag.
        return "0";
    }


}
