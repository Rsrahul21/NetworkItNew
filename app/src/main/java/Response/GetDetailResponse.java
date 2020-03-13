package Response;

public class GetDetailResponse {

    String email;
    String name;
    String phone;
    String zipcode;
    String address;
    String status;
    String response;
    String city;

    public String getCity() {
        return city;
    }

    public String getBussiness() {
        return bussiness;
    }

    String bussiness;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }
}
// {"email":"kunal1@gmail.com","name":"Kunal","phone":"789654","zipcode":"123654","address":"Phase 7 market","password":"202cb962ac59075b964b07152d234b70","status":1,"response":"Record Found"}