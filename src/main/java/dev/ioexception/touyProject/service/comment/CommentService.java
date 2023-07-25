package dev.ioexception.touyProject.service.comment;

import dev.ioexception.touyProject.dto.comment.CommentRequestDto;
import dev.ioexception.touyProject.dto.comment.CommentResponseDto;
import dev.ioexception.touyProject.entity.comment.Comment;
import dev.ioexception.touyProject.entity.Post;
import dev.ioexception.touyProject.entity.User;
import dev.ioexception.touyProject.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Comment commentSave(Long postId, CommentRequestDto requestDTO) {
        User user = userRepository.findById(requestDTO.getUser().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("not found: " + requestDTO.getUser()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + postId));

        requestDTO.setUser(user);
        requestDTO.setPost(post);

        // 대댓글인 경우
        if(requestDTO.getParent().getCommentId() != null) {
            Comment parent = commentRepository.findById(requestDTO.getParent().getCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("not found: " + requestDTO.getParent().getId()));

            requestDTO.setParent(parent);
        } else {
            requestDTO.setParent(null);

        }
        Comment comment = requestDTO.toEntity();

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment isDeletedUpdate(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + commentId));

        comment.updateIsDeleted();

        return comment;
    }

    @Override
    public List<CommentResponseDto> getAllComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + postId));

        return commentRepository.getComments(post);
    }

}
