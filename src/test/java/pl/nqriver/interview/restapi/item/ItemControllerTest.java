package pl.nqriver.interview.restapi.item;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.nqriver.interview.restapi.item.dto.ItemResponse;
import pl.nqriver.interview.restapi.item.dto.OwnerResponse;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @MockBean
    private ItemFacade itemFacade;

    @Autowired
    MockMvc mockMvc;


    @Test
    void shouldTriggerAccessDenied_whenAnonymousUserTriesToCreateItem() throws Exception {
        String jsonContent = "{\"name\": \"bike\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                .with(SecurityMockMvcRequestPostProcessors.anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldTriggerAccessDenied_whenAnonymousUserTriesToGetItems() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/items")
                .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    @WithMockUser(username = "test")
    void shouldRetrieveItems_whenUserIsAuthenticated() throws Exception {
        ItemResponse itemResponse = new ItemResponse(UUID.randomUUID(), "bike",
                new OwnerResponse(UUID.randomUUID(), "test"));

        BDDMockito.given(itemFacade.findAllByUser(any(), any()))
                .willReturn(Collections.singletonList(itemResponse));
        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password="somepassword", roles = "User")
    void shouldCreateItem_whenUserIsAuthenticated() throws Exception {
        ItemResponse itemResponse = new ItemResponse(UUID.randomUUID(), "bike",
                new OwnerResponse(UUID.randomUUID(), "test"));

        BDDMockito.given(itemFacade.create(any(), any())).willReturn(itemResponse);


        String jsonContent = "{\"name\": \"bike\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.uuid").isNotEmpty())
                .andExpect(jsonPath("$.owner").isNotEmpty());
    }


    @Test
    void shouldTriggerBadRequest_whenInputIsNotValid() throws Exception {
        String jsonContent = "{\"name\": \"\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}