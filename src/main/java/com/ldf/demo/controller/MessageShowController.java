package com.ldf.demo.controller;

import com.ldf.demo.pojo.Message;
import com.ldf.demo.pojo.User;
import com.ldf.demo.service.MessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.sound.midi.Track;
import javax.validation.Valid;
import java.util.List;

/**
 * @author: 清峰
 * @date: 2020/9/24 14:50
 * @code: 愿世间永无Bug!
 * @description:
 */
@Controller
@Api(tags = "信息展示模块")
public class MessageShowController {

    @Autowired
    private MessageService messageService;

    @Value("${message.avatar}")
    private String avatar;

    //留言页面展示
    @GetMapping("/message")
    public String Message() {
        return "message";
    }

    //异步查询留言信息
    @GetMapping("/messagecomment")
    public String messages(Model model) {
        //TODO 有Bug 查询不到留言信息
        List<Message> messages = messageService.listMessages();
        System.out.println(messages);
        model.addAttribute("messages", messages);
        return "message :: messageList";
    }

    //保存留言
    @PostMapping("/message")
    public String post(Message message, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        //设置头像
        if (user != null) {
            message.setAvatar(user.getAvatar());
            message.setAdminMessage(true);
        } else {
            message.setAvatar(avatar);
        }
        if (message.getParentMessage().getId() != null) {
            message.setParentMessageId(message.getParentMessage().getId());
        }
        System.out.println("准备保存留言");
        messageService.saveMessage(message);
        List<Message> messages = messageService.listMessages();
        System.out.println("保存完毕查询留言: "+messages);
        model.addAttribute("messages", messages);
        return "message::messageList";
    }

    @GetMapping("/messages/{id}/delete")
    public String deleteMessage(@PathVariable Long id,Model model){
        messageService.deleteMessage(id);
        List<Message> messages = messageService.listMessages();
        model.addAttribute("messages",messages);
        return "redirect:/message";
    }

}
