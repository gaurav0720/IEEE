package com.example.ieee;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ETRPersonAdapter extends FirebaseRecyclerAdapter<ETRPerson, ETRPersonAdapter.MyViewHolder> {

    private Context context;

    public ETRPersonAdapter(@NonNull FirebaseRecyclerOptions<ETRPerson> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull ETRPerson etrPerson) {
        myViewHolder.txtName.setText(etrPerson.getName());
        myViewHolder.txtExpertise.setText(etrPerson.getExpertise());

        String urlValue = etrPerson.getImageURL();
        if (urlValue.equals("notyet")){
            myViewHolder.profileImage.setImageResource(R.drawable.boss);
        }

        myViewHolder.userClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("userName", etrPerson.getName());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.etrperson_view, parent, false);

        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtExpertise;
        private ImageView profileImage;
        private RelativeLayout userClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.showFullName);
            txtExpertise = itemView.findViewById(R.id.showExpertise);

            profileImage = itemView.findViewById(R.id.userImage);

            userClick = itemView.findViewById(R.id.userClickable);

        }
    }
}
