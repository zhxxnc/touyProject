package dev.ioexception.touyProject.repository.comment.Impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.ioexception.touyProject.dto.comment.CommentResponseDto;
import dev.ioexception.touyProject.entity.comment.Comment;
import dev.ioexception.touyProject.entity.Post;
import dev.ioexception.touyProject.entity.QComment;
import dev.ioexception.touyProject.repository.comment.CommentCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<CommentResponseDto> getComments(Post post) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QComment comment = QComment.comment;

        BooleanExpression condition = comment.post.eq(post)
                .and(comment.parent.isNull()); // 대댓글이 아닌 댓글만 조회

        // 댓글을 순서대로 정렬한 리스트를 가져옴
        List<Comment> comments = queryFactory
                .select(comment)
                .from(comment)
                .leftJoin(comment.user)
                .where(condition)
                .orderBy(comment.commentCreatedAt.asc())
                .fetch();

        // 댓글의 대댓글이 있는 경우, 댓글의 대댓글이 순서대로 나오도록 추가
        List<CommentResponseDto> sortedComments = CommentResponseDto.sortByCommentAndReply(comments);

        return sortedComments;
    }
}
