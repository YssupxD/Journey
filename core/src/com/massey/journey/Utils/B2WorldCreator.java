package com.massey.journey.Utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.massey.journey.Journey;

public class B2WorldCreator {
    private int level1ID = 7;
    private int level2ID = 13;
    public B2WorldCreator(World world, TiledMap map) {
        //Create body and fixture variables
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //create ground fixtures
        for(MapObject object :
                map.getLayers().get(level1ID).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Journey.PPM, (rect.getY() + rect.getHeight() / 2) / Journey.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth() / 2 / Journey.PPM, rect.getHeight() / 2 / Journey.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
    }
}
