package dev.ioexception.touyProject.controller.comment;

import dev.ioexception.touyProject.dto.comment.CommentRequestDto;
import dev.ioexception.touyProject.dto.comment.CommentResponseDto;
import dev.ioexception.touyProject.entity.comment.Comment;
import dev.ioexception.touyProject.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/posts/comment/{postId}")
    public ResponseEntity<Comment> commentSave(@PathVariable Long postId, @RequestBody CommentRequestDto requestDTO) {
        Comment comment = commentService.commentSave(postId, requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comment);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deletedComment(@PathVariable Long commentId) {
        Comment comment = commentService.isDeletedUpdate(commentId);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/post/comment/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {

        List<CommentResponseDto> commentResponseDTOs = commentService.getAllComments(postId);
        return ResponseEntity.ok(commentResponseDTOs);

    }
}
