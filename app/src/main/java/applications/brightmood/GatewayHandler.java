package applications.brightmood;

/**
 * Created by Michael on 13/10/17.
 * Created by florali on 2/12/17.
 */
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GatewayHandler extends AsyncTask<String, Void, String> {
    private TextView tv;
    GatewayHandler(TextView tv) {
        this.tv = tv;
    }
    @Override
    protected String doInBackground(String... urls) {
        // we use the OkHttp library from https://github.com/square/okhttp
        OkHttpClient client = new OkHttpClient();
        Request request =
                new Request.Builder()
                        .url(urls[0])
                        .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed send";
    }
    @Override
    protected void onPostExecute(String result) {
//        tv.setText(result);
    }
}
