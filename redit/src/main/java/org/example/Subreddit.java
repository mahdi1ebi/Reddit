package org.example;
import java.io.IOException;
import java.util.*;



public class Subreddit {
    private String subName;
    private UUID subUUID;
    private UUID admin;

    static List<Subreddit> allSub = new ArrayList<>();

    private List<Account> allSubMember;
    static List<UUID> adminList;
    private List<UUID> postList;

    private List <Post> subPostList;



    static HashMap<UUID , String > SubMapPost = new HashMap<>();

    public Subreddit(String subname , UUID admin ){
        this.subName = "r/"+subname;
        this.admin = admin;
        this.subUUID = UUID.randomUUID();
        adminList = new ArrayList<>();
        postList = new ArrayList<>();
        subPostList = new ArrayList<>();
        subPostList = new ArrayList<>();
        allSubMember = new ArrayList<>();
    }

    public List<UUID> retPostList(){
        return this.postList;
    }

    public String retSubName(){
        return this.subName;
    }

    public UUID retSubUUID(){
        return this.subUUID;
    }

    public void addAdmin(Account acc ){
        adminList.add(acc.retAccUUID());

    }

    public static void hashMapPostSub(Post post , Subreddit sub){

        SubMapPost.put(post.retPostUUID(),sub.subName);
    }

    public static void showSub(Subreddit sub) throws IOException {
        while (true) {
            System.out.println("Subreddit Name : " + sub.subName);
            for (int i = 0; i < sub.adminList.size(); i++) {
                if (Account.userList.get(i).retAccUUID().equals(sub.adminList.get(i))) {
                    System.out.print("Admin : " + Account.userList.get(i).retUserName() + "\n");
                }
            }
            System.out.println(" 1.Posts \n2.members\n 2.Back");
            Scanner in = new Scanner(System.in);
            String input = in.next();
            if (input.equals("1")) {
                for (int i = 0; i < sub.postList.size(); i++) {
                    if (Post.allPost.get(i).retPostUUID().equals(sub.postList.get(i))) {
                        sub.subPostList.add(Post.allPost.get(i));
                    }
                }
                for (int i = 0; i < sub.subPostList.size(); i++) {
                    System.out.println(" Num = " + i);
                    System.out.println(" username : " + sub.subPostList.get(i).retUserPost());
                    System.out.println(" Titel : " + sub.subPostList.get(i).titel);
                    System.out.println(" Text : " + "\n" + "       " + sub.subPostList.get(i).text + "\n" + " Vote : " +
                            sub.subPostList.get(i).retLikeVote() + "\n\n\n");
                }
                int numPost = Integer.parseInt(in.next());
                if (numPost > sub.subPostList.size()) {
                    System.out.println("There is no post with this number");
                } else {
                    sub.subPostList.get(numPost).showPost(Post.allPost.get(numPost));
                }

            }
            else if(input.equals("2")){
                sub.showAllSubMembers();
            }
            else if (input.equals("3")){
                break;
            }
        }
    }

    public void addMembers(Account acc){
        Boolean find = false;
        for (int i = 0 ; i < allSubMember.size(); i++){
            if (allSubMember.get(i).retAccUUID().equals(acc.retAccUUID())){

            }
            find |= allSubMember.get(i).retAccUUID().equals(acc.retAccUUID());
        }
        if (!find){
            allSubMember.add(acc);
        }
    }

    public void showAllSubMembers(){
        for (int i =0 ; i < allSubMember.size() ; i++){
            System.out.println(i + allSubMember.get(i).retUserName());
        }
    }

    public void subAdmin(Account acc , Subreddit sub){
        while (true) {
            if (acc.retAccUUID().equals(this.admin)) {
                System.out.println(" 1.Manage Posts (Delete) \n 2.Manage Members (Delete) \n  3.Back");
                Scanner in = new Scanner(System.in);
                String input = in.next();
                if (input.equals("1")){
                    for (int i = 0; i < sub.postList.size(); i++) {
                        if (Post.allPost.get(i).retPostUUID().equals(sub.postList.get(i))) {
                            sub.subPostList.add(Post.allPost.get(i));
                        }
                    }
                    for (int i = 0; i < sub.subPostList.size(); i++) {
                        System.out.println(" Num = " + i);
                        System.out.println(" username : " + sub.subPostList.get(i).retUserPost());
                        System.out.println(" Titel : " + sub.subPostList.get(i).titel);
                        System.out.println(" Text : " + "\n" + "       " + sub.subPostList.get(i).text + "\n" + " Vote : " +
                                sub.subPostList.get(i).retLikeVote() + "\n\n\n");
                    }
                    int numPost = Integer.parseInt(in.next());
                    if (numPost > sub.subPostList.size()) {
                        System.out.println("There is no post with this number");
                    } else {
                        for (int i =0 ; i < Post.allPost.size() ; i++){
                            if(Post.allPost.get(i).retPostUUID().equals(sub.subPostList.get(numPost).retPostUUID())) {
                                Post.allPost.remove(i);
                            }
                        }
                        for ( int i =0 ; i < acc.retPostCreatList().size() ; i++){
                            if (acc.retPostCreatList().get(i).retPostUUID().equals(sub.subPostList.get(numPost).retPostUUID())){
                                acc.retPostCreatList().remove(i);
                            }
                        }
                        sub.subPostList.remove(numPost);

                    }
                }
                else if (input.equals("2")){
                    sub.showAllSubMembers();
                    int numMember = Integer.parseInt(in.next());
                    if (numMember > sub.allSubMember.size()){
                        System.out.println("Thers id no Member with this number");
                    }
                    else {
                        for ( int i = 0 ; i < Account.userList.size() ; i++){
                            if (sub.allSubMember.get(i).retAccUUID().equals(Account.userList.get(i).retAccUUID())){
                                for (int j = 0 ; j < Account.userList.get(i).retSubJoensList().size() ; j++){
                                    if (sub.retSubUUID().equals(Account.userList.get(i).retSubJoensList().get(j))){
                                        Account.userList.get(i).retSubJoensList().remove(j);
                                    }
                                }
                            }
                        }
                        sub.allSubMember.remove(numMember);
                    }
                }
                else if (input.equals("3")){
                    break;
                }
            } else {
                System.out.println("Your Not the admin");
            }
        }
    }

    public void showSubsPost(Subreddit sub){
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < sub.postList.size(); i++) {
            if (Post.allPost.get(i).retPostUUID().equals(sub.postList.get(i))) {
                sub.subPostList.add(Post.allPost.get(i));
            }
        }
        for (int i = 0; i < sub.subPostList.size(); i++) {
            System.out.println(" Num = " + i);
            System.out.println(" username : " + sub.subPostList.get(i).retUserPost());
            System.out.println(" Titel : " + sub.subPostList.get(i).titel);
            System.out.println(" Text : " + "\n" + "       " + sub.subPostList.get(i).text + "\n" + " Vote : " +
                    sub.subPostList.get(i).retLikeVote() + "\n\n\n");
        }
        int numPost = Integer.parseInt(in.next());
        if (numPost > sub.subPostList.size()) {
            System.out.println("There is no post with this number");
        }
    }

}
