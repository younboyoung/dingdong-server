package dingdong.dingdong.domain.post;


import com.fasterxml.jackson.annotation.JsonBackReference;
import dingdong.dingdong.domain.BaseTimeEntity;
import dingdong.dingdong.domain.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int cost;

    @Column(nullable = false)
    private int people;

    @Column(nullable = false)
    private String local;

    @Column(nullable = false)
    private String bio;

    @Column(nullable = false)
    private String imageUrl;

    @Column(columnDefinition = "boolean default false")
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    public void setCategory(Category category) {
        this.category = category;

        if(!category.getPosts().contains(this)) {
            category.getPosts().add(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public void setUser(User user) {
        this.user = user;

        if(!user.getPosts().contains(this)) {
            user.getPosts().add(this);
        }
    }

}
