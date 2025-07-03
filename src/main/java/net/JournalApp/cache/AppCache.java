package net.JournalApp.cache;

import net.JournalApp.entity.ConfigJournalAppEntity;
import net.JournalApp.repository.ConfigJurnalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API
    }

    @Autowired
    ConfigJurnalAppRepository configJurnalAppRepository;


    public Map<String,String> appCacheMap;

    @PostConstruct
    public void init(){
        appCacheMap=new HashMap<>();
        List<ConfigJournalAppEntity>all=configJurnalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity:all){
            appCacheMap.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }

//    @Scheduled(cron = "0 0 */10 * * ?")
    public void clearAppCache(){
        init();
    }
}
