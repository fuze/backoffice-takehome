package com.fuze.takehome.dao;

import java.util.Collection;
import java.util.List;

public interface LocationDAO {

	Location getLocation(Long id);
	List<Location> getLocations();
	List<Location> getLocations(Collection<Long> locationIds);
}
