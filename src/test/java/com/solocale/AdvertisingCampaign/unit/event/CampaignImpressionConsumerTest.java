package com.solocale.AdvertisingCampaign.unit.event;


import com.solocale.AdvertisingCampaign.entity.Campaign;
import com.solocale.AdvertisingCampaign.event.CampaignImpressionConsumer;
import com.solocale.AdvertisingCampaign.repository.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignImpressionConsumerTest {

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignImpressionConsumer consumer;

    @Test
    void shouldUpdateCampaignWhenExists() {
        // Given
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setCurrentImpression(999L);
        campaign.setCpm(2.0);
        campaign.setCurrentCost(1.8);

        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));

        // When
        consumer.consume("1");

        // Then
        verify(campaignRepository, times(1)).save(campaign);
        assert(campaign.getCurrentImpression() == 1000L);
        assert(campaign.getCurrentCost() == (1000 / 1000.0) * 2.0);
    }

    @Test
    void shouldNotSaveWhenCampaignDoesNotExist() {
        // Given
        when(campaignRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        consumer.consume("999");

        // Then
        verify(campaignRepository, never()).save(any());
    }
}
