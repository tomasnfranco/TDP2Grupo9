package com.tdp2grupo9.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tdp2grupo9.R;
import com.tdp2grupo9.drawer.DrawerMenuActivity;
import com.tdp2grupo9.modelo.PublicacionAtributos;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.exceptions.UsuarioNoActivoException;
import com.tdp2grupo9.modelo.exceptions.WrongPasswordException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LoginActivity extends InitialActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = "BSH.LoginAct";

    private static final Integer LONGPASSWORD = 6;

    private UserEmailPasswordLoginTask authenticationEmailPasswordTask = null;
    private UserFacebookLoginTask authenticationFacebookTask = null;
    private UserFacebookGetPhoto authenticationFacebookGetPhoto = null;

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

    private void getFacebookData(JSONObject object) {
        try {
            String id = object.getString("id");
            if (object.has("first_name"))
                Usuario.getInstancia().setNombre(object.getString("first_name"));
            if (object.has("last_name"))
                Usuario.getInstancia().setApellido(object.getString("last_name"));
            if (object.has("email"))
                Usuario.getInstancia().setEmail(object.getString("email"));

            if (authenticationFacebookGetPhoto != null)
                return;
            authenticationFacebookGetPhoto = new UserFacebookGetPhoto(this);
            authenticationFacebookGetPhoto.execute((Void) null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createFacebookSignInButton() {
        facebookSignInButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);
        facebookSignInButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        facebookSignInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Usuario.getInstancia().setFacebookId(Long.parseLong(loginResult.getAccessToken().getUserId()));
                Usuario.getInstancia().setFacebookToken(loginResult.getAccessToken().getToken());
                Usuario.getInstancia().setEmail("");
                Usuario.getInstancia().setPassword("");

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        getFacebookData(object);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                Log.e("FACEBOOKERROR", e.getMessage());
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

    private boolean isPasswordValid(String password) {
        if (password.length() < LONGPASSWORD) {
            passwordEditText.setError(getString(R.string.error_invalid_password_longitud) + " " + LONGPASSWORD);
            return false;
        }
        if (!password.matches("^[a-zA-Z0-9]*$")) {
            passwordEditText.setError(getString(R.string.error_invalid_password_alfanumerica));
            return false;
        }
        return true;
    }

    private boolean isLoginFormValid() {
        boolean valid = true;
        if (!isEmailValid(emailTextView.getText().toString())){
            emailTextView.setError(getString(R.string.error_invalid_email));
            valid = false;
        }
        if (!isPasswordValid(passwordEditText.getText().toString())){
            valid = false;
        }
        return valid;
    }

    public void attemptLogin() {

        String METHOD = "attemptLogin";

        if (authenticationEmailPasswordTask != null)
            return;

        Log.i(TAG, METHOD + " Login con email y password solicitado ");

        resetErrors();
        if (!isLoginFormValid()){
            Log.w(TAG, METHOD + " El formulario de logueo no es valido");
            if (emailTextView.getError() != null && !emailTextView.getError().toString().isEmpty())
                emailTextView.requestFocus();
            else if (passwordEditText.getError() != null && !passwordEditText.getError().toString().isEmpty())
                passwordEditText.requestFocus();
        } else {
            Log.i(TAG, METHOD + " El formulario de logueo es valido");
            showProgress(true);
            Usuario.getInstancia().setEmail(emailTextView.getText().toString());
            Usuario.getInstancia().setPassword(passwordEditText.getText().toString());
            Usuario.getInstancia().setFacebookId(null);
            Usuario.getInstancia().setFacebookToken("");
            authenticationEmailPasswordTask = new UserEmailPasswordLoginTask(this);
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

        private Context context;
        private boolean wrongpass = false;
        private boolean activo = true;

        UserEmailPasswordLoginTask(Context context) { this.context = context; }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Usuario.getInstancia().login();
                Thread.sleep(200);
            } catch (WrongPasswordException e) {
                this.wrongpass = true;
            } catch (UsuarioNoActivoException e) {
                this.activo = false;
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authenticationEmailPasswordTask = null;
            showProgress(false);
            if (success) {
                if (Usuario.getInstancia().isLogueado()) {
                    iniciar();
                    finish();
                }else if (this.wrongpass) {
                    Toast.makeText(this.context,
                            "Password incorrecta. Intente nuevamente.",
                        Toast.LENGTH_LONG).show();
                }else if (!this.activo) {
                    Toast.makeText(this.context,
                            "Usuario bloqueado. Revise su casilla de correo.",
                            Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(this.context,
                        "Error inesperado.",
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            authenticationEmailPasswordTask = null;
            showProgress(false);
        }
    }

    public class UserFacebookGetPhoto extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        UserFacebookGetPhoto(Context context) { this.context = context; }

        @Override
        protected Boolean doInBackground(Void... voids) {

            URL profile_pic = null;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + Usuario.getInstancia().getFacebookId() + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                Usuario.getInstancia().setFoto(BitmapFactory.decodeStream(profile_pic.openConnection().getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authenticationFacebookGetPhoto = null;
            authenticationFacebookTask = new UserFacebookLoginTask(this.context);
            authenticationFacebookTask.execute((Void) null);
        }

        @Override
        protected void onCancelled() {
            authenticationFacebookGetPhoto = null;
        }
    }

    public class UserFacebookLoginTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;
        private boolean wrongpass = false;
        private boolean activo = true;

        UserFacebookLoginTask(Context context) { this.context = context; }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Usuario.getInstancia().login();
                Thread.sleep(200);
            } catch (WrongPasswordException e) {
                this.wrongpass = true;
            } catch (UsuarioNoActivoException e) {
                this.activo = false;
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
                    iniciar();
                    finish();
                }else if (this.wrongpass) {
                    Toast.makeText(this.context,
                            "Password incorrecta. Intente nuevamente.",
                            Toast.LENGTH_LONG).show();
                }else if (!this.activo) {
                    Toast.makeText(this.context,
                            "Usuario bloqueado. Revise su casilla de correo.",
                            Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(this.context,
                        "Error inesperado.",
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            authenticationFacebookTask = null;
            showProgress(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

