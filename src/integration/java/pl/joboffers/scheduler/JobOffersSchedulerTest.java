package pl.joboffers.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.JobOffersSpringBootApplication;
import pl.joboffers.domain.offersfetcher.OffersFetchable;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = JobOffersSpringBootApplication.class, properties = "scheduling.enabled=true")
public class JobOffersSchedulerTest extends BaseIntegrationTest {

    @SpyBean
    OffersFetchable remoteOfferClient;

    @Test
    public void should_run_rest_client_offers_fetching_exactly_given_times() {
        await().atMost(Duration.ofSeconds(3)).untilAsserted(() -> verify(remoteOfferClient, times(3)).fetchOffers());
    }

}
