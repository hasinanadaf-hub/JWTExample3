package com.jwt.example.JWTExample3.services;

import com.jwt.example.JWTExample3.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServices
{
  private List<User> store = new ArrayList<>();

  public UserServices()
  {
      store.add(new User(UUID.randomUUID().toString(),"hasina",
                           "hasina@gmail.com"));
      store.add(new User(UUID.randomUUID().toString(),"Kajal",
              "kajal@gmail.com"));
      store.add(new User(UUID.randomUUID().toString(),"sahil",
              "sahil@gmail.com"));
      store.add(new User(UUID.randomUUID().toString(),"remya",
              "remya@gmail.com"));
  }
  public List<User> getUsers()
  {
      return this.store;
  }
}
