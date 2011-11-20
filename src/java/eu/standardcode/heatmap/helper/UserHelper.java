package eu.standardcode.heatmap.helper;

import eu.standardcode.heatmap.model.User;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class UserHelper {

    private UserHelper() {
    }

    public static User ensure(Session session, String cookie) {
        Query q = session.createQuery("from User where session = :cookie").setParameter("cookie", cookie);
        User user;
        List list = q.list();
        if (list.isEmpty()) {
            user = new User(cookie);
            session.save(user);
        } else {
            user = (User) list.get(0);
        }
        return user;
    }
}
