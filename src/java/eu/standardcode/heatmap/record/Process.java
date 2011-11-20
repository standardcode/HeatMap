package eu.standardcode.heatmap.record;

import eu.standardcode.heatmap.helper.DomainHelper;
import eu.standardcode.heatmap.helper.UrlHelper;
import eu.standardcode.heatmap.helper.UserHelper;
import eu.standardcode.heatmap.model.*;
import org.hibernate.Session;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
class Process implements Runnable {

    private static abstract class MouseEventFactory {

        abstract MouseEvent createMouseEvent(User user, Url url, short x, short y, long appear);

        abstract int getMaxDamaged();
    }
    MouseEventFactory[] mef = new MouseEventFactory[]{
        new MouseEventFactory() {

            @Override
            MouseEvent createMouseEvent(User user, Url url, short x, short y, long appear) {
                return new Mousemove(user, url, x, y, appear);
            }

            @Override
            int getMaxDamaged() {
                return 10;
            }
        },
        new MouseEventFactory() {

            @Override
            MouseEvent createMouseEvent(User user, Url url, short x, short y, long appear) {
                return new Mousedown(user, url, x, y, appear);
            }

            @Override
            int getMaxDamaged() {
                return 2;
            }
        },
        new MouseEventFactory() {

            @Override
            MouseEvent createMouseEvent(User user, Url url, short x, short y, long appear) {
                return new Mouseup(user, url, x, y, appear);
            }

            @Override
            int getMaxDamaged() {
                return 2;
            }
        }
    };
    private final Parameters param;

    public Process(Parameters param) {
        this.param = param;
    }

    @Override
    public void run() {
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        User user = UserHelper.ensure(session, param.getUserCookie());
        Domain domain = DomainHelper.ensure(session, param.getReferrer().getHost());
        Url url = UrlHelper.ensure(session, domain, param.getReferrer().getPath());
        for (int i = 0; i < mef.length; i++) {
            processMouseEvent(session, i, user, url);
        }
        session.getTransaction().commit();
        session.close();
    }

    private void processMouseEvent(Session session, int type, User user, Url url) {
        String px = param.get("px" + type);
        if (px == null) {
            return;
        }
        String[] p = px.split(",");
        int bad = 0;
        long previous = 0;
        for (int i = 0; i < p.length;) {
            try {
                short x = Short.parseShort(p[i]);
                short y = Short.parseShort(p[i + 1]);
                long diff = Long.parseLong(p[i + 2]);
                long time = previous + diff;
                previous = time;
                MouseEvent me = mef[type].createMouseEvent(user, url, x, y, time);
                session.save(me);
            } catch (NumberFormatException e) {//skip malformed data
                bad++;
                if (bad > mef[type].getMaxDamaged()) {//if too many malformed data
                    break;
                }
            } finally {
                i += 3;
            }
        }
    }
}
