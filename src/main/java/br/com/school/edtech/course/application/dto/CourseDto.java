package br.com.school.edtech.course.application.dto;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.Status;
import br.com.school.edtech.shared.model.exceptions.ValidationMessage;
import br.com.school.edtech.shared.model.exceptions.Validations;
import br.com.school.edtech.user.domain.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class CourseDto {

  @NotNull
  private String name;

  @Size(max = 10, message = "The course must have a maximum of 10 characters.")
  @NotNull
  private String code;

  @JsonProperty(access = Access.READ_ONLY)
  @NotNull
  private User instructor;

  @JsonProperty(access = Access.WRITE_ONLY)
  @NotNull
  private UUID idInstructor;;

  @NotNull
  private String description;

  @JsonProperty(access = Access.READ_ONLY)
  private Status status;

  @JsonProperty(access = Access.READ_ONLY)
  private Instant creationDate;

  @JsonProperty(access = Access.READ_ONLY)
  private Instant inactivationDate;

  public static CourseDto map(Course course) {
    Validations.isNotNull(course, ValidationMessage.REQUIRED_COURSE);

    CourseDto courseDto = new CourseDto();
    courseDto.setName(course.getName());
    courseDto.setCode(course.getCode());
    courseDto.setInstructor(course.getInstructor());
    courseDto.setDescription(course.getDescription());
    courseDto.setStatus(course.getStatus());
    courseDto.setCreationDate(course.getCreationDate());
    courseDto.setInactivationDate(course.getInactivationDate());

    return courseDto;
  }
}
