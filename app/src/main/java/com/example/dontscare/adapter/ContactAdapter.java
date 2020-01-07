package com.example.dontscare.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dontscare.R;
import com.example.dontscare.bean.ContactBean;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    //MyAdapter的成员变量mFruitList, 这里被我们用作数据的来源
    private List<ContactBean> mFruitList;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //用来创建ViewHolder实例，再将加载好的布局传入构造函数，最后返回ViewHolder实例
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,null);
        ViewHolder holder=new ViewHolder(view);
        return new ViewHolder(view);
    }

    //ContactAdapter的构造器
    public ContactAdapter(List<ContactBean> fruitList){
        mFruitList=fruitList;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //用于对RecyclerView的子项进行赋值，会在每个子项滚动到屏幕内的时候执行
        ContactBean fruit=mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.location.setText(fruit.getLocation());
        holder.status.setText(fruit.getStatus());
    }


     public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fruitImage;
        TextView fruitName;
        TextView location;
        TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitImage=itemView.findViewById(R.id.card_image);
            fruitName=itemView.findViewById(R.id.card_name);
            location=itemView.findViewById(R.id.card_location);
            status=itemView.findViewById(R.id.card_security_status);
        }
    }


    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}


