package com.afkl.cases.df.api.v1.fares.controller;

import com.afkl.cases.df.api.common.TravelAPIConstants;
import com.afkl.cases.df.api.v1.airports.resource.Airport;
import com.afkl.cases.df.api.v1.fares.resources.Currency;
import com.afkl.cases.df.api.v1.fares.resources.Fare;
import com.afkl.cases.df.service.FareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FareControllerTest {

    private static final String AIRPORT_CODE_AMS = "AMS";
    private static final String AIRPORT_CODE_CDG = "CDG";
    @MockBean
    public OAuth2RestTemplate mockOAuth2RestTemplate;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private FareService fareService;

    @InjectMocks
    private FareController fareController;

    private MockMvc mvc;
    private Fare testFare;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        testFare = new Fare(12.0d, Currency.EUR, new Airport(AIRPORT_CODE_AMS), new Airport(AIRPORT_CODE_CDG));
    }

    @Test
    void test_GetFare_SuccessfulResponse() throws Exception {
        //mock
        when(fareService.getFare(anyString(), anyString(), anyString())).thenReturn(testFare);

        //test
        mvc.perform(MockMvcRequestBuilders.get(TravelAPIConstants.API_V1_BASE_ENDPOINT_URL + TravelAPIConstants.FARES_ENDPOINT_URL,
                AIRPORT_CODE_AMS, AIRPORT_CODE_CDG)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void test_GetFare_NotFoundResponse() throws Exception {
        //test
        mvc.perform(MockMvcRequestBuilders.get(TravelAPIConstants.API_V1_BASE_ENDPOINT_URL + TravelAPIConstants.FARES_ENDPOINT_URL,
                AIRPORT_CODE_AMS, AIRPORT_CODE_CDG)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}