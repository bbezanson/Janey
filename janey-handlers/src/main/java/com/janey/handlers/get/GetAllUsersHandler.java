package com.janey.handlers.get;

import java.sql.SQLException;
import java.util.List;

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

public class GetAllUsersHandler extends BaseHandler implements Handler {

	public GetAllUsersHandler() {
		super(Actions.GET_ALL_USERS);
	}

	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		List<User> users = daoManager.getUserManager().getAll();
		
		if ( users != null ) {
			out.object();
			out.key("items");
			out.array();
			for ( User user : users ) {
				user.toJson(out);
			}
			out.endArray();
			out.endObject();
		} else {
			// TODO: Handler no users
		}
	}

}
