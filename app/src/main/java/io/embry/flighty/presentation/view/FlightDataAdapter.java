package io.embry.flighty.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import io.embry.flighty.R;
import io.embry.flighty.data.FlightData;

import java.util.List;

public class FlightDataAdapter extends RecyclerView.Adapter<FlightDataAdapter.FlightDataViewHolder> {

    private List<FlightData> flightData;
    private Context context;

    public FlightDataAdapter(List<FlightData> flights, Context context) {
        this.flightData = flights;
        this.context = context;
    }

    @NonNull
    @Override
    public FlightDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_flight_data, parent, false);
        return new FlightDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightDataViewHolder holder, int position) {
        FlightData data = flightData.get(position);
        if (data == null) {
            return;
        }
        if (data.getAirlineLogoAddress() != null) {
            Picasso.get().load(data.getAirlineLogoAddress()).into(holder.logo);
        }
        if (data.getAirlineName() != null) {
            holder.title.setText(data.getAirlineName());
        } else {
            holder.title.setText(context.getString(R.string.unknown));
        }
        if (data.getAmount() != null) {
            holder.cost.setText(String.format(context.getString(R.string.txt_cost), data.getAmount()));
        }else {
            holder.title.setText(String.format(context.getString(R.string.txt_cost), context.getString(R.string.unknown)));
        }
        if (data.getInboundFlightsDuration() != null) {
            holder.inboundDuration.setText(String.format(context.getString(R.string.txt_inbound_duration), data.getInboundFlightsDuration()));
        }else {
            holder.title.setText(String.format(context.getString(R.string.txt_inbound_duration), context.getString(R.string.unknown)));
        }
        if (data.getOutboundFlightsDuration() != null) {
            holder.outboundDuration.setText(String.format(context.getString(R.string.txt_outbound_duration), data.getOutboundFlightsDuration()));
        }else {
            holder.title.setText(String.format(context.getString(R.string.txt_outbound_duration), context.getString(R.string.unknown)));
        }
        holder.stops.setText(String.format(context.getString(R.string.txt_stops), data.getStops()));
    }

    @Override
    public int getItemCount() {
        return flightData.size();
    }


    public class FlightDataViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView title;
        TextView inboundDuration;
        TextView outboundDuration;
        TextView cost;
        TextView stops;

        public FlightDataViewHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.airline_logo);
            title = itemView.findViewById(R.id.txt_airline_name);
            inboundDuration = itemView.findViewById(R.id.txt_inbound_flight_duration);
            outboundDuration = itemView.findViewById(R.id.txt_outbound_flight_duration);
            cost = itemView.findViewById(R.id.txt_cost);
            stops = itemView.findViewById(R.id.txt_stops);
            ButterKnife.bind(itemView);
        }
    }
}
