package org.example.dragonservice;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class MyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(org.example.dragonservice.resource.DragonResource.class);
        resources.add(org.example.dragonservice.filter.CORSFilter.class);
        return resources;
    }
}
