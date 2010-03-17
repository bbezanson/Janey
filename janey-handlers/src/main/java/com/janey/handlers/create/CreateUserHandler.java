package com.janey.handlers.create;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.janey.core.dao.DAOManager;
import com.janey.core.helpers.JaneyException;
import com.janey.core.helpers.JaneySession;
import com.janey.core.objects.User;
import com.janey.handlers.Actions;
import com.janey.handlers.BaseHandler;
import com.janey.handlers.Handler;

public class CreateUserHandler extends BaseHandler implements Handler {

	public CreateUserHandler() {
		super(Actions.CREATE_USER);
	}

	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		String id = json.getString("id");
		String password = json.getString("password");
		String email = json.getString("email");
		Boolean active = json.getString("active").equals("false") ? false : true;
		
		// TODO: need to encrypt the password
		User user = new User(id, password, email, active);
		daoManager.getUserManager().create(user);
		
		returnStatus(out, STAT_SUCCESS);
	}

}
