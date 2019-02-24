package tfg_manualvaro.androidmotogp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.EmployeeDetails;

import java.util.List;


/**
 * Created by Abhi on 03 Sep 2017 008.
 */

public class EmployeeAdapter extends ArrayAdapter<EmployeeDetails> {
    private static final String KEY_EMPLOYEE_ID = "Employee Id: ";
    private static final String KEY_NAME = "Name: ";
    private static final String KEY_DOB = "Date of Birth: ";
    private static final String KEY_DESIGNATION = "Designation: ";
    private static final String KEY_CONTACT_NUMBER = "Contact Number: ";
    private static final String KEY_EMAIL = "Email: ";
    private static final String KEY_SALARY = "Salary: ";
    private List<EmployeeDetails> dataSet;

    public EmployeeAdapter(List<EmployeeDetails> dataSet, Context mContext) {
        super(mContext, R.layout.employee_row, dataSet);
        this.dataSet = dataSet;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.employee_row, null);
        }
        EmployeeDetails employee = dataSet.get(position);
        if (employee != null) {
            //Text View references
            TextView employeeId = (TextView) v.findViewById(R.id.employeeId);
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView dob = (TextView) v.findViewById(R.id.dob);
            TextView designation = (TextView) v.findViewById(R.id.designation);
            TextView contactNumber = (TextView) v.findViewById(R.id.contact_number);
            TextView email = (TextView) v.findViewById(R.id.email);
            TextView salary = (TextView) v.findViewById(R.id.salary);

            //Updating the text views
            employeeId.setText(KEY_EMPLOYEE_ID + employee.getEmployeeId());
            name.setText(KEY_NAME + employee.getName());
            dob.setText(KEY_DOB + employee.getDob());
            designation.setText(KEY_DESIGNATION + employee.getDesignation());
            contactNumber.setText(KEY_CONTACT_NUMBER + employee.getContactNumber());
            email.setText(KEY_EMAIL + employee.getEmail());
            salary.setText(KEY_SALARY + employee.getSalary().toString());
        }

        return v;
    }
}