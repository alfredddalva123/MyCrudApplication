package com.example.mycrudapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class PersonalInfoAdapter extends BaseAdapter {

    private Context context;
    private List<PersonalInfo> infoList;
    private OnItemAction listener;

    public interface OnItemAction {
        void onEdit(PersonalInfo info);
        void onDelete(PersonalInfo info);
    }

    public PersonalInfoAdapter(Context context, List<PersonalInfo> infoList, OnItemAction listener) {
        this.context = context;
        this.infoList = infoList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return infoList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Inflate the layout if it's not being recycled
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_personal_info, parent, false);
        }

        // 2. Get the current data object
        PersonalInfo info = infoList.get(position);

        // 3. Initialize Views
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvDetails = convertView.findViewById(R.id.tvDetails);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        // 4. Set the Data
        tvName.setText(info.getName());
        tvDetails.setText(info.getEmail() + " | " + info.getPhone());

        /* NOTE: We removed setTextColor(Color.WHITE) because the
           XML now handles the dark green (#014421) text color
           to contrast with the white CardView background.
        */

        // 5. Set Click Listeners
        btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(info);
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(info);
            }
        });

        return convertView;
    }
}