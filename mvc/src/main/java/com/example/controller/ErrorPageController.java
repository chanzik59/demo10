package com.example.controller;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author czq
 * @date 2023/10/20 15:10
 * @Description:
 */
@Controller
public class ErrorPageController {
    @ResponseBody
    @RequestMapping("/error/{status}")
    public String errorPage(@PathVariable Integer status, HttpServletRequest request) {
        switch (status) {
            case 401:
            case 400:
                return "/error/404";
            case 404:
                return "听说你找不到页面？";
            case 500:
                return "/error/500";
            default:
                return "/error/default";
        }
    }

}
