package com.solocale.AdvertisingCampaign.controller;

import com.solocale.AdvertisingCampaign.dto.CampaignDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Campaign API", description = "CRUD endpoints for managing advertising campaigns")
@RequestMapping("/api/campaigns")
public interface CampaignController {

    @Operation(summary = "Create a new campaign")
    @PostMapping
    ResponseEntity<CampaignDTO> createCampaign(@Valid @RequestBody CampaignDTO campaignDto);

    @Operation(summary = "Retrieve all campaigns")
    @GetMapping
    ResponseEntity<List<CampaignDTO>> fetchAllCampaigns();

    @Operation(summary = "Get campaign details by ID")
    @GetMapping("/{id}")
    ResponseEntity<CampaignDTO> fetchCampaignById(
            @Parameter(description = "Campaign ID") @PathVariable Long id);

    @Operation(summary = "Update campaign details by ID")
    @PutMapping("/{id}")
    ResponseEntity<CampaignDTO> updateCampaign(
            @PathVariable Long id,
            @Valid @RequestBody CampaignDTO campaignDto);

    @Operation(summary = "Delete a campaign by ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCampaign(
            @Parameter(description = "Campaign ID") @PathVariable Long id);

    @Operation(summary = "Increment campaign impression (async + thread-safe)")
    @PostMapping("/{id}/impression")
    ResponseEntity<String> incrementImpression(
            @Parameter(description = "Campaign ID") @PathVariable Long id);
}
