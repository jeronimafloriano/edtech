package br.com.school.edtech.course.application.api;

import br.com.school.edtech.course.application.dto.CourseDto;
import br.com.school.edtech.course.application.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Course", description = "Course manager")
@RestController
@RequestMapping("/courses")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @Operation(description = "Search all courses")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User created")})
  @GetMapping()
  public ResponseEntity<List<CourseDto>> getAll(
      @Parameter(description = "Paging and sorting parameters") @ParameterObject
          Pageable pageable,
      @Parameter(description = "Filter for course status")
      @RequestParam(required = false)
          String filter) {
    List<CourseDto> courses = courseService.getAll(pageable, filter);
    return new ResponseEntity<>(courses, HttpStatus.OK);
  }

  @Operation(description = "Create a course")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Course created")})
  @PostMapping()
  public CourseDto create(
      @Parameter(description = "Course information") @RequestBody
          CourseDto courseDto) {
    return courseService.create(courseDto);
  }

  @Operation(description = "Set a course as inactive")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Inactivated course")})
  @PutMapping("/{code}")
  public CourseDto inactivate(
      @Parameter(description = "Course to be inactivated") @PathVariable String code) {
    return courseService.inactivate(code);
  }
}
