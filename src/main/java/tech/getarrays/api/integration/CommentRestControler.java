package tech.getarrays.api.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.getarrays.api.integration.entity.CommentViewModel;
import tech.getarrays.api.integration.service.CommentService;

import java.security.Principal;
import java.util.List;

@RestController
public class CommentRestControler {

    private final CommentService commentService;

    @Autowired
    public CommentRestControler(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("api/{routeId}/comments")
    public ResponseEntity<List<CommentViewModel>> getComments(@PathVariable Long routeId,
                                                              Principal principal) {
        return ResponseEntity.ok(commentService.getComments(routeId));
    }
}
