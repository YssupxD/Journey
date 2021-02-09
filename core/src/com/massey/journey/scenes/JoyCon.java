package com.massey.journey.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.massey.journey.Journey;

import javax.print.attribute.standard.JobOriginatingUserName;

public class JoyCon implements Disposable {
    public Stage stage;
    public OrthographicCamera cam;
    private Viewport vp;
    boolean PressedJump, PressedLeft, PressedRight, PressedThrow;

    public JoyCon(SpriteBatch spriteBatch) {
        cam = new OrthographicCamera();
        vp = new FitViewport(800, 400, cam);
        stage = new Stage(vp, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();

        Image left = new Image(new Texture("btnLeft.png"));
        left.setSize(50, 50);
        left.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PressedLeft = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PressedLeft = false;
            }
        });

        Image right = new Image(new Texture("btnRight.png"));
        right.setSize(50, 50);
        right.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PressedRight = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PressedRight = false;
            }
        });

        Image jump = new Image(new Texture("btnJump.png"));
        jump.setSize(50, 50);
        jump.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PressedJump = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PressedJump = false;
            }
        });

        Image actionThrow = new Image(new Texture("btnAction.png"));
        actionThrow.setSize(50, 50);
        actionThrow.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PressedThrow = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PressedThrow = false;
            }
        });
        table.bottom().left();
        table.row();
        table.add(left).size(left.getWidth(), left.getHeight()).padLeft(20).padRight(15).padBottom(10);
        table.add(right).size(right.getWidth(), right.getHeight()).padRight(265).padBottom(10);
        table.add(jump).size(jump.getWidth(), jump.getHeight()).padLeft(265).padBottom(10);
        table.add(actionThrow).size(actionThrow.getWidth(), actionThrow.getHeight()).padLeft(15).padBottom(50);
        stage.addActor(table);
    }

    public JoyCon() {
        cam = new OrthographicCamera();
        vp = new FitViewport(800, 400, cam);
    }

    public boolean isPressLeft() {
        return PressedLeft;
    }
    public boolean isPressRight() {
        return PressedRight;
    }
    public boolean isPressJump() {
        return PressedJump;
    }
    public boolean isPressThrow() {
        return PressedThrow;
    }

    public void resize(int width, int height) {
        vp.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
