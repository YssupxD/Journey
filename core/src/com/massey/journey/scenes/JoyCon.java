package com.massey.journey.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class JoyCon implements Disposable {
    public Stage stage;
    public OrthographicCamera cam;
    private Viewport vp;
    boolean isPressJump, isPressLeft, isPressRight, isPressThrow;

    public JoyCon(SpriteBatch spriteBatch) {
        cam = new OrthographicCamera();
        vp = new FitViewport(800, 400, cam);
        stage = new Stage(vp, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();

        //Image left = new Image(new Texture());
    }


    @Override
    public void dispose() {

    }
}
