package com.task.spring;

import com.task.spring.controller.ApiController;
import com.task.spring.service.S3Service;
import com.task.spring.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class ApiControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private ApiController apiController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    void testRegister() throws Exception {
        mockMvc.perform(post("/api/v1/user/register")
                        .contentType("application/json")
                        .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered"));
    }

    @Test
    void testLogin() throws Exception {
        when(userService.authenticate("test@example.com", "password")).thenReturn("token");

        mockMvc.perform(post("/api/v1/user/login")
                        .param("email", "test@example.com")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().string("token"));
    }

    @Test
    void testGenerateUploadUrl() throws Exception {
        when(s3Service.generateUploadUrl("file.txt")).thenReturn("uploadUrl");

        mockMvc.perform(get("/api/v1/user/generate-upload-url")
                        .param("fileName", "file.txt"))
                .andExpect(status().isOk())
                .andExpect(content().string("uploadUrl"));
    }

    @Test
    void testGenerateDownloadUrl() throws Exception {
        when(s3Service.generateDownloadUrl("file.txt")).thenReturn("downloadUrl");

        mockMvc.perform(get("/api/v1/user/generate-download-url")
                        .param("fileName", "file.txt"))
                .andExpect(status().isOk())
                .andExpect(content().string("downloadUrl"));
    }
}