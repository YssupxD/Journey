package com.massey.journey.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.massey.journey.Journey;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    public static Integer daggerCounter;
    public static float timeCount;
    public static Integer hpCounter;

    Label countdownLabel;
    Label lifepointLabel;
    Label daggerLabel;
    Label levelLabel;
    Label worldLabel;
    Label ginoLabel;

    public Hud(SpriteBatch sb) {
        daggerCounter = 0;
        timeCount = 0;
        hpCounter = 100;

        viewport = new FitViewport(Journey.SCREEN_WIDTH, Journey.SCREEN_HEIGHT, new OrthographicCamera());
        //
        stage = new Stage(viewport, sb);

        //
        Table table = new Table();
        table.top();
        //table is now size of the stage;
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%02d", daggerCounter),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lifepointLabel = new Label("HP " + String.format("%03d", hpCounter),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        daggerLabel = new Label("Dagger", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ginoLabel = new Label("GINO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(ginoLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(daggerLabel).expandX().padTop(10);
        table.row();
        table.add(lifepointLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel). expandX();

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
