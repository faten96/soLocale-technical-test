package com.solocale.AdvertisingCampaign.event;

import com.solocale.AdvertisingCampaign.repository.CampaignRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CampaignImpressionConsumer {
    private final CampaignRepository campaignRepository;

    @KafkaListener(topics = "campaign-impressions", groupId = "campaign-group")
    @Transactional
    public void consume(String campaignId) {
        Long id = Long.parseLong(campaignId);
        campaignRepository.findById(id).ifPresent(
                campaign -> {
                    long newImpression =   campaign.getCurrentImpression() + 1;
                    double newCost = (newImpression / 1000.0) * campaign.getCpm();
                    campaign.setCurrentImpression(campaign.getCurrentImpression() + 1);
                    campaign.setCurrentCost((newImpression / 1000.0) * campaign.getCpm());
                    campaignRepository.save(campaign);
                    log.info("Updated Campaign {} with Impressions: {} and Cost: {}", id, newImpression, newCost);
                }
        );
    }
}
