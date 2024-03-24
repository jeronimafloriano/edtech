package br.com.school.edtech.enrollment.domain.model;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.Status;
import br.com.school.edtech.shared.model.DomainEntityId;
import br.com.school.edtech.shared.model.exceptions.InvalidArgumentException;
import br.com.school.edtech.shared.model.exceptions.ValidationMessage;
import br.com.school.edtech.shared.model.exceptions.Validations;
import br.com.school.edtech.user.domain.model.Role;
import br.com.school.edtech.user.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Entity
public class Enrollment extends DomainEntityId<EnrollmentId> {

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @Column(name = "enrollment_date")
  private Instant enrollmentDate;

  public Enrollment(User user, Course course) {
    this();

    Validations.isNotNull(user, ValidationMessage.REQUIRED_USER);
    Validations.isNotNull(course, ValidationMessage.REQUIRED_COURSE);

    this.user = user;
    this.course = course;
    this.enrollmentDate = Instant.now();
  }

  protected Enrollment() {
    super(new EnrollmentId());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Enrollment that = (Enrollment) o;

    return new EqualsBuilder().appendSuper(super.equals(o))
        .append(user, that.user).append(course, that.course)
        .append(enrollmentDate, that.enrollmentDate).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(user).append(course)
        .append(enrollmentDate).toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("user", user)
        .append("course", course)
        .append("enrollmentDate", enrollmentDate)
        .toString();
  }
}
