package com.example.weatherforecast;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherforecast.dummy.DummyContent.DummyItem;

import java.util.List;


//Name and Matric Number: Hafsah Kamran, S1627179
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private List<DummyItem> mValues;
    private View.OnClickListener mClickListener;
    public MyItemRecyclerViewAdapter(List<DummyItem> items) {

        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View view = inflater.inflate(R.layout.fragment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DummyItem dummyItem = mValues.get(position);

        TextView content = holder.number;
        content.setText(dummyItem.getId());

        TextView id = holder.content;
        id.setText(dummyItem.getContent());

        TextView mItem2 = holder.degree;
        mItem2.setText(dummyItem.getDetails());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    //Name and Matric Number: Hafsah Kamran, S1627179
public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView number;
        public final TextView content;
        public final TextView degree;
       // public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            number = (TextView) view.findViewById(R.id.item_number);
            content = (TextView) view.findViewById(R.id.content);
            degree = (TextView) view.findViewById(R.id.degree);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + content.getText() + "'";
        }
    }


    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }
}