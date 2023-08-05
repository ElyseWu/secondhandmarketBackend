package team3.secondhand.repository;

import java.util.Set;

public interface UserLocationRepository {
    Set<Long> searchByDistance(double lat, double lon, String distance);
}
