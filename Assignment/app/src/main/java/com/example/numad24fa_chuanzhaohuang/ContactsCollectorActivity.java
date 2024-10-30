package com.example.numad24fa_chuanzhaohuang;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad24fa_chuanzhaohuang.Adapters.ContactAdapter;
import com.example.numad24fa_chuanzhaohuang.Models.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactsCollectorActivity extends AppCompatActivity {

    private ContactAdapter contactAdapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_collector);

        // Initialize contact list and adapter
        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contactList, new ContactAdapter.OnContactClickListener() {
            @Override
            public void onEdit(Contact contact) {
                showEditContactDialog(contact);
            }

            @Override
            public void onDelete(Contact contact) {
                deleteContact(contact);
            }

            @Override
            public void onCall(Contact contact) {
                initiateCall(contact);
            }
        });

        // Set up RecyclerView
        RecyclerView recyclerViewContacts = findViewById(R.id.recycler_view_contacts);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(contactAdapter);

        // Set up FloatingActionButton for adding contacts
        FloatingActionButton fabAddContact = findViewById(R.id.fab_add_contact);
        fabAddContact.setOnClickListener(v -> showAddContactDialog());
    }

    private void showAddContactDialog() {
        // Inflate the custom layout for the dialog
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null);
        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText editTextPhone = dialogView.findViewById(R.id.editTextPhone);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Contact")
                .setView(dialogView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editTextName.getText().toString();
                        String phone = editTextPhone.getText().toString();

                        if (!name.isEmpty() && !phone.isEmpty()) {
                            Contact newContact = new Contact(name, phone);
                            contactList.add(newContact);
                            contactAdapter.notifyDataSetChanged();
                            Toast.makeText(ContactsCollectorActivity.this, "Contact added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ContactsCollectorActivity.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showEditContactDialog(final Contact contact) {
        // Reuse dialog layout and update with contact details for editing
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null);
        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText editTextPhone = dialogView.findViewById(R.id.editTextPhone);

        // Prepopulate fields with current contact info
        editTextName.setText(contact.getName());
        editTextPhone.setText(contact.getPhoneNumber());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Contact")
                .setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editTextName.getText().toString();
                        String phone = editTextPhone.getText().toString();

                        if (!name.isEmpty() && !phone.isEmpty()) {
                            contact.setName(name);
                            contact.setPhoneNumber(phone);
                            contactAdapter.notifyDataSetChanged();
                            Toast.makeText(ContactsCollectorActivity.this, "Contact updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ContactsCollectorActivity.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteContact(Contact contact) {
        contactList.remove(contact);
        contactAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void initiateCall(Contact contact) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No app available to make calls", Toast.LENGTH_SHORT).show();
        }
    }
}
