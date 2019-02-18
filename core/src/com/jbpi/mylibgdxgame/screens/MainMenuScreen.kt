package com.jbpi.mylibgdxgame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Matrix4
import com.jbpi.mylibgdxgame.DropGame

class MainMenuScreen constructor(private val game: DropGame): Screen  {

    private val orthographicCamera: OrthographicCamera = OrthographicCamera()

    init {

        orthographicCamera.setToOrtho(false, 800.0f, 480.0f)
    }

    override fun render(delta: Float) {

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        orthographicCamera.update()

        game.spriteBatch.projectionMatrix = orthographicCamera.combined
        game.spriteBatch.begin()
        game.bitmapFont.draw(game.spriteBatch, "Welcome to Drop!!!", 100f, 150f)
        game.bitmapFont.draw(game.spriteBatch, "Tap anywhere to begin!", 100f, 100f)
        game.spriteBatch.end()

        if (Gdx.input.isTouched) {
            game.changeScreenToGame()
            dispose()
        }
    }

    override fun show() {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {

    }

    override fun hide() {

    }
}