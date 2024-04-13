package org.example;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class  Account {

    private String email;
    private String password;
    private String username;
    private UUID accountUUID;

    private List <Post> postCreatList = new ArrayList<>();

    static List <Post> postLikesList = new ArrayList<>();
    static List <Comment> postCommentList = new ArrayList<>();
    static List <Account> userList = new ArrayList<>();
    private List <UUID> subjoens ;

    private List <UUID> accVotePostUp;

    private List <UUID> accVotePostDown;

    private List <Subreddit> adminSubs;
    static HashMap<String, String> emailMAPpass = new HashMap<>();


    public Account(String email, String password, String username) {

        this.email = email;
        this.password = hash(password);
        this.accountUUID = UUID.randomUUID();
        this.username = username;
        emailMAPpass.put(email, password);
        this.accVotePostUp = new ArrayList<>();
        this.accVotePostDown = new ArrayList<>();
        this.subjoens = new ArrayList<>();
        this.adminSubs = new ArrayList<>();
    }

    public static String hash(String password) {
        long mod = 1000000007, mabna = 457;
        long ans = 0, ans2 = 0, pow = 1, pow2 = 1;
        for (int i = 0; i < password.length(); i++) {
            int save = password.charAt(i) - '0';
            ans = (ans + (pow * save)) % mod;
            pow = (pow * mabna) % mod;

        }
        return String.valueOf(ans);
    }


    public List<Post> retPostCreatList(){
        return this.postCreatList;
    }
    public String retUserName() {
        return "u/" + username;
    }

    public String retUserEmail() {
        return email;
    }

    public UUID retAccUUID() {
        return this.accountUUID;
    }

    public List<UUID> retSubJoensList(){
        return this.subjoens;
    }


    public boolean check(String pass) {
        if (this.password.equals(hash(pass))) {
            return true;
        } else
            return false;
    }

    public void changePassword(String newPassword) {

        this.password = hash(newPassword);
    }

    public static boolean emailValidator(String email) {

        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static void showAllAccPost(Account acc) throws IOException {
        for (int i = 0; i < acc.postCreatList.size(); i++) {
            Post post = new Post("", "", acc ,"");
            post = acc.postCreatList.get(i);
            System.out.println(" Num = " + i);
            System.out.println(" usename : " + acc.retUserName());
            System.out.println(" Titel : " + post.retTitel());
            System.out.println(" Text : " + "\n" + "       " + post.retText() + "\n" + " Vote : " +
                    post.retLikeVote() + "\n\n\n");
        }
        Scanner in = new Scanner(System.in);
        int numAccPost = Integer.parseInt(in.next());
        if (numAccPost > acc.postCreatList.size()) {
            System.out.println("There is no post with this number");
        } else {
            acc.postCreatList.get(numAccPost).showPost(acc.postCreatList.get(numAccPost));
        }
    }

    public static void serachShowAllAccPost( Account acc ,Account accSerch) throws IOException {
        for (int i = 0; i < accSerch.postCreatList.size(); i++) {
            Post post = new Post("", "", acc ,"");
            post = accSerch.postCreatList.get(i);
            System.out.println(" Num = " + i);
            System.out.println(" usename : " + accSerch.retUserName());
            System.out.println(" Titel : " + post.retTitel());
            System.out.println(" Text : " + "\n" + "       " + post.retText() + "\n" + " Vote : " +
                    post.retLikeVote() + "\n\n\n");
        }
        Scanner in = new Scanner(System.in);
        int numAccPost = Integer.parseInt(in.next());
        if (numAccPost > accSerch.postCreatList.size()) {
            System.out.println("There is no post with this number");
        } else {
            accSerch.postCreatList.get(numAccPost).showPostSearch(accSerch.postCreatList.get(numAccPost),acc);
        }
    }

    public static void deletAccPost(Account acc ) throws IOException {
        for (int i = 0; i < acc.postCreatList.size(); i++) {
            Post post = new Post("", "", acc, "");
            post = acc.postCreatList.get(i);
            System.out.println(" Num = " + i);
            System.out.println(" usename : " + acc.retUserName());
            System.out.println(" Titel : " + post.retTitel());
            System.out.println(" Text : " + "\n" + "       " + post.retText() + "\n" + " Vote : " +
                    post.retLikeVote() + "\n\n\n");
        }
        Scanner in = new Scanner(System.in);
        int numAccPost = Integer.parseInt(in.next());
        if (numAccPost > acc.postCreatList.size()) {
            System.out.println("There is no post with this number");
        } else {
            acc.postCreatList.remove(numAccPost);
            for (int i = 0 ; i <Post.allPost.size() ; i++){
                if (acc.postCreatList.get(numAccPost).retPostUUID().equals(Post.allPost.get(i).retPostUUID())){
                    Post.allPost.remove(i);
                }
            }
            for (int i = 0; i < Subreddit.allSub.size(); i++) {

                for (int j = 0 ; j < Subreddit.allSub.get(i).retPostList().size() ; j++){
                    if (acc.postCreatList.get(numAccPost).retPostUUID().equals(Subreddit.allSub.get(i).retPostList().get(j))){
                        Subreddit.allSub.get(i).retPostList().remove(j);
                    }
                }

            }

            //for (int i = 0 ; i < )


            System.out.println("Your post has been removed");
        }

    }
    public void addPost(Post post, Account acc) {
        acc.postCreatList.add(post);
    }

    public void addSub(Subreddit sub ) {
        Boolean find = false;
        for (int i = 0; i < subjoens.size(); i++) {

            find |= subjoens.get(i).equals(sub.retSubUUID());

        }
        if (!find){
            subjoens.add(sub.retSubUUID());
        }
    }

    public void showAccSubs(Account acc){
        Boolean find = false;
        for (int i = 0 ; i< subjoens.size();i++){
            if (subjoens.get(i).equals(Subreddit.allSub.get(i).retSubUUID())){
                System.out.println(Subreddit.allSub.get(i).retSubName());
            }
            find |= subjoens.get(i).equals(Subreddit.allSub.get(i).retSubUUID());
        }
        if(!find){
            System.out.println("No Subs Join");
        }
    }

    public boolean checkPostVoteUp(Post post){
        for (int i = 0 ; i < this.accVotePostUp.size() ; i++){
            if (this.accVotePostUp.get(i).equals(post.retPostUUID())){
                return false;
            }
        }
        this.accVotePostUp.add(post.retPostUUID());
        return true;
    }

    public boolean checkPostVoteDown(Post post){
        for (int i = 0 ; i < this.accVotePostDown.size() ; i++){
            if (this.accVotePostDown.get(i).equals(post.retPostUUID())){
                return false;
            }
        }
        this.accVotePostDown.add(post.retPostUUID());
        return true;
    }

    public List<UUID> retAccVoteDownList(){
        return this.accVotePostDown;
    }

    public List<UUID> retAccVoteUpList(){
        return this.accVotePostUp;
    }

    public void addAccAdminSubs(Subreddit sub){
        adminSubs.add(sub);
    }

    public void showSubAdmins(Account acc ) {
        if (adminSubs.size() > 0){
            for (int i = 0; i < adminSubs.size(); i++) {
                System.out.println(i + "_" +adminSubs.get(i).retSubName());
            }
            Scanner in = new Scanner(System.in);
            int numSub = Integer.parseInt(in.next());
            adminSubs.get(numSub).subAdmin(acc, adminSubs.get(numSub));
        }
        else{
            System.out.println("Your not admin of ant reddit");
        }
    }

    public void showAccPostLike(){
        for (int i =0 ; i< accVotePostUp.size();i++){
            for (int j =0 ; j < Post.allPost.size() ; j++){
                if (accVotePostUp.get(i).equals(Post.allPost.get(j).retPostUUID())){
                    System.out.println(" Num = " +i);
                    System.out.println(" Subredit : " + Post.allPost.get(i).subPostName);
                    System.out.println(" usename : "+Post.allPost.get(i).retUserPost());
                    System.out.println(" Titel : " + Post.allPost.get(i).titel);
                    System.out.println(" Text : " + "\n" + "       " + Post.allPost.get(i).text + "\n" + " Vote : " +
                            Post.allPost.get(i).retLikeVote()+"\n\n\n" );
                }
            }
        }
    }
}