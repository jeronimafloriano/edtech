package br.com.school.edtech.course.application.dto;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.Status;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.shared.exceptions.Validations;
import br.com.school.edtech.user.application.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseDto {

  public static final String MAX_CHARACTERES = "The course must have a maximum of 10 characters.";

  @JsonProperty(access = Access.READ_ONLY)
  private UUID id;

  @NotNull
  private String name;

  @Size(max = 10, message = MAX_CHARACTERES)
  @NotNull
  private String code;

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private UserDto instructor;

  @JsonProperty(access = Access.WRITE_ONLY)
  @NotNull
  private UUID idInstructor;

  @NotNull
  private String description;

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Status status;

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Instant creationDate;

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Instant inactivationDate;

  public CourseDto(String name, String code, UUID idInstructor, String description) {
    this.name = name;
    this.code = code;
    this.idInstructor = idInstructor;
    this.description = description;
  }

  public static CourseDto map(Course course) {
    Validations.isNotNull(course, ValidationMessage.REQUIRED_COURSE);

    CourseDto courseDto = new CourseDto();
    courseDto.setId(course.getId().getValue());
    courseDto.setName(course.getName());
    courseDto.setCode(course.getCode());
    courseDto.setInstructor(UserDto.map(course.getInstructor()));
    courseDto.setDescription(course.getDescription());
    courseDto.setStatus(course.getStatus());
    courseDto.setCreationDate(course.getCreationDate());
    courseDto.setInactivationDate(course.getInactivationDate());

    return courseDto;
  }
}
