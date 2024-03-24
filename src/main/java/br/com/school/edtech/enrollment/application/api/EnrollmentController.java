package br.com.school.edtech.enrollment.application.api;

import br.com.school.edtech.enrollment.application.dto.EnrollmentDto;
import br.com.school.edtech.enrollment.application.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Enrollment", description = "Enrollment manager")
@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

  private final EnrollmentService enrollmentService;

  public EnrollmentController(EnrollmentService enrollmentService) {
    this.enrollmentService = enrollmentService;
  }

  @Operation(description = "Create an enrollment")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Enrollment created")})
  @PostMapping()
  public EnrollmentDto register(
      @Parameter(description = "Enrollment information") @RequestBody
          EnrollmentDto enrollmentDto) {
    return enrollmentService.register(enrollmentDto);
  }
}
