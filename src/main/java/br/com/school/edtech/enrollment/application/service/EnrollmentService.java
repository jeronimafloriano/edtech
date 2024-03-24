package br.com.school.edtech.enrollment.application.service;

import br.com.school.edtech.enrollment.application.dto.EnrollmentDto;

public interface EnrollmentService {

  EnrollmentDto register(EnrollmentDto enrollmentDto);
}
