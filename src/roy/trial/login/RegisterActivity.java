package roy.trial.login;

import org.json.JSONException;
import org.json.JSONObject;

import roy.trial.login.library.DatabaseHandler;
import roy.trial.login.library.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {
	Button btnRegister;
    Button btnLinkToLogin;
    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword;
    TextView registerErrorMsg;
    
    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
 
        // Importing all assets like buttons, text fields
        inputFullName = (EditText) findViewById(R.id.registerName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.registerUser(name, email, password);
                
                try {
                	if (json.getString(KEY_SUCCESS) != null) {
                		registerErrorMsg.setText("");
                		String res = json.getString(KEY_SUCCESS);
                		if (Integer.parseInt(res) == 1) {
                			DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                			JSONObject json_user = json.getJSONObject("user");
                			
                			userFunction.logoutUser(getApplicationContext());
                			db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
                			
                			Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                			
                			dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                			startActivity(dashboard);
                			
                			finish();
                		} else {
                			registerErrorMsg.setText("Error Occurred During Registration");
                		}
                	}
                } catch (JSONException e) {
                	e.printStackTrace();
                }
			}
		});
        
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
				finish();
			}
		});
    }
}
