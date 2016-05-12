package tk.zielony.codechallange.api;

import tk.zielony.codechallange.postcomments.CommentsEvent;
import tk.zielony.codechallange.postlist.PostListEvent;

/**
 * Created by Marcin on 2016-05-12.
 */
public class CommentEndpoint {
    private CommentEndpoint() {
    }

    public static void list(int postId) {
        DataAPI.get(DataAPI.COMMENT + "?postId=" + postId, new CommentsEvent());
    }

}
