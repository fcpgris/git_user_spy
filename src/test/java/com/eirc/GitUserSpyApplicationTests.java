package com.eirc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.eirc.model.GitUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GitUserSpyApplication.class)
@WebAppConfiguration
public class GitUserSpyApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

	@Test
	public void testMainController() throws Exception{
	    mockMvc.perform(get("/"))
	    .andExpect(status().isOk())
	    .andExpect( model().attribute("gitUser", notNullValue() ))
	    .andExpect( model().attribute("gitUser", instanceOf(GitUser.class) ));
	}
	
	@Test
    public void testFailedMainController() throws Exception{
        mockMvc.perform(get("/?user=admin"))
        .andExpect(status().isOk())
        .andExpect( model().attribute("gitUser", nullValue() ));
    }
}
