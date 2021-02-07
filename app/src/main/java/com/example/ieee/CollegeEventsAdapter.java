package com.example.ieee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CollegeEventsAdapter extends FirebaseRecyclerAdapter<CollegeEvents, CollegeEventsAdapter.MyViewHolder> {

    private Context context;

    public CollegeEventsAdapter(@NonNull FirebaseRecyclerOptions<CollegeEvents> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHoder, int i, @NonNull CollegeEvents collegeEvents) {
        myViewHoder.title.setText(collegeEvents.getEventTitle());
        myViewHoder.date.setText(collegeEvents.getEventDate());
        myViewHoder.etr.setText(collegeEvents.getEventETRPerson());
        myViewHoder.cname.setText(collegeEvents.getCollegeName());
        myViewHoder.cmember.setText(collegeEvents.getCommitteeMember());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.college_event_view1, parent, false);

        return new CollegeEventsAdapter.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, date,etr,cname, cmember;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.displayEventTitle);
            date = itemView.findViewById(R.id.displayEventDate);
            etr = itemView.findViewById(R.id.displayEventETRPerson);
            cname = itemView.findViewById(R.id.displayCollegeName);
            cmember = itemView.findViewById(R.id.displayCommitteeMember);

        }
    }
}
