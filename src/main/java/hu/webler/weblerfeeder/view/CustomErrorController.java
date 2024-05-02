package hu.webler.weblerfeeder.view;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest servletRequest) {
        Object status = servletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status !=null && Integer.valueOf(status.toString()) == HttpStatus.NOT_FOUND.value())
        {
            return "feeder-error404";
        }
        return "feeder-error";
    }
}
