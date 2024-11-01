package com.example.numad24fa_chuanzhaohuang.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad24fa_chuanzhaohuang.Models.Contact;
import com.example.numad24fa_chuanzhaohuang.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contacts;
    private OnContactClickListener listener;

    // Interface for handling contact actions
    public interface OnContactClickListener {
        void onEdit(Contact contact);
        void onDelete(Contact contact);
        void onCall(Contact contact);
    }

    public ContactAdapter(List<Contact> contacts, OnContactClickListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.tvName.setText(contact.getName());
        holder.tvPhoneNumber.setText(contact.getPhoneNumber());

        // Trigger onCall when the item (except delete button) is clicked
        holder.itemView.setOnClickListener(v -> listener.onCall(contact));

        // Trigger onDelete when delete button is clicked
        holder.tvDelete.setOnClickListener(v -> listener.onDelete(contact));

        // Optional: Trigger onEdit if there's an edit action needed
        holder.itemView.setOnLongClickListener(v -> {
            listener.onEdit(contact);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhoneNumber, tvDelete;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhoneNumber = itemView.findViewById(R.id.tv_phone_number);
            tvDelete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
