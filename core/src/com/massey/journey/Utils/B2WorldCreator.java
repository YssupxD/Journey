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
import com.massey.journey.sprites.Dagger;

import static com.massey.journey.Utils.Box2dVariables.PPM;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map, int levelID) {
        //Create body and fixture variables
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object :
                map.getLayers().get(levelID).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Box2dVariables.BIT_GROUND;
            fixtureDef.filter.maskBits = Box2dVariables.BIT_PLAYER | Box2dVariables.BIT_ENEMY;
            body.createFixture(fixtureDef);
            body.setUserData("Ground");
        }
    }
}
