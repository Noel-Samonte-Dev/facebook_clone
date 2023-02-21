package com.example.facebook_clone.comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.facebook_clone.R;
import com.example.facebook_clone.profile_page;
import com.example.facebook_clone.shares.share_adapter;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class comments_adapter extends RecyclerView.Adapter<comments_adapter.comments_adapter_view_holder> {

    private List<comments_model> items;
    private Context context;
    private View v;
    private RecyclerView recyclerView;

    public comments_adapter(Context context, List<comments_model> items, RecyclerView recyclerView, commentOnClickListener commentOnClickListener) {
        this.items = items;
        this.recyclerView = recyclerView;
        this.context = context;
        this.commentOnClickListener = commentOnClickListener;
    }

    public void setItems(List<comments_model> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public comments_adapter.comments_adapter_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);
        comments_adapter.comments_adapter_view_holder rvh = new comments_adapter.comments_adapter_view_holder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull comments_adapter.comments_adapter_view_holder holder, int position) {
        comments_model currentItem = items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class comments_adapter_view_holder extends RecyclerView.ViewHolder {

        private TextView name, date, post_desc, like_count, share_count, comment_count;
        private CircleImageView profile_image;
        private LinearLayout post_counts_layout;
        private Button like_btn, comment_btn, share_btn;
        comments_adapter_view_holder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            post_desc = itemView.findViewById(R.id.comment_desc);
            profile_image = itemView.findViewById(R.id.profile_image);
            profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    manager.beginTransaction()
                            .add(R.id.fragmentContainerView, new profile_page())
                            .addToBackStack(null).commit();

                    String[] arr = new String[1];
                    arr[0] = "1";
                    commentClick(arr);

                }
            });

            name = itemView.findViewById(R.id.comment_name);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    manager.beginTransaction()
                            .add(R.id.fragmentContainerView, new profile_page())
                            .addToBackStack(null).commit();

                    String[] arr = new String[1];
                    arr[0] = "1";
                    commentClick(arr);
                }
            });

            like_count = itemView.findViewById(R.id.like_count);
            share_count = itemView.findViewById(R.id.share_count);
            comment_count = itemView.findViewById(R.id.comment_count);

//            like_btn = itemView.findViewById(R.id.like_btn);
//            comment_btn = itemView.findViewById(R.id.comment_btn);
//            comment_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    comments_bottom_sheet bottom_sheet_dialog_saved_cards = new comments_bottom_sheet();
//                    bottom_sheet_dialog_saved_cards.show(((AppCompatActivity) context).getSupportFragmentManager(), "null");
//                }
//            });

            share_btn = itemView.findViewById(R.id.share_btn);
        }
    }

    private commentOnClickListener commentOnClickListener;
    public void commentClick(String[] string_array) {commentOnClickListener.onClick(string_array);
    }

    public interface commentOnClickListener {
        void onClick(String[] string_array);
    }
}