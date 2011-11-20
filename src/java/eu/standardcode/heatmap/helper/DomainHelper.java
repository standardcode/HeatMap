package eu.standardcode.heatmap.helper;

import eu.standardcode.heatmap.model.Domain;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class DomainHelper {

    private DomainHelper() {
    }

    public static Domain ensure(Session session, String name) {
        Query q = session.createQuery("from Domain where domain = :domain").setParameter("domain", name);
        Domain domain;
        List list = q.list();
        if (list.isEmpty()) {
            domain = new Domain(name);
            session.save(domain);
        } else {
            domain = (Domain) list.get(0);
        }
        return domain;
    }

    public static Domain find(Session session, String name) {
        Query q = session.createQuery("from Domain where domain = :domain").setParameter("domain", name);
        List list = q.list();
        return list.isEmpty() ? null : (Domain) list.get(0);
    }
}
