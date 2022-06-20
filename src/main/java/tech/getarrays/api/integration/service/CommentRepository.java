package tech.getarrays.api.integration.service;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.getarrays.api.integration.entity.CommentViewModel;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentViewModel, Long> {

    List<CommentViewModel> getComments(Long id);
}
