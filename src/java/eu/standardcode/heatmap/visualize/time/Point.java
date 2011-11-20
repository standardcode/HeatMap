package eu.standardcode.heatmap.visualize.time;

import org.hibernate.Query;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class Point extends TimeScope {

    private final long from, to;

    public Point(long point, long radius) {
        this.from = point - radius;
        this.to = point + radius;
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
