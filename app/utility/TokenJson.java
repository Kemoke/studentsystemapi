package utility;

/**
 * Created by kemoke on 11/19/16.
 */
public class TokenJson {
    public String jwt;
    public String type;

    public TokenJson(String jwt, String type) {
        this.jwt = jwt;
        this.type = type;
    }
}
