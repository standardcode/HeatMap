package eu.standardcode.heatmap.helper;

import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class MouseEventHelper {

    private MouseEventHelper() {
    }

    public static <T> List<T> getAll(Session session, Class<T> cls) {
        return session.createQuery("from " + cls.getSimpleName()).list();
    }
}
