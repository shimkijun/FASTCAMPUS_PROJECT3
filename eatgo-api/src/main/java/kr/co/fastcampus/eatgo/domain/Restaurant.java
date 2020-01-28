package kr.co.fastcampus.eatgo.domain;


public class Restaurant {
    private Long id;
    private String name;
    private String address;

    public Restaurant(String name){
        this.name = name;
    }

    public Restaurant(String name,String address){
        this.name = name;
        this.address = address;
    }

    public Restaurant(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getInfomation() {
        return name + " in "+ address;
    }

    public String getAddress() {
        return address;
    }

}
