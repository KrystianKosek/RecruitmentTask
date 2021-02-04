import models.Post;
import models.TypicodeApi;
import models.User;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.PostTitleDuplicatesUtil;
import utils.UserDistanceUtil;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    private static final String url = "https://jsonplaceholder.typicode.com";

    public static void main(String[] args) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        List<User> users = downloadData(httpClient);

        for (User user : users) {
            System.out.println(user.getUsername() + " napisał(a) " + user.getPostCount() + " postów.");
        }

        Set<String> duplicates = PostTitleDuplicatesUtil.returnListOfDuplicates(users);

        System.out.println("\nPowtarzające się tytuły: " + duplicates + "\n");

        List<AbstractMap.SimpleEntry<User, User>> userPairs = UserDistanceUtil.getTheNearestUserForEach(users);

        userPairs.forEach(pair -> System.out.println("Najbliżej użytkownika " +
                pair.getKey().getUsername() + " mieszka " +
                pair.getValue().getUsername()));

        httpClient.connectionPool().evictAll();
    }

    private static List<User> downloadData(OkHttpClient httpClient) {
        List<User> users = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TypicodeApi service = retrofit.create(TypicodeApi.class);

        try {
            Response<List<User>> userCall = service.listUsers().execute();

            if (Objects.isNull(userCall.body())) {
                return Collections.emptyList();
            }

            Response<List<Post>> posts = service.listPosts().execute();

            if (Objects.isNull(posts.body())) {
                return users;
            }

            Map<Integer, List<Post>> collect = posts.body().stream()
                    .collect(Collectors.groupingBy(Post::getUserId));

            for (User user : userCall.body()) {
                List<Post> userPosts = collect.get(user.getId());
                user.setPosts(userPosts);
                users.add(user);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }
}
