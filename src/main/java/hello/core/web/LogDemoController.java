package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
    // ObjectProvider를 적용하면 MyLogger를 주입받는 것이 아닌,
    // MyLogger를 찾을(DL) 수 있는 Provider가 주입된다.
    // 그래서 기존 request 스코프에서 주입 시점이 맞지 않아 에러가 난 것을 수정할 수 있다.

    @RequestMapping("log-demo")
    @ResponseBody // view를 리턴하지 않아도 돼서 문자만 반환할 수 있는 ResponseBody 사용
    public String logDemo(HttpServletRequest request) {
        // HttpServletRequest: 자바에서 제공하는 표준 서블릿 규약
        // 고객의 요청 정보를 받을 수 있다.

        System.out.println("myLogger = " + myLogger.getClass());
        String requestURL = request.getRequestURL().toString();
        // 고객이 어떤 url로 요청했는지 확인

        // 세터로 값 세팅
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }


    // ObjectProvider
    // http가 살아있는 상태에서 컨트롤러에 요청이 오면 그 때서야 DI가 일어난다.
}
