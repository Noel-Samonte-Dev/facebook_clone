package com.example.facebook_clone.post_model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.facebook_clone.R;
import com.example.facebook_clone.comments.comments_bottom_sheet;
import com.example.facebook_clone.profile_page;
import com.example.facebook_clone.shares.share_bottom_sheet;

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

        private TextView name, date, post_desc, like_count, share_count, comment_count;
        private ImageView post_image;
        private CircleImageView profile_image;
        private LinearLayout post_counts_layout;
        private Button like_btn, comment_btn, share_btn;
        post_adapter_view_holder(@NonNull View itemView) {
            super(itemView);
            post_counts_layout = itemView.findViewById(R.id.post_counts_layout);
            date = itemView.findViewById(R.id.date);
            post_desc = itemView.findViewById(R.id.post_desc);
            post_image = itemView.findViewById(R.id.post_image);
            profile_image = itemView.findViewById(R.id.profile_image);
            profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    manager.beginTransaction()
                            .add(R.id.fragmentContainerView, new profile_page())
                            .addToBackStack(null).commit();

                }
            });

            name = itemView.findViewById(R.id.name);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    manager.beginTransaction()
                            .add(R.id.fragmentContainerView, new profile_page())
                            .addToBackStack(null).commit();
                }
            });

            like_btn = itemView.findViewById(R.id.like_btn);
            like_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewGroup.LayoutParams pm = post_counts_layout.getLayoutParams();
                    if (pm.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                        pm.height = 1;
                        pm.width = 1;
                        like_btn.setTextColor(ContextCompat.getColor(context, R.color.black));
                    } else {
                        pm.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        pm.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        like_btn.setTextColor(ContextCompat.getColor(context, R.color.blue));
                    }

                    post_counts_layout.setLayoutParams(pm);
                }
            });

            comment_btn = itemView.findViewById(R.id.comment_btn);
            comment_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comments_bottom_sheet bs = new comments_bottom_sheet();
                    bs.show(((AppCompatActivity) context).getSupportFragmentManager(), "null");
                }
            });

            share_btn = itemView.findViewById(R.id.share_btn);
            share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share_bottom_sheet bs = new share_bottom_sheet();
                    bs.show(((AppCompatActivity) context).getSupportFragmentManager(), "null");
                }
            });

            like_count = itemView.findViewById(R.id.like_count);
            share_count = itemView.findViewById(R.id.share_count);
            comment_count = itemView.findViewById(R.id.comment_count);
        }
    }
}