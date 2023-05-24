package com.example.facebook_clone;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_clone.Database.DBhelper;
import com.example.facebook_clone.Database.ProfileModel;
import com.example.facebook_clone.post_model.post_adapter;
import com.example.facebook_clone.post_model.post_model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile_page extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_page, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        SharedPreferences sp = getActivity().getSharedPreferences("Profile", MODE_PRIVATE);
        String login_id = sp.getString("login_id", "");
        String selected_id = sp.getString("selected_id", "");
        TextView profile_name_tv = v.findViewById(R.id.profile_name_tv);
        ImageView banner_profile = v.findViewById(R.id.banner_profile);
        CircleImageView profile_image_civ = v.findViewById(R.id.profile_image);

        DBhelper db = new DBhelper(getContext());
        ProfileModel profile_model = db.getProfileDetails(selected_id);
        String firstname = profile_model.getFirstname();
        String lastname = profile_model.getLastname();
        String fullname = firstname + " " + lastname;
        String posts = profile_model.getPost();
        String shared_posts = profile_model.getShared_post();
        String profile_image = profile_model.getProfile_image();
        List<String> post_array = Arrays.asList(posts.trim().split(","));
        List<String> shared_posts_array = Arrays.asList(shared_posts.trim().split(","));

        profile_name_tv.setText(fullname);
        banner_profile.setImageResource(db.getBannerImageV2(Integer.parseInt(post_array.get(3))));
        profile_image_civ.setImageResource(profile_image.trim().isEmpty() ? R.drawable.lesbian : db.profileImageV2(profile_image));

        RecView = v.findViewById(R.id.profile_rv);
        for (int i = 0; i < post_array.size(); i++) {
            int profile_image_int = profile_image.trim().isEmpty() ? R.drawable.lesbian : db.profileImageV2(profile_image);
            int banner_image_int = db.getBannerImageV2(Integer.parseInt(post_array.get(i)));
            post_list.add(new post_model(fullname, "Feb 21, 2023", "Test Description", profile_image_int, banner_image_int, selected_id));
            buildRVNewsFeed();
        }

        Button logout_btn = v.findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("Profile", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("selected_id", "");
                editor.putString("login_id", "");
                editor.commit();

                Intent intent = new Intent(getActivity(), login_page.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        if (!selected_id.equals(login_id)) {
            ViewGroup.LayoutParams pm = logout_btn.getLayoutParams();
            pm.height = 1;
            pm.width = 1;
            logout_btn.setLayoutParams(pm);
            logout_btn.setEnabled(false);
        }
    }

    private RecyclerView RecView;
    private RecyclerView.LayoutManager RecManager;
    private post_adapter RecAdapter;
    private ArrayList<post_model> post_list = new ArrayList<>();
    private void buildRVNewsFeed() {
        RecView.setHasFixedSize(false);
        RecManager = new LinearLayoutManager(getActivity());
        RecAdapter = new post_adapter(getActivity(), post_list, RecView);
        RecView.setLayoutManager(RecManager);
        RecView.setAdapter(RecAdapter);
    }
}
