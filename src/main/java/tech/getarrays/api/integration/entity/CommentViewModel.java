package tech.getarrays.api.integration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "comments")
public class CommentViewModel {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String commentText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommentViewModel(String commentText) {
        this.commentText = commentText;
    }

    public CommentViewModel() {

    }
}
