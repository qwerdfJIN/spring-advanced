package org.example.expert.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.request.CommentUpdateRequest;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.service.CommentService;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommentSaveResponse> saveComment(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @Valid @RequestBody CommentSaveRequest commentSaveRequest
    ) {
        return ResponseEntity.ok(commentService.saveComment(authUser, todoId, commentSaveRequest));
    }

    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable long todoId) {
        return ResponseEntity.ok(commentService.getComments(todoId));
    }

    // 작성자 본민만 댓글 수정 가능
    @PutMapping("/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment (
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @PathVariable long commentId,
            @Valid @RequestBody CommentUpdateRequest commentUpdateRequest
    ) {
        return ResponseEntity.ok(commentService.updateComment(authUser, commentId, commentUpdateRequest));
    }

    // 작성자 본인만 댓글 삭제 가능
    @DeleteMapping("/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment (
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @PathVariable long commentId
    ) {
        commentService.deleteComment(authUser, commentId);
        return ResponseEntity.noContent().build();
    }
}
