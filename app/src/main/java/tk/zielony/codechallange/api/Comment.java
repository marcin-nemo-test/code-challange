package tk.zielony.codechallange.api;

/**
 * Created by Marcin on 2016-05-12.
 */
public class Comment {
    int postId;
    int id;
    String name;
    String email;
    String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // id should probably be read-only
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostId() {
        return postId;
    }

    // id should probably be read-only
    public void setPostId(int postId) {
        this.postId = postId;
    }
}
