package Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://techpyton.000webhostapp.com/NetworkIt/";

    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

  /*  OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS).build();*/

    private RetrofitClient() {

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();


    }

    public static synchronized RetrofitClient getInstance() {


        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

}
