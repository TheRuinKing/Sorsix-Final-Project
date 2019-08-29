package com.sorsix.finkicommunity.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sorsix.finkicommunity.domain.enums.Role;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long userId;

    @Column(unique=true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique=true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Character sex;

    @Column(name = "posts_number")
    private int numberOfPosts = 0;

    private Role role;

    private boolean active = true;

    private int numberOfFollowers = 0;

    private int numberOfFollowings = 0;

    /*
           USER --- follows --- USER     ManyToMany
    */
    @ManyToMany(
            fetch=FetchType.EAGER
    )
    @JoinTable(
            name="user_follows_user",
            joinColumns = @JoinColumn(name="user_id_following"),
            inverseJoinColumns = @JoinColumn(name = "user_id_followed")
    )
    private Set<User> followings = new HashSet<>();

    /*
            USER --- followed by --- USER   ManyToMany
         */
    @ManyToMany(
            mappedBy = "followings",
            fetch=FetchType.EAGER
    )
    private Set<User> followers = new HashSet<>();


    /*
            USER --- posts --- POST     OneToMany
         */
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    private Set<Post> posts = new HashSet<>();

    /*
        USER --- likes --- POST     ManyToMany
     */
    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name="user_likes_post",
            joinColumns = @JoinColumn(name = "fk_user_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_post_id")
    )
    private Set<Post> postsLiked = new HashSet<>();


    /*
        This is used by JACKSON JSON when converts the POJO to json
     */
    public User() {
    }

    public User(String username, String password, Role role){
        this.username = username;
        this.password = password;
        this.role = role;

        Random random = new Random();
        if(random.nextInt(2)==0){
            this.sex='F';
        }else{
            this.sex='M';
        }

    }

    public User(String username, String password, String email, String firstName, String lastName, char sex, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getNumberOfFollowings() {
        return numberOfFollowings;
    }

    public void setNumberOfFollowings(int numberOfFollowings) {
        this.numberOfFollowings = numberOfFollowings;
    }

    public Set<Post> getPosts(){
        return posts;
    }

    // FUNCTIONS
    public void incrementNumberOfPosts(){
        numberOfPosts++;
    }

    public void decrementNumberOfPosts(){
        numberOfPosts--;
    }

    public void incrementNumberOfFollowings(){numberOfFollowings++;}

    public void decrementNumberOfFollowings(){numberOfFollowings--;}

    public void incrementNumberOfFollowers(){numberOfFollowers++;}

    public void decrementNumberOfFollowers(){numberOfFollowers--;}

    public boolean addNewFollowing(User newFollowing){
        this.incrementNumberOfFollowings();
        newFollowing.incrementNumberOfFollowers();
        return followings.add(newFollowing);
    }

    public boolean removeFollowing(User following){
        this.decrementNumberOfFollowings();
        following.decrementNumberOfFollowers();
        return followings.remove(following);
    }

    public boolean addNewPost(Post newPost){
        this.incrementNumberOfPosts();
        return posts.add(newPost);
    }

    public boolean removePost(Post post){
        this.decrementNumberOfPosts();
        return posts.remove(post);
    }

    public boolean addPostLiked(Post newPostLiked){
        newPostLiked.incrementNumberOfLikes();
        return postsLiked.add(newPostLiked);
    }

    public boolean removePostLiked(Post postLiked){
        postLiked.decrementNumberOfLikes();
        return postsLiked.remove(postLiked);
    }

    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    @JsonIgnore
    public Set<User> getFollowings() {
        return followings;
    }

    @JsonIgnore
    public Set<User> getFollowers() {
        return followers;
    }

    @JsonIgnore
    public Set<Post> getPostsLiked() {
        return postsLiked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email);
    }
}
