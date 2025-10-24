package com.solocale.AdvertisingCampaign.service;

import com.solocale.AdvertisingCampaign.dto.CampaignDTO;

import java.util.List;

public interface CampaignService {

    CampaignDTO createCampaign(CampaignDTO campaign);

    List<CampaignDTO> getAllCampaigns();

    CampaignDTO getCampaignById(Long id);

    CampaignDTO updateCampaign(Long id, CampaignDTO updatedCampaign);

    void deleteCampaign(Long id);

    // Optional async requirement
    void incrementImpression(Long id);
}
