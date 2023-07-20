package dev.ioexception.touyProject.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", updatable = false)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Column(name = "content", nullable = false)
    private String content;

    @ColumnDefault("FALSE")
    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "commentCreateAt", nullable = false)
    private LocalDateTime commentCreatedAt;

    @Builder
    public Comment(String content, User user, Post post, Comment parent) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.parent = parent;
        this.isDeleted = false;
        this.commentCreatedAt = LocalDateTime.now();
    }

    public void updateIsDeleted() {
        this.isDeleted = true;
    }

}
