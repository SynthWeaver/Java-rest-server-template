package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class Mapping {

    @ResponseBody
    @GetMapping("/get")
    public String get() throws SQLException{
        return "get werkt!";
    }

    @ResponseBody
    @RequestMapping(value = "/post", method = RequestMethod.POST, consumes = "text/plain")
    public void addAccount(@RequestBody String post) {
        System.out.println(post + "= werkt!");
    }
}