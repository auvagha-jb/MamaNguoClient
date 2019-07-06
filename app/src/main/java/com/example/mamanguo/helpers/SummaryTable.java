package com.example.mamanguo.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mamanguo.R;

import java.util.Locale;

public class SummaryTable {
    Context context;

    public SummaryTable(Context context) {
        this.context = context;
    }

    public TableRow itemRow(String item, int unitPrice, String subtotal) {
        TableRow tableRow = new TableRow(context);
        // Set new table row layout parameters.
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        tableRow.setLayoutParams(layoutParams);


        return tableRow;
    }

    public TableRow grandTotalRow(int billTotal) {
        TableRow tableRow = new TableRow(context);

        // Set new table row layout parameters.
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        tableRow.setLayoutParams(layoutParams);

        // Add a TextView in the first column.
        TextView textView = new TextView(context);
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 3;
        textView.setLayoutParams(params);
        textView.setText(context.getString(R.string.grand_total_text));
        textView.setTypeface(null, Typeface.BOLD);
        tableRow.addView(textView, 0);

        // Add a TextView in the first column.
        TextView billTotalText = new TextView(context);
        billTotalText.setText(String.format(Locale.getDefault(), "%d", billTotal));
        billTotalText.setTypeface(null, Typeface.BOLD);
        tableRow.addView(billTotalText, 1);

        return tableRow;
    }

}
