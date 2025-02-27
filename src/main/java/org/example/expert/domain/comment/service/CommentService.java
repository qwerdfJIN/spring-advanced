package org.example.expert.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.request.CommentUpdateRequest;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentSaveResponse saveComment(AuthUser authUser, long todoId, CommentSaveRequest commentSaveRequest) {

        // Todo 존재 여부를 먼저 확인하여 불필요한 유저 객체 생성을 방지
        Todo todo = todoRepository.findById(todoId).orElseThrow(() ->
                new InvalidRequestException("Todo not found"));

        User user = User.fromAuthUser(authUser);

        Comment newComment = new Comment(
                commentSaveRequest.getContents(),
                user,
                todo
        );

        Comment savedComment = commentRepository.save(newComment);

        return new CommentSaveResponse(
                savedComment.getId(),
                savedComment.getContents(),
                new UserResponse(user.getId(), user.getEmail())
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(long todoId) {
        List<Comment> commentList = commentRepository.findByTodoIdWithUser(todoId);

        List<CommentResponse> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            User user = comment.getUser();
            CommentResponse dto = new CommentResponse(
                    comment.getId(),
                    comment.getContents(),
                    new UserResponse(user.getId(), user.getEmail())
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional
    public CommentResponse updateComment(AuthUser authUser, long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new InvalidRequestException("Comment not found"));

        User user = User.fromAuthUser(authUser);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new InvalidRequestException("수정 권한이 없습니다.");
        }

        comment.update(request.getContents());
        commentRepository.save(comment);

        return new CommentResponse(
                comment.getId(),
                comment.getContents(),
                new UserResponse(user.getId(), user.getEmail())
        );
    }

    @Transactional
    public void deleteComment(AuthUser authUser, long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new InvalidRequestException("Comment not found"));

        User user = User.fromAuthUser(authUser);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new InvalidRequestException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
