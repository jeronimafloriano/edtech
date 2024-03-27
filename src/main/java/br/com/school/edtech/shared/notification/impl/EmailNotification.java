package br.com.school.edtech.shared.notification.impl;

import br.com.school.edtech.feedback.domain.model.CourseReview;
import br.com.school.edtech.shared.notification.Notification;
import br.com.school.edtech.user.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class EmailNotification implements Notification {

  @Override
  public void send(String recipientEmail, String subject, String body) {
      System.out.println(
          "Simulating sending email to [%s]:\n".formatted(recipientEmail)
      );
      System.out.println("""
          Subject: %s
          Body: %s
         """.formatted(subject, body));
  }
}
