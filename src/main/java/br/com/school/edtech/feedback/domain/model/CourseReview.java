package br.com.school.edtech.feedback.domain.model;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.shared.model.DomainEntityId;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.shared.exceptions.Validations;
import br.com.school.edtech.user.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Entity
public class CourseReview extends DomainEntityId<CourseReviewId> {

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "rating")
  private Rating rating;

  @Column(name = "justification")
  private String justification;

  public CourseReview(User user, Course course, Rating rating, String justification) {
    this();

    Validations.isNotNull(user, ValidationMessage.REQUIRED_USER);
    Validations.isNotNull(course, ValidationMessage.REQUIRED_COURSE);
    Validations.isNotBlank(justification, ValidationMessage.REQUIRED_REVIEW_JUSTIFICATION);

    this.user = user;
    this.course = course;
    this.rating = rating;
    this.justification = justification;
  }

  protected CourseReview() {
    super(new CourseReviewId());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CourseReview that = (CourseReview) o;

    return new EqualsBuilder().appendSuper(super.equals(o))
        .append(user, that.user).append(course, that.course).append(rating, that.rating).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(user).append(course)
        .append(rating).toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("user", user)
        .append("course", course)
        .append("rating", rating)
        .append("justification", justification)
        .toString();
  }
}
