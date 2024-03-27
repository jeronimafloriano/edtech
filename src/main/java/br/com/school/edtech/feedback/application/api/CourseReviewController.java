package br.com.school.edtech.feedback.application.api;

import br.com.school.edtech.feedback.application.dto.CourseReviewDto;
import br.com.school.edtech.feedback.application.service.CourseReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Course Review", description = "Course Review manager")
@RestController
@RequestMapping("/courses-review")
public class CourseReviewController {

  private final CourseReviewService courseReviewService;

  public CourseReviewController(CourseReviewService courseReviewService) {
    this.courseReviewService = courseReviewService;
  }

  @Operation(description = "Report with NPS of each course that has more than four enrollments")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Report received")})
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  @GetMapping()
  public ResponseEntity<Map<String, Double>> getNPS() {
    Map<String, Double> courses = courseReviewService.getNPS();
    return new ResponseEntity<>(courses, HttpStatus.OK);
  }

  @Operation(description = "Register a review")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Review created")})
  @PostMapping()
  public CourseReviewDto register(
      @Parameter(description = "Course Review information") @RequestBody
          CourseReviewDto courseReviewDto) {
    return courseReviewService.register(courseReviewDto);
  }
}
