package com.bianchunguang.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class BaseController {

    public static final String MODEL_KEY_LOCALE = "locale";
    public static final String MODEL_KEY_THEME = "theme";

    private @Autowired MessageSource messageSource;

    private ThreadLocal<Model> modelThreadLocal = new ThreadLocal<>();

    @ModelAttribute
    public void initModel(Model model, HttpServletRequest request) {
        modelThreadLocal.set(model);
        model.addAttribute(MODEL_KEY_LOCALE, RequestContextUtils.getLocale(request).toString());
        model.addAttribute(MODEL_KEY_THEME, RequestContextUtils.getTheme(request).getName());
    }

    protected String getMessage(String messageCode, Object[] messageArgs) {
        Locale locale = StringUtils.parseLocaleString(modelThreadLocal.get().asMap().get(MODEL_KEY_LOCALE).toString());
        return messageSource.getMessage(messageCode, messageArgs, locale);
    }

    protected String getMessage(String messageCode) {
        return getMessage(messageCode, null);
    }

    public Model getModel() {
        return modelThreadLocal.get();
    }

    public <T> T messageResponseEntity(Object message, HttpStatus httpStatus) {
        return (T) new ResponseEntity("{\"message\": \"" + message + "\"}", httpStatus);
    }

    public static void assertErrorIsNull(BindingResult result) {
        if (result.getErrorCount() > 0) {
            String message = result.getAllErrors().stream().map(error -> error.getDefaultMessage()).reduce((error1, error2) -> error1 + "、" + error2).get();
            throw new IllegalArgumentException(message);
        }
    }

}
