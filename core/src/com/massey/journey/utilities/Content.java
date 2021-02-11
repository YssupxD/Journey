package com.massey.journey.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class Content {
    private HashMap<String, Texture> textures;
    private HashMap<String, Music> music;
    private HashMap<String, Sound> sounds;

    public Content() {
        textures = new HashMap<String, Texture>();
        music = new HashMap<String, Music>();
        sounds = new HashMap<String, Sound>();

    }
    //image texture

    public void loadTexture(String path) {
        int slashIndex = path.lastIndexOf('/');
        String key;
        if(slashIndex == -1){
            key = path.substring(0, path.lastIndexOf('.'));
        }
        else {
            key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
        }
        loadTexture(path, key);
    }
    public void loadTexture(String path, String key) {
        Texture texture = new Texture(Gdx.files.internal(path));
        textures.put(key, texture);
    }
    public Texture getTexture(String key) {
        return textures.get(key);
    }

    //Loading Music
    public void loadMusic(String path) {
        int slashIndex = path.lastIndexOf('/');
        String key;
        if(slashIndex == -1){
            key = path.substring(0, path.lastIndexOf('.'));
        }
        else {
            key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
        }
        loadMusic(path, key);
    }

    public void loadMusic(String path, String key) {
        Music m = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.put(key, m);
    }

    public Music getMusic(String key) {
        return music.get(key);
    }

    //loading sfx
    public void loadSoundEffect(String path) {
        int slashIndex = path.lastIndexOf('/');
        String key;
        if(slashIndex == -1){
            key = path.substring(0, path.lastIndexOf('.'));
        }
        else {
            key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
        }
        loadSoundEffect(path, key);
    }

    public void loadSoundEffect(String path, String key) {
        Sound s = Gdx.audio.newSound(Gdx.files.internal(path));
        sounds.put(key, s);
    }

    public Sound getSound(String key) {
        return sounds.get(key);
    }

    //dispose function
    public void removeAll() {
        for(Object o : textures.values()) {
            Texture tex = (Texture) o;
            tex.dispose();
        }
        textures.clear();
        for(Object o : music.values()) {
            Music music = (Music) o;
            music.dispose();
        }
        music.clear();
        for(Object o : sounds.values()) {
            Sound s = (Sound) o;
            s.dispose();
        }
        sounds.clear();
    }
}
