package tk.zielony.codechallange.api;

import tk.zielony.codechallange.postcomments.CommentsEvent;
import tk.zielony.codechallange.postlist.PostListEvent;

/**
 * Created by Marcin on 2016-05-12.
 */
public class PostEndpoint {
    private PostEndpoint() {

    }

    public static void list() {
        DataAPI.get(DataAPI.POST, new PostListEvent());
    }

    public static void get(int id) {
        DataAPI.get(DataAPI.POST + "/" + id, new CommentsEvent());
    }
}
