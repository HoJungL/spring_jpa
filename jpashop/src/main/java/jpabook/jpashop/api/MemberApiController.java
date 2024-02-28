package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController // controller, ResponseBody가 둘다 담겨있는 컨트롤러
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList()); // 이거 안하면 리스트로 확나가니까 유연성 떨어짐.

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result <T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
    //등록
    // 장점 : Class를 하나더 안 만들어도 됨.
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //장점 : API 스펙이 안바뀜 -> compile 오류를 만들어내줌. -> 안정적
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member); // request는 안들어감!
        return new CreateMemberResponse(id);
    }

    // 회원 수정 API
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMember2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        //변경감지임. update 확인해보깅
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }


    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        //생성자
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
