package com.tdp2grupo9.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tdp2grupo9.R;
import com.tdp2grupo9.inicio.HomeActivity;
import com.tdp2grupo9.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Muestra las opciones de loguearse con Facebook, email o registrar una cuenta.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private UserEmailPasswordLoginTask authenticationEmailPasswordTask = null;
    private UserFacebookLoginTask authenticationFacebookTask = null;

    private AutoCompleteTextView emailTextView;
    private EditText passwordEditText;
    private LoginButton facebookSignInButton;
    private View progressView;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();

        createEmailTextView();
        createPasswordTextView();
        createEmailSignInButton();
        createFacebookSignInButton();

        progressView = findViewById(R.id.login_progress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void createEmailTextView() {
        emailTextView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();
    }

    private void createPasswordTextView() {
        passwordEditText = (EditText) findViewById(R.id.password);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    private void createEmailSignInButton() {
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void createFacebookSignInButton() {
        facebookSignInButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);
        facebookSignInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (authenticationFacebookTask != null)
                    return;

                Usuario.getInstancia().setFacebookId(Long.parseLong(loginResult.getAccessToken().getUserId()));
                Usuario.getInstancia().setFacebookToken(loginResult.getAccessToken().getToken());
                authenticationFacebookTask = new UserFacebookLoginTask();
                authenticationFacebookTask.execute((Void) null);
                Toast.makeText(getApplicationContext(), "ID: " + loginResult.getAccessToken().getUserId() + "\n"
                        + "Token: " + loginResult.getAccessToken().getToken(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    private void resetErrors() {
        emailTextView.setError(null);
        passwordEditText.setError(null);
    }

    private boolean isEmailValid(String email) {
        return email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: VER SI ES PARAMETRIZABLE LA LONGITUD O SI SE AGREGAN OTRAS CONDICIONES
        return password.length() == 6;
    }

    private boolean isLoginFormValid() {
        boolean valid = true;
        if (!isEmailValid(emailTextView.getText().toString())){
            emailTextView.setError(getString(R.string.error_invalid_email));
            valid = false;
        }
        if (!isPasswordValid(passwordEditText.getText().toString())){
            passwordEditText.setError(getString(R.string.error_invalid_password));
            valid = false;
        }
        return valid;
    }

    public void attemptLogin() {
        if (authenticationEmailPasswordTask != null)
            return;
        Log.i("BuscaSusHuellas", "Login con email y password solicitado");
        resetErrors();
        if (!isLoginFormValid()){
            Log.w("BuscaSusHuellas", "El formulario de logueo no es valido");
            if (!emailTextView.getError().toString().isEmpty())
                emailTextView.requestFocus();
            else if (!passwordEditText.getError().toString().isEmpty())
                passwordEditText.requestFocus();
        } else {
            Log.i("BuscaSusHuellas", "El formulario de logueo es valido");
            showProgress(true);
            Usuario.getInstancia().setEmail(emailTextView.getText().toString());
            Usuario.getInstancia().setPassword(passwordEditText.getText().toString());
            authenticationEmailPasswordTask = new UserEmailPasswordLoginTask();
            authenticationEmailPasswordTask.execute((Void) null);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
                ContactsContract.Contacts.Data.MIMETYPE + " = ?",
                new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE},
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {}

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };
        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
        emailTextView.setAdapter(adapter);
    }

    public class UserEmailPasswordLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String email;
        private final String password;

        UserEmailPasswordLoginTask() {
            password = passwordEditText.getText().toString();
            email = emailTextView.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Usuario.getInstancia().login();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: validar si el mail corresponde a una cuenta existente y si la password es la correcta
            if (email.equals("a@b")) {
                return password.equals("c");
            }

            // TODO: aca va el codigo para registrar una nueva cuenta.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authenticationEmailPasswordTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                passwordEditText.setError(getString(R.string.error_incorrect_password));
                passwordEditText.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            authenticationEmailPasswordTask = null;
            showProgress(false);
        }
    }

    public class UserFacebookLoginTask extends AsyncTask<Void, Void, Boolean> {

        UserFacebookLoginTask() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Usuario.getInstancia().loginConFacebook();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authenticationFacebookTask = null;
            showProgress(false);
            if (success) {
                if (Usuario.getInstancia().isLogueado()) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                }

                finish();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            authenticationFacebookTask = null;
            showProgress(false);
        }
    }


}

