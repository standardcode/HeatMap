package eu.standardcode.heatmap.visualize.time;

import org.hibernate.Query;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public abstract class TimeScope {

    TimeScope() {
    }

    public abstract String getQueryWhere();

    public abstract Query setParameters(Query query);
}
