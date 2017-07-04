package com.example.android.sunshine;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>{

    private String[] mWeatherData;


    public ForecastAdapter(){

    }
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item,parent,false);
         return new ForecastAdapterViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mWeatherData==null ? 0 : mWeatherData.length;
    }

    public void setWeatherData(String[] data){
        mWeatherData=data;
        notifyDataSetChanged();
    }








    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView mWeatherTextView;

        public ForecastAdapterViewHolder(View itemView) {
            super(itemView);
            mWeatherTextView=(TextView)itemView.findViewById(R.id.tv_weather_data);
        }

        void bind(int position){
            mWeatherTextView.setText(mWeatherData[position]);
        }
    }
}
