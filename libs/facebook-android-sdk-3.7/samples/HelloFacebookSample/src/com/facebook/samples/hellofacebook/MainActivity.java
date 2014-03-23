package com.facebook.samples.hellofacebook;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {

		   // callback when session changes state
		   public void call(Session session, SessionState state, Exception exception)   
		   {
		      if (session.isOpened()) {
		      // make request to;2 the /me API
		         Request.executeMeRequestAsync(session, new Request.
		         GraphUserCallback() {

		            // callback after Graph API response with user object
		            @Override
		            public void onCompleted(GraphUser user, Response response) {
		            if (user != null) {
		               TextView welcome = (TextView) findViewById(R.id.normal);
		               welcome.setText("Hello " + user.getName() + "!");
		              }
		            }
		       }); 
	} 

}
		   }
	}}
