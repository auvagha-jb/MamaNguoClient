package com.example.mamanguo.chooseServices;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mamanguo.R;

import java.util.ArrayList;
import java.util.Arrays;


class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.MyViewHolder> {
    private ArrayList<DummyParentDataItem> dummyParentDataItems;
    private static int[] bill_total;

    RecyclerDataAdapter(ArrayList<DummyParentDataItem> dummyParentDataItems) {
        this.dummyParentDataItems = dummyParentDataItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_child_listing, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DummyParentDataItem dummyParentDataItem = dummyParentDataItems.get(position);
        String text = dummyParentDataItems.get(position).getChildDataItems().get(1).getChildName();
        holder.textView_parentName.setText(dummyParentDataItem.getParentName());
        holder.field_value.setText(text);
        holder.field_index.setText(Integer.toString(position));
        holder.subtotal_value.setText("Subtotal: 0");

        int noOfChildTextViews = holder.linearLayout_childItems.getChildCount();
        for (int index = 0; index < noOfChildTextViews; index++) {
            TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(index);
            currentTextView.setVisibility(View.VISIBLE);
        }

        int noOfChild = dummyParentDataItem.getChildDataItems().size();
        if (noOfChild < noOfChildTextViews) {
            for (int index = noOfChild; index < noOfChildTextViews; index++) {
                TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(index);
                currentTextView.setVisibility(View.GONE);
            }
        }
        for (int textViewIndex = 0; textViewIndex < noOfChild; textViewIndex++) {
            TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(textViewIndex);
            currentTextView.setText(dummyParentDataItem.getChildDataItems().get(textViewIndex).getChildName());
        }
    }

    @Override
    public int getItemCount() {
        return dummyParentDataItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView textView_parentName;
        private TextView field_value;
        private TextView field_index;
        private TextView subtotal_value;
        private EditText editText_parent;
        private LinearLayout linearLayout_childItems;
        private String TAG = this.getClass().getSimpleName();

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textView_parentName = itemView.findViewById(R.id.tv_parentName);
            field_value = itemView.findViewById(R.id.field_value);
            field_index = itemView.findViewById(R.id.field_index);
            subtotal_value = itemView.findViewById(R.id.subtotal_value);
            editText_parent = itemView.findViewById(R.id.editText_parent);
            linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
            linearLayout_childItems.setVisibility(View.GONE);
            int size = getItemCount();
            bill_total = new int[size];
            initializeArray(bill_total);

            int intMaxNoOfChild = 0;
            for (int index = 0; index < dummyParentDataItems.size(); index++) {
                int intMaxSizeTemp = dummyParentDataItems.get(index).getChildDataItems().size();
                if (intMaxSizeTemp > intMaxNoOfChild) intMaxNoOfChild = intMaxSizeTemp;
            }
            for (int indexView = 0; indexView < intMaxNoOfChild; indexView++) {
                TextView textView = new TextView(context);
                textView.setId(indexView);
                textView.setPadding(0, 30, 0, 30);
                textView.setGravity(Gravity.CENTER);
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.background_sub_module_text));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setOnClickListener(this);
                linearLayout_childItems.addView(textView, layoutParams);
            }


            textView_parentName.setOnClickListener(this);
            editText_parent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence editText_value, int start, int before, int count) {
                    int length = editText_value.toString().length();
                    int index = Integer.parseInt(field_index.getText().toString());
                    int unit_price = Integer.parseInt(field_value.getText().toString());
                    int no_items = length == 0 ? 0 : Integer.parseInt(editText_value.toString());
                    int subtotal = unit_price * no_items;
                    subtotal_value.setText("Subtotal: " + subtotal);
                    bill_total[index] = subtotal;
                    int currentTotal = getBillTotal();

                    Toast.makeText(context, "Bill total: "+currentTotal, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_parentName) {
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linearLayout_childItems.setVisibility(View.GONE);
                } else {
                    linearLayout_childItems.setVisibility(View.VISIBLE);
                }
            } else {
                TextView textViewClicked = (TextView) view;
                Toast.makeText(context, "" + textViewClicked.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }

        public int getBillTotal() {
            int total = 0;
            for (int bill_item : bill_total) {
                total += bill_item;
            }
            return total;
        }

        public void initializeArray(int bill_total[]) {
            for (int i = 0; i < bill_total.length; i++) {
                bill_total[i] = 0;
            }
        }

    }
}