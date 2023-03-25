package tk.ivision.core.global;

import java.net.URL;

public class Resource {

    public static URL getImage(String image){
        return Resource.class.getClassLoader().getResource("assets/" + image);
    }

    public static URL getConfig(String file){
        return Resource.class.getClassLoader().getResource("config/" + file);
    }

}
