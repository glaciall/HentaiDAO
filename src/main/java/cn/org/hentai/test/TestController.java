package cn.org.hentai.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by matrixy on 2017-11-13.
 */
@Controller
public class TestController
{
    @Autowired
    TestDAO testDAO;

    @RequestMapping("/test/index")
    public void index(HttpServletResponse response)
    {
        testDAO.findCount(12, "haha");
        try
        {
            response.getWriter().println("<h1>Hello HentaiDAO</h1>");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
