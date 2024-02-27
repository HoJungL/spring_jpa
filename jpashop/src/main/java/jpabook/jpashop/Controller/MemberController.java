package jpabook.jpashop.Controller;

import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        // memberForm이라는 빈 껍데기를 들고 가는거임
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }
}
