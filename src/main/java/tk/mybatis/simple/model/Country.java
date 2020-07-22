package tk.mybatis.simple.model;

public class Country {
    private Long id;
    private String countryName;
    private String countryCode;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName(){
        return countryName;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
