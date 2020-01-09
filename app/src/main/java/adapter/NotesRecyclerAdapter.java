package adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingwithtushar.mynotes.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import JavaIntefrace.ListenerInterface;
import models.Note;

import android.view.View.*;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {

    List<Note> mNotes = new ArrayList<>();
    ListenerInterface mListener;

    public NotesRecyclerAdapter(List<Note> notes, ListenerInterface listener) {
        mNotes = notes;
        mListener = listener;
    }

    public void setNotes(List<Note> notes) {
        mNotes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.titleView.setText(mNotes.get(position).getTitle());
        String timeInMilisecond = mNotes.get(position).getTimeStamp();
        String formatString = getTime(timeInMilisecond);
        holder.timeStampView.setText(formatString);
    }

    private String getTime(String timeInMilisecond) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        long milliSeconds= Long.parseLong(timeInMilisecond);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

   static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        TextView titleView, timeStampView;
        ListenerInterface listener;
        public ViewHolder(@NonNull View itemView, ListenerInterface listener) {
            super(itemView);
            titleView = itemView.findViewById(R.id.note_title);
            timeStampView = itemView.findViewById(R.id.note_timestamp);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.listener.onClickEvent(getAdapterPosition());
        }
    }

}
