package com.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ajax.ajaxDAO;
import com.fenye.Fenye;
import com.service.BlogService;

public abstract class control {

	public abstract String toUsercenter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException ;

}
