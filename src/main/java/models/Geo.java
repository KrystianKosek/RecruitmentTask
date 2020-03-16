package models;

import lombok.Data;
import lombok.NonNull;

@Data
public class Geo {
    @NonNull
    private String lat;
    @NonNull
    private String lng;
}
