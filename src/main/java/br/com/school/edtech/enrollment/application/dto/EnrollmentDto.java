package br.com.school.edtech.enrollment.application.dto;

import br.com.school.edtech.course.application.dto.CourseDto;
import br.com.school.edtech.enrollment.domain.model.Enrollment;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.shared.exceptions.Validations;
import br.com.school.edtech.user.application.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EnrollmentDto {

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private UserDto user;

  @JsonProperty(access = Access.WRITE_ONLY)
  @NotNull
  private UUID userId;

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private CourseDto course;

  @JsonProperty(access = Access.WRITE_ONLY)
  @NotNull
  private UUID courdeId;

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Instant enrollmentDate;

  public EnrollmentDto(UUID userId, UUID courdeId) {
    this.userId = userId;
    this.courdeId = courdeId;
  }

  public static EnrollmentDto map(Enrollment enrollment) {
    Validations.isNotNull(enrollment, ValidationMessage.REQUIRED_ENROLLMENT);

    EnrollmentDto enrollmentDto = new EnrollmentDto();
    enrollmentDto.setUser(UserDto.map(enrollment.getUser()));
    enrollmentDto.setCourse(CourseDto.map(enrollment.getCourse()));
    enrollmentDto.setEnrollmentDate(enrollment.getEnrollmentDate());

    return enrollmentDto;
  }
}
