package com.solocale.AdvertisingCampaign.integration.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.solocale.AdvertisingCampaign.dto.CampaignDTO;
import com.solocale.AdvertisingCampaign.entity.Campaign;
import com.solocale.AdvertisingCampaign.repository.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" }
)
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, topics = {"campaign-impressions"})
@DirtiesContext
class CampaignControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;



    @Test
    void shouldFetchAllCampaigns() throws Exception {
        mockMvc.perform(get("/api/campaigns"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[0].name", not(emptyOrNullString())));
    }

    @Test
    void shouldFetchCampaignById() throws Exception {
        mockMvc.perform(get("/api/campaigns/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Winter Promo")))
                .andExpect(jsonPath("$.targetImpression", is(20000)));
    }

    @Test
    void shouldReturnNotFoundWhenFetchingNonExistingCampaign() throws Exception {
        mockMvc.perform(get("/api/campaigns/{id}", 9999))
                .andExpect(status().isNotFound());
    }



    @Test
    void shouldCreateCampaign() throws Exception {
        CampaignDTO newCampaign = new CampaignDTO();
        newCampaign.setName("name");
        newCampaign.setTargetImpression(5000L);
        newCampaign.setTargetBudget(2500.0);
        newCampaign.setCpm(2.5);
        newCampaign.setCurrentImpression(0L);
        newCampaign.setCurrentCost(0.0);

        mockMvc.perform(post("/api/campaigns")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCampaign)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void shouldUpdateCampaign() throws Exception {
        CampaignDTO update = new CampaignDTO();
        update.setId(1L);
        update.setName("Updated Summer Sale");
        update.setTargetImpression(15000L);
        update.setTargetBudget(6000.0);
        update.setCpm(2.5);
        update.setCurrentImpression(800L);
        update.setCurrentCost(2.0);

        mockMvc.perform(put("/api/campaigns/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Summer Sale")))
                .andExpect(jsonPath("$.targetImpression", is(15000)));
    }


    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingCampaign() throws Exception {
        CampaignDTO update = new CampaignDTO();
        update.setId(9999L);
        update.setName("Non Existing");
        update.setTargetImpression(1000L);
        update.setTargetBudget(500.0);
        update.setCpm(1.5);
        update.setCurrentImpression(0L);
        update.setCurrentCost(0.0);

        mockMvc.perform(put("/api/campaigns/{id}", 9999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isNotFound());
    }
    @Test
    void shouldDeleteCampaign() throws Exception {
        mockMvc.perform(delete("/api/campaigns/{id}", 1))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/campaigns/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldIncrementImpression() throws Exception {
        mockMvc.perform(post("/api/campaigns/{id}/impression", 1))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCampaignAfterImpressionEventIsProcessed() throws Exception {

        kafkaListenerEndpointRegistry.getListenerContainers()
                .forEach(container -> {
                    try { container.start(); } catch (Exception ignored) {}
                });
        // Given
        Campaign campaign = new Campaign();
        campaign.setName("Kafka Flow Test");
        campaign.setCpm(10.0);
        campaign.setTargetImpression(1000L);
        campaign.setTargetBudget(5000.0);
        campaign.setCurrentImpression(0L);
        campaign.setCurrentCost(0.0);
        campaign = campaignRepository.save(campaign);

        // When: API triggers the producer
        mockMvc.perform(post("/api/campaigns/{id}/impression", campaign.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Wait for async Kafka consumer to process the event
        Thread.sleep(5000);

        // Then: verify campaign is updated
        Campaign updated = campaignRepository.findById(campaign.getId()).orElseThrow();
        assertThat(updated.getCurrentImpression()).isEqualTo(1L);
        assertThat(updated.getCurrentCost()).isEqualTo((1 / 1000.0) * updated.getCpm());
    }

}
