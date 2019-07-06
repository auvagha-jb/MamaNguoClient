 package com.example.mamanguo.ui.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mamanguo.R;
import com.example.mamanguo.helpers.SummaryTable;

public class CheckoutActivity extends AppCompatActivity {
    //Class that contains methods to populate the table layout dynamically
    SummaryTable summaryTable = new SummaryTable(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_);

        TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayout);
        //Attach the rows
        TableRow grandTotalRow =  summaryTable.grandTotalRow(1000);
        tableLayout.addView(grandTotalRow);
    }

}
