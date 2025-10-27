package com.solocale.AdvertisingCampaign.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CampaignImpressionProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "campaign-impressions";

    public void sendImpressionEvent(Long campaignId) {
        kafkaTemplate.send(TOPIC,campaignId.toString());
    }
}
