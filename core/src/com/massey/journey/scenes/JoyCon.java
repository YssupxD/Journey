package com.massey.journey.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Disposable;

public class JoyCon implements Disposable {
    public Stage stage;
    public Touchpad tPad;
    public OrthographicCamera cam;




    @Override
    public void dispose() {

    }
}
