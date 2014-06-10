package roy.trial.login;

import roy.trial.login.library.UserFunctions;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends Activity {

	UserFunctions userFunctions;
    Button btnLogout;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userFunctions = new UserFunctions();
		
		if(userFunctions.isUserLoggedIn(getApplicationContext())) {
			setContentView(R.layout.dashboard);
			
			btnLogout = (Button) findViewById(R.id.btnLogout);
            
            btnLogout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    
                    finish();
				}
			});
		} else {
			Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            
            startActivity(login);
            
            finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}

}
