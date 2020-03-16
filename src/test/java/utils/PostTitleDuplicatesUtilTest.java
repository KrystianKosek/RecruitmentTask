package utils;

import models.Post;
import models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostTitleDuplicatesUtilTest {
    @Mock
    private User user;
    @Mock
    private User user2;

    public static final String DUPLICATE_TITLE = "Duplicate Title";
    public static final String TEST_TITLE = "Test Title";

    @Test
    public void shouldPassWhenDuplicatesReturned() {
        // given
        User user3 = Mockito.mock(User.class);
        given(user.getPosts()).willReturn(singletonList(new Post(DUPLICATE_TITLE)));
        given(user2.getPosts()).willReturn(singletonList(new Post(DUPLICATE_TITLE)));
        given(user3.getPosts()).willReturn(singletonList(new Post(TEST_TITLE)));

        // when
        final Set<String> result = PostTitleDuplicatesUtil.returnListOfDuplicates(Arrays.asList(user, user2, user3));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.contains(DUPLICATE_TITLE)).isTrue();
        assertThat(result.contains(TEST_TITLE)).isFalse();
    }

    @Test
    public void shouldPassWhenNoDuplicatesReturned() {
        // given
        given(user.getPosts()).willReturn(singletonList(new Post(DUPLICATE_TITLE)));
        given(user2.getPosts()).willReturn(singletonList(new Post(TEST_TITLE)));

        // when
        final Set<String> result = PostTitleDuplicatesUtil.returnListOfDuplicates(Arrays.asList(user, user2));

        // then
        assertThat(result).isEmpty();
        assertThat(result).hasSize(0);
    }

    @Test
    public void shouldPassWhenNoDuplicatesReturnedAndEmptyListGiven() {
        // given
        List<User> userList = new ArrayList<>();

        // when
        final Set<String> result = PostTitleDuplicatesUtil.returnListOfDuplicates(userList);

        // then
        assertThat(result).isEmpty();
        assertThat(result).hasSize(0);
    }

    @Test
    public void shouldPassWhenNoDuplicatesReturnedAndNullGiven() {
        // when
        final Set<String> result = PostTitleDuplicatesUtil.returnListOfDuplicates(null);

        // then
        assertThat(result).isEmpty();
        assertThat(result).hasSize(0);
    }
}
