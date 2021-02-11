package com.massey.journey.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.massey.journey.main.Journey;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    public Integer diamondCounter;
    public Integer hpCounter;

    private Label countdownLabel;
    private Label hpLabel;
    private Label diamondLabel;
    private Label levelLabel;
    private Label worldLabel;

    Label ginoLabel;

    public Hud(SpriteBatch sb) {
        diamondCounter = 0;
        hpCounter = 100;

        viewport = new FitViewport(Journey.WORLD_WIDTH, Journey.WORLD_HEIGHT,
                new OrthographicCamera());
        //
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        //table is now size of the stage;
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%02d", diamondCounter),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        hpLabel = new Label("HP " + String.format("%03d", hpCounter),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        diamondLabel = new Label("Diamond", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ginoLabel = new Label("GINO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(ginoLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(diamondLabel).expandX().padTop(10);
        table.row();
        table.add(hpLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }
    public void addDiamond(int value) {
        diamondCounter += value;
        diamondLabel.setText(String.format("%02d", diamondCounter));
    }

    public void loseHP(int value) {
        hpCounter -= value;
        hpLabel.setText("HP " + String.format("%03d", hpCounter));
    }
}

