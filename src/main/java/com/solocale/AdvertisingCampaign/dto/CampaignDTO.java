package com.solocale.AdvertisingCampaign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDTO {
    private Long id;
    private String name;
    private long targetImpression;
    private double targetBudget;
    private double cpm;
    private long currentImpression;
    private double currentCost;
}
