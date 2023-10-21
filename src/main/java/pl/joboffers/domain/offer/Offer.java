package pl.joboffers.domain.offer;

import lombok.Builder;
import lombok.Getter;

@Builder

record Offer(
        String id,
        String company,
        String title,
        String salary,
        String offerUrl
) {
}
