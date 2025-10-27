package com.solocale.AdvertisingCampaign.mapper;

import com.solocale.AdvertisingCampaign.dto.CampaignDTO;
import com.solocale.AdvertisingCampaign.entity.Campaign;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CampaignMapper {
    Campaign toEntity(CampaignDTO dto);
    List<Campaign> toEntity(List<CampaignDTO> dto);
    CampaignDTO toDto(Campaign entity);
    List<CampaignDTO> toDto(List<Campaign> entity);
}
