package com.example.facebook_clone.stories_model;

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

public class stories_adapter extends RecyclerView.Adapter<stories_adapter.stories_model_view_holder> {

    private List<stories_model> items;
    private Context context;
    private View v;
    private RecyclerView recyclerView;

    public stories_adapter(Context context, List<stories_model> items, RecyclerView recyclerView) {
        this.items = items;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public void setItems(List<stories_model> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public stories_model_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stories_layout, parent, false);
        stories_adapter.stories_model_view_holder rvh = new stories_adapter.stories_model_view_holder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull stories_adapter.stories_model_view_holder holder, int position) {
        stories_model currentItem = items.get(position);
        holder.post_image.setImageResource(currentItem.getPost());
        holder.profile_image.setImageResource(currentItem.getDp());
        holder.name.setText(currentItem.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class stories_model_view_holder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView post_image;
        private CircleImageView profile_image;
        stories_model_view_holder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            post_image = itemView.findViewById(R.id.story_img);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}
