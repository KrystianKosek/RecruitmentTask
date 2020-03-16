package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
    @Expose(serialize = false, deserialize = false)
    private List<Post> posts = new ArrayList<>();

    public Integer getPostCount() {
        return posts.size();
    }

}
