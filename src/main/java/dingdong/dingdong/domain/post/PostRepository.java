package dingdong.dingdong.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 홈화면 최신순 정렬
    @Query(value = "select * from post, user where post.user_id = user.user_id AND (user.local1 = :localId or user.local2 = :localId) ORDER BY post.created_date DESC",
        countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findAllByCreateDateWithLocal(Long localId, Pageable pageable);

    // 홈화면 마감일자순 정렬
    @Query(value = "select * from post, user where post.user_id = user.user_id and (user.local1 = :localId or user.local2 = :localId) "
            +
            "order by (post.gathered_people / post.people) desc",
        countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findAllByEndDateWithLocal(Long localId, Pageable pageable);

    @Query(value = "select * from post, user where post.user_id = user.user_id and (user.local1 = :localId or user.local2 = :localId) and post.category_id = :categoryId ORDER BY post.created_date DESC",
        countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findPostByCategoryIdWithLocal(Long categoryId, Long localId, Pageable pageable);

    @Query(value = "select * from post, user where post.user_id = user.user_id and (user.local1 = :localId or user.local2 = :localId) and post.category_id = :categoryId order by (post.gathered_people / post.people) desc",
        countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findPostByCategoryIdSortByEndDateWithLocal(Long categoryId, Long localId, Pageable pageable);

    Page<Post> findByUserId(Long userId, Pageable pageable);

    @Query(value = "select * from post, chat_join where chat_join.user_id = :userId and chat_join.post_id = post.post_id",
        countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findPostByUserIdOnChatJoin(Long userId, Pageable pageable);

    @Query(value =
        "select * from post, user, post_tag, tag where post.user_id = user.user_id AND post.post_id = post_tag.post_id AND post_tag.tag_id = tag.tag_id AND"
            +
            "(tag.name LIKE %:keyword% )",
        countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findAllSearchByTag(String keyword, Pageable pageable);

    @Query(value = "select * from post, user, category where post.user_id = user.user_id AND post.category_id = category.category_id AND (post.title LIKE %:keyword% OR  category.name LIKE %:keyword%)",
        countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findAllSearch(String keyword, Pageable pageable);

    @Query(value =
        "select * from post, user, category where post.user_id = user.user_id AND post.category_id = category.category_id AND "
            +
            "(post.title LIKE %:keyword% OR  category.name LIKE %:keyword%)AND (user.local1 = :local1 or user.local2 = :local2)",
        countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findAllSearchWithLocal(String keyword, Long local1, Long local2, Pageable pageable);

    // 검색 기능
    @Query(value =
        "select * from post, user, post_tag, tag where post.user_id = user.user_id AND post.post_id = post_tag.post_id AND post_tag.tag_id = tag.tag_id AND "
            +
            "(user.local1 = :local1 or user.local2 = :local2) AND (tag.name LIKE %:keyword% )",
        countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findAllSearchByTagWithLocal(String keyword, Long local1, Long local2,
        Pageable pageable);

    @Query(value = "select * from post", countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findPostsSortByCreatedDateNotLocal(Pageable pageable);

    @Query(value = "select * from post ORDER BY (post.gathered_people / post.people) desc", countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findPostsSortByEndDateNotLocal(Pageable pageable);

    @Query(value = "select * from post WHERE post.category_id = :categoryId ORDER BY post.created_date DESC",
        countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findPostByCategoryIdSortByCreatedDateNotLocal(Long categoryId, Pageable pageable);

    @Query(value = "select * from post WHERE post.category_id = :categoryId order by (post.gathered_people / post.people) desc",
        countQuery = "select count(*) from post",
        nativeQuery = true)
    Page<Post> findPostByCategoryIdSortByEndDateNotLocal(Long categoryId, Pageable pageable);

    @Modifying
    @Query(value = "delete from post where post.post_id = :postId",
        nativeQuery = true)
    void deletePostById(Long postId);
}
