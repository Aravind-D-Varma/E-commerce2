package com.example.socialx.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.socialx.R;
import com.example.socialx.activities.HomePage;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText mName, mEmail, mPassword, mContact;
    private CheckBox mAgreed;
    private Button mRegister;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.introduction_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        mName = view.findViewById(R.id.register_name);
        mEmail = view.findViewById(R.id.register_email);
        mPassword = view.findViewById(R.id.register_password);
        mContact = view.findViewById(R.id.register_contact);
        mAgreed = view.findViewById(R.id.agree);
        mRegister = view.findViewById(R.id.register_button);
        mRegister.setOnClickListener(v -> Register());
        Button goToLogin = (Button) view.findViewById(R.id.go_signin);
        goToLogin.setOnClickListener(v -> {
            Fragment fragment = new LoginFragment();
            FragmentManager fM = getActivity().getSupportFragmentManager();
            FragmentTransaction fT = fM.beginTransaction();
            fT.replace(R.id.simpleFrameLayout, fragment);
            fT.commit();
        });
        return view;
    }
    private void Register() {
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String number = mContact.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mName.setError("Enter your Name");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Enter your email");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Enter valid email ID");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Enter your password");
            return;
        } else if (password.length() < 6) {
            mPassword.setError("Password too short/weak");
            return;
        }
        if(!isValidMobile(number)){
            mContact.setError("Please provide a valid phone number");
            return;
        }
        if (!mAgreed.isChecked()){
            Toast.makeText(getActivity(), "Please agree to the terms and conditions!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Registration Successful!"
                                , Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), HomePage.class));
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Registration Failed!"
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
