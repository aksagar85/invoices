package com.assignment.invoices;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

	public class LineItemControllerTests extends InvoicesApplicationTests {

		@Autowired
		private WebApplicationContext webApplicationContext;

		private MockMvc mockMvc;

		@Before
		public void setup() {
			mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		}

		@Test
		public void testLineItems() throws Exception {
			mockMvc.perform(get("/invoices/5/lineItems")).andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(jsonPath("$[0].description").value("Computer")).andExpect(jsonPath("$[0].price").value(1500));

		}

		@Test
		public void testInvoices() throws Exception {
			mockMvc.perform(get("/invoices/5")).andExpect(status().isOk())
					.andExpect(content().contentType("application/hal+json;charset=UTF-8"))
					.andExpect(jsonPath("$.name").value("Vrushali Deo")).andExpect(jsonPath("$.email").value("vdeo@gmail.com"));

		}
		
		
	}
