import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class HW08_20220808006 {

}

class User {

    private int id;
    private String userName;
    private String email;
    private Set<User> followers;
    private Set<User> following;
    private Set<Post> likedPosts;
    private Map<User, Queue<Message>> messages;

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
        id = this.hashCode();
        followers = new HashSet<>();
        following = new HashSet<>();
        likedPosts = new HashSet<>();
        messages = new HashMap<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void message(User recipient, String content) {

        if (!messages.containsKey(recipient)) {
            messages.put(recipient, new LinkedList<>());
        } else {
            //
        }

    }

    public void read(User user) {
        if (messages.containsKey(user)) {
            for (Message message : messages.get(user)) {
                System.out.println(message);

            }
        }
    }

    public void follow(User user) {
        if (following.contains(user)) {
            following.remove(user);
            user.followers.remove(this);
        } else {
            following.add(user);
            user.followers.add(this);
        }
    }

    public void like(Post post) {
        if (likedPosts.contains(post)) {
            likedPosts.remove(post);
            post.likedBy(this);
        } else {
            likedPosts.add(post);
            post.likedBy(this);
        }
    }

    public Post post(String content) {
        return new Post(content);
    }

    public Comment comment(Post post, String content) {

        Comment newComment = new Comment(content);
        post.commentBy(this, newComment);
        return newComment;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (getClass() != obj.getClass() || obj == null) {
                return false;
            } else {
                User user = (User) obj;
                return id == user.getId();
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public int getId() {
        return id;
    }

}

class Message {

    private boolean seen;
    private Date dateSent;
    private String content;
    private User sender;

    public Message(User sender, String content) {
        this.content = content;
        this.sender = sender;
        seen = false;
        dateSent = new Date();
    }

    public String read(User reader) {
        if (!sender.equals(reader)) {
            seen = true;
        }
        System.out.println("Sent at: " + dateSent);
        return content;
    }

    public boolean hasRead() {
        return seen;
    }

}

class Post {
    private Date datePosted;
    private String content;
    private Set<User> likes;
    private Map<User, List<Comment>> comments;

    public Post(String content) {
        this.content = content;
        datePosted = new Date();
        likes = new HashSet<>();
        comments = new HashMap<>();
    }

    public boolean likedBy(User user) {
        if (likes.contains(user)) {
            likes.remove(user);
            return false;
        } else {
            likes.add(user);
            return true;
        }
    }

    public boolean commentBy(User user, Comment comment) {
        if (!comments.containsKey(user)) {
            comments.put(user, new ArrayList<>());
            comments.get(user).add(comment);
            return true;
        } else {
            return false;
        }
    }

    public String getContent() {
        System.out.println("Posted at: " + datePosted);
        return content;
    }

    public Comment getComment(User user, int index) {
        if (comments.size() >= 1 && index < comments.get(user).size()) {
            return comments.get(user).get(index);
        } else {
            return null;
        }
    }

    public int getCommentCount() {

        int counter = 0;
        for (List<Comment> list : comments.values()) {
            counter += list.size();
        }
        return counter;
    }

    public int getCommentCountByUser(User user) {
        if (comments.containsKey(user)) {
            return comments.get(user).size();
        } else {
            return 0;
        }
    }
}

class Comment extends Post {

    public Comment(String content) {
        super(content);
    }

}

class SocialNetwork {

    private static Map<User, List<Post>> postsByUsers = new HashMap<>();

    public User register(String username, String email) {
        User user = new User(username, email);
        if (!postsByUsers.containsKey(user)) {
            postsByUsers.put(user, new ArrayList<>());
            return user;
        } else {
            return null;
        }
    }

    public static Post post(User user, String content) {

        Post post = new Post(content);

        if (postsByUsers.containsKey(user)) {
            postsByUsers.get(user).add(post);
            return post;
        } else {
            return null;
        }
    }

    public static User getUser(String email) {

        int emailNew = Objects.hash(email);

        for (User usr : postsByUsers.keySet()) {
            if (usr.getId() == emailNew) {
                return usr;
            }
        }
        return null;
    }

    public static Set<Post> getFeed(User user) {
        Set<Post> feeding = new HashSet<>();
        return feeding;
    }

    public static Map<User, String> search(String keyword) {

        Map<User, String> searching = new HashMap<>();
        for (User user : postsByUsers.keySet()) {
            if (user.getUserName().contains(keyword)) {
                searching.put(user, user.getUserName());
            }
        }
        return searching;
    }

    public static <K, V> Map<V, Set<K>> reverseMap(Map<K, V> map) {
        Map<V, Set<K>> reverseMap = new HashMap<>();
        return reverseMap;
    }
}