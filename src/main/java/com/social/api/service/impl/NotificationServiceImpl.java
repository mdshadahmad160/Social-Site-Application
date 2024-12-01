package com.social.api.service.impl;

import com.social.api.entity.Comment;
import com.social.api.entity.Notification;
import com.social.api.entity.Post;
import com.social.api.entity.User;
import com.social.api.exception.NotificationNotFoundException;
import com.social.api.repository.NotificationRepository;
import com.social.api.service.NotificationService;
import com.social.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {


    private final NotificationRepository notificationRepository;

    private final UserService userService;

    @Override
    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(NoClassDefFoundError::new);
    }

    @Override
    public Notification getNotificationByReceiverAndOwningPostAndType(User receiver, Post owningPost, String type) {

        return notificationRepository.findByReceiverAndOwningPostAndType(receiver, owningPost, type)
                .orElseThrow(NotificationNotFoundException::new);
    }

    @Override
    public void sendNotification(User receiver, User sender, Post owningPost, Comment owningComment, String type) {

        try {
            Notification targetNotification = getNotificationByReceiverAndOwningPostAndType(receiver, owningPost, type);
            targetNotification.setSender(sender);
            targetNotification.setIsSeen(false);
            targetNotification.setIsRead(false);
            targetNotification.setDateUpdated(new Date());
            targetNotification.setDateLastModified(new Date());
            notificationRepository.save(targetNotification);
        }

    }

    @Override
    public void removeNotification(User receiver, Post owningPost, String type) {

    }

    @Override
    public List<Notification> getNotificationsForAuthUserPaginate(Integer page, Integer size) {
        return null;
    }

    @Override
    public void markAllSeen() {

    }

    @Override
    public void markAllRead() {

    }

    @Override
    public void deleteNotification(User receiver, Post owningPost, String type) {

    }

    @Override
    public void deleteNotificationByOwningPost(Post owningPost) {

    }

    @Override
    public void deleteNotificationByOwningComment(Comment owningComment) {

    }
}
