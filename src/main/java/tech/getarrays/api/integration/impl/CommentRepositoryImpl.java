package tech.getarrays.api.integration.impl;

import org.springframework.stereotype.Repository;
import tech.getarrays.api.integration.entity.CommentViewModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class CommentRepositoryImpl {

    public List<CommentViewModel> getComments(Long id) {
        return Stream.of( new CommentViewModel("new comment test"), new CommentViewModel("new comment test 2")).collect(Collectors.toList());
      //  return Collections.singletonList(new CommentViewModel("new comment test"));
    }
}
