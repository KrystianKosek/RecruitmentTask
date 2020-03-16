package models;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface TypicodeApi {
    @GET("users/")
    Call<List<User>> listUsers();

    @GET("posts/")
    Call<List<Post>> listPosts();
}
