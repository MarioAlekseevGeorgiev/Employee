package tech.getarrays.api.integration.service;

import tech.getarrays.api.integration.entity.CommentViewModel;

import java.util.List;

public interface CommentService {

    List<CommentViewModel> getComments(Long id);
}
