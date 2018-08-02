package ua.com.company.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.AuthenticatedMatcher;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.company.config.AplicationConfig;
import ua.com.company.entity.Person;
import ua.com.company.serviceInterface.IpersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {AplicationConfig.class})
@WebAppConfiguration
public class AppControllerTest {
	@Autowired
	private WebApplicationContext webAppContext;
	
	@Autowired
	private IpersonService personService;
	
	@Autowired
	private FilterChainProxy chainProxy;
	
	private MockMvc mockMvc;

	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
				.apply(SecurityMockMvcConfigurers.springSecurity(chainProxy)).build();
	}

	@Test
	public void testGetOnePersonById() throws Exception {
		Person person = personService.findOneById(2);
		
		mockMvc.perform(get("/ref/{id}", 2).with(httpBasic("vasia", "soplia")).accept(MediaType.APPLICATION_JSON_UTF8))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is("kolia")));
	}
	
	@Test
	public void adminReferenceAcessTest() throws Exception {
		mockMvc.perform(get("/admin").with(httpBasic("vasia", "soplia"))).andDo(print())
		.andExpect(status().isForbidden());
		
		mockMvc.perform(get("/admin").with(httpBasic("admin", "root"))).andDo(print())
		.andExpect(status().isOk());
		
		
	}
	
}
