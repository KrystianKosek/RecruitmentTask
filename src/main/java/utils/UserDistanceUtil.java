package utils;

import models.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserDistanceUtil {
    public static List<AbstractMap.SimpleEntry<User, User>> getTheNearestUserForEach(List<User> users) {
        if (Objects.isNull(users)) {
            return Collections.emptyList();
        }

        List<User> userList = users.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<AbstractMap.SimpleEntry<User, User>> userPairs = new ArrayList<>();

        for (User user : userList) {
            double theShortestDistance = Double.MAX_VALUE;
            User nearestNeighbor = null;

            for (User neighbor : userList) {
                if (user.getId() != neighbor.getId()) {
                    double dist = calculateDistanceBetweenTwoUsers(user, neighbor);
                    if (dist < theShortestDistance) {
                        theShortestDistance = dist;
                        nearestNeighbor = neighbor;
                    }
                }
            }
            if (Objects.nonNull(nearestNeighbor)) {
                userPairs.add(new AbstractMap.SimpleEntry<>(user, nearestNeighbor));
            }
        }

        return userPairs;
    }

    private static double calculateDistanceBetweenTwoUsers(User user1, User user2) {
        double lat1 = Double.parseDouble(user1.getAddress().getGeo().getLat());
        double lat2 = Double.parseDouble(user2.getAddress().getGeo().getLat());
        double lon1 = Double.parseDouble(user1.getAddress().getGeo().getLng());
        double lon2 = Double.parseDouble(user2.getAddress().getGeo().getLng());

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0.0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1))
                    * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));

            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515 * 1.609344;

            return dist;
        }
    }
}
