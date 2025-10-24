package com.solocale.AdvertisingCampaign.service.impl;

import com.solocale.AdvertisingCampaign.dto.CampaignDTO;
import com.solocale.AdvertisingCampaign.entity.Campaign;
import com.solocale.AdvertisingCampaign.mapper.CampaignMapper;
import com.solocale.AdvertisingCampaign.repository.CampaignRepository;
import com.solocale.AdvertisingCampaign.service.CampaignService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;

    @Override
    public CampaignDTO createCampaign(CampaignDTO campaignDto) {
        Campaign campaign = campaignMapper.toEntity(campaignDto);
        campaign.setId(null);
        return campaignMapper.toDto(campaignRepository.save(campaign));
    }

    @Override
    public List<CampaignDTO> getAllCampaigns() {
        return campaignMapper.toDto(campaignRepository.findAll());
    }

    @Override
    public CampaignDTO getCampaignById(Long id) {
        return campaignMapper.toDto(campaignRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public CampaignDTO updateCampaign(Long id, CampaignDTO campaignDto) {
        campaignRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Campaign campaign = campaignMapper.toEntity(campaignDto);
        return campaignMapper.toDto(campaignRepository.save(campaign));
    }

    @Override
    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }

    @Override
    public void incrementImpression(Long id) {

    }
}
