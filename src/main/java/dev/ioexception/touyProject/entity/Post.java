package dev.ioexception.touyProject.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "category_id")
    private Category category;

    @OneToMany(mappedBy = "tag_id")
    private Tag tag;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 2000, columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isDeleted;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime postCreateAt;

    @LastModifiedDate
    private LocalDateTime postModifyAt;

    @Builder

    public Post(User user, Category category, Tag tag, String title, String content, boolean isDeleted) {
        this.user = user;
        this.category = category;
        this.tag = tag;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
    }
}
