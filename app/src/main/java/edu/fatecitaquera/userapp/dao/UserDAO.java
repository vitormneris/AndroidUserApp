package edu.fatecitaquera.userapp.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
        User user = new User();

        try {
            FindByIdRequest findByIdRequest = new FindByIdRequest();
            String jsonString = findByIdRequest.execute(id).get();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(jsonString, Map.class);
            user.setId((String) map.get("id"));
            user.setName((String) map.get("name"));
            user.setEmail((String) map.get("email"));
            user.setPassword((String) map.get("password"));

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findByEmail(String email) {
        User user = new User();

        try {
            FindByEmailRequest findByEmailRequest = new FindByEmailRequest();
            String jsonString = findByEmailRequest.execute(email).get();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(jsonString, Map.class);
            user.setId((String) map.get("id"));
            user.setName((String) map.get("name"));
            user.setEmail((String) map.get("email"));
            user.setPassword((String) map.get("password"));

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public String insert(User user){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonUser = objectMapper.writeValueAsString(user);

            InsertRequest insertRequest = new InsertRequest();
            String jsonString = insertRequest.execute(jsonUser).get();

            Map<String, Object> map = objectMapper.readValue(jsonString, Map.class);
            user.setId((String) map.get("id"));
            user.setName((String) map.get("name"));
            user.setEmail((String) map.get("email"));
            user.setPassword((String) map.get("password"));

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return user.getId();
    }

    public void update(String id, User user){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonUser = objectMapper.writeValueAsString(user);

            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.execute(id, jsonUser).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(String id){
        try {
            DeleteRequest deleteRequest = new DeleteRequest();
            deleteRequest.execute(id).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String email, String password) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        boolean value = false;

        try {
            String jsonUser = objectMapper.writeValueAsString(user);

            LoginRequest loginRequest = new LoginRequest();
            String jsonString = loginRequest.execute(jsonUser).get();

            value = Boolean.valueOf(jsonString);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    public void userAddEvent(String userId, String eventId) {
        UserAddEventRequest userAddEventRequest = new UserAddEventRequest();
        userAddEventRequest.execute(userId, eventId);
    }
}
