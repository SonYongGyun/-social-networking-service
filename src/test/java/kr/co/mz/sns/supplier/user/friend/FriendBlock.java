package kr.co.mz.sns.supplier.user.friend;

import kr.co.mz.sns.service.user.FriendService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class FriendBlock {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FriendService friendService;
    private String token;

    @Test
    void testLogin() throws Exception {
        var login = mockMvc.perform(
                        post("/api/unauth/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                          "email": "admin1@mz.co.kr",
                                          "password": "1"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andReturn();
        token = login.getResponse().getHeader("Authorization");
    }


    @Test
    void testBlock() throws Exception {
        mockMvc.perform(get("/api/auth/users/friends/request/block/51")
                        .header("Authorization", token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userSeq").value("19"))
                .andExpect(jsonPath("$.friendSeq").value("51"));
    }

}
