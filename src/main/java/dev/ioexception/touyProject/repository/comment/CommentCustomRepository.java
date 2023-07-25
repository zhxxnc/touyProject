package dev.ioexception.touyProject.repository.comment;

import dev.ioexception.touyProject.dto.comment.CommentResponseDto;
import dev.ioexception.touyProject.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentCustomRepository {
    List<CommentResponseDto> getComments(Post post);
}
