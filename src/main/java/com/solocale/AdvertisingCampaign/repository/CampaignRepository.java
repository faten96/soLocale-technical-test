package com.solocale.AdvertisingCampaign.repository;


import com.solocale.AdvertisingCampaign.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {}