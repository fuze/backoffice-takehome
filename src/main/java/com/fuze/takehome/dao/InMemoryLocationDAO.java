package com.fuze.takehome.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryLocationDAO implements LocationDAO {

	private static List<Location> locations = Arrays.asList(
			new Location(1L, "Hell's Kitchen"),
			new Location(2L, "Harlem"),
			new Location(3L, "New York City"),
			new Location(4L, "Brooklyn"),
			new Location(5L, "Queens"),
			new Location(6L, "Long Island"),
			new Location(7L, "Bronx")
			// ... and many more
			);
	
	@Override
	public Location getLocation(Long id) {
		delay();
		return locations.stream().filter(l -> l.getId() == id).findFirst().get();
	}

	@Override
	public List<Location> getLocations() {
		delay();
		return locations;
	}

	@Override
	public List<Location> getLocations(Collection<Long> locationIds) {
		delay();
		return locations.stream().filter(l -> locationIds.contains(l.getId())).collect(Collectors.toList());
	}
	
	private void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
