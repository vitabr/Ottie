package restforyou.com.tofsologia.activities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.model.Record;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {


    private final AdapterCallback listener;
    private final List<Record> records;


    HistoryAdapter(List<Record> records, AdapterCallback listener) {
        this.records = records;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bindItem(records.get(position));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }


    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title, content;

        HistoryViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                final int position = getAdapterPosition();
                listener.onItemClick(records.get(position), position);
            }
        }

        void bindItem(Record record) {
            title.setText(record.getFileName());
            content.setText(record.getDescription());

        }
    }
}
