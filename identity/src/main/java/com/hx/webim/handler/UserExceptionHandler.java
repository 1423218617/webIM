package com.hx.webim.handler;



import com.hx.webim.Exception.UserException;
import com.hx.webim.model.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class UserExceptionHandler  {

    private static final Logger log= LoggerFactory.getLogger(UserExceptionHandler.class);


    @ExceptionHandler(value = UserException.class)
    public Object ss(HttpServletRequest request, UserException e){
        log.error(e.getMessage());
        return new ResultVo<String>();
    }
}
