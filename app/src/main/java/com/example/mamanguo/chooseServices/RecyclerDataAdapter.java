package com.example.mamanguo.chooseServices;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.MyViewHolder> {
    //Custom interface to keep track of the changes
    private ServicesSelectedListener mServicesSelectedListener;
    private Context mContext;
    //Data structures to hold the activity data
    private ArrayList<DummyParentDataItem> dummyParentDataItems;
    private Map<String, Integer> serviceCount = new HashMap<>();
    private Map<String, Integer> serviceTotal = new HashMap<>();

    RecyclerDataAdapter(ArrayList<DummyParentDataItem> dummyParentDataItems, ServicesSelectedListener servicesSelectedListener) {
        this.dummyParentDataItems = dummyParentDataItems;
        this.mServicesSelectedListener = servicesSelectedListener;
    }

    //e.g. Shirts: 2
    private void setServiceCount(String item, int quantity) {
        //To eliminate possibility of having duplicate key entries
        if (this.serviceCount.containsKey(item)) {
            this.serviceCount.remove(item);
        }
        this.serviceCount.put(item, quantity);
    }

    private Map<String, Integer> getServiceCount() {
        return this.serviceCount;
    }

    //Shirts: sh. 255
    private void setServiceTotal(String item, int subtotal) {
        //To eliminate possibility of having duplicate key entries
        if (this.serviceTotal.containsKey(item)) {
            this.serviceTotal.remove(item);
        }
        this.serviceTotal.put(item, subtotal);
    }

    private Map<String, Integer> getServiceTotal() {
        return this.serviceTotal;
    }

    private void removeItem(String item) {
        if (this.serviceCount.containsKey(item)) this.serviceCount.remove(item);
        if (this.serviceTotal.containsKey(item)) this.serviceTotal.remove(item);
    }

    private int getBillTotal() {
        int bill_total = 0;
        //Returns set view
        Set<Map.Entry<String, Integer>> set = this.serviceTotal.entrySet();
        for (Map.Entry<String, Integer> me : set) {
            bill_total += me.getValue();
        }
        return bill_total;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_child_listing, parent, false);
        mContext = parent.getContext();
        return new MyViewHolder(itemView, mServicesSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DummyParentDataItem dummyParentDataItem = dummyParentDataItems.get(position);
        String text = dummyParentDataItems.get(position).getChildDataItems().get(1).getChildName();
        holder.textView_parentName.setText(dummyParentDataItem.getParentName());
        holder.field_value.setText(text);
        //holder.field_index.setText(Integer.toString(position));
        holder.subtotal_value.setText(mContext.getString(R.string.default_subtotal));

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

    public interface ServicesSelectedListener {
        void onServicesUpdated(Map<String, Integer> serviceCount, Map<String, Integer> serviceTotal, int billTotal);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView textView_parentName;
        private TextView field_value;
        private TextView field_index;
        private TextView subtotal_value;
        private EditText editText_parent;
        private LinearLayout linearLayout_childItems;
        private String TAG = this.getClass().getSimpleName();

        //My custom interface to get that keeps track of the values entered
        ServicesSelectedListener servicesSelectedListener;

        public MyViewHolder(View itemView, final ServicesSelectedListener servicesSelectedListener) {
            super(itemView);
            this.servicesSelectedListener = servicesSelectedListener;
            context = itemView.getContext();
            textView_parentName = itemView.findViewById(R.id.tv_parentName);
            field_value = itemView.findViewById(R.id.field_value);
            field_index = itemView.findViewById(R.id.field_index);
            subtotal_value = itemView.findViewById(R.id.subtotal_value);
            editText_parent = itemView.findViewById(R.id.editText_parent);
            linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
            linearLayout_childItems.setVisibility(View.GONE);

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
                    //String index = field_index.getText().toString();
                    int length = editText_value.toString().length();
                    int unitPrice = Integer.parseInt(field_value.getText().toString());
                    int noItems = length == 0 ? 0 : Integer.parseInt(editText_value.toString());
                    int subtotal = unitPrice * noItems;

                    String item = textView_parentName.getText().toString();
                    subtotal_value.setText(String.format(context.getString(R.string.new_subtotal), subtotal));
                    setServiceCount(item, noItems);
                    setServiceTotal(item, subtotal);
                    int currentTotal = getBillTotal();

                    //Remove item from order if
                    if (noItems == 0) removeItem(item);
                    //Update the info
                    servicesSelectedListener.onServicesUpdated(getServiceCount(), getServiceTotal(), currentTotal);
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
    }

}