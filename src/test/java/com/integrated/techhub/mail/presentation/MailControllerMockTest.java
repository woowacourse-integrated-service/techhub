//package com.integrated.techhub.mail.presentation;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.integrated.techhub.auth.application.AuthQueryService;
//import com.integrated.techhub.mail.application.MailService;
//import com.integrated.techhub.member.fixture.MemberFixture;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@ExtendWith(SpringExtension.class)
//@SuppressWarnings("NonAsciiCharacters")
//@WebMvcTest(controllers = MailController.class)
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//class MailControllerMockTest {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AuthQueryService authQueryService;
//
//    @MockBean
//    private MailService mailService;
//
//    @Test
//    void sendMail() throws Exception {
//        //given
//        var content = objectMapper.writeValueAsString(MemberFixture.무민_회원가입_요청);
//
//        //when
//        var 요청 = mockMvc.perform(
//                post("/mail/authorization-code")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content));
//
//        //then
//        요청.andExpect(status().isOk());
//    }
//
//}