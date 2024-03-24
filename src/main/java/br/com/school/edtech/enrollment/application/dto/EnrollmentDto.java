package br.com.school.edtech.enrollment.application.dto;

import br.com.school.edtech.course.application.dto.CourseDto;
import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.enrollment.domain.model.Enrollment;
import br.com.school.edtech.shared.model.exceptions.ValidationMessage;
import br.com.school.edtech.shared.model.exceptions.Validations;
import br.com.school.edtech.user.application.dto.UserDto;
import br.com.school.edtech.user.domain.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class EnrollmentDto {

  @JsonProperty(access = Access.READ_ONLY)
  private UserDto user;

  @JsonProperty(access = Access.WRITE_ONLY)
  @NotNull
  private UUID userId;

  @JsonProperty(access = Access.READ_ONLY)
  private CourseDto course;

  @JsonProperty(access = Access.WRITE_ONLY)
  @NotNull
  private UUID courdeId;

  @JsonProperty(access = Access.READ_ONLY)
  private Instant enrollmentDate;

  public static EnrollmentDto map(Enrollment enrollment) {
    Validations.isNotNull(enrollment, ValidationMessage.REQUIRED_ENROLLMENT);

    EnrollmentDto enrollmentDto = new EnrollmentDto();
    enrollmentDto.setUser(UserDto.map(enrollment.getUser()));
    enrollmentDto.setCourse(CourseDto.map(enrollment.getCourse()));
    enrollmentDto.setEnrollmentDate(enrollment.getEnrollmentDate());

    return enrollmentDto;
  }
}
