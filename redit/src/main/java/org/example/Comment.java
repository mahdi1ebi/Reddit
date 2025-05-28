package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Comment {

    private UUID commentID;
    private String comment;
    private Account userCreatComment;
    private int likeComment;

    static List <Comment> allComment = new ArrayList<>();
    static List <Account> userCommented = new ArrayList<>();


    public Comment(String comment,Account acc , Post post ){
        this.userCreatComment = acc;
        this.comment = comment;
        this.likeComment = 0;
    }
    public String retUserComment(){
        return this.userCreatComment.retUserName();
    }

    public void upVote(){
        this.likeComment = this.likeComment + 1;
    }
    public void downVote(){
        this.likeComment = this.likeComment - 1;

    }

    public int retVoteComment(){
        return this.likeComment;
    }

    public String retComment(){
        return this.comment;
    }
    public void addComment() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String bodyText;
        System.out.println("Type your comment (press Enter without typing anything to finish !)\n");
        while ((bodyText = reader.readLine()) != null && !bodyText.isEmpty()) {
            // Process each line as needed
            this.comment = bodyText;
        }
    }

    public void showComment(Comment com){
        System.out.println("username : " + com.retUserComment());
        System.out.println("Comment : " + "      " + "\n\n" + com.retComment());
        System.out.println("Vote : " + com.retVoteComment());
        System.out.println("UpVote : +       DownVote : -   1.back" );
        Scanner in = new Scanner(System.in);
        while (true) {
            String input = in.next();
            if (input.equals("+")) {
                com.upVote();
                showComment(com);
                break;
            }
            else if (input.equals("-")) {
                com.downVote();
                showComment(com);
                break;
            }
            else if (input.equals("1")){
                break;
            }
        }
    }
}
