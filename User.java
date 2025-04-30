import java.util.ArrayList;

public class User {

    String username;
    String password;
    ArrayList<Post> posts = new ArrayList<>();

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    void addPost(String content) {
        posts.add(new Post(content));
    }

    void viewPosts() {
        if (posts.isEmpty()) {
            System.out.println(username + " hasn't posted any posts yet...");
        }
        else {
            System.out.println(username + " posts: ");
            for (Post post : posts) {
                post.viewPost();
            }
        }
    }
}
