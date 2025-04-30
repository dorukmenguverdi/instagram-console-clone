import java.util.ArrayList;

public class Post {
    String content;
    ArrayList<String> comments = new ArrayList<>();

    Post(String content) {
        this.content = content;
    }

    void addComment(String comment) {
        comments.add(comment);
    }

    void viewPost(){
        System.out.println("- " + content);
        if (comments.isEmpty()) {
            System.out.println(" (No comments yet)");
        }
        else {
            System.out.println(" Comments: ");
            for (String comment : comments) {
                System.out.println("    • " + comment);
            }
        }
    }
}
