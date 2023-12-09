package pl.joboffers.controller.error;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import pl.joboffers.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OfferUrlDuplicateKeyExceptionIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser
    public void should_return_409_conflict_when_added_offer_with_duplicate_offer_url() throws Exception {

        // step 1
        // given && when
        ResultActions firstPerform = mockMvc.perform(post("/offers").
                content(
                        """
                                {
                                "company": "test",
                                "title": "Junior Java Developer",
                                "salary": "5000.00 PLN",
                                "offerUrl": "www.example.com"
                                }
                                """.trim()
                ).contentType(MediaType.APPLICATION_JSON)
        );

        // then
        firstPerform.andExpect(status().isCreated());

        // step 1
        // given && when
        ResultActions secondPerform = mockMvc.perform(post("/offers").
                content(
                        """
                                {
                                "company": "test",
                                "title": "Junior Java Developer",
                                "salary": "5000.00 PLN",
                                "offerUrl": "www.example.com"
                                }
                                """.trim()
                ).contentType(MediaType.APPLICATION_JSON)
        );
        // then
        secondPerform.andExpect(status().isConflict());

    }
}
