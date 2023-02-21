package com.example.facebook_clone.comments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.facebook_clone.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class comments_bottom_sheet extends BottomSheetDialogFragment implements comments_adapter.commentOnClickListener {

    private View mv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mv = inflater.inflate(R.layout.comment_bottom_sheet, container, false);
        comments_rv = mv.findViewById(R.id.comments_rv);

        ImageView close_comments = mv.findViewById(R.id.close_comments);
        close_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        TextInputEditText comment_field = mv.findViewById(R.id.comment_field);
        comment_field.requestFocus();

        for (int i = 0; i < 5; i++) {
            comments_list.add(new comments_model("", "", "", 1, 2, 3, R.drawable.sample_profile));
            buildRVComments();
        }


        return mv;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomSheetBehavior<View> bsb = BottomSheetBehavior.from((View) mv.getParent());
        bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private RecyclerView comments_rv;
    private RecyclerView.LayoutManager RecManager;
    private comments_adapter RecAdapter;
    private ArrayList<comments_model> comments_list = new ArrayList<>();
    private void buildRVComments() {
        comments_rv.setHasFixedSize(false);
        RecManager = new LinearLayoutManager(getActivity());
        RecAdapter = new comments_adapter(getActivity(), comments_list, comments_rv, this);
        comments_rv.setLayoutManager(RecManager);
        comments_rv.setAdapter(RecAdapter);
    }

    @Override
    public void onClick(String[] string_array) {
        dismiss();
    }
}
