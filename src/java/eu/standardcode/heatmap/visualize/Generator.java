package eu.standardcode.heatmap.visualize;

import eu.standardcode.heatmap.helper.UrlHelper;
import eu.standardcode.heatmap.model.HibernateUtil;
import eu.standardcode.heatmap.model.MouseEvent;
import eu.standardcode.heatmap.model.Url;
import eu.standardcode.heatmap.visualize.color.ColorScheme;
import eu.standardcode.heatmap.visualize.motif.Motif;
import eu.standardcode.heatmap.visualize.time.TimeScope;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
class Generator {

    private final Options op;
    private int maxCount;

    public Generator(Options op) {
        this.op = op;
    }

    private List<MouseEvent> list() {
        Session session = HibernateUtil.openSession();
        Url url = UrlHelper.find(session, op.getUrl());
        List<MouseEvent> list;
        if (url != null) {
            list = new ArrayList<>();
            for (Class<? extends MouseEvent> c : op.getType().getClasses()) {
                list.addAll(listMouseEvent(session, url, c));
            }
        } else {
            list = Collections.EMPTY_LIST;
        }
        session.close();
        return list;
    }

    private <T extends MouseEvent> List<T> listMouseEvent(Session session, Url url, Class<T> from) {
        TimeScope time = op.getTimeScope();
        String whereTime = time.getQueryWhere();
        if (!whereTime.isEmpty()) {
            whereTime = " and (" + whereTime + ")";
        }
        int userId = op.getUserId();
        String whereUser = userId == 0 ? "" : " and user_id = :user";
        Query q = session.createQuery("from " + from.getSimpleName() + " where url = :url " + whereTime + whereUser).setParameter("url", url);
        if (userId != 0) {
            q.setParameter("user", userId);
        }
        return time.setParameters(q).list();
    }

    BufferedImage createImage() {
        int[][] pixels = createPixels();
        int width = pixels[0].length;
        int height = pixels.length;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
        for (int row = 0; row < pixels.length; row++) {
            bi.setRGB(0, row, width, 1, pixels[row], 0, width);
        }
        return bi;
    }

    private int[][] createPixels() {
        int[][] countEvents = countEvents();
        ColorScheme colorScheme = op.getColorScheme();
        colorScheme.setMax(maxCount);
        Motif motif = op.getMotif();
        motif.setColorScheme(colorScheme);
        return motif.getPixels(countEvents);
    }

    private int[][] countEvents() {
        List<MouseEvent> list = list();
        int maxX = 0, maxY = 0;
        for (MouseEvent m : list) {
            maxX = Math.max(m.getX(), maxX);
            maxY = Math.max(m.getY(), maxY);
        }
        ++maxX;
        ++maxY;
        int[][] b = new int[maxY][maxX];
        for (MouseEvent m : list) {
            maxCount = Math.max(maxCount, ++b[m.getY()][m.getX()]);
        }
        return b;
    }
}
