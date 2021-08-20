package dingdong.dingdong.domain.user;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String nickname;

    private String profile_bio;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    public Profile(User user) {
        this.id = user.getId();
        this.user = user;
    }

    public void createNickname(String nickname) {
        this.nickname = nickname;
    }
}
