package com.aire.android.recyclerview;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aire.android.test.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyAdaoter mAdaoter = new MyAdaoter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRecyclerView = findViewById(R.id.main_recyclerview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.setAdapter(mAdaoter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public void refresh(View view) {
        mAdaoter.notifyDataSetChanged();
    }


    static class MyAdaoter extends RecyclerView.Adapter<MyAdaoter.MyViewHolder> {
        private List<String> mList = new ArrayList() {
            {
                int viewCount = 0;
                for (int i = 0; i < 10; i++) {
                    add("heihei" + (viewCount++));
                }
            }
        };

        @NonNull
        @Override
        public MyAdaoter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView tv = new TextView(parent.getContext());
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (200 * 2.75));
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            return new MyViewHolder(tv);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            String item = mList.get(position);
            holder.tv.setText(item);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv = (TextView) itemView;
            }
        }
    }
}