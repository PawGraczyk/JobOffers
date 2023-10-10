package pl.joboffers.domain.offer;

import java.net.URL;

record Offer(
        String id,
        String title,
        String salary,
        URL offerUrl
) {
}
