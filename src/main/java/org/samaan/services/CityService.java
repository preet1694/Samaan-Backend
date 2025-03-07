package org.samaan.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private static final List<String> CITIES = Arrays.asList(
            "Ahmedabad", "Surat", "Vadodara", "Rajkot", "Bhavnagar",
            "Jamnagar", "Gandhinagar", "Junagadh", "Anand", "Navsari",
            "Morbi", "Nadiad", "Mehsana", "Bharuch", "Vapi",
            "Gondal", "Veraval", "Godhra", "Patan", "Porbandar",
            "Dahod", "Amreli", "Deesa", "Jetpur", "Bhuj",
            "Surendranagar", "Himatnagar", "Valsad", "Kalol", "Botad",
            "Palanpur", "Anjar", "Mahuva", "Sanand", "Chhota Udaipur",
            "Wadhwan", "Modasa", "Kadi", "Dhoraji", "Visnagar"
    );

    public List<String> searchCities(String query) {
        return CITIES.stream()
                .filter(city -> city.toLowerCase().startsWith(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}

