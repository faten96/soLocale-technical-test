package com.solocale.AdvertisingCampaign.unit.service.impl;

import com.solocale.AdvertisingCampaign.dto.CampaignDTO;
import com.solocale.AdvertisingCampaign.entity.Campaign;
import com.solocale.AdvertisingCampaign.event.CampaignImpressionProducer;
import com.solocale.AdvertisingCampaign.mapper.CampaignMapper;
import com.solocale.AdvertisingCampaign.repository.CampaignRepository;
import com.solocale.AdvertisingCampaign.service.impl.CampaignServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

class CampaignServiceImplTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private CampaignMapper campaignMapper;

    @Mock
    private CampaignImpressionProducer producer;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    private CampaignDTO campaignDTO;
    private Campaign campaign;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        campaignDTO = new CampaignDTO();
        campaignDTO.setId(1L);
        campaignDTO.setName("Ad Campaign");

        campaign = new Campaign();
        campaign.setId(1L);
        campaign.setName("Ad Campaign");
    }

    @Test
    void givenCampaignDto_whenCreateCampaign_thenReturnSavedDto() {
        // given
        CampaignDTO savedDto = new CampaignDTO();
        savedDto.setId(1L);

        given(campaignMapper.toEntity(campaignDTO)).willReturn(campaign);
        given(campaignRepository.save(any(Campaign.class))).willReturn(campaign);
        given(campaignMapper.toDto(campaign)).willReturn(savedDto);

        // when
        CampaignDTO result = campaignService.createCampaign(campaignDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        then(campaignRepository).should(times(1)).save(any(Campaign.class));
    }

    @Test
    void givenExistingCampaigns_whenFetchAll_thenReturnListOfDtos() {
        // given
        List<Campaign> campaigns = List.of(campaign);
        List<CampaignDTO> campaignDTOs = List.of(campaignDTO);

        given(campaignRepository.findAll()).willReturn(campaigns);
        given(campaignMapper.toDto(campaigns)).willReturn(campaignDTOs);

        // when
        List<CampaignDTO> result = campaignService.fetchAllCampaigns();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Ad Campaign");
        then(campaignRepository).should(times(1)).findAll();
    }

    @Test
    void givenExistingId_whenGetCampaignById_thenReturnDto() {
        // given
        given(campaignRepository.findById(1L)).willReturn(Optional.of(campaign));
        given(campaignMapper.toDto(campaign)).willReturn(campaignDTO);

        // when
        CampaignDTO result = campaignService.getCampaignById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        then(campaignRepository).should(times(1)).findById(1L);
    }

    @Test
    void givenNonExistingId_whenGetCampaignById_thenThrowEntityNotFound() {
        // given
        given(campaignRepository.findById(1L)).willReturn(Optional.empty());

        // when / then
        assertThrows(EntityNotFoundException.class, () -> campaignService.getCampaignById(1L));
        then(campaignRepository).should(times(1)).findById(1L);
    }

    @Test
    void givenExistingIdAndUpdatedDto_whenUpdateCampaign_thenSaveAndReturnUpdatedDto() {
        // given
        CampaignDTO updatedDto = new CampaignDTO();
        updatedDto.setId(1L);
        updatedDto.setName("Updated Campaign");

        given(campaignRepository.findById(1L)).willReturn(Optional.of(campaign));
        given(campaignMapper.toEntity(updatedDto)).willReturn(campaign);
        given(campaignRepository.save(campaign)).willReturn(campaign);
        given(campaignMapper.toDto(campaign)).willReturn(updatedDto);

        // when
        CampaignDTO result = campaignService.updateCampaign(1L, updatedDto);

        // then
        assertThat(result.getName()).isEqualTo("Updated Campaign");
        then(campaignRepository).should(times(1)).save(campaign);
    }

    @Test
    void givenNonExistingId_whenUpdateCampaign_thenThrowEntityNotFound() {
        // given
        given(campaignRepository.findById(1L)).willReturn(Optional.empty());

        // when / then
        assertThrows(EntityNotFoundException.class, () -> campaignService.updateCampaign(1L, campaignDTO));
        then(campaignRepository).should(times(1)).findById(1L);
        then(campaignRepository).should(never()).save(any());
    }

    @Test
    void givenId_whenDeleteCampaign_thenRepositoryDeleteCalled() {
        // when
        campaignService.deleteCampaign(1L);

        // then
        then(campaignRepository).should(times(1)).deleteById(1L);
    }

    @Test
    void givenCampaignId_whenIncrementImpression_thenSendEvent() {
        // when
        campaignService.incrementImpression(1L);

        // then
        then(producer).should(times(1)).sendImpressionEvent(1L);
    }
}
