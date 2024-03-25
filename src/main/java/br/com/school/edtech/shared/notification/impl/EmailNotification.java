package br.com.school.edtech.shared.notification.impl;

import br.com.school.edtech.feedback.domain.model.CourseReview;
import br.com.school.edtech.shared.notification.Notification;
import br.com.school.edtech.user.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class EmailNotification implements Notification {

  @Override
  public void send(CourseReview courseReview) {
    User instructor = courseReview.getCourse().getInstructor();
    System.out.println(String.format("Send email to: %s", instructor));
  }
}
