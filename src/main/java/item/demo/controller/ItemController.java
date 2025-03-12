package item.demo.controller;

import item.demo.domain.Item;
import item.demo.domain.Member;
import item.demo.service.ItemService;
import item.demo.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final MemberService memberService;

    public ItemController(ItemService itemService, MemberService memberService) {
        this.itemService = itemService;
        this.memberService=memberService;
    }

    @GetMapping
    public String itemList(Model model) {
        List<Item> items=itemService.findListItem();
        model.addAttribute("items", items);
        return "items/list"; // templates/items/list.html
    }

    @GetMapping("/{itemId}")
    public String itemOne(@PathVariable("itemId") Long id, Model model){
        Item item=itemService.findItem(id);
        model.addAttribute("item",item);
        return "items/item";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        Item item=new Item();
        model.addAttribute("item", item);

        List<Member> members = memberService.findListMember();
        model.addAttribute("members", members);
        return "items/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, @RequestParam("memberId") Long memberId){
        Member member = memberService.findMember(memberId);
        item.setMember(member);

        itemService.saveItem(item);
        return "redirect:/items";
    }

    @GetMapping("/{itemId}/update")
    public String updateForm(@PathVariable("itemId") Long id, Model model){
        Item item = itemService.findItem(id);
        model.addAttribute("item", item);

        List<Member> members = memberService.findListMember();
        model.addAttribute("members", members);
        return "items/updateForm";
    }

    @PostMapping("/{itemId}/update")
    public String updateItem(@PathVariable("itemId") Long id, @ModelAttribute Item item,
                             @RequestParam("memberId") Long memberId){
        Member member = memberService.findMember(memberId);
        item.setMember(member);

        item.setId(id);
        itemService.updateItem(item, id);
        return "redirect:/items/"+id;
    }

    @PostMapping("/{itemId}/delete")
    public String deleteItem(@PathVariable("itemId") Long id, RedirectAttributes rttr){
        Item itemDel = itemService.findItem(id);
        if(itemDel != null) {
            itemService.deleteItem(id);
            rttr.addFlashAttribute("msg", "게시물이 삭제됐습니다.");
        }
        return "redirect:/items";
    }
}
