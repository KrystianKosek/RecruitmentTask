package utils;

import models.Post;
import models.User;

import java.util.*;
import java.util.stream.Collectors;

public class PostTitleDuplicatesUtil {
    public static Set<String> returnListOfDuplicates(List<User> users) {
        if (Objects.isNull(users)) {
            return Collections.emptySet();
        }

        return users.stream()
                .flatMap(user -> user.getPosts().stream())
                .collect(Collectors.groupingBy(Post::getTitle))
                .values().stream()
                .filter(posts -> posts.size() > 1)
                .flatMap(Collection::stream)
                .map(Post::getTitle)
                .collect(Collectors.toSet());
    }
}
