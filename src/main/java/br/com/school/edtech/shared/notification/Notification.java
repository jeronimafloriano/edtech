package br.com.school.edtech.shared.notification;

import br.com.school.edtech.feedback.domain.model.CourseReview;

public interface Notification {

  void send(String recipientEmail, String subject, String body);

}
