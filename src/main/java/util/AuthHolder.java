package util;

public class  AuthHolder {

    private Integer tokenId;
    private String tokenName;

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public void reset() {
        tokenId = null;
        tokenName = null;
    }
}
