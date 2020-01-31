package kr.co.fastcampus.eatgo.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import kr.co.fastcampus.eatgo.domain.Region;
import kr.co.fastcampus.eatgo.domain.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RegionServiceTests {

    private RegionService regionService;

    @Mock
    private RegionRepository regionRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        regionService = new RegionService(regionRepository);
    }

    @Test
    void getRegions(){

        List<Region> mockRegions = new ArrayList<>();
        mockRegions.add(Region.builder().name("Seoul").build());

        given(regionRepository.findAll()).willReturn(mockRegions);

        List<Region> regions = regionService.getRegions();
        Region region = regions.get(0);
        assertEquals(region.getName(),"Seoul");
    }

    @Test
    void addRegion(){
        Region region = regionService.addRegion("Seoul");
        verify(regionRepository).save(any());
        assertEquals(region.getName(),"Seoul");
    }
}