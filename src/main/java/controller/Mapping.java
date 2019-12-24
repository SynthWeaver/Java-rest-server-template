package controller;

import database.UserDB;
import model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class Mapping {
    private UserDB userDb = new UserDB();

    @ResponseBody
    @RequestMapping(value = "/create/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody String json) throws SQLException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonUser = (JSONObject) jsonParser.parse(json);
        User user = new User(jsonUser);

        return userDb.createUser(user);
    }

    @ResponseBody
    @GetMapping("/read/user/{email}")
    public User readUser(@PathVariable("email") String email) throws SQLException{
        return userDb.readUser(email);
    }

    @ResponseBody
    @GetMapping("/read/users")
    public List<User> readUsers() throws SQLException{
        return userDb.readUsers();
    }

    @ResponseBody
    @RequestMapping(value = "/update/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUser(@RequestBody String json) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject oldAndNewUserJsonArray = (JSONObject) jsonParser.parse(json);

        JSONObject oldUserJson = (JSONObject) oldAndNewUserJsonArray.get("oldUser");
        JSONObject newUserJson = (JSONObject) oldAndNewUserJsonArray.get("newUser");
        User oldUser = new User(oldUserJson);
        User newUser = new User(newUserJson);

        return userDb.updateUser(oldUser, newUser);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(@RequestBody String json) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonUser = (JSONObject) jsonParser.parse(json);
        User user = new User(jsonUser);

        return userDb.deleteUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean login(@RequestBody String json) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonUser = (JSONObject) jsonParser.parse(json);
        User user = new User(jsonUser);

        return userDb.login(user);
    }
}