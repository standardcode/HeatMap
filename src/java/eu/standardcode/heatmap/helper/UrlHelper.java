package eu.standardcode.heatmap.helper;

import eu.standardcode.heatmap.model.Domain;
import eu.standardcode.heatmap.model.Url;
import java.net.URL;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class UrlHelper {

    private UrlHelper() {
    }

    public static Url ensure(Session session, Domain domain, String path) {
        Query q = session.createQuery("from Url where domain = :domain and path = :path").setParameter("domain", domain).setParameter("path", path);
        Url url;
        List list = q.list();
        if (list.isEmpty()) {
            url = new Url(domain, path);
            session.save(url);
        } else {
            url = (Url) list.get(0);
        }
        return url;
    }

    public static Url find(Session session, URL url) {
        Domain domain = DomainHelper.find(session, url.getHost());
        if (domain != null) {
            Query q = session.createQuery("from Url where domain = :domain and path = :path").setParameter("domain", domain).setParameter("path", url.getPath());
            List list = q.list();
            return list.isEmpty() ? null : (Url) list.get(0);
        }
        return null;
    }
}
