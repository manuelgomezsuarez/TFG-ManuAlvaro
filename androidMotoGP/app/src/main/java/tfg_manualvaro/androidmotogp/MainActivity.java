package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import tfg_manualvaro.androidmotogp.adapter.EmployeeAdapter;
import tfg_manualvaro.androidmotogp.models.EmployeeDetails;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_EMPLOYEE_ID = "employee_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DOB = "dob";
    private static final String KEY_DESIGNATION = "designation";
    private static final String KEY_CONTACT_NUMBER = "contact_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SALARY = "salary";
    private String url = "http://api.androiddeft.com/json/employee.php";
    private ProgressDialog pDialog;
    private int success;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Call the AsyncTask
        new FetchEmployeeDetails().execute();

    }

    private class FetchEmployeeDetails extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Data.. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();
            response = jsonParser.makeHttpRequest(url,"GET",null);
            try {
                success = response.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {

                    ListView listView =(ListView)findViewById(R.id.employeeList);
                    if (success == 1) {
                        try {
                            JSONArray employeeArray =  response.getJSONArray(KEY_DATA);
                            List<EmployeeDetails> employeeList = new ArrayList<>();
                            //Populate the EmployeeDetails list from response
                            for (int i = 0; i<employeeArray.length();i++){
                                EmployeeDetails employeeDetails = new EmployeeDetails();
                                JSONObject employeeObj = employeeArray.getJSONObject(i);
                                employeeDetails.setEmployeeId(employeeObj.getInt(KEY_EMPLOYEE_ID));
                                employeeDetails.setName(employeeObj.getString(KEY_NAME));
                                employeeDetails.setDob(employeeObj.getString(KEY_DOB));
                                employeeDetails.setDesignation(employeeObj.getString(KEY_DESIGNATION));
                                employeeDetails.setContactNumber(employeeObj.getString(KEY_CONTACT_NUMBER));
                                employeeDetails.setEmail(employeeObj.getString(KEY_EMAIL));
                                employeeDetails.setSalary(employeeObj.getString(KEY_SALARY));
                                employeeList.add(employeeDetails);
                            }
                            //Create an adapter with the EmployeeDetails List and set it to the LstView
                            adapter = new EmployeeAdapter(employeeList,getApplicationContext());
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivity.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

}