package integrationTest;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tech.getarrays.api.integration.service.CommentRepository;
import tech.getarrays.api.web.entity.UserWeb;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    private UserWeb testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserWeb();
        testUser.setUsername("lucho");
        testUser.setPassword("123456");
        testUser.setId(514L);

        commentRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
    }

    @Test
    void testGetComments() throws Exception {
        long routeId = 43434L;
        mockMvc.perform(get("api/" + routeId + "/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
