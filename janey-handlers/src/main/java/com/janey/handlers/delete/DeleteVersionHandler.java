package com.janey.handlers.delete;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.janey.core.dao.DAOManager;
import com.janey.core.helpers.JaneyException;
import com.janey.core.helpers.JaneySession;
import com.janey.core.objects.Version;
import com.janey.handlers.Actions;
import com.janey.handlers.BaseAdminHandler;
import com.janey.handlers.Handler;

public class DeleteVersionHandler extends BaseAdminHandler implements Handler {

	public DeleteVersionHandler() {
		super(Actions.DELETE_VERSION);
		// TODO Auto-generated constructor stub
	}

	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		// TODO Auto-generated method stub
		Version version = new Version(json);
		daoManager.getVersionManager().delete(version);
	}

}
