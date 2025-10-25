package com.solocale.AdvertisingCampaign.service;

import com.solocale.AdvertisingCampaign.dto.CampaignDTO;

import java.util.List;

public interface CampaignService {

    CampaignDTO createCampaign(CampaignDTO campaign);

    List<CampaignDTO> fetchAllCampaigns();

    CampaignDTO getCampaignById(Long id);

    CampaignDTO updateCampaign(Long id, CampaignDTO updatedCampaign);

    void deleteCampaign(Long id);

    void incrementImpression(Long id);
}
