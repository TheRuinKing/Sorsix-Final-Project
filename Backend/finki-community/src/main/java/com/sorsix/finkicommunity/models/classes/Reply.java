package com.sorsix.finkicommunity.models.classes;

import javax.persistence.*;
/**@Id should be a combination on
 * Long basePostID (Id of the post that was replied to)
 * Long replyPostID (Id of the reply post)
 * */
@Entity
@Table(name="replies")
public class Reply {
    @Id
    @Column(name="base_post_id")
    private long basePostId;
    @Id
    @Column(name="reply_post_id")
    private long replyPostId;

    public Reply() {}

    public Reply(long basePostId, long replyPostId) {
        this.basePostId = basePostId;
        this.replyPostId = replyPostId;
    }

    public long getBasePostId() {
        return basePostId;
    }

    public long getReplyPostId() {
        return replyPostId;
    }
}
