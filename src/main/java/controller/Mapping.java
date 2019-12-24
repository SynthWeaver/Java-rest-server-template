package controller;

import database.DB;
import model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class Mapping {
    private DB db = new DB();

    @ResponseBody
    @RequestMapping(value = "/create/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody String json) throws SQLException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonUser = (JSONObject) jsonParser.parse(json);
        User user = new User(jsonUser);

        db.createUser(user);
    }

    @ResponseBody
    @GetMapping("/read/user/{email}")
    public User readUser(@PathVariable("email") String email) throws SQLException{
        return db.readUser(email);
    }

    @ResponseBody
    @GetMapping("/read/users")
    public List<User> readUsers() throws SQLException{
        return db.readUsers();
    }

    @ResponseBody
    @RequestMapping(value = "/update/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody String json) throws SQLException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonUser = (JSONObject) jsonParser.parse(json);
        User user = new User(jsonUser);

        db.updateUser(user.getEmail(), user);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/user/{email}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable("email") String email) throws SQLException {
        db.deleteUser(email);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean login(@RequestBody String json) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonUser = (JSONObject) jsonParser.parse(json);
        User user = new User(jsonUser);

        return db.login(user);
    }
}