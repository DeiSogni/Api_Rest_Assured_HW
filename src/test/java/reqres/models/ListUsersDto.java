package reqres.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListUsersDto {

    private Integer page, per_page, total, total_pages;
    private ArrayList<Datum> data;
    private Support support;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Datum{
        private Integer id;
        private String email, first_name, last_name, avatar;
    }
@Data
@NoArgsConstructor
@AllArgsConstructor
    public static class Support{
        private String url, text;
    }
}
