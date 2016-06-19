package com.eirc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.eirc.model.GitUser;

public class GitUserHandler {
    private final static Logger logger = LoggerFactory.getLogger(GitUserHandler.class);

    private static String urlPrefix = "https://api.github.com/users/";

    public static GitUser getGitUserByName(String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = urlPrefix + username;
        try {
            ResponseEntity<GitUser> response = restTemplate.getForEntity(url, GitUser.class);
            logger.info("successfully got user(" + username + ") info: " + response.toString());
            return response.getBody();
        } catch (Exception e) {
            logger.info("failed to get user(" + username + ") info", e);
            return null;
        }
    }
}
