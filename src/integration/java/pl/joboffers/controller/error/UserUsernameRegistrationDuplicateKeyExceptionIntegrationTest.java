package pl.joboffers.controller.error;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import pl.joboffers.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserUsernameRegistrationDuplicateKeyExceptionIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_409_conflict_when_registering_user_with_existing_username() throws Exception {
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

        // step 2
        // given && when
        ResultActions secondPerform = mockMvc.perform(post("/register").content("""
                {
                "username": "someUsername",
                "password": "somePassword"
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON));
        // then
        secondPerform.andExpect(status().isConflict()).andExpect((content().json("""
                {
                   "message": "User with given username already exists",
                   "status": "CONFLICT"
                }
                """)));
    }

}
