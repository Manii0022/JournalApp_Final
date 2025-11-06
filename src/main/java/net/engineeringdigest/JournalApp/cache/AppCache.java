package net.engineeringdigest.JournalApp.cache;

import net.engineeringdigest.JournalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.JournalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys {
        WEATHER_API
    }

    @Autowired
    ConfigJournalAppRepository configJournalAppRepository;


    public Map<String, String> appCacheMap;

    @PostConstruct    // as soon as AppCache bean in created, below method is run
    public void init() {
        appCacheMap = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all) {
            appCacheMap.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

    //    @Scheduled(cron = "0 0 */10 * * ?")
    public void clearAppCache() {
        init();
    }
}
