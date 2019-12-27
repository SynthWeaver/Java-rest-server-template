package controller.mapping;

import database.tabels.UserTable;
import model.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class UserMapping {
    private UserTable userTable = new UserTable();

    @ResponseBody
    @RequestMapping(value = "/create/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody User user) throws SQLException {
        return userTable.createUser(user);
    }

    @ResponseBody
    @GetMapping("/read/user/{email}")
    public User readUser(@PathVariable("email") String email) throws SQLException{
        return userTable.readUser(email);
    }

    @ResponseBody
    @GetMapping("/read/users")
    public List<User> readUsers() throws SQLException{
        return userTable.readUsers();
    }

    @ResponseBody
    @RequestMapping(value = "/update/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUser(@RequestBody List<User> users) throws Exception {
        User oldUser = users.get(0);
        User newUser = users.get(1);

        return userTable.updateUser(oldUser, newUser);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/user", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Object> deleteUser(@RequestBody User user) throws Exception {
        return userTable.deleteUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean login(@RequestBody User user) throws Exception {
        return userTable.login(user);
    }
}