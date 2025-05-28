package org.example;
import java.io.BufferedReader;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;

public class Post {

    private UUID postUUID;

    protected String titel;

    protected String text;

    protected String subPostName;

    private int likeVote;
    private Account userPost = new Account("", "", "");

    private List<Comment> postCommentList;

    private List<UUID> upVoteUserList;

    private List<UUID> downVoteUserList;


    static List<Account> userLikesList = new ArrayList<>();

    static List<Post> allPost = new ArrayList<>();


    public Post(String titel, String text, Account userPost,String subPostName ) {
        this.titel = titel;
        this.text = text;
        this.userPost = userPost;
        this.subPostName = subPostName;
        this.postCommentList = new ArrayList<>();
        this.postUUID = UUID.randomUUID();
        this.upVoteUserList = new ArrayList<>();
        this.downVoteUserList = new ArrayList<>();
    }

    public void addUserLikes(Account acc, Post post) {
        userLikesList.add(acc);
        acc.postLikesList.add(post);
    }

    public void upVote( Post post , Account acc) {
        if (acc.checkPostVoteUp(post)) {
            this.likeVote = this.likeVote + 1;
        }
        else {
            for (int i =0 ; i < acc.retAccVoteUpList().size() ; i++){
                if (acc.retAccVoteUpList().get(i).equals(post.retPostUUID())){
                    acc.retAccVoteUpList().remove(i);
                }
            }
            this.likeVote = this.likeVote -1;
        }

    }

    public void downVote( Post post , Account acc) {
        if (acc.checkPostVoteDown(post)) {
            this.likeVote = this.likeVote - 1;
        }
        else {
            for (int i =0 ; i < acc.retAccVoteDownList().size() ; i++){
                if (acc.retAccVoteDownList().get(i).equals(post.retPostUUID())){
                    acc.retAccVoteDownList().remove(i);
                }
            }
            this.likeVote = this.likeVote + 1;
        }

    }

    public void addText() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String bodyText;
        System.out.println("Type your text(press Enter without typing anything to finish !)\n");
        while ((bodyText = reader.readLine()) != null && !bodyText.isEmpty()) {
            // Process each line as needed
            this.text = bodyText;
        }
    }

    public void addTitel() {
        System.out.print(" Post Titel :");
        Scanner in = new Scanner(System.in);
        this.titel = in.nextLine();
    }

    public String retTitel() {
        return this.titel;
    }

    public String retText() {
        return this.text;
    }
    public UUID retPostUUID(){
        return this.postUUID;
    }

    public String retUserPost() {
        return this.userPost.retUserName();
    }

    public int retLikeVote() {
        return this.likeVote;
    }

    public void showPost(Post post) throws IOException {
        System.out.println("username : " + post.userPost.retUserName());
        System.out.println("Title : " + post.titel);
        System.out.println("Text : " + "\n" + "       " + post.text + "\n" + "Vote : " + post.likeVote + "\n");
        System.out.println("UpVote = +   DownVote = -  1. Add Comment  2.All Comment 3.back");
        Scanner in = new Scanner(System.in);
        while (true) {
            String input = in.next();
            if (input.equals("+")) {

                    post.upVote(post, post.userPost);

                showPost(post);
            } else if (input.equals("-")) {

                    post.downVote(post , post.userPost);
                    showPost(post);
                break;
            } else if (input.equals("1")) {
                creatComment("", post.userPost, post);
                showPost(post);
                break;
            } else if (input.equals("2")) {
                showAllPostComment(post);
                break;
            } else if (input.equals("3")) {
                break;
            } else {
                System.out.println("errore");
            }
        }
    }

    public void showPostSearch(Post post , Account acc) throws IOException {
        System.out.println("username : " + post.userPost.retUserName());
        System.out.println("Title : " + post.titel);
        System.out.println("Text : " + "\n" + "       " + post.text + "\n" + "Vote : " + post.likeVote + "\n");
        System.out.println("UpVote = +   DownVote = -  1. Add Comment  2.All Comment 3.back");
        Scanner in = new Scanner(System.in);
        while (true) {
            String input = in.next();
            if (input.equals("+")) {

                post.upVote(post , acc);

                break;
            } else if (input.equals("-")) {

                post.downVote(post,acc);
                break;
            } else if (input.equals("1")) {
                creatComment("",acc , post);
                showPost(post);
                break;
            } else if (input.equals("2")) {
                showAllPostComment(post);
                break;
            } else if (input.equals("3")) {
                break;
            } else {
                System.out.println("errore");
            }
        }
    }

    public static void showAllPosts(){
            for (int i = Post.allPost.size() - 1; i >= 0; i--) {
                System.out.println(" Num = " + i);
                System.out.println(" Subredit : " + Post.allPost.get(i).subPostName);
                System.out.println(" usename : " + Post.allPost.get(i).retUserPost());
                System.out.println(" Titel : " + Post.allPost.get(i).titel);
                System.out.println(" Text : " + "\n" + "       " + Post.allPost.get(i).text + "\n" + " Vote : " +
                        Post.allPost.get(i).retLikeVote() + "\n\n\n");
            }
    }


    public static void creatComment(String com, Account acc, Post post) throws IOException {
        Comment comment = new Comment("", acc, post);
        comment.addComment();
        Comment.allComment.add(comment);
        Comment.userCommented.add(acc);
        Account.postCommentList.add(comment);
        post.postCommentList.add(comment);
    }


    public void showAllPostComment(Post post) {
        for (int i = 0; i < post.postCommentList.size(); i++) {
            System.out.println("Num : " + i);
            System.out.println("username : " + post.postCommentList.get(i).retUserComment());
            System.out.println("Comment : " + "       " + post.postCommentList.get(i).retComment());
            System.out.println("vote : " + post.postCommentList.get(i).retVoteComment());
        }
        Scanner in = new Scanner(System.in);
        int numComment = Integer.parseInt(in.next());
        if (numComment > post.postCommentList.size()){
            System.out.println("There is no comment with this num");
        }
        else{
            post.postCommentList.get(numComment).showComment(post.postCommentList.get(numComment));
        }
    }
    public void showComment(Post post , int commentNum){
        System.out.println(" username : " +post.postCommentList.get(commentNum).retUserComment());
        System.out.println(" Comment : " + "       " + post.postCommentList.get(commentNum).retComment());
        System.out.println("UpVote : +       DownVote : -   1.back" );
        Scanner in = new Scanner(System.in);
        while(true) {
            String input = in.next();
            if (input.equals("+")) {
                post.postCommentList.get(commentNum).upVote();
            } else if (input.equals("-")){
                post.postCommentList.get(commentNum).downVote();
            }
        }
    }
}


