package com.example.fotnews.ui.slideshow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fotnews.LoginActivity;
import com.example.fotnews.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SlideshowFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        TextView nameTextView = root.findViewById(R.id.user_info_username);
        TextView emailTextView = root.findViewById(R.id.user_info_email);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("username", "N/A");
        String email = sharedPreferences.getString("email", "N/A");

        nameTextView.setText("Name: " + name);
        emailTextView.setText("Email: " + email);

        Button updateBtn = root.findViewById(R.id.btn_update);
        Button logoutBtn = root.findViewById(R.id.btn_logout);
        Button deleteBtn = root.findViewById(R.id.btn_delete);

        updateBtn.setOnClickListener(v -> showEditDialog());
        logoutBtn.setOnClickListener(v -> showLogoutDialog());
        deleteBtn.setOnClickListener(v -> showDeleteDialog());

        return root;
    }

    private void showEditDialog() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.user_edit, null);

        EditText usernameField = dialogView.findViewById(R.id.editUsername);
        EditText emailField = dialogView.findViewById(R.id.editEmail);
        Button btnOk = dialogView.findViewById(R.id.btnOk);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        // Load saved data
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String currentUsername = sharedPreferences.getString("username", ""); 
        String currentEmail = sharedPreferences.getString("email", "");

        // Pre-fill the fields
        usernameField.setText(currentUsername);
        emailField.setText(currentEmail);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setCancelable(false)
                .create();

        btnOk.setOnClickListener(view -> {
            String username = usernameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            String currentUserId = sharedPreferences.getString("uid", null); // Get UID from SharedPreferences

            if (currentUserId == null) {
                Toast.makeText(getActivity(), "User ID not found", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

            usersRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    boolean emailExists = false;

                    for (DataSnapshot userSnapshot : task.getResult().getChildren()) {
                        String uid = userSnapshot.getKey();
                        String existingEmail = userSnapshot.child("email").getValue(String.class);

                        if (!uid.equals(currentUserId) && existingEmail != null && existingEmail.equalsIgnoreCase(email)) {
                            emailExists = true;
                            break;
                        }
                    }

                    if (emailExists) {
                        Toast.makeText(getActivity(), "This email is already in use!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Update Firebase
                        usersRef.child(currentUserId).child("username").setValue(username);
                        usersRef.child(currentUserId).child("email").setValue(email);

                        // Update SharedPreferences
                        editor.putString("username", username);
                        editor.putString("email", email);
                        editor.apply();

                        // Update UI
                        TextView nameTextView = requireView().findViewById(R.id.user_info_username);
                        TextView emailTextView = requireView().findViewById(R.id.user_info_email);
                        nameTextView.setText("Name: " + username);
                        emailTextView.setText("Email: " + email);

                        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to connect to database", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnCancel.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }


    private void showLogoutDialog() {
        // Inflate the confirm_logout layout
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.confirm_logout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        Button btnNo = dialogView.findViewById(R.id.btnNo);
        Button btnYes = dialogView.findViewById(R.id.btnYes);

        btnNo.setOnClickListener(v -> dialog.dismiss());

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();

            // Clear SharedPreferences
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();

            // Navigate to LoginActivity
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            // Finish current activity to prevent back navigation
            requireActivity().finish();
        });

    }
    private void showDeleteDialog() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.confirm_delete, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        Button btnNo = dialogView.findViewById(R.id.btnNo);
        Button btnYes = dialogView.findViewById(R.id.btnYes);

        btnNo.setOnClickListener(v -> dialog.dismiss());

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();

            // Get UID from SharedPreferences
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String currentUserId = sharedPreferences.getString("uid", null);

            if (currentUserId == null) {
                Toast.makeText(getActivity(), "User ID not found", Toast.LENGTH_SHORT).show();
                return;
            }

            // Delete user from Firebase
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
            usersRef.child(currentUserId).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        // Clear shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        Toast.makeText(getActivity(), "Account deleted", Toast.LENGTH_SHORT).show();

                        // Navigate to login activity
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(getActivity(), "Failed to delete account", Toast.LENGTH_SHORT).show()
                    );
        });
    }

}