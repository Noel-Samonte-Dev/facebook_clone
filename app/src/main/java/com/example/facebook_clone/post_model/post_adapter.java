package com.example.facebook_clone.post_model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.facebook_clone.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class post_adapter extends RecyclerView.Adapter<post_adapter.post_adapter_view_holder> {

    private List<post_model> items;
    private Context context;
    private View v;
    private RecyclerView recyclerView;

    public post_adapter(Context context, List<post_model> items, RecyclerView recyclerView) {
        this.items = items;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public void setItems(List<post_model> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public post_adapter_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
        post_adapter.post_adapter_view_holder rvh = new post_adapter.post_adapter_view_holder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull post_adapter_view_holder holder, int position) {
        post_model currentItem = items.get(position);
        holder.profile_image.setImageResource(currentItem.getDp());
        holder.post_image.setImageResource(currentItem.getPost());
        holder.name.setText(currentItem.getName());
        holder.date.setText(currentItem.getDate());
        holder.post_desc.setText(currentItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class post_adapter_view_holder extends RecyclerView.ViewHolder {

        private TextView name, date, post_desc;
        private ImageView post_image;
        private CircleImageView profile_image;
        post_adapter_view_holder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            post_desc = itemView.findViewById(R.id.post_desc);
            post_image = itemView.findViewById(R.id.post_image);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}