package hu.webler.weblerfeeder.address;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webler.weblerfeeder.address.controller.AddressController;
import hu.webler.weblerfeeder.address.model.AddressModel;
import hu.webler.weblerfeeder.address.service.AddressService;
import hu.webler.weblerfeeder.base.Identifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
@ContextConfiguration(classes = {AddressController.class})
@ExtendWith(MockitoExtension.class)
@DisplayName("Address controller - Integration test")
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;

    @Test
    @DisplayName("Given empty list when listAllAddress then return empty list")
    public void givenEmptyList_whenListAllAddress_thenReturnEmptyList() throws Exception {
        //Given
        given(addressService.getAllAddress()).willReturn(Collections.emptyList());
        List<AddressModel> expectedModels = new ArrayList<>();

        //When
        MvcResult result = mockMvc.perform(get("/api/addresses"))
                .andExpect(status().isOk())
                .andReturn();

        List<AddressModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        //Then
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .isEmpty();
        assertThat(actualModels).isEqualTo(expectedModels);
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .hasSize(0);
    }

    @Test
    @DisplayName("Given non-empty list when listAllAddress then return non empty list")
    public void givenNonEmptyList_whenListAllAddress_thenReturnNonEmptyList() throws Exception {
        //Given
        AddressModel addressModelOne = new AddressModel();

        addressModelOne.setId(new Identifier().getId());
        addressModelOne.setRegistrationDate(LocalDateTime.now());
        addressModelOne.setPostalCode("1014");
        addressModelOne.setStreetAndNumber("Tarnok 2");
        addressModelOne.setCity("Bp");

        AddressModel addressModelTwo = new AddressModel();

        addressModelTwo.setId(new Identifier().getId());
        addressModelTwo.setRegistrationDate(LocalDateTime.now());
        addressModelTwo.setPostalCode("1810");
        addressModelTwo.setStreetAndNumber("Tarnok 3");
        addressModelTwo.setCity("Budapest");


        List<AddressModel> expectedModels = Arrays.asList(
                addressModelOne, addressModelOne
        );

        given(addressService.getAllAddress()).willReturn(expectedModels);

        //When
        MvcResult result = mockMvc.perform(get("/api/addresses"))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<AddressModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .isNotEmpty(); // Assert that the list is not empty
        assertThat(actualModels)
                .usingRecursiveComparison()
                .isEqualTo(expectedModels); // Assert that the lists are equal
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .hasSize(expectedModels.size()); // Assert that the size of actual list matches the size of expected list
    }
}
