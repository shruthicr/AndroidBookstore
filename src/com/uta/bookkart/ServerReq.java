package com.uta.bookkart;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.uta.bookkart.GetUserCallback;
import com.uta.bookkart.User;
import com.uta.bookkart.ServerReq.StoreUserDataAsyncTask;
import com.uta.bookkart.ServerReq.fetchUserDataAsyncTask;

public class ServerReq {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://www.bookkart.herobo.com/";

    public ServerReq(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallBack) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallBack).execute();
    }

    public void fetchUserDataAsyncTask(User user, GetUserCallback userCallBack) {
        progressDialog.show();
        new fetchUserDataAsyncTask(user, userCallBack).execute();
    }

    public void fetchBookDataAsyncTask(book book, GetBookCallback bookCallback) {
        progressDialog.show();
        new fetchBookDataAsyncTask(book, bookCallback).execute();
    }


    /**
     * parameter sent to task upon execution progress published during
     * background computation result of the background computation
     */

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallBack;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
            dataToSend.add(new BasicNameValuePair("f_name", user.f_name));
            dataToSend.add(new BasicNameValuePair("l_name", user.l_name));
            dataToSend.add(new BasicNameValuePair("emailid", user.emailid));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            dataToSend.add(new BasicNameValuePair("c_num", user.c_num));
            dataToSend.add(new BasicNameValuePair("address", user.address));

            HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            userCallBack.done(null);
        }

    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallBack;

        public fetchUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            String result = "";
            InputStream isr = null;
            ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
            //String a = user.emailid;
            dataToSend.add(new BasicNameValuePair("emailid", user.emailid));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost("http://www.bookkart.herobo.com/FetchUserData.php");

            User returnedUser = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                isr = entity.getContent();
                    /*String result = EntityUtils.toString(entity);
	                JSONObject jObject = new JSONObject(result);

	                if (jObject.length() != 0){
	                    //Log.v("happened", "2");
	                    String f_name= jObject.getString("f_name");
	                    String l_name= jObject.getString("l_name");
	                    String c_num= jObject.getString("c_num");
	                    String address = jObject.getString("address");

	                    returnedUser = new User(f_name,l_name, user.emailid, user.password,c_num,address);
	                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                isr.close();

                result = sb.toString();
            } catch (Exception e) {
                Log.e("log_tag", "Error  converting result " + e.toString());
            }
            try {
                String s = "";
                JSONArray jArray = new JSONArray(result);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    String f_name = json.getString("f_name");
                    String l_name = json.getString("l_name");
                    String c_num = json.getString("c_num");
                    String address = json.getString("address");

                    returnedUser = new User(f_name, l_name, user.emailid, user.password, c_num, address);
                }

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error Parsing Data " + e.toString());
            }

            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            super.onPostExecute(returnedUser);
            progressDialog.dismiss();
            userCallBack.done(returnedUser);
        }
    }

    public class fetchBookDataAsyncTask extends AsyncTask<Void, Void, book> {
        book book;
        GetBookCallback bookCallBack;

        public fetchBookDataAsyncTask(book book, GetBookCallback bookCallBack) {
            this.book = book;
            this.bookCallBack = bookCallBack;
        }

        @Override
        protected book doInBackground(Void... params) {
            // TODO Auto-generated method stub

            String result = "";
            InputStream isr = null;
            ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
            //String a = user.emailid;
            dataToSend.add(new BasicNameValuePair("bookname", book.b_name));
            //dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost("http://www.bookkart.herobo.com/bookfetch.php");

            book returnedbook = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                isr = entity.getContent();
		                /*String result = EntityUtils.toString(entity);
		                JSONObject jObject = new JSONObject(result);

		                if (jObject.length() != 0){
		                    //Log.v("happened", "2");
		                    String f_name= jObject.getString("f_name");
		                    String l_name= jObject.getString("l_name");
		                    String c_num= jObject.getString("c_num");
		                    String address = jObject.getString("address");

		                    returnedUser = new User(f_name,l_name, user.emailid, user.password,c_num,address);
		                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                isr.close();

                result = sb.toString();
            } catch (Exception e) {
                Log.e("log_tag", "Error  converting result " + e.toString());
            }
            try {
                String s = "";
                JSONArray jArray = new JSONArray(result);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    String b_isbn = json.getString("ISBN");
                    String b_author = json.getString("Author_Name");
                    String b_category = json.getString("Category");
                    float b_price = (float) json.getDouble("Price");
                    int b_quantity = json.getInt("Quantity");

                    returnedbook = new book(book.b_name, b_category, b_isbn, b_author, b_price, b_quantity);
                }

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error Parsing Data " + e.toString());
            }

            return returnedbook;
        }

        @Override
        protected void onPostExecute(book returnedbook) {
            super.onPostExecute(returnedbook);
            progressDialog.dismiss();
            bookCallBack.done(returnedbook);
        }
    }


}
