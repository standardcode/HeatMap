package eu.standardcode.heatmap.model;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.*;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
@Entity
public abstract class MouseEvent implements java.io.Serializable {

    private Long id;
    private User user;
    private Url url;
    private short x;
    private short y;
    private long appear;

    public MouseEvent() {
    }

    public MouseEvent(User user, Url url, short x, short y, long appear) {
        this.user = user;
        this.url = url;
        this.x = x;
        this.y = y;
        this.appear = appear;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id", nullable = false)
    public Url getUrl() {
        return this.url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    @Column(name = "x", nullable = false)
    public short getX() {
        return this.x;
    }

    public void setX(short x) {
        this.x = x;
    }

    @Column(name = "y", nullable = false)
    public short getY() {
        return this.y;
    }

    public void setY(short y) {
        this.y = y;
    }

    @Column(name = "appear", nullable = false)
    public long getAppear() {
        return this.appear;
    }

    public void setAppear(long appear) {
        this.appear = appear;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
