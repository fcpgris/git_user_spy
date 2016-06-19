package com.eirc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class GitUserSpyApplication {

    private final static Logger logger = LoggerFactory.getLogger(GitUserSpyApplication.class);
    
	public static void main(String[] args) {
	    logger.info("Application is running");
		SpringApplication.run(GitUserSpyApplication.class, args);
	}
}
