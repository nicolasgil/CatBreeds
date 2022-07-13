package com.app.CatBreeds.model;

import com.google.gson.JsonObject;

public class ModelLanding {

    private final String id;
    private final String name;
    private final String origin;
    private final String description;
    private final String intelligence;
    private final JsonObject image;

    public ModelLanding(String id, String name, String origin, String description, String intelligence, JsonObject image) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.description = description;
        this.intelligence = intelligence;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDescription() {
        return description;
    }

    public String getIntelligence() {
        return intelligence;
    }

    public JsonObject getImage() {
        return image;
    }

}
