package br.com.school.edtech.course.domain.model;

import br.com.school.edtech.enrollment.domain.model.Enrollment;
import br.com.school.edtech.shared.model.DomainEntityId;
import br.com.school.edtech.shared.exceptions.InvalidArgumentException;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.shared.exceptions.Validations;
import br.com.school.edtech.user.domain.model.Role;
import br.com.school.edtech.user.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.Instant;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Entity
public class Course extends DomainEntityId<CourseId> {

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @ManyToOne
  @JoinColumn(name = "instructor_id")
  private User instructor;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @Column(name = "creation_date")
  private Instant creationDate;

  @Column(name = "inactivation_date")
  private Instant inactivationDate;

  @OneToMany(mappedBy = "course")
  private List<Enrollment> enrollments;

  public Course(String name, String code, User instructor, String description) {
    this();

    Validations.isNotBlank(name, ValidationMessage.REQUIRED_NAME);
    Validations.isNotBlank(code, ValidationMessage.REQUIRED_COURSE_CODE);
    Validations.isNotNull(instructor, ValidationMessage.REQUIRED_INSTRUCTOR);
    Validations.isNotBlank(description, ValidationMessage.REQUIRED_DESCRIPTION);
    Validations.isValidCourseCodeFormat(code, ValidationMessage.INVALID_COURSE_CODE);

    validateInstructor(instructor);

    this.name = name;
    this.code = code;
    this.instructor = instructor;
    this.description = description;
    this.status = Status.ACTIVE;
    this.creationDate = Instant.now();
  }

  protected Course() {
    super(new CourseId());
  }

  public void inactivate() {
    this.status = Status.INACTIVE;
    this.inactivationDate = Instant.now();
  }

  private void validateInstructor(User instructor) {
    if(!instructor.getRole().equals(Role.INSTRUCTOR)) {
      throw new InvalidArgumentException(ValidationMessage.REQUIRED_USER_INSTRUCTOR);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Course course = (Course) o;

    if(course.getId() != null && course.getId().equals(getId())) {
      return true;
    }

    return new EqualsBuilder().append(code, course.code).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(code).toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("code", code)
        .append("instructor", instructor)
        .append("description", description)
        .append("status", status)
        .append("creationDate", creationDate)
        .append("inactivationDate", inactivationDate)
        .toString();
  }

}
