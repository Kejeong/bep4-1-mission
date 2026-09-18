package com.back.boundedContext.member.in;

import com.back.boundedContext.member.app.MemberFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
    private final MemberFacade memberFacade;

    @GetMapping("randomSecureTip")
    public String getRandomSecureTip() { // 결합도를 없애기 위해 분리
        return memberFacade.getRandomSecureTip();
    }
}