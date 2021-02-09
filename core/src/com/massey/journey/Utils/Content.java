package com.massey.journey.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class Content {
    private HashMap<String, Texture> textures;

    public Content() {
        textures = new HashMap<String, Texture>();
    }

    public void loadTexture(String path, String key) {
        Texture texture = new Texture(Gdx.files.internal(path));
        textures.put(key, texture);
    }

    public Texture getTexture(String key) {
        return textures.get(key);
    }

    public void disposeTexture(String key) {
        Texture texture = textures.get(key);
        if(texture != null) {
            texture.dispose();
        }
    }
}
