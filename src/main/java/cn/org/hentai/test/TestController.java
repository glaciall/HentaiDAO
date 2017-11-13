package cn.org.hentai.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by matrixy on 2017-11-13.
 */
@Controller
@RequestMapping("/test")
public class TestController
{
    @Autowired
    TestDAO testDAO;

    @RequestMapping("/index")
    public String index(HttpServletResponse response)
    {
        Log.debug("haha here...");
        TestModel model = new TestModel();
        model.setId(1212);
        model.setName("hahahahahaha");
        testDAO.save(model);

        model.setId(24);
        model.setName("gagagaga" + Math.random());
        testDAO.update(model);

        if (response != null) throw new RuntimeException("roll back");
        return "test";
    }
}
