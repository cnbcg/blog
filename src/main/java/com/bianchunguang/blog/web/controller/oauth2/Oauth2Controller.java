package com.bianchunguang.blog.web.controller.oauth2;

import com.bianchunguang.blog.web.controller.BaseController;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@Controller
@ControllerAdvice
@RequestMapping("/oauth")
public class Oauth2Controller extends BaseController {

    @RequestMapping("confirm_access")
    public String getAccessConfirmation(Model model, HttpServletRequest request) throws Exception {
        if (request.getAttribute("_csrf") != null) {
            model.addAttribute("_csrf", request.getAttribute("_csrf"));
        }

        if (!model.containsAttribute("scopes")) {
            model.addAttribute("scopes", request.getAttribute("scopes"));
        }

        return "views/oauth/confirm";
    }

    @RequestMapping("error")
    public String handleError(Model model, HttpServletRequest request) {
        Object error = request.getAttribute("error");
        if (error == null) {
            error = Collections.singletonMap("summary", "Unknown error");
        }
        model.addAttribute("error", error);
        return "views/oauth/error";
    }

}
