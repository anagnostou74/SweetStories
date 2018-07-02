package gr.mobap.sweetstories.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitBuilder {
    final Retrofit retrofit;

    final Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    final OkHttpClient client = new OkHttpClient();

    public RetrofitBuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public RecipesInterface createRecipesInterface() {
        return retrofit.create(RecipesInterface.class);
    }
}
