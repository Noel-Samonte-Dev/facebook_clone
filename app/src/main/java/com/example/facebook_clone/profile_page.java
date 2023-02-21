package com.example.facebook_clone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.facebook_clone.post_model.post_adapter;
import com.example.facebook_clone.post_model.post_model;
import java.util.ArrayList;

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

        RecView = v.findViewById(R.id.profile_rv);

        for (int i = 0; i < 20; i++) {
            post_list.add(new post_model("Juan Dela Cruz", "Feb 21, 2023", "Test Description", R.drawable.sample_profile, R.drawable.sample_banner));
            buildRVNewsFeed();
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
