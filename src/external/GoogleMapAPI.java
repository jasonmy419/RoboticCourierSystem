package external;

import java.util.List;

import entity.Address;
import entity.ItemSize;
import entity.Route;

public interface GoogleMapAPI {
	
	/**
	 * Close the connection.
	 */
	public List<Route> getRouteInfo(Address origin, Address destination, Address waypoint, ItemSize size);

}
