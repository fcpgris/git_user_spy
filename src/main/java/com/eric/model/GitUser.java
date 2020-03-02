package com.eric.model;

import java.util.Calendar;

public class GitUser {

    private String login;
    private Long id;
    private String avatar_url;
    private String url;
    private String name;
    private String email;
    private Long public_repos;
    private Calendar created_at;
    private Calendar updated_at;
    
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAvatar_url() {
        return avatar_url;
    }
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Long getPublic_repos() {
        return public_repos;
    }
    public void setPublic_repos(Long public_repos) {
        this.public_repos = public_repos;
    }
    public Calendar getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Calendar created_at) {
        this.created_at = created_at;
    }
    public Calendar getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(Calendar updated_at) {
        this.updated_at = updated_at;
    }
    
    public String toString(){
        
        return "id="+id + " name=" + name;
    }
}
