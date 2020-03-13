package Api;

import Models.ApprovedEventData;
import Models.MeetingData;
import Response.ApprovedEventResponse;
import Response.EventDataResponse;
import Response.GeneralResponse;
import Response.GetDetailResponse;
import Response.GroupDataResponse;
import Response.LoginResponse;
import Response.MeetingDataResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {


    //compulsory
    @FormUrlEncoded
    //end point
    @POST("registration.php")
    //if you dont have any idea that what will be the reponsse type then call responseBody
    Call<ResponseBody> userRegistration(
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username,
            @Field("status") String status,
            @Field("device_id") String device_id,
            @Field("token_id") String token_id

    );


    //for login

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> userLogin(

            @Field("email") String email,
            @Field("password") String password

    );


    //for one time registration
    @FormUrlEncoded
    @POST("details.php")
    Call<ResponseBody> userDetails(
            @Field("name") String name,
            @Field("bussiness") String bussiness,
            @Field("city") String city,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("zipcode") String zipcode,
            @Field("email") String email,
            @Field("password") String password


    );


    //Group data api
    //@FormUrlEncoded
    @GET("groups.php")
    Call<GroupDataResponse> groupData();

    @GET("featured_groups.php")
    Call<GroupDataResponse> featuredGroupData();

    //events data
    @GET("events.php")
    Call<EventDataResponse> eventData();

    //featured events data
    @GET("featured_events.php")
    Call<EventDataResponse> featuredEventData();

    //meetings data
    @GET("meetings.php")
    Call<MeetingDataResponse> meetingData();


    //join group request
    @FormUrlEncoded
    @POST("join_group.php")
    Call<ResponseBody> joinGroup(
            @Field("name") String name,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("request_date") String date,
            @Field("request_time") String time,
            @Field("group_id") String group_id,
            @Field("status") String status,
            @Field("title") String title,
            @Field("group_name") String group_name,
            @Field("bussiness") String bussiness,
            @Field("join_date") String join_date,
            @Field("join_time") String join_time

    );


    @FormUrlEncoded
    @POST("group_joined_list.php")
    Call<ResponseBody> groupJoined(

            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("getdetail.php")
    Call<GetDetailResponse> getDetail(

            @Field("email") String email

    );


    @FormUrlEncoded
    @POST("updatedetail.php")
    Call<ResponseBody> updateDetail(
            @Field("name") String name,
            @Field("bussiness") String bussiness,
            @Field("city") String city,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("email") String email
    );


    @FormUrlEncoded
    @POST("request_guest_pass.php")
    Call<GeneralResponse> requestGuestPass(
            @Field("email") String email,
            @Field("event_id") String event_id,
            @Field("status") String status,
            @Field("event_name") String event_name,
            @Field("date") String date,
            @Field("time")String time


    );

    @FormUrlEncoded
    @POST("event_approved.php")
    Call<ApprovedEventResponse> getApprovedEventList(

            @Field("email") String email

    );
}
