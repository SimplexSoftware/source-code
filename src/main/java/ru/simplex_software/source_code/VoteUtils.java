package ru.simplex_software.source_code;

import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.simplex_software.source_code.dao.ReportDAO;
import ru.simplex_software.source_code.dao.TagDAO;
import ru.simplex_software.source_code.model.Report;
import ru.simplex_software.source_code.model.Speaker;
import ru.simplex_software.source_code.model.Tag;
import ru.simplex_software.source_code.security.AuthService;

import java.util.Map;

/**
 * Util class supporting operations voting for tags and reports
 */
public class VoteUtils {

    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private ReportDAO reportDAO;

    @Autowired
    private AuthService authService;

    /**
     * Tries to value the tag according to the value strategy.
     * If user has already valued the tag he is able to cancel
     * his vote by using the same strategy or vote again using
     * the other strategy .
     *
     * @param model    the IModel with the tag to value
     * @param strategy not-null valueTag strategy. Can be LIKE or DISLIKE
     * @return <tt>true</tt> if the user voted successfully
     */
    public boolean valueTag(@NotNull IModel<Tag> model,
                            @NotNull ValueStrategy strategy) {
        Tag tag = model.getObject();
        if (tag == null) {
            throw new RuntimeException(
                "DB mistake: The current tag was removed.");
        }
        Speaker user = authService.getLogginedUserOrRedirect();
        Map<Speaker, Boolean> usersValuedTagMap = tag.getWhoLikedIt();
        switch (strategy) {
            case LIKE:
                if (valuedAs(user, tag, ValueStrategy.DISLIKE)) {
                    tag.setRating(tag.getRating() + 2);
                    usersValuedTagMap.put(user, true);
                } else if (valuedAs(user, tag, ValueStrategy.LIKE)) {
                    tag.setRating(tag.getRating() - 1);
                    usersValuedTagMap.remove(user);
                } else {
                    tag.setRating(tag.getRating() + 1);
                    usersValuedTagMap.put(user, true);
                }
                break;
            case DISLIKE:
                if (valuedAs(user, tag, ValueStrategy.LIKE)) {
                    tag.setRating(tag.getRating() - 2);
                    usersValuedTagMap.put(user, false);
                } else if (valuedAs(user, tag, ValueStrategy.DISLIKE)) {
                    tag.setRating(tag.getRating() + 1);
                    usersValuedTagMap.remove(user);
                } else {
                    tag.setRating(tag.getRating() - 1);
                    usersValuedTagMap.put(user, false);
                }
                break;
            default:
                throw new UnsupportedOperationException(
                    "Unsupported strategy: " + strategy.name());
        }
        tagDAO.update(tag);
        return true;
    }

    /**
     * Tries to value the report according to the value strategy.
     * User can value the report only once and can not change his mind.
     *
     * @param model    the IModel with the report to value
     * @param strategy not-null value strategy. Can be LIKE or DISLIKE
     * @return <tt>true</tt> if the user voted successfully
     */
    public boolean valueReport(@NotNull IModel<Report> model,
                               @NotNull ValueStrategy strategy) {
        Report report = model.getObject();
        if (report == null) {
            throw new RuntimeException(
                "DB mistake: The current report was removed.");
        }
        Speaker user = authService.getLogginedUserOrRedirect();
        if (valuedAs(user, report, strategy) || hasAlreadyVoted(user, report)) {
            return false;
        }
        Map<Speaker, Boolean> usersValuedTagMap = report.getWhoLikedIt();
        switch (strategy) {
            case LIKE:
                usersValuedTagMap.put(user, true);
                break;
            case DISLIKE:
                usersValuedTagMap.put(user, false);
                break;
            default:
                throw new UnsupportedOperationException(
                    "Unsupported strategy: " + strategy.name());
        }
        reportDAO.update(report);
        return true;
    }

    /**
     * Returns <tt>true</tt> if the user has already voted for the tag.
     *
     * @param speaker user
     * @param tag     interesting tag
     * @return <tt>true</tt> if the user has already voted for the tag
     */
    private boolean hasAlreadyVoted(@NotNull Speaker speaker,
                                    @NotNull Tag tag) {

        return tag.getWhoLikedIt().containsKey(speaker);
    }

    /**
     * Returns <tt>true</tt> if the user has already voted for the report.
     *
     * @param speaker user
     * @param report  report
     * @return <tt>true</tt> if the user has already voted for the report
     */
    private boolean hasAlreadyVoted(@NotNull Speaker speaker,
                                    @NotNull Report report) {

        return report.getWhoLikedIt().containsKey(speaker);
    }

    /**
     * Returns <tt>true</tt> if the user has already voted
     * for the tag according to the passed valueTag strategy.
     *
     * @param speaker  user
     * @param tag      interesting tag
     * @param strategy value strategy
     * @return <tt>true</tt> if the user has already voted
     * for the tag according to the passed valueTag strategy
     */
    private boolean valuedAs(@NotNull Speaker speaker,
                             @NotNull Tag tag,
                             @NotNull ValueStrategy strategy) {

        if (!hasAlreadyVoted(speaker, tag)) {
            return false;
        }
        switch (strategy) {
            case LIKE:
                return tag.getWhoLikedIt().get(speaker);
            case DISLIKE:
                return !tag.getWhoLikedIt().get(speaker);
            default:
                throw new UnsupportedOperationException(
                    "Unsupported strategy: " + strategy.name());
        }
    }

    /**
     * Returns <tt>true</tt> if the user has already voted
     * for the record according to the passed valueTag strategy.
     *
     * @param speaker  user
     * @param report   report
     * @param strategy value strategy
     * @return <tt>true</tt> if the user has already voted
     * for the record according to the passed value strategy
     */
    private boolean valuedAs(@NotNull Speaker speaker,
                             @NotNull Report report,
                             @NotNull ValueStrategy strategy) {

        if (!hasAlreadyVoted(speaker, report)) {
            return false;
        }
        switch (strategy) {
            case LIKE:
                return report.getWhoLikedIt().get(speaker);
            case DISLIKE:
                return !report.getWhoLikedIt().get(speaker);
            default:
                throw new UnsupportedOperationException(
                    "Unsupported strategy: " + strategy.name());
        }
    }

    /**
     * Strategy of valuing.
     */
    public enum ValueStrategy {
        LIKE,
        DISLIKE
    }
}
