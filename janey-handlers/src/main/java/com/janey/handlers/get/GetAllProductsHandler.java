package com.janey.handlers.get;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.janey.core.dao.DAOManager;
import com.janey.core.helpers.JaneyException;
import com.janey.core.helpers.JaneySession;
import com.janey.core.objects.Product;
import com.janey.handlers.Actions;
import com.janey.handlers.BaseHandler;
import com.janey.handlers.Handler;

public class GetAllProductsHandler extends BaseHandler implements Handler {

	public GetAllProductsHandler() {
		super(Actions.GET_ALL_PRODUCTS);
		// TODO Auto-generated constructor stub
	}

	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		// TODO Auto-generated method stub
		List<Product> products = daoManager.getProductsManager().getAll();
		
		if ( products != null ) {
			out.object();
			out.array();
			for ( Product product : products ) {
				product.toJson(out);
			}
			out.endArray();
			out.endObject();
		}
	}

}
