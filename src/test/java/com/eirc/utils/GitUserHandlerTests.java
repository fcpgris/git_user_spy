package com.eirc.utils;

import org.junit.Assert;
import org.junit.Test;

import com.eirc.model.GitUser;

public class GitUserHandlerTests {

    @Test
    public void testGetGitUser(){
        String username = "fcpgris";
        GitUser gitUser = GitUserHandler.getGitUserByName(username);
        Assert.assertNotNull(gitUser);
        Assert.assertEquals("Eric", gitUser.getName());
    }
    
    @Test
    public void testGetGitUserWhichDoesntExist(){
        String username = "admin";
        GitUser gitUser = GitUserHandler.getGitUserByName(username);
        Assert.assertNull(gitUser);
    }
}
