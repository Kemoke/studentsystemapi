package util;

public class TokenJson {
    public String jwt;
    public String type;

    public TokenJson(String jwt, String type) {
        this.jwt = jwt;
        this.type = type;
    }
}
