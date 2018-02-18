package bxute.readmore.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;
import bxute.readmore.ConnectivityUtils;
import bxute.readmore.FontManager;
import bxute.readmore.R;
import bxute.readmore.data.FirebaseDataManager;
import bxute.readmore.preference.PreferenceManager;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @InjectView(R.id.sign_in_button)
    SignInButton signInButton;
    @InjectView(R.id.user_icon)
    TextView userIcon;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mApiClient;
    private int RC_SIGN_IN = 199;
    private PreferenceManager preferenceManager;
    private Snackbar snackbar;
    private ProgressDialog progressDialog;
    private FirebaseDataManager firebaseDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        userIcon.setTypeface(new FontManager(this).getTypeFace());
        preferenceManager = new PreferenceManager(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // Signed In
                    navigateToHome();
                } else {
                    // Signed Out
                }
            }
        };

        if (!ConnectivityUtils.getInstance(this).isConnectedToNet()) {
            signInButton.setEnabled(false);
            makeSnackbar(true, getString(R.string.no_internet));
        } else {
            signInButton.setEnabled(true);
        }

        configureGoogleSignIn();

    }

    private void navigateToHome() {
        Intent homeItent = new Intent(this, HomeActivity.class);
        startActivity(homeItent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void configureGoogleSignIn() {

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        mApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();

        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn() {

        if (mApiClient.hasConnectedApi(Auth.GOOGLE_SIGN_IN_API)) {
            mApiClient.clearDefaultAccountAndReconnect();
        }

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            processSignInResult(result);
        }
    }

    private void processSignInResult(GoogleSignInResult result) {
        hideProgress();
        if (result.isSuccess()) {
            showProgress(getString(R.string.authenticating_msg));
            GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
            loginUsingFirebase(googleSignInAccount);
        }
    }

    private void loginUsingFirebase(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Logged In from Firebase
                            FirebaseUser user = mAuth.getCurrentUser();
                            preferenceManager.setUserId(user.getUid());
                            preferenceManager.setUserPhotoUrl(user.getPhotoUrl());
                            preferenceManager.setUserEmail(user.getEmail());
                            new FirebaseDataManager(LoginActivity.this).syncFavorite();
                        } else {
                            // Failed Authentication.
                            makeSnackbar(true, getString(R.string.aut_failed_msg));
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        makeSnackbar(true, getResources().getString(R.string.no_connection_msg));
    }

    public void makeSnackbar(boolean show, String msg) {

        if (snackbar == null) {
            snackbar = Snackbar
                    .make(signInButton, msg, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.snackbar_settings, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });
        }
        if (show) {
            snackbar.show();
        } else {
            snackbar.dismiss();
        }

    }

    private void showProgress(String message) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }

        progressDialog.setMessage(message);
        progressDialog.show();

    }

    private void hideProgress() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgress();
    }

}
