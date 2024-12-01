package com.social.api.service;

import com.social.api.entity.Comment;
import com.social.api.entity.Notification;
import com.social.api.entity.Post;
import com.social.api.entity.User;

import java.util.List;

public interface NotificationService {

    Notification getNotificationById(Long notificationId);
    Notification getNotificationByReceiverAndOwningPostAndType(User receiver, Post owningPost, String type);
    void sendNotification(User receiver, User sender, Post owningPost, Comment owningComment, String type);
    void removeNotification(User receiver, Post owningPost, String type);
    List<Notification> getNotificationsForAuthUserPaginate(Integer page, Integer size);
    void markAllSeen();
    void markAllRead();
    void deleteNotification(User receiver, Post owningPost, String type);
    void deleteNotificationByOwningPost(Post owningPost);
    void deleteNotificationByOwningComment(Comment owningComment);
}
