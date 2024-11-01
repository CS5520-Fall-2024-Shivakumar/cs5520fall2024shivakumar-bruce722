package com.example.numad24fa_chuanzhaohuang;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad24fa_chuanzhaohuang.Adapters.ContactAdapter;
import com.example.numad24fa_chuanzhaohuang.Models.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
                            showSnackbar(newContact);
                        } else {
                            Toast.makeText(ContactsCollectorActivity.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showEditContactDialog(final Contact contact) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null);
        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText editTextPhone = dialogView.findViewById(R.id.editTextPhone);

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
        String phoneNumber = "tel:" + contact.getPhoneNumber().replaceAll("[^0-9]", "");
        Uri uri = Uri.parse(phoneNumber);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);

        // Debugging: Log the URI and show a Toast with the exact phone number
        Log.d("initiateCall", "Dialing URI: " + uri.toString());
        Toast.makeText(this, "Dialing: " + phoneNumber, Toast.LENGTH_SHORT).show();

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Use ACTION_CALL as a fallback if ACTION_DIAL is not supported (for testing)
            Intent callIntent = new Intent(Intent.ACTION_CALL, uri);

            // Check if CALL_PHONE permission is granted
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                startActivity(callIntent);
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void showSnackbar(Contact contact) {
        Snackbar.make(findViewById(R.id.recycler_view_contacts), "Contact added", Snackbar.LENGTH_LONG)
                .setAction("UNDO", v -> {
                    // Undo action to re-add the contact if deleted
                    contactList.add(contact);
                    contactAdapter.notifyDataSetChanged();
                })
                .show();
    }


}
