package com.janey.handlers.get;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.janey.core.dao.DAOManager;
import com.janey.core.helpers.JaneyException;
import com.janey.core.helpers.JaneySession;
import com.janey.core.objects.Version;
import com.janey.handlers.Actions;
import com.janey.handlers.BaseHandler;
import com.janey.handlers.Handler;

public class GetAllVersionsHandler extends BaseHandler implements Handler {

	public GetAllVersionsHandler() {
		super(Actions.GET_ALL_VERSIONS);
		// TODO Auto-generated constructor stub
	}

	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		// TODO Auto-generated method stub
		long product_id = json.getLong("product_id");
		List<Version> versions = daoManager.getVersionManager().getAll(product_id);
		if ( versions != null ) {
			out.object();
			out.array();
			for (Version version : versions ) {
				version.toJson(out);
			}
			out.endArray();
			out.endObject();
		}
	}

}
