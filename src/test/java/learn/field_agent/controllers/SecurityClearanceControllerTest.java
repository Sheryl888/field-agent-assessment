package learn.field_agent.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityClearanceControllerTest {   ////////////////////////I copied these from the AgencyControllerTest////////////

        @MockBean
        SecurityClearance repository;

        @Autowired
        MockMvc mvc;

        @Test
        void addShouldReturn400WhenEmpty() throws Exception {

            var request = post("/api/security_clearance")
                    .contentType(MediaType.APPLICATION_JSON);

            mvc.perform(request)
                    .andExpect(status().isBadRequest());
        }

        @Test
        void addShouldReturn400WhenInvalid() throws Exception {

            ObjectMapper jsonMapper = new ObjectMapper();

            SecurityClearance securityClearance = new SecurityClearance();
            String agencyJson = jsonMapper.writeValueAsString(securityClearance);

            var request = post("/api/security_clearance")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(agencyJson);

            mvc.perform(request)
                    .andExpect(status().isBadRequest());

        }

        @Test
        void addShouldReturn415WhenMultipart() throws Exception {

            ObjectMapper jsonMapper = new ObjectMapper();

            SecurityClearance securityClearance = new SecurityClearance(0, "TST");
            String agencyJson = jsonMapper.writeValueAsString(securityClearance);

            var request = post("/api/security_clearance")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .content(agencyJson);

            mvc.perform(request)
                    .andExpect(status().isUnsupportedMediaType());
        }

//        @Test
//        void addShouldReturn201() throws Exception {
//
//            SecurityClearance securityClearance = new SecurityClearance(0, "TST");
//            SecurityClearance expected = new SecurityClearance(1, "TST");
//
////            when(repository.add(any())).thenReturn(expected);  ////////why does this work in AgencyControllerTest and not here?
////            ObjectMapper jsonMapper = new ObjectMapper();
//
//            String agencyJson = jsonMapper.writeValueAsString(securityClearance);
//            String expectedJson = jsonMapper.writeValueAsString(expected);
//
//            var request = post("/api/security_clearance")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(agencyJson);
//
//            mvc.perform(request)
//                    .andExpect(status().isCreated())
//                    .andExpect(content().json(expectedJson));
//        }

    }