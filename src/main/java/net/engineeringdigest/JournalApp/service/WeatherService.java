package net.engineeringdigest.JournalApp.service;
import net.engineeringdigest.JournalApp.Constants.placeholders;
import net.engineeringdigest.JournalApp.api.response.WeatherResponse;
import net.engineeringdigest.JournalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    public String apikey;            // API key injection from .yml file

    //public static final String API="http://api.weatherstack.com/current?access_key=<API_KEY>&query=<CITY>";         // endpoint (url)...api ko db me daalne se pehle isi tariqe se kr rhe the

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache ;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }
        else{
            String finalAPI=appCache.appCacheMap.get(AppCache.keys.WEATHER_API.toString()).replace(placeholders.CITY,city).replace(placeholders.APIP_KEY,apikey);      // final url bna liya
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
                                                                             // url,   HttpMethod , returned body or headers, expected type
            WeatherResponse body = response.getBody();        // extracts the json response i.e. body
            if(body !=null){
                redisService.set("weather_of_"+city,body,300l);
            }
            return body;

        }



    }

     /*
     just to show , how to POST something

        String requestBody= "any relevant string data" ;

        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.set("key","value");

        HttpEntity<String> httpEntity=new HttpEntity<>(requestBody,httpHeaders);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, httpEntity, WeatherResponse.class);
    }
     */

}
