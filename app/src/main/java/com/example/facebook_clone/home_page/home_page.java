package com.example.facebook_clone.home_page;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_clone.Database.DBhelper;
import com.example.facebook_clone.Database.ProfileModel;
import com.example.facebook_clone.R;
import com.example.facebook_clone.post_model.post_adapter;
import com.example.facebook_clone.post_model.post_model;
import com.example.facebook_clone.stories_model.stories_adapter;
import com.example.facebook_clone.stories_model.stories_model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class home_page extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_page, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        RecView = v.findViewById(R.id.news_feed_rv);
        RecView1 = v.findViewById(R.id.stories_rv);

        DBhelper db = new DBhelper(getContext());
        ArrayList<ProfileModel> pm_array_list = db.getAllProfileDetails();

        for (int i = 0; i < pm_array_list.size(); i++) {
            ProfileModel profile_model  = pm_array_list.get(i);
            Integer user_id = profile_model.getUser_id();
            String firstname = profile_model.getFirstname();
            String lastname = profile_model.getLastname();
            String fullname = firstname + " " + lastname;
            String posts = profile_model.getPost();
            String profile_image = profile_model.getProfile_image();
            List<String> post_array = Arrays.asList(posts.trim().split(","));

            for (int a = 0; a < post_array.size(); a++) {
                int profile_image_int = profile_image.trim().isEmpty() ? R.drawable.lesbian : db.profileImageV2(profile_image);
                int banner_image_int = db.getBannerImageV2(Integer.parseInt(post_array.get(a)));

                post_list.add(new post_model(fullname, "Feb 21, 2023", "Test Description", profile_image_int, banner_image_int, String.valueOf(user_id)));
                buildRVNewsFeed();
            }

            Random random = new Random();
            story_list.add(new stories_model(fullname, db.profileImageV2(profile_image), db.getBannerImageV2(Integer.parseInt(post_array.get(random.nextInt(post_array.size())))), String.valueOf(user_id)));
            buildRVStrories();
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

    private RecyclerView RecView1;
    private LinearLayoutManager RecManager1;
    private stories_adapter RecAdapter1;
    private ArrayList<stories_model> story_list = new ArrayList<>();
    private void buildRVStrories() {
        RecView1.setHasFixedSize(false);
        RecManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecAdapter1 = new stories_adapter(getActivity(), story_list, RecView1);
        RecView1.setLayoutManager(RecManager1);
        RecView1.setAdapter(RecAdapter1);
    }
}
