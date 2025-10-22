package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.controller;


@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody CommentRequest request, Authentication auth) {
        return ResponseEntity.ok(commentService.addComment(request, auth.getName()));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable String taskId) {
        return ResponseEntity.ok(commentService.getCommentsByTask(taskId));
    }

    @DeleteMapping("/{taskId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String taskId, @PathVariable String commentId, Authentication auth) {
        commentService.deleteComment(taskId, commentId, auth.getName());
        return ResponseEntity.ok("Comment deleted successfully");
    }
}