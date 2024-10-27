package edu.fatecitaquera.userapp.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.fatecitaquera.userapp.background.DeleteRequest;
import edu.fatecitaquera.userapp.background.FindByEmailRequest;
import edu.fatecitaquera.userapp.background.FindByIdRequest;
import edu.fatecitaquera.userapp.background.InsertRequest;
import edu.fatecitaquera.userapp.background.LoginRequest;
import edu.fatecitaquera.userapp.background.UpdateRequest;
import edu.fatecitaquera.userapp.background.UserAddEventRequest;
import edu.fatecitaquera.userapp.model.User;

public class UserDAO {

    public User findById(String id) {
        User user = null;

        try {
            FindByIdRequest findByIdRequest = new FindByIdRequest();
            String jsonString = findByIdRequest.execute(id).get();

            if (jsonString == null) return null;

            user = new User();
            user = convertToUser(jsonString);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User findByEmail(String email) {
        User user = null;

        try {
            FindByEmailRequest findByEmailRequest = new FindByEmailRequest();
            String jsonString = findByEmailRequest.execute(email).get();

            if (jsonString == null) return null;

            user = new User();
            user = convertToUser(jsonString);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean insert(User user){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            InsertRequest insertRequest = new InsertRequest();
            return insertRequest.execute(objectMapper.writeValueAsString(user)).get();
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(String id, User user){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            UpdateRequest updateRequest = new UpdateRequest();
            return updateRequest.execute(id, objectMapper.writeValueAsString(user)).get();
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteById(String id){
        try {
            DeleteRequest deleteRequest = new DeleteRequest();
            return deleteRequest.execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean login(String email, String password) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User(null, null, email, password);

        try {
            LoginRequest loginRequest = new LoginRequest();
            String login = loginRequest.execute(objectMapper.writeValueAsString(user)).get();
            if (login == null) return null;
            return Boolean.valueOf(login);
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean userAddEvent(String userId, String eventId) {
        try {
            UserAddEventRequest userAddEventRequest = new UserAddEventRequest();
            return userAddEventRequest.execute(userId, eventId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User convertToUser(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();

        try {
            Map map = objectMapper.readValue(jsonString, Map.class);
            user.setId((String) map.get("id"));
            user.setName((String) map.get("name"));
            user.setEmail((String) map.get("email"));
            user.setPassword((String) map.get("password"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
