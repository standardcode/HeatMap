package eu.standardcode.heatmap.visualize.time;

import org.hibernate.Query;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class Between extends TimeScope {

    private final long from, to;

    public Between(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String getQueryWhere() {
        return "appear > :from and appear < :to";
    }

    @Override
    public Query setParameters(Query query) {
        return query.setParameter("from", from).setParameter("to", to);
    }
}
