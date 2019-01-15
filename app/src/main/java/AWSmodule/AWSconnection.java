package AWSmodule;


import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Callback;


/**
 * Created on 16/02/18.
 */

//Methods for interacting with AWS
public class AWSconnection {


    //API Key Value
    private static String xAPI() {
        return "KOerxTcmrQ5YKCu7NpsKJ3jUFpTxIBSM38cVhYdN";
    }


    //Callback interface for string results from async calls
    public interface textResult {
        void getResult(String textResult);
    }


    //Callback interface for array results from async calls
    public interface arrayResult {
        void getResult(ArrayList<String> arrayListResult);
    }


    /////////////////////////////////////////
    //Get terms and Conditions text from AWS
    /////////////////////////////////////////
    public static void selectTacURL(final textResult result) {

        //HTTP configure
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("x-api-key", xAPI())
                .get()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/tac")
                .build();

        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //callback results
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                String jsonData = response.body().string();


                //Format terms and conditions to String
                try {

                    JSONObject Jobject = new JSONObject(jsonData);
                    JSONArray Jarray = Jobject.getJSONArray("Items");


                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jo = Jarray.getJSONObject(i);

                        String text = jo.optString("tacText");

                        //Send callback results to interface so it can be retrieved by another class
                        result.getResult(text);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    /////////////////////////////////////////
    //Get appliance list from AWS
    /////////////////////////////////////////
    public static void selectApplianceListURL(final arrayResult result) {

        //HTTP configure
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/appliancelist")
                .get()
                .addHeader("x-api-key", xAPI())
                .build();

        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }


            //callback results
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                String jsonData = response.body().string();


                //Get appliance list from callback and format into ArrayList
                try {

                    JSONObject Jobject = new JSONObject(jsonData);
                    JSONArray Jarray = Jobject.getJSONArray("Items");
                    ArrayList<String> list = new ArrayList();


                    if (Jarray != null) {
                        int len = Jarray.length();
                        for (int i = 0; i < len; i++) {

                            list.add(Jarray.get(i).toString());
                        }
                    }

                    //Send callback results to interface so it can be retrieved by another class
                    result.getResult(list);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    /////////////////////////////////////////
    //Register Customers
    /////////////////////////////////////////
    public static void insertCustomerURL(String email, String password, String street_address,
                                         String suburb, String city, String first_name, String last_name, int postcode, String country,
                                         final textResult result) {


        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("email", email);
            item.put("password", password);
            item.put("street_address", street_address);
            item.put("suburb", suburb);
            item.put("city", city);
            item.put("first_name", first_name);
            item.put("last_name", last_name);
            item.put("postcode", postcode);
            item.put("country", country);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/customer/customerinsert")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                //Format Callback message to String
                String jsonData = response.body().string();

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(jsonData);


            }
        });

    }


    /////////////////////////////////////////
    //Select individual customer details based upon email and password
    /////////////////////////////////////////
    public static void selectCustomerURL(String email, String password, final arrayResult result) {


        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("email", email);
            item.put("password", password);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON to be posted
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/customer/customerselect")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback results
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                String jsonData = response.body().string();


                //format customer details retrieved from AWS to ArrayList
                try {

                    JSONObject Jobject = new JSONObject(jsonData);
                    JSONArray Jarray = Jobject.getJSONArray("Items");
                    ArrayList<String> list = new ArrayList();


                    if (Jarray != null) {
                        int len = Jarray.length();
                        for (int i = 0; i < len; i++) {

                            list.add(Jarray.get(i).toString());
                        }
                    }

                    //Send callback results to interface so it can be retrieved by another class
                    result.getResult(list);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    /////////////////////////////////////////
    //Update customer details
    /////////////////////////////////////////

    public static void updateCustomerDetailsURL(String email, String street_address,
                                                  String suburb, String city, String first_name, String last_name, int postcode, String country,
                                                final textResult result) {


        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("email", email);
            item.put("newstreet_address", street_address);
            item.put("newsuburb", suburb);
            item.put("newcity", city);
            item.put("newfirstname", first_name);
            item.put("newlastname", last_name);
            item.put("newpostcode", postcode);
            item.put("newcountry", country);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON to be posted
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/updatecustomer/updatecustomerdetails")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                //Format Callback message to String
                String jsonData = response.body().string();

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(jsonData);


            }
        });

    }


    /////////////////////////////////////////
    //Update customer password
    /////////////////////////////////////////

    public static void updateCustomerPasswordURL(String email, String password,
                                                   final textResult result) {


        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("email", email);
            item.put("newpassword", password);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON to be posted
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/updatecustomer/updatecustomerpassword")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                //Format Callback message to String
                String jsonData = response.body().string();

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(jsonData);


            }
        });

    }


    /////////////////////////////////////////
    //Register Appliance
    /////////////////////////////////////////
    public static void insertCustomerApplianceURL(String app_serial_num, String
            app_type, String fire_model,String wifi_dongle_UUID, String email, String nickname,
                                                  final textResult result) {

        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("app_serial_num", app_serial_num);
            item.put("app_type", app_type);
            item.put("fire_model", fire_model);
            item.put("wifi_dongle_UUID", wifi_dongle_UUID);
            item.put("email", email);
            item.put("nickname", nickname);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON to be posted
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/appliance/customerapplianceinsert")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                //Format Callback message to String
                String jsonData = response.body().string();

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(jsonData);


            }
        });

    }



    /////////////////////////////////////////
    //Select appliances owned by a customer
    /////////////////////////////////////////
    public static void selectCustomerApplianceURL(String email, final arrayResult result){


    //HTTP initiate
    OkHttpClient httpClient = new OkHttpClient();
    JSONArray array = new JSONArray();


        try {
        //Create JSON object
        JSONObject item = new JSONObject();
        item.put("cust_email", email);
        array.put(item);

    } catch (JSONException e) {
        e.printStackTrace();
    }

    //Format JSON to be posted
    String postArray = array.toString();
    postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    //Configure HTTP headers and body
    RequestBody body = RequestBody.create(mediaType, postArray);
    Request request = new Request.Builder()
            .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/appliance/customerapplianceselect")
            .post(body)
            .addHeader("x-api-key", xAPI())
            .addHeader("content-type", "application/json")
            .build();


    //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("error", "error in getting response using async okhttp call");
        }

        //Callback results
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (!response.isSuccessful()) {
                throw new IOException("Error response " + response);
            }

            String jsonData = response.body().string();


            //format Appliance details from to ArrayList
            try {

                JSONObject Jobject = new JSONObject(jsonData);
                JSONArray Jarray = Jobject.getJSONArray("Items");
                ArrayList<String> list = new ArrayList();


                if (Jarray != null) {
                    int len = Jarray.length();
                    for (int i = 0; i < len; i++) {

                        list.add(Jarray.get(i).toString());
                    }
                }

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(list);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    });
}





    /////////////////////////////////////////
    //Update customer appliance ownership
    /////////////////////////////////////////

    public static void updateCustomerApplianceURL(String app_serial_num, String email, final textResult result) {

        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("newcust_email", email);
            item.put("app_serial_num", app_serial_num);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON to be posted
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/changecustomerappliancelink")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                //Format Callback message to String
                String jsonData = response.body().string();

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(jsonData);


            }
        });

    }



    /////////////////////////////////////////
    //Insert into Operations
    /////////////////////////////////////////
    public static void insertOperationURL(String wifi_dongle_UUID, String
            burning_speed_information, String burning_state,
                                          String economy_function, String error_code, String flame_level,
                                          String lighting, String lighting_information, String main_power_switch,
                                          String message1, String message2, String operation_mode,
                                          String operation_state, String room_temperature, String setting_temperature) {


        try {
            //HTTP configuration, Create and Format JSON token and execution
            OkHttpClient client = new OkHttpClient();
            JSONArray array = new JSONArray();
            try {

                JSONObject item = new JSONObject();
                item.put("wifi_dongle_UUID", wifi_dongle_UUID);
                item.put("burning_speed_information", burning_speed_information);
                item.put("burning_state", burning_state);
                item.put("economy_function", economy_function);
                item.put("error_code", error_code);
                item.put("flame_level", flame_level);
                item.put("lighting", lighting);
                item.put("lighting_information", lighting_information);
                item.put("main_power_switch", main_power_switch);
                item.put("message1", message1);
                item.put("message2", message2);
                item.put("operation_mode", operation_mode);
                item.put("operation_state", operation_state);
                item.put("room_temperature", room_temperature);
                item.put("setting_temperature", setting_temperature);
                array.put(item);


                String postArray = array.toString();
                postArray = postArray.replaceAll("[\\[\\]]", "");
                Log.i("JSONarray: ", postArray);

                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");


                RequestBody body = RequestBody.create(mediaType, postArray);
                Request request = new Request.Builder()
                        .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/operation")
                        .post(body)
                        .addHeader("x-api-key", xAPI())
                        .addHeader("content-type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }


    }


    /////////////////////////////////////////
    //Insert into Energy Information
    /////////////////////////////////////////
    public static void insertEnergyInformationURL(String wifi_dongle_UUID, String
            first_speed_burning_hours, String second_speed_burning_hours,
                                                  String third_speed_burning_hours, String fourth_speed_burning_hours, String
                                                          fifth_speed_burning_hours,
                                                  String sixth_speed_burning_hours, String seventh_speed_burning_hours) {

        try {
            //HTTP configuration, Create and Format JSON token and execution
            OkHttpClient client = new OkHttpClient();
            JSONArray array = new JSONArray();
            try {

                JSONObject item = new JSONObject();
                item.put("wifi_dongle_UUID", wifi_dongle_UUID);
                item.put("first_speed_burning_hours", first_speed_burning_hours);
                item.put("second_speed_burning_hours", second_speed_burning_hours);
                item.put("third_speed_burning_hours", third_speed_burning_hours);
                item.put("fourth_speed_burning_hours", fourth_speed_burning_hours);
                item.put("fifth_speed_burning_hours", fifth_speed_burning_hours);
                item.put("sixth_speed_burning_hours", sixth_speed_burning_hours);
                item.put("seventh_speed_burning_hours", seventh_speed_burning_hours);
                array.put(item);


                String postArray = array.toString();
                postArray = postArray.replaceAll("[\\[\\]]", "");
                Log.i("JSONarray: ", postArray);

                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");


                RequestBody body = RequestBody.create(mediaType, postArray);
                Request request = new Request.Builder()
                        .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/streaminformation/energyinformation")
                        .post(body)
                        .addHeader("x-api-key", xAPI())
                        .addHeader("content-type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    /////////////////////////////////////////
    //Insert into Maintenance Information
    /////////////////////////////////////////
    public static void insertMaintenanceInformationURL(String wifi_dongle_UUID, String
            error_history_1, String error_history_2,
                                                       String error_history_3, String error_history_4, String error_history_5,
                                                       String error_history_6, String error_history_7, String error_history_8,
                                                       String error_history_9, String total_burning_hours, String total_burning_cycles) {


        try {
            //HTTP configuration, Create and Format JSON token and execution
            OkHttpClient client = new OkHttpClient();
            JSONArray array = new JSONArray();
            try {

                JSONObject item = new JSONObject();
                item.put("wifi_dongle_UUID", wifi_dongle_UUID);
                item.put("error_history_1", error_history_1);
                item.put("error_history_2", error_history_2);
                item.put("error_history_3", error_history_3);
                item.put("error_history_4", error_history_4);
                item.put("error_history_5", error_history_5);
                item.put("error_history_6", error_history_6);
                item.put("error_history_7", error_history_7);
                item.put("error_history_8", error_history_8);
                item.put("error_history_9", error_history_9);
                item.put("total_burning_hours", total_burning_hours);
                item.put("total_burning_cycles", total_burning_cycles);
                array.put(item);


                String postArray = array.toString();
                postArray = postArray.replaceAll("[\\[\\]]", "");
                Log.i("JSONarray: ", postArray);

                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");


                RequestBody body = RequestBody.create(mediaType, postArray);
                Request request = new Request.Builder()
                        .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/streaminformation/maintenanceinformation")
                        .post(body)
                        .addHeader("x-api-key", xAPI())
                        .addHeader("content-type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }


    }


    /////////////////////////////////////////
    //Insert into Running Information
    /////////////////////////////////////////
    public static void insertRunningInformationURL(String wifi_dongle_UUID, String
            flame_rod_output, String burning_fan_rpm) {


        try {
            //HTTP configuration, Create and Format JSON token and execution
            OkHttpClient client = new OkHttpClient();
            JSONArray array = new JSONArray();
            try {

                JSONObject item = new JSONObject();
                item.put("wifi_dongle_UUID", wifi_dongle_UUID);
                item.put("flame_rod_output", flame_rod_output);
                item.put("burning_fan_rpm", burning_fan_rpm);
                array.put(item);


                String postArray = array.toString();
                postArray = postArray.replaceAll("[\\[\\]]", "");
                Log.i("JSONarray: ", postArray);

                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");


                RequestBody body = RequestBody.create(mediaType, postArray);
                Request request = new Request.Builder()
                        .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/streaminformation/runninginformation")
                        .post(body)
                        .addHeader("x-api-key", xAPI())
                        .addHeader("content-type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }


    }


    /////////////////////////////////////////
    //Insert into Product Information
    /////////////////////////////////////////
    public static void insertProductInformationURL(String wifi_dongle_UUID, String
            gas_type_code, String PCB_model_code) {

        try {
            //HTTP configuration, Create and Format JSON token and execution
            OkHttpClient client = new OkHttpClient();
            JSONArray array = new JSONArray();
            try {

                JSONObject item = new JSONObject();
                item.put("wifi_dongle_UUID", wifi_dongle_UUID);
                item.put("gas_type_code", gas_type_code);
                item.put("PCB_model_code", PCB_model_code);
                array.put(item);


                String postArray = array.toString();
                postArray = postArray.replaceAll("[\\[\\]]", "");
                Log.i("JSONarray: ", postArray);

                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");


                RequestBody body = RequestBody.create(mediaType, postArray);
                Request request = new Request.Builder()
                        .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/streaminformation/productinformation")
                        .post(body)
                        .addHeader("x-api-key", xAPI())
                        .addHeader("content-type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }


    }


    /////////////////////////////////////////
    //Password Reset - Generate and Email verification token
    /////////////////////////////////////////
    public static void resetPasswordGenerateTokenURL(String email,
                                         final textResult result) {


        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("email", email);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/resetpassword/resetpasswordgeneratetoken")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                //Format Callback message to String
                String jsonData = response.body().string();

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(jsonData);


            }
        });

    }


    /////////////////////////////////////////
    //Password Reset - Verify token and insert new password
    /////////////////////////////////////////
    public static void resetPasswordVerifyTokenURL(String email, String token, String password,
                                                     final textResult result) {


        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("email", email);
            item.put("token", token);
            item.put("password", password);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/resetpassword/resetpasswordverifytoken")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                //Format Callback message to String
                String jsonData = response.body().string();

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(jsonData);


            }
        });

    }



    /////////////////////////////////////////
    //Update appliance nickname
    /////////////////////////////////////////

    public static void updateApplianceNicknameURL(String wifi_dongle_UUID, String newnickname,
                                                final textResult result) {


        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("wifi_dongle_UUID", wifi_dongle_UUID);
            item.put("newnickname", newnickname);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON to be posted
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/appliance/updatenickname")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                //Format Callback message to String
                String jsonData = response.body().string();

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(jsonData);


            }
        });

    }


    /////////////////////////////////////////
    //Update appliance UUID
    /////////////////////////////////////////

    public static void updateApplianceUUIDURL(String app_serial_num, String wifi_dongle_UUID,
                                                  final textResult result) {


        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("wifi_dongle_UUID", wifi_dongle_UUID);
            item.put("app_serial_num", app_serial_num);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON to be posted
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/appliance/updateuuid")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                //Format Callback message to String
                String jsonData = response.body().string();

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(jsonData);


            }
        });

    }


    /////////////////////////////////////////
    //Remote Control - Device State
    /////////////////////////////////////////

    public static void remoteControlDeviceStateURL(String app_serial_num, String device_state,
                                              final textResult result) {


        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("device_state", device_state);
            item.put("app_serial_num", app_serial_num);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON to be posted
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/remotecontrol/device-state")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                //Format Callback message to String
                String jsonData = response.body().string();

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(jsonData);


            }
        });

    }

    /////////////////////////////////////////
    //Remote Control - Device Temp
    /////////////////////////////////////////

    public static void remoteControlDeviceTempURL(String app_serial_num, int device_temp,
                                                   final textResult result) {


        //HTTP initiate
        OkHttpClient httpClient = new OkHttpClient();
        JSONArray array = new JSONArray();


        try {
            //Create JSON object
            JSONObject item = new JSONObject();
            item.put("device_temp", device_temp);
            item.put("app_serial_num", app_serial_num);
            array.put(item);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format JSON to be posted
        String postArray = array.toString();
        postArray = postArray.replaceAll("[\\[\\]]", "");
        Log.i("JSONarray: ", postArray);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        //Configure HTTP headers and body
        RequestBody body = RequestBody.create(mediaType, postArray);
        Request request = new Request.Builder()
                .url("https://y49sqtdtv4.execute-api.ap-southeast-2.amazonaws.com/prod/remotecontrol/device-temp")
                .post(body)
                .addHeader("x-api-key", xAPI())
                .addHeader("content-type", "application/json")
                .build();


        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error in getting response using async okhttp call");
            }

            //Callback
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                //Format Callback message to String
                String jsonData = response.body().string();

                //Send callback results to interface so it can be retrieved by another class
                result.getResult(jsonData);


            }
        });

    }


}




