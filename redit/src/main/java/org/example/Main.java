package org.example;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
        Account acc = new Account("amir@gmail.com", "123", "amir");
        while (true){

            System.out.println("1.Sign in \n2.Log in");
            Scanner in = new Scanner(System.in);
            String input = in.next();
            if (input.equals("1")){
                acc=signinMenu();

            }
            else if (input.equals("2")){
                acc=loginMenu();

            }
            while (true) {
                System.out.println(" 1.Creat Your Post\n 2.Show All Post \n 3.Profile \n 4.Search \n 5.Log out");
                input = in.next();
                if (input.equals("1")) {
                    creatSub(acc);
                } else if (input.equals("2")) {
                    if(Post.allPost.size() >0) {
                        Post.showAllPosts();
                        int numPost = Integer.parseInt(in.next());
                        if (numPost > Post.allPost.size()) {
                            System.out.println("There is no post with this number");
                        } else {
                            Post.allPost.get(numPost).showPost(Post.allPost.get(numPost));
                        }
                    }
                    else{
                        System.out.println("There is no post ");
                    }

                }
                else if (input.equals("3")){
                    profileUser(acc);
                }
                else if (input.equals("4")) {
                    search(acc);
                }
                else if (input.equals("5")){
                    break;
                }
            }
        }
    }
    public static Account signinMenu(){
        while (true) {
            System.out.print("0.Back \nEnter username: \n");
            Scanner in = new Scanner(System.in);
            String username = in.next();
            boolean find = false;
            if (username.equals("0")){
                break;
            }
            for (int i = 0; i < Account.userList.size(); i++) {
                find |= Account.userList.get(i).retUserName().equals("u/"+username);
            }
            if (find) {
                System.out.println("username already exist!");
            }
            else {
                System.out.print("0.Back\n Enter email: \n");
                String email = in.next();
                while(true){
                    if (email.equals("0")){
                        break;
                    }
                    if(Account.emailValidator(email)){
                        break;
                    }
                    else{
                        System.out.println("Enter valid email");
                        email = in.next();
                    }
                }
                for (int i = 0; i < Account.userList.size(); i++) {
                    find |= Account.userList.get(i).retUserEmail().equals(email);
                }
                if (find) {
                    System.out.println("email already exist!");
                }
                else {
                    System.out.println("Enter Your Password");
                    String pass = in.next();
                    Account acc = new Account(email,pass,username);
                    Account.userList.add(acc);
                    System.out.println("your account has been craeted");
                    return acc;
                }
            }
        }
        return null;
    }
    public static Account loginMenu(){
        while (true){
            System.out.println("0.Back \n Enter Your Email : \n");
            Scanner in = new Scanner(System.in);
            String email = in.next();
            boolean find = false;
            if (email.equals("0")){
                break;
            }
            Account acc = new Account("", "", "");
            for (int i = 0 ; i < Account.userList.size(); i++ ){
                if (Account.userList.get(i).retUserEmail().equals(email)) {
                   acc = Account.userList.get(i);
                }
                find |= Account.userList.get(i).retUserEmail().equals(email);
            }
            if (!find){
                System.out.println("Your email has not found");
            }
            else {
                while (true){
                    System.out.println("0.Back \n Enter pass :\n");
                    String pass = in.next();
                    if (pass.equals("0")){
                        break;
                    }
                    if (acc.check(pass)){
                        System.out.println("Welcome to your account"+acc.retUserName());
                        return acc;
                    }
                    else {
                        System.out.println("your pass is not correct");
                    }
                }
            }
        }
        return null;
    }

    public static void profileUser(Account acc) throws IOException {
        while (true){
            System.out.println(" username : " + acc.retUserName());
            System.out.println(" email : " + acc.retUserEmail()+"\n\n");
            System.out.println("1. All posts");
            System.out.println("2. All Subredits");
            System.out.println("3. Delete Posts");
            System.out.println("4. All Post likes");
            System.out.println("5. Admin");
            System.out.println("6. Back");
            Scanner in = new Scanner(System.in);
            String input = in.next();
            if (input.equals("1")){
               Account.showAllAccPost(acc);
            }
            else if (input.equals("2")){
                while (true) {
                    System.out.println(" 1.Back \nALL Subreddits : ");
                    for (int i = 0 ;i < Account.userList.size() ; i++){
                        if (acc.retAccUUID().equals(Account.userList.get(i).retAccUUID())){
                            Account.userList.get(i).showAccSubs(Account.userList.get(i));
                        }
                    }
                    input=in.next();
                    if (input.equals("1")){
                        break;
                    }
                }
            }
            else if (input.equals("3")){
                Account.deletAccPost(acc);
            }
            else if (input.equals("4")){
                acc.showAccPostLike();
            }
            else if (input.equals("5")){
               acc.showSubAdmins(acc);
            }

            else if (input.equals("6")){
                break;
            }
        }
    }

    public static void creatSub(Account acc) throws IOException {
        while (true) {
            System.out.println("1.Creat Subreddit \n 2.Chose Subreddit \n 3.back");
            Scanner in = new Scanner(System.in);
            String input = in.next();
            if (input.equals("1")) {
                while (true) {
                    System.out.println("Enter Subreddit Name : ");
                    System.out.println(" 1.back");
                    input = in.next();
                    if (input.equals("1")) {
                        break;
                    }
                    Boolean find = false;
                    for (int i = 0; i < Subreddit.allSub.size(); i++) {
                        find |= Subreddit.allSub.get(i).retSubName().equals("r/" + input);
                    }
                    if (find) {
                        System.out.println("SubName is already exist!");
                    } else {
                        Subreddit sub = new Subreddit(input, acc.retAccUUID());
                        Subreddit.allSub.add(sub);
                        Post post = new Post("","",acc,sub.retSubName());
                        post.addTitel();
                        post.addText();
                        System.out.println(acc.retUserName()+" new post\n"+"Titel : "+post.retTitel()+"\n"+post.retText());
                        Post.allPost.add(post);
                        acc.addPost(post,acc);
                        sub.retPostList().add(post.retPostUUID());
                        sub.addMembers(acc);
                        acc.addSub(sub);
                        acc.addAccAdminSubs(sub);
                        break;
                    }
                }
            } else if (input.equals("2")) {
                Subreddit sub = new Subreddit("",acc.retAccUUID());
                while (true) {
                    System.out.println("Enter Subreddit Name : ");
                    System.out.println(" 1.back");
                    input = in.next();
                    if (input.equals("1")) {
                        break;
                    }
                    boolean ok = false;
                    for (int i = 0; i < Subreddit.allSub.size(); i++) {
                        if (Subreddit.allSub.get(i).retSubName().equals("r/"+input))
                        {
                            sub = Subreddit.allSub.get(i);
                            Post post = new Post("","",acc,sub.retSubName());
                            post.addTitel();
                            post.addText();
                            System.out.println(acc.retUserName()+" new post\n"+"Titel : "+post.retTitel()+"\n"+post.retText());
                            Post.allPost.add(post);
                            acc.addPost(post,acc);
                            Subreddit.hashMapPostSub(post,sub);
                            Subreddit.allSub.get(i).retPostList().add(post.retPostUUID());
                            sub.addMembers(acc);
                            acc.addSub(sub);
                            ok=true;
                            break;
                        }
                        else{
                            System.out.println("Not Found");
                        }
                    }
                    if (ok){
                        break;
                    }
                }
            } else if (input.equals("3")) {
                break;
            }
        }
    }
    public static void search(Account acc) throws IOException {
        while (true){
            System.out.println("Use u/ to search for people & r/ to serch for community \n 2.Back");
            Scanner in = new Scanner(System.in);
            String input = in.next();

            if (input.equals("2")){
                break;
            }
            else if(input.substring(0,2).equals("r/")){
                Boolean find = false;
                for (int i =0 ; i<Subreddit.allSub.size() ; i++){
                    if (input.equals(Subreddit.allSub.get(i).retSubName())){
                        Subreddit.showSub(Subreddit.allSub.get(i));
                    }
                    find |= input.equals(Subreddit.allSub.get(i).retSubName());
                }
                if (!find){
                    System.out.println("There is nio community with this name");
                }
            }
            else if (input.substring(0,2).equals("u/")){
                Boolean find = false;
                for (int i = 0 ; i<Account.userList.size() ; i++){
                    if (input.equals(Account.userList.get(i).retUserName())){
                        SearchProfile(acc,Account.userList.get(i));
                    }
                    find |= input.equals(Account.userList.get(i).retUserName());
                }
                if (!find){
                    System.out.println("There is no user with this name");
                }
            }
        }
    }

    public static void SearchProfile( Account acc ,Account accSrech) throws IOException {
        while (true){
            System.out.println(" Username : " + accSrech.retUserName());
            System.out.println(" Email : " + accSrech.retUserEmail()+"\n\n");
            System.out.println("1. All posts");
            System.out.println("2. All Subredits");
            System.out.println("3. Back");
            Scanner in = new Scanner(System.in);
            String input = in.next();
            if (input.equals("1")){
                Account.serachShowAllAccPost(acc,accSrech);
            }
            else if (input.equals("2")){
                while (true) {
                    System.out.println(" 1.Back \nALL Subreddits : ");
                    for (int i = 0 ;i < Account.userList.size() ; i++){
                        if (accSrech.retAccUUID().equals(Account.userList.get(i).retAccUUID())){
                            Account.userList.get(i).showAccSubs(Account.userList.get(i));
                        }
                    }
                    input=in.next();
                    if (input.equals("1")){
                        break;
                    }
                }
            }
            else if (input.equals("3")){
               break;
            }
        }
    }
}