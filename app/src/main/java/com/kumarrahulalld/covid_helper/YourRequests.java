package com.kumarrahulalld.covid_helper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.MapView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class YourRequests extends AppCompatActivity {
    RecyclerView rv;
    String ph="";
    double lt,lg;
   DatabaseReference mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_requests);
        ph=getIntent().getStringExtra("Phone");
        rv=(RecyclerView) findViewById(R.id.recyle);
        mdb=FirebaseDatabase.getInstance().getReference(ph+"/Need");
        mdb.keepSynced(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerOptions<Requests> options=new FirebaseRecyclerOptions.Builder<Requests>().setQuery(mdb,Requests.class).build();
        FirebaseRecyclerAdapter<Requests,RequestHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Requests,RequestHolder>(options) {
            @NonNull
            @Override
            public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.reqcard, parent, false);
                RequestHolder r=new RequestHolder(view);
                return r;
            }

            @Override
            protected void onBindViewHolder(@NonNull RequestHolder holder, int position, @NonNull final Requests model) {
                if(model!=null && model.getStatus()!=null) {
                    if (model.getStatus().equals("0")) {
                        holder.nm.setText(model.getName());
                        holder.des.setText(model.getDescription());
                        holder.got.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deact(model.getHelpers(), model.getHelpersId());
                            }
                        });
                    }
                    else
                    {
                        holder.itemView.setVisibility(View.GONE);
                        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        //holder.nm.setVisibility(View.GONE);
                        //holder.des.setVisibility(View.GONE);
                        //holder.got.setVisibility(View.GONE);
                    }
                }
            }

        };
        rv.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public void deact(String help,String helpid){

        //Log.d("RemoveProb",help.length()+"   "+helpid.length());
        if(help!=null && helpid!=null) {
            String[] hl = help.split("\\$");
            String[] hi = helpid.split("\\$");
           // Log.d("RemoveProb",hl.length+"   "+hi.length);

            for (int i = 0; i < hl.length-1; i++) {
                DatabaseReference md = FirebaseDatabase.getInstance().getReference(hl[i] + "/Will/" + hi[i]);
                md.child("Status").setValue("1");
                //Log.d("RemoveProb",hl[i]+"   "+hi[i]);
            }
           DatabaseReference md = FirebaseDatabase.getInstance().getReference(hl[hl.length-1] + "/Need/" + hi[hl.length-1]);
            md.child("Status").setValue("1");
            Toast.makeText(YourRequests.this, "Request Removed Successfully.", Toast.LENGTH_SHORT).show();

        }
    }
    public static class RequestHolder extends RecyclerView.ViewHolder{
        TextView nm,des;
        Button got;
        public RequestHolder(View itemView){
            super(itemView);
            nm=itemView.findViewById(R.id.name);
            des=itemView.findViewById(R.id.desc1);
            got=itemView.findViewById(R.id.btn_got);
        }
    }
}
