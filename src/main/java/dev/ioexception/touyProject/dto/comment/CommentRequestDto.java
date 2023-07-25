package dev.ioexception.touyProject.dto.comment;

import dev.ioexception.touyProject.entity.comment.Comment;
import dev.ioexception.touyProject.entity.Post;
import dev.ioexception.touyProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private String content;
    private User user;
    private Post post;
    private Comment parent;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .user(user)
                .post(post)
                .parent(parent)
                .build();
    }
}
