package tk.zielony.codechallange.api;

/**
 * Created by Marcin on 2016-05-12.
 */
public class PostEndpoint {
    private PostEndpoint() {

    }

    public static void list() {
        DataAPI.get(DataAPI.POST, new ListEvent());
    }

    public static void get(int id) {
        DataAPI.get(DataAPI.POST + "/" + id, new CommentsEvent());
    }
}
