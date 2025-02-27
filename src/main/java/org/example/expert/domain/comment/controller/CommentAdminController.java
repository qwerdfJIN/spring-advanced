package org.example.expert.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.request.CommentUpdateRequest;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.service.CommentAdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentAdminController {

    private final CommentAdminService commentAdminService;

    // 관리자는 모든 댓글 수정 가능
    @PutMapping("/admin/comments/{commentId}")
    public CommentResponse updateComment(
            @PathVariable long commentId,
            @RequestBody CommentUpdateRequest commentUpdateRequest
            ) {
        return commentAdminService.updateComment(commentId, commentUpdateRequest);
    }

    @DeleteMapping("/admin/comments/{commentId}")
    public void deleteComment(@PathVariable long commentId) {
        commentAdminService.deleteComment(commentId);
    }
}
