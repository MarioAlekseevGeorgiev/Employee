package tech.getarrays.api.integration.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.getarrays.api.integration.entity.CommentViewModel;
import tech.getarrays.api.integration.service.CommentRepository;
import tech.getarrays.api.integration.service.CommentService;

import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public List<CommentViewModel> getComments(Long id) {
        return commentRepository.getComments(id);

    }
}
