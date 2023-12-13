package com.integrated.techhub.member.presentation;

import com.integrated.techhub.member.application.MemberService;
import com.integrated.techhub.member.dto.MemberChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody final MemberChangePasswordRequest request
    ) {
        request.validateSamePassword();
        memberService.changePassword(request);
        return ResponseEntity.ok().build();
    }

}
