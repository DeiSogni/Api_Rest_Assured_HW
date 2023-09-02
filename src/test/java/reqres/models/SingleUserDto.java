package reqres.models;

import lombok.Data;

@Data
public class SingleUserDto {

    private Data data;
    private Support support;

    @lombok.Data
    public class Data{
        private Integer id;
        private String email, first_name, last_name, avatar;
    }

    @lombok.Data
    public class Support{
        private String url, text;
        }
}
