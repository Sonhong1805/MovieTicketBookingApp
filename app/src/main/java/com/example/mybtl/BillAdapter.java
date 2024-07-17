package com.example.mybtl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder>{
    private Context context;
    private ArrayList<Bill> bills;

    public BillAdapter(Context context, ArrayList<Bill> bills) {
        this.context = context;
        this.bills = bills;
    }

    @NonNull
    @Override
    public BillAdapter.BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_item, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.BillViewHolder holder, int position) {
        Bill bill = bills.get(position);
        if (bill == null){
            return;
        }
        holder.tvId.setText(String.valueOf(bill.getIdBill()));
        holder.tvName.setText(bill.getMovieName());
        holder.tvPremiere.setText(bill.getMoviePremiere());
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormatter.format(bill.getMoviePrice());
        holder.tvPrice.setText(formattedPrice);

        holder.tvSelectedChair.setText(bill.getSelectedChair());
        holder.tvSelectedFood.setText(bill.getSelectedFood());
        holder.tvMethodPayment.setText(bill.getMethodPayment());
        String formattedTotalPrice = currencyFormatter.format(bill.getTotalPrice());
        holder.tvTotalPrice.setText(formattedTotalPrice);

        int quantityChair = bill.getSelectedChair().split(", ").length;
        holder.tvQuantity.setText(String.valueOf(quantityChair));

        holder.tvDateOrder.setText(bill.getDateOrder());
        holder.tvTimeOrder.setText(bill.getTimeOrder());
    }

    @Override
    public int getItemCount() {
        if(bills !=null)
            return bills.size();
        return 0;
    }

    public class BillViewHolder extends RecyclerView.ViewHolder{

        TextView tvId,tvName,tvPremiere,tvDateOrder,tvTimeOrder,tvPrice,tvQuantity,tvSelectedChair,tvSelectedFood,tvMethodPayment,tvTotalPrice;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPremiere = itemView.findViewById(R.id.tv_premiere);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvSelectedChair = itemView.findViewById(R.id.tv_selected_chair);
            tvSelectedFood = itemView.findViewById(R.id.tv_selected_food);
            tvMethodPayment = itemView.findViewById(R.id.tv_method_payment);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvDateOrder = itemView.findViewById(R.id.tv_date_order);
            tvTimeOrder = itemView.findViewById(R.id.tv_time_order);
        }
    }
}
