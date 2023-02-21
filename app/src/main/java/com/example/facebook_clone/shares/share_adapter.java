package com.example.facebook_clone.shares;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.facebook_clone.R;
import com.example.facebook_clone.profile_page;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class share_adapter extends RecyclerView.Adapter<share_adapter.share_adapter_view_holder> {

    private List<share_model> items;
    private Context context;
    private View v;
    private RecyclerView recyclerView;

    public share_adapter(Context context, List<share_model> items, RecyclerView recyclerView, sendShareOnClickListener sendShareOnClick) {
        this.items = items;
        this.recyclerView = recyclerView;
        this.context = context;
        this.sendShareOnClick = sendShareOnClick;
    }

    @NonNull
    @Override
    public share_adapter.share_adapter_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_layout, parent, false);
        share_adapter.share_adapter_view_holder rvh = new share_adapter.share_adapter_view_holder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull share_adapter.share_adapter_view_holder holder, int position) {
        share_model currentItem = items.get(position);
        holder.name.setText(currentItem.getName());
        holder.profile_image.setImageResource(currentItem.getDp());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class share_adapter_view_holder extends RecyclerView.ViewHolder {

        private ImageView send_share;
        private TextView name;
        private CircleImageView profile_image;
        share_adapter_view_holder(@NonNull View itemView) {
            super(itemView);

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
                    shareClick(arr);
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

                    String[] arr = new String[1];
                    arr[0] = "1";
                    shareClick(arr);
                }
            });

            send_share = itemView.findViewById(R.id.send_share);
            send_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] arr = new String[1];
                    arr[0] = "1";
                    shareClick(arr);
                }
            });
        }
    }

    private sendShareOnClickListener sendShareOnClick;
    public void shareClick(String[] string_array) {sendShareOnClick.onSendShare(string_array);
    }

    public interface sendShareOnClickListener {
        void onSendShare(String[] string_array);
    }
}