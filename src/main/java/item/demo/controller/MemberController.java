package item.demo.controller;

import item.demo.domain.Member;
import item.demo.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String memberList(Model model){
        List<Member> members = memberService.findListMember();
        model.addAttribute("members",members);
        return "members/list";
    }

    @GetMapping("/{memberId}")
    public String memberOne(@PathVariable("memberId") Long id, Model model){
        Member member = memberService.findMember(id);
        model.addAttribute("member",member);
        return "members/member";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        Member member=new Member();
        model.addAttribute("member",member);
        return "members/addForm";
    }

    @PostMapping("/add")
    public String addMember(@ModelAttribute Member member){
        memberService.saveMember(member);
        return "redirect:/members";
    }

    @GetMapping("/{memberId}/update")
    public String updateForm(@PathVariable("memberId") Long id, Model model){
        Member member = memberService.findMember(id);
        model.addAttribute("member", member);
        return "members/updateForm";
    }

    @PostMapping("/{memberId}/update")
    public String update(@PathVariable("memberId") Long id, @ModelAttribute Member member){
        member.setId(id);
        memberService.updateMember(member, id);
        return "redirect:/members/" + id;
    }

    @PostMapping("/{memberId}/delete")
    public String deleteMember(@PathVariable("memberId") Long id) {
        memberService.deleteMember(id);
        return "redirect:/members";
    }
}
