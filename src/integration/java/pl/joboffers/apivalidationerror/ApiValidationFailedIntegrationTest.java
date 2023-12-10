package pl.joboffers.apivalidationerror;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.infrastructure.apivalidation.ApiValidationErrorDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser
    public void should_return_400_and_validation_message_when_registering_user_does_have_empty_required_field() throws Exception {
        //given
        //when
        ResultActions performPostNewOffer = mockMvc.perform(post("/register").content("""
                {
                "username": "someUser",
                "password": "" 
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON));

        // then
        MvcResult mvcResult = performPostNewOffer.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto apiValidationErrorDto = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(apiValidationErrorDto.errorMessages()).contains("Password must not be empty.");
    }


    @Test
    @WithMockUser
    public void should_return_400_and_validation_message_when_register_request_is_empty() throws Exception {
        //given
        //when
        ResultActions performPostNewOffer = mockMvc.perform(post("/register").content("""
                {}
                """.trim()).contentType(MediaType.APPLICATION_JSON));

        // then
        MvcResult mvcResult = performPostNewOffer.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto apiValidationErrorDto = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(apiValidationErrorDto.errorMessages()).containsExactlyInAnyOrder(
                "Username must not be empty.",
                "Password must not be empty.",
                "Username must not be null.",
                "Password must not be null.");
    }

    @Test
    @WithMockUser
    public void should_return_400_and_validation_message_when_request_does_have_empty_required_field() throws Exception {
        //given
        //when
        ResultActions performPostNewOffer = mockMvc.perform(post("/offers").content("""
                {
                "company": "company",
                "title": "title",\s
                "salary": "salary",
                "offerUrl": ""
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON));

        // then
        MvcResult mvcResult = performPostNewOffer.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto apiValidationErrorDto = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(apiValidationErrorDto.errorMessages()).contains("Offer's url must not be empty.");


    }

    @Test
    @WithMockUser
    public void should_return_400_and_validation_message_when_request_is_empty() throws Exception {
        //given
        //when
        ResultActions performPostEmptyRequest = mockMvc.perform(post("/offers").content("""
                {}
                """.trim()).contentType(MediaType.APPLICATION_JSON));
        // then
        MvcResult mvcResult = performPostEmptyRequest.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto apiValidationErrorDto = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(apiValidationErrorDto.errorMessages()).containsExactlyInAnyOrder(
                "Salary must not be null.",
                "Offer's url must not be empty.",
                "Company must not be null.",
                "Offer's url must not be null.",
                "Company must not be empty.",
                "Title must not be null.",
                "Title must not be empty.",
                "Salary must not be empty.");


    }

}
