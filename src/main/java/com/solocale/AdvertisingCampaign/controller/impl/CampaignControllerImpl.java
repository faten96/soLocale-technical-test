package com.solocale.AdvertisingCampaign.controller.impl;

import com.solocale.AdvertisingCampaign.controller.CampaignController;
import com.solocale.AdvertisingCampaign.dto.CampaignDTO;
import com.solocale.AdvertisingCampaign.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CampaignControllerImpl implements CampaignController {

    private final CampaignService campaignService;

    @Override
    public ResponseEntity<CampaignDTO> createCampaign(CampaignDTO campaignDto) {
        return ResponseEntity.ok(campaignService.createCampaign(campaignDto));
    }

    @Override
    public ResponseEntity<List<CampaignDTO>> fetchAllCampaigns() {
        return ResponseEntity.ok(campaignService.fetchAllCampaigns());
    }

    @Override
    public ResponseEntity<CampaignDTO> fetchCampaignById(Long id) {
        return ResponseEntity.ok(campaignService.getCampaignById(id));
    }

    @Override
    public ResponseEntity<CampaignDTO> updateCampaign(Long id, CampaignDTO campaignDto) {
        return ResponseEntity.ok(campaignService.updateCampaign(id, campaignDto));
    }

    @Override
    public ResponseEntity<Void> deleteCampaign(Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<String> incrementImpression(Long id) {
        campaignService.incrementImpression(id);
        return ResponseEntity.ok("Impression Count incremented Successfully !");
    }
}
