package pl.joboffers.controller.error;


import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import pl.joboffers.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class InvalidBearerTokenExceptionIntegrationTest extends BaseIntegrationTest {


    @Test
    public void should_return_403Forbidden_when_given_invalid_bearer_token() throws Exception {
        // step 1
        // given && when
        ResultActions firstPerform = mockMvc.perform(post("/register").content("""
                {
                "username": "someUsername",
                "password": "somePassword"
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON));

        // then
        firstPerform.andExpect(status().isCreated());

        //step 2
        // given && when
        ResultActions performWithWrongToken = mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer randomToken"));
        // then
        performWithWrongToken.andExpect(status().isForbidden())
                .andExpect(content().json(
                        """
                                {
                                "message": "Invalid token",
                                "status": "FORBIDDEN"
                                }  
                                """
                ));

    }

}
