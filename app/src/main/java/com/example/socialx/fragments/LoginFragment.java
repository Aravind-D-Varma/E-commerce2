package com.example.socialx.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.socialx.R;
import com.example.socialx.activities.HomePage;
import com.facebook.AccessToken;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class LoginFragment extends Fragment {
    public static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 1000;
    private ImageButton fbLoginButton;
    private FirebaseAuth mAuth;
    private EditText mEmail, mPassword;
    private ProgressDialog mProgressDialog;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.introduction_login, container, false);
        mAuth = FirebaseAuth.getInstance();

        mEmail = view.findViewById(R.id.login_email);
        mPassword = view.findViewById(R.id.login_password);
        Button register = view.findViewById(R.id.login_register);
        Button forgot = view.findViewById(R.id.login_forgot);
        Button login = view.findViewById(R.id.login_button);
        register.setOnClickListener(v -> {
            Fragment fragment = new RegisterFragment();
            FragmentManager fM = getActivity().getSupportFragmentManager();
            FragmentTransaction fT = fM.beginTransaction();
            fT.replace(R.id.simpleFrameLayout, fragment);
            fT.commit();
        });

        login.setOnClickListener(v -> Login());
        forgot.setOnClickListener(v -> ForgotPassword());
        fbLoginButton = (ImageButton) view.findViewById(R.id.fb_signin);
        facebookLogin();

        SignInButton googleSignInButton = view.findViewById(R.id.google_signin);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent,RC_SIGN_IN);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(getContext(), HomePage.class));
            getActivity().finish();
        }
    }
    private void facebookLogin() {
        fbLoginButton.setOnClickListener(v -> LoginManager.getInstance().logInWithReadPermissions(getVisibleFragment(),Arrays.asList(EMAIL)));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken token = AccessToken.getCurrentAccessToken();
                        AuthCredential cred = FacebookAuthProvider.getCredential(token.getToken());
                        mAuth.signInWithCredential(cred).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                updateUI();
                            }
                        });
                    }
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        updateUI();
                    }
                });
    }
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }
    private void Login() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Enter your email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Enter your password");
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    mProgressDialog.dismiss();
                    startActivity(new Intent(getContext(), HomePage.class));
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Incorrect combination of email and password !"
                            , Toast.LENGTH_SHORT).show();
                }
        });
    }
    private void ForgotPassword() {
        final String email = mEmail.getText().toString();

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Enter your email");
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                Toast.makeText(getContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
        });
    }
    private void updateUI() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(getActivity(), HomePage.class));
            getActivity().finish();
        }
        else{
            Toast.makeText(getContext(), "Sign In unsuccessful!"
                    , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Sign In unsuccessful!"+e.toString()
                        , Toast.LENGTH_LONG).show();
            }
        }
    }
}
