package com.jbpi.mylibgdxgame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Matrix4
import com.jbpi.mylibgdxgame.DropGame

class MainMenuScreen constructor(game: DropGame): Screen  {

    private val dropGame = game
    private val orthographicCamera: OrthographicCamera = OrthographicCamera()

    init {

        orthographicCamera.setToOrtho(false, 800.0f, 480.0f)
    }

    override fun render(delta: Float) {

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        orthographicCamera.update()

        dropGame.spriteBatch.projectionMatrix = Matrix4(orthographicCamera.combined)
        dropGame.spriteBatch.begin()
        dropGame.bitmapFont.draw(dropGame.spriteBatch, "Welcome to Drop!!!", 100f, 150f)
        dropGame.bitmapFont.draw(dropGame.spriteBatch, "Tap anywhere to begin!", 100f, 100f)
        dropGame.spriteBatch.end()

        if (Gdx.input.isTouched) {
            dropGame.screen = GameScreen(dropGame)
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