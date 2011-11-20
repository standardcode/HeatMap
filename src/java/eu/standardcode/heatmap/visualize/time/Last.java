package eu.standardcode.heatmap.visualize.time;

import org.hibernate.Query;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class Last extends TimeScope {
    private final int last;

    public Last(int last) {
        this.last = last;
    }

    @Override
    public String getQueryWhere() {
        return "";
    }

    @Override
    public Query setParameters(Query query) {
        return query.setMaxResults(last);
    }
    
}
