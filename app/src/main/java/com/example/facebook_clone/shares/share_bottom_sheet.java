package com.example.facebook_clone.shares;

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
import java.util.ArrayList;

public class share_bottom_sheet extends BottomSheetDialogFragment implements share_adapter.sendShareOnClickListener{

    private View mv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mv = inflater.inflate(R.layout.share_bottom_modal, container, false);
        share_rv = mv.findViewById(R.id.share_rv);

        ImageView close_share = mv.findViewById(R.id.close_share);
        close_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        for (int i = 0; i < 5; i++) {
            share_list.add(new share_model("Juan Dela Cruz", R.drawable.sample_profile));
            buildRVShares();
        }


        return mv;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomSheetBehavior<View> bsb = BottomSheetBehavior.from((View) mv.getParent());
        bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private RecyclerView share_rv;
    private RecyclerView.LayoutManager RecManager;
    private share_adapter RecAdapter;
    private ArrayList<share_model> share_list = new ArrayList<>();
    private void buildRVShares() {
        share_rv.setHasFixedSize(false);
        RecManager = new LinearLayoutManager(getActivity());
        RecAdapter = new share_adapter(getActivity(), share_list, share_rv, this);
        share_rv.setLayoutManager(RecManager);
        share_rv.setAdapter(RecAdapter);
    }

    @Override
    public void onSendShare(String[] string_array) {
        dismiss();
    }
}
