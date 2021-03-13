package com.example.asteroidsproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen implements Screen {

    SpriteBatch batch;
    Texture img;

    private TextureAtlas textureAtlas;

    TextureRegion testText;
    GameScreen(){
        batch = new SpriteBatch();
       // img = new Texture(Gdx.files.internal("STUFFUSED/enemy_A.png"));
        textureAtlas = new TextureAtlas(Gdx.files.internal("STUFFUSED/images.atlas"));
        testText =textureAtlas.findRegion("Starscape00");
    }




    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //batch.draw(img, 250, 250);
        batch.draw(testText,0,0);
        batch.end();
    }


    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        //img.dispose();
    }
}
