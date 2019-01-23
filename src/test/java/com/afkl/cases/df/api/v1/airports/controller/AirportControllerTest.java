package com.afkl.cases.df.api.v1.airports.controller;

import com.afkl.cases.df.api.v1.airports.resource.Airport;
import com.afkl.cases.df.service.AirportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

import static com.afkl.cases.df.api.common.TravelAPIConstants.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AirportControllerTest {

    private static final String AIRPORT_CODE_AMS = "AMS";
    @MockBean
    public OAuth2RestTemplate mockOAuth2RestTemplate;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;

    private MockMvc mvc;
    private Airport testAirport;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        testAirport = new Airport("AMS");
    }

    @Test
    void test_GetAirports_SuccessfulResponse() throws Exception {
        //test and assert
        mvc.perform(MockMvcRequestBuilders.get(API_V1_BASE_ENDPOINT_URL + AIRPORTS_ENDPOINT_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void test_GetAirportWithCode_SuccessfulResponse() throws Exception {
        //mock
        when(airportService.getAirportForCode(anyString())).thenReturn(testAirport);

        //test and assert
        mvc.perform(MockMvcRequestBuilders.get(API_V1_BASE_ENDPOINT_URL + AIRPORTS_ENDPOINT_URL + AIRPORT_CODE_ENDPOINT_URL,
                AIRPORT_CODE_AMS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void test_GetAirportWithCode_NotFoundResponse() throws Exception {
        //test and assert
        mvc.perform(MockMvcRequestBuilders.get(API_V1_BASE_ENDPOINT_URL + AIRPORTS_ENDPOINT_URL + AIRPORT_CODE_ENDPOINT_URL,
                AIRPORT_CODE_AMS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_GetAirportWithCode_BadRequestResponse() throws Exception {
        //test and assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                mvc.perform(MockMvcRequestBuilders.get(API_V1_BASE_ENDPOINT_URL + AIRPORTS_ENDPOINT_URL + AIRPORT_CODE_ENDPOINT_URL,
                        null)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest()));
    }
}