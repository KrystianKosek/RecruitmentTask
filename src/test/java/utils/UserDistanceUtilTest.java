package utils;

import models.Geo;
import models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDistanceUtilTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private User user;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private User user2;

    private static final String lat1 = "-37.3159";
    private static final String lng1 = "81.1496";
    private static final String lat2 = "-43.9509";
    private static final String lng2 = "-34.4618";

    @Test
    public void shouldPassWhenDistancePairsReturned() {
        // given
        given(user.getId()).willReturn(0);
        given(user2.getId()).willReturn(1);
        given(user.getAddress().getGeo()).willReturn(new Geo(lat1, lng1));
        given(user2.getAddress().getGeo()).willReturn(new Geo(lat2, lng2));

        // when
        final List<AbstractMap.SimpleEntry<User, User>> result = UserDistanceUtil.getTheNearestUserForEach(Arrays.asList(user, user2));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getKey()).isEqualTo(user);
        assertThat(result.get(0).getValue()).isSameAs(user2);
        assertThat(result.get(1).getKey()).isEqualTo(user2);
        assertThat(result.get(1).getValue()).isEqualTo(user);
    }

    @Test
    public void shouldPassWhenGivenOneUninitializedAndNull() {
        // when
        final List<AbstractMap.SimpleEntry<User, User>> result = UserDistanceUtil.getTheNearestUserForEach(Arrays.asList(user, null));

        // then
        assertThat(result).isEmpty();
        assertThat(result).hasSize(0);
    }

    @Test
    public void shouldPassWhenGivenOneInitializedAndNull() {
        // given
        given(user.getId()).willReturn(0);
        given(user.getAddress().getGeo()).willReturn(new Geo(lat1, lng1));

        // when
        final List<AbstractMap.SimpleEntry<User, User>> result = UserDistanceUtil.getTheNearestUserForEach(Arrays.asList(user, null));

        // then
        assertThat(result).isEmpty();
        assertThat(result).hasSize(0);
    }

    @Test
    public void shouldPassWhenGivenDuplicates() {
        // given
        given(user.getId()).willReturn(0);
        given(user.getAddress().getGeo()).willReturn(new Geo(lat1, lat2));

        // when
        final List<AbstractMap.SimpleEntry<User, User>> result = UserDistanceUtil.getTheNearestUserForEach(Arrays.asList(user, user));

        // then
        assertThat(result).isEmpty();
        assertThat(result).hasSize(0);
    }

    @Test
    public void shouldPassWhenGivenTwoUninitialized() {
        // when
        final List<AbstractMap.SimpleEntry<User, User>> result = UserDistanceUtil.getTheNearestUserForEach(Arrays.asList(user, user2));

        // then
        assertThat(result).isEmpty();
        assertThat(result).hasSize(0);
    }

    @Test
    public void shouldPassWhenEmptyListGiven() {
        // when
        final List<AbstractMap.SimpleEntry<User, User>> result = UserDistanceUtil.getTheNearestUserForEach(Collections.emptyList());

        // then
        assertThat(result).isEmpty();
        assertThat(result).hasSize(0);
    }

    @Test
    public void shouldPassWhenNullGiven() {
        // when
        final List<AbstractMap.SimpleEntry<User, User>> result = UserDistanceUtil.getTheNearestUserForEach(null);

        // then
        assertThat(result).isEmpty();
        assertThat(result).hasSize(0);
    }
}
