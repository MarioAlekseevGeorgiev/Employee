package webTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void when_getOneUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("users/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("one"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser("customUserName")
    public void getMessage() {
        //...
    }

    @Test
    @WithMockUser(username = "admin", roles={"USER", "ADMIN"})
    public void getMessage2() {
        //...
    }

    @Test
    public void testGetAuthors() throws Exception {
        mockMvc.perform(get("/rest/widgets"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


        this.mockMvc.perform(get("/authors"))
                .andExpect(status().isOk());
              //  .andExpect(jsonPath("$", hasSize(2)))
    }

}
