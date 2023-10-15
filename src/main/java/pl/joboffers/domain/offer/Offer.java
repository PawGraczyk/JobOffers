package pl.joboffers.domain.offer;

import lombok.Builder;

@Builder
record Offer(
        String id,
        String company,
        String title,
        String salary,
        String offerUrl
) {
}
