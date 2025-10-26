package com.solocale.AdvertisingCampaign.unit.event;

import com.solocale.AdvertisingCampaign.event.CampaignImpressionProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CampaignImpressionProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private CampaignImpressionProducer producer;

    @Test
    void shouldSendImpressionEvent() {
        Long campaignId = 1L;

        producer.sendImpressionEvent(campaignId);

        verify(kafkaTemplate, times(1))
                .send("campaign-impressions", campaignId.toString());
    }
}